package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.YueAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Yuee;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.YueModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.DividerItemDecoration;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.TitleLayout;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.Dialogwithdrawals;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.RecyleViewButtom;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.Text_Size;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class YueeActivity extends BaseActivity {

    @Bind(R.id.yue_title)
    TitleLayout yueTitle;
    @Bind(R.id.yuee_tvnumber)
    TextView yueeTvnumber;
    //    @Bind(R.id.yue_btnchongzhi)
//    Button yueBtnchongzhi;
    @Bind(R.id.yue_btntixian)
    Button yueBtntixian;
    @Bind(R.id.yue_appbar)
    AppBarLayout yueAppbar;
    @Bind(R.id.yue_recyleview)
    RecyclerView yueRecyleview;
    @Bind(R.id.yue_layout)
    SwipeRefreshLayout yueLayout;
    private YueAdapter adapter;
    private YueModel model;
    private int page = 0;
    private Yuee yuee;
    private List<Yuee.DataBean.ConsumeListBean> list = new ArrayList<>();

    @Override
    protected void onDestroy() {
        adapter = null;
        model = null;
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yuee);
        ButterKnife.bind(this);
        init();
        setListener();
        getData();
    }

    /**
     * 获取数据
     */
    private void getData() {
        yueLayout.setRefreshing(true);
        Call data = model.getData(App.getUserId(), page, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(final Object obj) {
                if (isFinishing()) {
                    return;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        yuee = (Yuee) obj;
                        if (page == 0) {
                            list.clear();
                        }
                        list.addAll(yuee.getData().getConsume_list());
                        setView();
                        yueLayout.setRefreshing(false);
                        page++;
                    }
                });
            }

            @Override
            public void onError(final Object obj) {
                if (isFinishing()) {
                    return;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        To.oo(obj);
                        yueLayout.setRefreshing(false);
                    }
                });
            }
        });
        setCall(data);
    }

    /**
     * 监听器
     */
    private void setListener() {
        yueLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 0;
                getData();
            }
        });
        yueAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                yueLayout.setEnabled(verticalOffset >= 0);
            }
        });
    }

    /**
     * 写入信息
     */
    private void setView() {
        String str = "￥" + yuee.getData().getUser_money() + "（元）";
        Text_Size.setSize(this, yueeTvnumber, str, 0, str.length() - 3, "#ffffff", 30, str.length() - 3, str.length(), "#ffffff", 18);
        setAdapter();
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            adapter = new YueAdapter(this, list);
            yueRecyleview.setAdapter(adapter);
            yueRecyleview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            yueRecyleview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
            yueRecyleview.setItemAnimator(new DefaultItemAnimator());
            yueRecyleview.addOnScrollListener(new RecyleViewButtom(new RecyleViewButtom.Listener() {
                @Override
                public void last(int position) {
                    getData();
                }
            }));
        }
    }

    /**
     * 初始化操作
     */
    private void init() {
        yueLayout.setColorSchemeResources(R.color.app_color);
        yueLayout.setProgressBackgroundColorSchemeResource(R.color.white);
        yueTitle.setTitle(getString(R.string.myyue), true);
        yueTitle.setCallback(new TitleLayout.Callback(this));
        model = new YueModel();
    }

    @OnClick(R.id.yue_btntixian)
    public void onClick() {//提现
        if (yuee != null) {
            String user_money = yuee.getData().getUser_money();
            if (!TextUtils.isEmpty(user_money)) {
                new Dialogwithdrawals(this, new Dialogwithdrawals.CallBack() {
                    @Override
                    public void onSuccess() {
                        page = 0;
                        getData();
                    }
                }, user_money, model).show();
            }
        }

    }
}
