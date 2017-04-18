package com.yunyouzhiyuan.qianbaoshangchengclient.fragment_dingdan;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.pingplusplus.android.Pingpp;
import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.PingstorActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.DianDanAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Dingdan;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.DingDanModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.LoadingDialog;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.PingDialog;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.ListViewListener;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.Utils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

import static com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean.DINGDANWANCHENG;

/**
 * Created by 王俊强 on 2016/11/1.
 */
public class AllFragment extends BaseFragment {
    private ExpandableListView dingdanElistview;
    private SwipeRefreshLayout dingdanLayout;
    private View view;
    private DianDanAdapter adapter;
    private DingDanModel model;
    private int page = 0;
    private int type = 0;//（0全部  1待支付  2进行中 3已完成）
    private List<Dingdan.DataBean.OrderListBean> list = new ArrayList<>();
    private boolean loading = false;//加载是否返回
    private LoadingDialog loadingDialog;

    @Override
    public void onDestroy() {
        list.clear();
        super.onDestroy();
    }

    public AllFragment() {
    }

    public static AllFragment newInstance() {
        AllFragment fragment = new AllFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_all, container, false);
            init();
            setListener();
            setIsok(true);
            lazyLoad();
        }
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(view);
        }
        return view;
    }

    /**
     * 监听器
     */
    private void setListener() {
        dingdanLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(true);
            }
        });
        dingdanElistview.setOnScrollListener(new ListViewListener() {
            @Override
            public void bottom() {
                if (loading) {
                    loading = false;
                    getData(false);
                }
            }
        });
        dingdanElistview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });


    }

    /**
     * 初始化
     */
    private void init() {
        dingdanElistview = (ExpandableListView) view.findViewById(R.id.dingdan_elistview);
        dingdanLayout = (SwipeRefreshLayout) view.findViewById(R.id.dingdan_layout);
        dingdanLayout.setColorSchemeResources(R.color.white);
        dingdanLayout.setProgressBackgroundColorSchemeResource(R.color.app_color);
        model = new DingDanModel();
    }


    @Override
    protected void lazyLoad() {
        if (!dingdanLayout.isRefreshing()) {
            dingdanLayout.setRefreshing(true);
        }
        getData(true);
    }

    /**
     * 获取数据
     */
    private void getData(final boolean isTop) {
        if (isTop) {
            page = 0;
        }
        Call date = model.getDate(App.getUserId(), page, type, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (getContext() != null) {
                    if (isTop) {
                        list.clear();
                    }
                    list.addAll(((Dingdan) obj).getData().getOrder_list());
                    setadapter();
                    dingdanLayout.setRefreshing(false);
                    page++;
                    loading = true;
                }
            }

            @Override
            public void onError(Object obj) {
                if (getContext() != null) {
                    To.oo(obj);
                    if (isTop && list != null) {
                        list.clear();
                        setadapter();
                    } else {
                        TextView textview = (TextView) dingdanElistview.findViewWithTag("textview");
                        if (textview != null) {
                            textview.setText("已经到底了");
                        }
                    }
                    dingdanLayout.setRefreshing(false);
                }
            }
        });
        setCalls(date);

    }

    /**
     * 设置适配器
     */
    private void setadapter() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            adapter = new DianDanAdapter(getActivity(), list);
            dingdanElistview.setAdapter(adapter);
            adapter.setCallback(new DianDanAdapter.Callback() {
                @Override
                public void onClickPingjia(String store_id, String order_id) {
                    Intent intent = new Intent(getActivity(), PingstorActivity.class);
                    intent.putExtra("storid", store_id);
                    intent.putExtra("ordson_id", order_id);
                    startActivityForResult(intent, DINGDANWANCHENG);
                }

                @Override
                public void onPayment(int position) {
                    new PingDialog(getActivity(), list.get(position).getOrder_amount(),
                            list.get(position).getOrder_sn(), new PingDialog.Callback() {
                        @Override
                        public void onSuccess(String obj) {
                            toZhifu(obj);
                        }
                    }).show();
                }

                @Override
                public void deleteReFund(String order_id, String order_sn, String goods_id, String storid) {
                    showDeleteReFund(order_id, order_sn, goods_id, storid);

                }
            });
        }
        for (int i = 0; i < list.size(); i++) {
            dingdanElistview.expandGroup(i);
        }
    }

    /**
     * 显示 退款对话框
     *
     * @param order_id
     * @param order_sn
     * @param goods_id
     */
    private void showDeleteReFund(final String order_id, final String order_sn,
                                  final String goods_id, final String storid) {
        final EditText editText = new EditText(getActivity());
        editText.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_color));
        editText.setTextSize(13);
        editText.setPadding(Utils.dip2px(App.getContext(), 10), Utils.dip2px(App.getContext(), 10),
                Utils.dip2px(App.getContext(), 10), Utils.dip2px(App.getContext(), 10));
        editText.setHint(R.string.ninkeyishuru);
        editText.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.text_color_alp));
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.shentingtuikuan)
                .setView(editText)
                .setIcon(ContextCompat.getDrawable(getActivity(), R.mipmap.ic_launcher))
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (loadingDialog == null) {
                            loadingDialog = new LoadingDialog(getContext());
                        }
                        loadingDialog.show();
                        model.deleteReFund(App.getUserId(), order_id, order_sn, goods_id,
                                editText.getText().toString().trim(), null, storid, new IModel.AsyncCallBack() {
                                    @Override
                                    public void onSucceed(Object obj) {
                                        if (getContext() == null) {
                                            return;
                                        }
                                        To.dd(obj);
                                        lazyLoad();
                                        loadingDialog.dismiss();
                                    }

                                    @Override
                                    public void onError(Object obj) {
                                        if (getContext() == null) {
                                            return;
                                        }
                                        To.ee(obj);
                                        loadingDialog.dismiss();
                                    }
                                });
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    /**
     * 去支付
     *
     * @param obj
     */
    private void toZhifu(String obj) {
        Pingpp.createPayment(this, obj);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //支付页面返回处理
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                boolean isfinsh = false;
                String title = null;
                String result = data.getExtras().getString("pay_result");
                if ("success".equals(result)) {
                    title = "支付成功";
                    isfinsh = true;
                } else if ("fail".equals(result)) {
                    title = "支付失败";
                } else if ("cancel".equals(result)) {
                    title = "取消支付";
                } else if ("invalid".equals(result)) {
                    title = "支付插件未安装（微信客户端是否安装？）";
                }
                // 处理返回值
                // "success" - 支付成功
                // "fail"    - 支付失败
                // "cancel"  - 取消支付
                // "invalid" - 支付插件未安装（一般是微信客户端未安装的情况）
                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                showMsg(title, errorMsg, isfinsh);
            }
        }
    }

    /**
     * 显示 支付返回 对话框
     *
     * @param result
     * @param extraMsg
     * @param isfinsh
     */
    private void showMsg(String result, String extraMsg, final boolean isfinsh) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (isfinsh) {
                    lazyLoad();
                }
                dialog.dismiss();
            }
        });
        builder.setTitle("黔宝商城提醒您：");
        builder.setMessage(isfinsh ? result : "" + result);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }
}
