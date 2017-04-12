package com.yunyouzhiyuan.qianbaoshangchengclient.fragment_dingdan;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.DianDanAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Dingdan;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.DingDanModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.ListViewListener;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by 王俊强 on 2016/11/1.
 */
public class ReFundFragment extends BaseFragment {
    private ExpandableListView dingdanElistview;
    private SwipeRefreshLayout dingdanLayout;
    private View view;
    private DianDanAdapter adapter;
    private DingDanModel model;
    private int page = 0;
    private List<Dingdan.DataBean.OrderListBean> list = new ArrayList<>();
    private boolean loading = false;//加载是否返回

    @Override
    public void onDestroy() {
        list.clear();
        super.onDestroy();
    }

    public ReFundFragment() {
    }

    public static ReFundFragment newInstance() {
        ReFundFragment fragment = new ReFundFragment();
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
        Call date = model.getReFund(App.getUserId(), String.valueOf(page), new IModel.AsyncCallBack() {
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
                    if (page == 0 && list != null && dingdanLayout != null) {
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
    private void setadapter() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            adapter = new DianDanAdapter(getActivity(), list);
            dingdanElistview.setAdapter(adapter);
        }
        for (int i = 0; i < list.size(); i++) {
            dingdanElistview.expandGroup(i);
        }
    }

}
