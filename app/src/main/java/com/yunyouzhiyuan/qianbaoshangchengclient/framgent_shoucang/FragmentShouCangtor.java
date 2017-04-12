package com.yunyouzhiyuan.qianbaoshangchengclient.framgent_shoucang;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.ShouCangStorAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.ShouCang;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.ShouCangModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.DividerItemDecoration;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

public class FragmentShouCangtor extends BaseShouCangFragment {
    @Bind(R.id.shoucang_shop_recyleview)
    RecyclerView shoucangShopRecyleview;
    @Bind(R.id.shoucang_shop_layout)
    SwipeRefreshLayout shoucangShopLayout;
    private View view;
    private ShouCangStorAdapter adapter;
    private ShouCangModel model;
    private List<ShouCang.DataBean> list = new ArrayList<>();


    public FragmentShouCangtor() {
        // Required empty public constructor
    }

    public static FragmentShouCangtor newInstance(String param1) {
        return new FragmentShouCangtor();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_fragmentshoucangshopp_list, container, false);
            ButterKnife.bind(this, view);
            init();
            setListener();
            setadapter();
            getData();
        }
        return view;
    }

    /**
     * 监听器
     */
    private void setListener() {
        shoucangShopLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
    }
    /**
     * 获取信息
     */
    private void getData() {
        shoucangShopLayout.setRefreshing(true);
        Call data = model.getData(App.getUserId(), 2, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (getContext() == null) {
                    return;
                }
                list.clear();
                list.addAll(((ShouCang) obj).getData());
                setadapter();
                shoucangShopLayout.setRefreshing(false);
            }
            @Override
            public void onError(Object obj) {
                if (getContext() == null) {
                    return;
                }
                To.oo(obj);
                list.clear();
                setadapter();
                shoucangShopLayout.setRefreshing(false);
            }
        });
        listener.setFragmentCall(data);
    }

    /**
     * 设置适配器
     */
    private void setadapter() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            adapter = new ShouCangStorAdapter(getActivity(), list);
            shoucangShopRecyleview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            shoucangShopRecyleview.setAdapter(adapter);
            shoucangShopRecyleview.setItemAnimator(new DefaultItemAnimator());
            shoucangShopRecyleview.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    /**
     * 初始化
     */
    private void init() {
        shoucangShopLayout.setColorSchemeResources(R.color.white);
        shoucangShopLayout.setProgressBackgroundColorSchemeResource(R.color.app_color);
        model = new ShouCangModel();
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }
}
