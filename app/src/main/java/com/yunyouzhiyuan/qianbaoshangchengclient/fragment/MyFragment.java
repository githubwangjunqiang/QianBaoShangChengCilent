package com.yunyouzhiyuan.qianbaoshangchengclient.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.DingDanActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.EvaluateListActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.LoginActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.SheZhiActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.ShouCangActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.WebViewActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.WeiBoShareActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.YouhuiquanActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.YueeActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.UserInfo;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.MyModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.LoadingDialog;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.scrllview.PullToZoomBase;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.scrllview.PullToZoomScrollViewEx;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.ActivityCollector;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.LogUtils;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

import static com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean.LOGIN;
import static com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean.SHEZHI;

/**
 */
public class MyFragment extends MainFragment {
    @Bind(R.id.fragment_my_scview)
    PullToZoomScrollViewEx scrollViewEx;
    private View view;

    private View headView;
    private View zoomView;
    private View contentView;
    private TextView tvtopUsername;//顶部登录按钮
    private TextView tvpingjai;//评价按钮
    private TextView tvShouCang;//收藏按钮
    private Button tvYuee;//我的余额
    private Button btnyouhuiquan;//我的余额
    private Button btnkefu;//客服
    private ImageView ivphoto;//收藏按钮
    private TextView tvAlldingdan;//全部订单
    private TextView tvdaifukuan;//待付款订单
    private TextView tvdaishiyong;//待试用订单
    private TextView tvdaipingjia;//待评价订单
    private TextView tvyiwancheng;//已完成订单
    private boolean isRefresh = false;//拉动zoomview 是否进行刷新
    private ProgressBar progressBar;//头部的
    private LoadingDialog loadingDialog;
    private MyModel model;
    private UserInfo.DataBean data;
    private Button btnRuzhu;
    private Button btnGuanyu;
    private Button btnOut;
    private Button btnFenxiang;//分享

    public MyFragment() {
        // Required empty public constructor
    }

    /**
     */
    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_my, container, false);
            ButterKnife.bind(this, view);
            initScllView();
            init();
            setListener();
        }
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(view);
        }

        return view;
    }

    /**
     * 初始化一些操作
     */
    private void init() {
        loadingDialog = new LoadingDialog(getActivity());
        model = new MyModel();
        reFresh();
    }

    /**
     * 监听
     */
    private void setListener() {
        tvtopUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//点击头像昵称
                if (App.getUserId() != null && data != null) {//去设置界面
                    Intent intent = new Intent(getActivity(), SheZhiActivity.class);
                    intent.putExtra("userinfo", data);
                    startActivityForResult(intent, Bean.SHEZHI);
                    return;
                }
                startActivityForResult(new Intent(getActivity(), LoginActivity.class), LOGIN);//去登陆
            }
        });
        tvpingjai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(App.getUserId())) {
                    To.oo("您还没登陆！");
                    return;
                }
                startActivity(new Intent(getActivity(), EvaluateListActivity.class));//去评价界面
            }
        });
        tvShouCang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(App.getUserId())) {
                    To.oo("您还没登陆！");
                    return;
                }
                startActivity(new Intent(getActivity(), ShouCangActivity.class));//去收藏界面
            }
        });
        ivphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(App.getUserId()) || data == null) {
                    To.oo("您还没登陆！");
                    return;
                }
                Intent intent = new Intent(getActivity(), SheZhiActivity.class);
                intent.putExtra("userinfo", data);
                startActivityForResult(intent, Bean.SHEZHI); //点击头像去修改界面
            }
        });
        tvAlldingdan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(App.getUserId())) {
                    To.oo("您还没登陆！");
                    return;
                }
                DingDanActivity.startDingdanActivity(getActivity(), 0);//去订单界面
            }
        });
        tvdaifukuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(App.getUserId())) {
                    To.oo("您还没登陆！");
                    return;
                }
                DingDanActivity.startDingdanActivity(getActivity(), 1);//去订单界面
            }
        });
        tvdaishiyong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(App.getUserId())) {
                    To.oo("您还没登陆！");
                    return;
                }
                DingDanActivity.startDingdanActivity(getActivity(), 2);//去订单界面
            }
        });
        tvdaipingjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(App.getUserId())) {
                    To.oo("您还没登陆！");
                    return;
                }
                DingDanActivity.startDingdanActivity(getActivity(), 4);//去订单界面
            }
        });
        tvyiwancheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(App.getUserId())) {
                    To.oo("您还没登陆！");
                    return;
                }
                DingDanActivity.startDingdanActivity(getActivity(), 3);//去订单界面
            }
        });
        tvYuee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(App.getUserId())) {
                    To.oo("您还没登陆！");
                    return;
                }
                startActivity(new Intent(getActivity(), YueeActivity.class));//我的余额
            }
        });
        btnyouhuiquan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(App.getUserId())) {
                    To.oo("您还没登陆！");
                    return;
                }
                startActivity(new Intent(getActivity(), YouhuiquanActivity.class));//优惠券
            }
        });
        btnkefu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getKefu();
            }
        });//客服电话
        btnRuzhu.setOnClickListener(new View.OnClickListener() {//入驻商家
            @Override
            public void onClick(View v) {
                startAPP(Bean.APPPAKEGER);
            }
        });
        scrollViewEx.setOnPullZoomListener(new PullToZoomBase.OnPullZoomListener() {
            @Override
            public void onPullZooming(int newScrollValue) {
                if (progressBar.getVisibility() == View.GONE) {
                    progressBar.setVisibility(View.VISIBLE);
                }
                if (newScrollValue / 2 < -150) {
                    if (!isRefresh) {
                        isRefresh = true;
                    }
                }
            }

            @Override
            public void onPullZoomEnd() {
                if (isRefresh) {
                    isRefresh = !isRefresh;
                    reFresh();
                } else {
                    hideProgress();
                }

            }
        });
        btnGuanyu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.startWebViewActivity(getActivity(), HTTPURL.netinfo, getString(R.string.guanyuqianbao));
            }
        });
        btnOut.setOnClickListener(new View.OnClickListener() {//退出程序
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(getString(R.string.quedingyaotui));
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCollector.finishAll();
                    }
                });
                builder.setNegativeButton(R.string.quxiao, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });
        btnFenxiang.setOnClickListener(new View.OnClickListener() {//分享
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), WeiBoShareActivity.class));
                getActivity().overridePendingTransition(R.anim.tr_in,0);
            }
        });
    }

    /**
     * 启动一个app
     */
    public void startAPP(String appPackageName) {
        try {
            Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage(appPackageName);
            startActivity(intent);
        } catch (Exception e) {
            WebViewActivity.startWebViewActivity(getActivity(), Bean.APPURL, "商家端");
        }
    }

    /**
     * 获取客服电话
     */
    private void getKefu() {
        loadingDialog.show();
        setCalls(model.getKeFu(new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (getContext() == null) {
                    return;
                }
                showkefu((List<String>) obj);
                loadingDialog.dismiss();
            }

            @Override
            public void onError(Object obj) {
                if (getContext() == null) {
                    return;
                }
                To.oo(obj);
                loadingDialog.dismiss();
            }
        }));
    }

    /**
     * 刷新方法
     */
    private void reFresh() {
        String userId = App.getUserId();
        if (TextUtils.isEmpty(userId)) {
            hideProgress();
            return;
        }
        Call call = model.getUserInfo(userId, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (getContext() == null) {
                    return;
                }
                data = (UserInfo.DataBean) obj;
                setView(data.getHead_pic(), data.getNickname());
                hideProgress();
            }

            @Override
            public void onError(Object obj) {
                if (getContext() == null) {
                    return;
                }
                To.oo(obj);
                hideProgress();
            }
        });
        setCalls(call);
    }

    /**
     * 写入顶部信息
     *
     * @param head_pic
     * @param nickname
     */
    private void setView(String head_pic, String nickname) {
        ToGlide.urlCircle(this, HTTPURL.IMAGE + head_pic, ivphoto);
        tvtopUsername.setText(nickname == null ? "点击登陆" : nickname);
    }

    /**
     * 让进度条隐藏
     */
    private void hideProgress() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Bean.LOGIN://登陆返回
                if (resultCode == LOGIN) {
                    reFresh();
                }
                break;
            case SHEZHI://设置返回
                if (resultCode == SHEZHI) {
                    if (data != null) {
                        this.data = (UserInfo.DataBean) data.getSerializableExtra("userinfo");
                        setView(this.data.getHead_pic(), this.data.getNickname());
                    } else {//退出登录
                        this.data = null;
                        setView(null, null);
                        startActivityForResult(new Intent(getActivity(), LoginActivity.class), LOGIN);//去登陆
                    }
                }
                break;
        }
    }

    /**
     * 显示客服电话对话框
     */
    private void showkefu(final List<String> list) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_kefu, null);
        ListView listView = (ListView) view.findViewById(R.id.dialog_kefu_listview);
        listView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.e(position + "");
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + list.get(position)));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        builder.setView(view);
        builder.show();

    }

    /**
     * 设置scview 的布局
     */
    private void initScllView() {
        headView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_my_head_view, null, false);
        zoomView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_my_zoom_view, null, false);
        contentView = LayoutInflater.from(getActivity()).inflate(R.layout.fragmnet_my_content, null, false);
        scrollViewEx.setHeaderView(headView);
        scrollViewEx.setZoomView(zoomView);
        scrollViewEx.setScrollContentView(contentView);
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int mScreenHeight = localDisplayMetrics.heightPixels;
        int mScreenWidth = localDisplayMetrics.widthPixels;
        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth, (int) (9.0F * (mScreenWidth / 16.0F)));
        scrollViewEx.setHeaderLayoutParams(localObject);

        tvtopUsername = (TextView) headView.findViewById(R.id.tv_user_name);
        progressBar = (ProgressBar) headView.findViewById(R.id.progressBar);
        tvpingjai = (TextView) headView.findViewById(R.id.head_tv_pingjia);
        tvShouCang = (TextView) headView.findViewById(R.id.head_tvshoucang);
        ivphoto = (ImageView) headView.findViewById(R.id.iv_user_head);
        tvAlldingdan = (TextView) contentView.findViewById(R.id.fragment_my_content_tvmydingdan);
        tvdaifukuan = (TextView) contentView.findViewById(R.id.fragment_my_content_tvdaifukuan);
        tvYuee = (Button) contentView.findViewById(R.id.fragment_my_content_tvyuee);
        btnyouhuiquan = (Button) contentView.findViewById(R.id.fragment_my_content_btnyouhuiquan);
        btnRuzhu = (Button) contentView.findViewById(R.id.btnruzhushanjgia);
        btnFenxiang = (Button) contentView.findViewById(R.id.fragment_my_content_btnshare);
        btnGuanyu = (Button) contentView.findViewById(R.id.fragment_my_content_btnguanyu);
        btnOut = (Button) contentView.findViewById(R.id.fragment_my_content_btnout);
        btnkefu = (Button) contentView.findViewById(R.id.fragment_my_content_btnkefu);
        tvdaishiyong = (TextView) contentView.findViewById(R.id.fragment_my_content_tvdaishiyong);
        tvdaipingjia = (TextView) contentView.findViewById(R.id.fragment_my_content_tvdaipingjia);
        tvyiwancheng = (TextView) contentView.findViewById(R.id.fragment_my_content_tvyiwancheng);
    }


    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    @Override
    public void upLocation(BDLocation location) {

    }

    @Override
    public void upCity() {

    }
}