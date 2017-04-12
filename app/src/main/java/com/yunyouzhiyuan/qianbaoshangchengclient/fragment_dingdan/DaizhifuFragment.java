package com.yunyouzhiyuan.qianbaoshangchengclient.fragment_dingdan;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.pingplusplus.android.Pingpp;
import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.DianDanAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Dingdan;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.DingDanModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.PingDialog;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.ListViewListener;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by 王俊强 on 2016/11/1.
 */
public class DaizhifuFragment extends BaseFragment {
    private ExpandableListView dingdanElistview;
    private SwipeRefreshLayout dingdanLayout;
    private View view;
    private DianDanAdapter adapter;
    private DingDanModel model;
    private int page = 0;
    private int type = 1;//（0全部  1待支付  2进行中 3已完成）
    private List<Dingdan.DataBean.OrderListBean> list = new ArrayList<>();
    private boolean loading = false;//加载是否返回

    @Override
    public void onDestroy() {
        list.clear();
        list = null;
        super.onDestroy();

    }

    public DaizhifuFragment() {
    }

    public static DaizhifuFragment newInstance() {
        DaizhifuFragment fragment = new DaizhifuFragment();
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
            ButterKnife.bind(this, view);
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
                page = 0;
                getData();
            }
        });
        dingdanElistview.setOnScrollListener(new ListViewListener() {
            @Override
            public void bottom() {
                if (loading) {
                    loading = false;
                    getData();
                }
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
        page = 0;
        getData();
    }

    /**
     * 获取数据
     */
    private void getData() {
        if (dingdanLayout.isRefreshing()) {
            dingdanLayout.setRefreshing(true);
        }
        Call date = model.getDate(App.getUserId(), page, type, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if(getContext() != null){
                    if (page == 0) {
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
                if(getContext() != null){
                    To.oo(obj);
                    if (page == 0 ) {
                        list.clear();
                        setadapter();
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

                }
            });
        }
        for (int i = 0; i < list.size(); i++) {
            dingdanElistview.expandGroup(i);
        }
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
