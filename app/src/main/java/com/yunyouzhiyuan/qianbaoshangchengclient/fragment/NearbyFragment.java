package com.yunyouzhiyuan.qianbaoshangchengclient.fragment;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.baidu.location.BDLocation;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.NearGvAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.NearRecyleAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Specialty;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Specialty_Fenlei;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.SpecialtyModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.DividerItemDecoration;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.MyGridView;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.LogUtils;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 */
public class NearbyFragment extends MainFragment {

    @Bind(R.id.fragment_nearby_rg)
    RadioGroup fragmentNearbyRg;
    @Bind(R.id.fragment_nearby_gv)
    MyGridView fragmentNearbyGv;
    @Bind(R.id.fragment_nearby_rec)
    RecyclerView recyclerView;
    @Bind(R.id.layout_near)
    SwipeRefreshLayout layout;
    @Bind(R.id.appbar_near)
    AppBarLayout appBarLayout;
    @Bind(R.id.fragment_nearby_rb0)
    RadioButton radioButton0;
    @Bind(R.id.fragment_nearby_rb1)
    RadioButton radioButton1;
    @Bind(R.id.fragment_nearby_rb2)
    RadioButton radioButton2;
    @Bind(R.id.fragment_nearby_rb3)
    RadioButton radioButton3;
    private View view;
    private NearGvAdapter nearGvAdapter;//分类适配器
    private NearRecyleAdapter adapter;//数据adapter
    private SpecialtyModel model;
    private List<Specialty_Fenlei.DataBean> list = new ArrayList<>();
    private String class2;
    private String class3;
    private List<Specialty.DataBean> listBottom = new ArrayList<>();

    public NearbyFragment() {
    }

    @Override
    public void onDestroy() {
        adapter = null;
        nearGvAdapter = null;
        model = null;
        class2 = null;
        class3 = null;
        list.clear();
        list = null;
        listBottom.clear();
        listBottom = null;
        super.onDestroy();
    }

    /**
     *
     */
    public static NearbyFragment newInstance() {
        NearbyFragment fragment = new NearbyFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_nearby, null);
            ButterKnife.bind(this, view);
            init();
            setView();
            listener();
        }
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(view);
        }
        return view;
    }

    /**
     * 设置数据
     */
    private void setView() {
        getTopFenlei();
    }

    /**
     * 获取分类
     */
    private void getTopFenlei() {
        layout.setRefreshing(true);
        setCalls(model.getFenLei(new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (getContext() == null) {
                    return;
                }
                list.clear();
                list.addAll((List<Specialty_Fenlei.DataBean>) obj);
                LogUtils.d("list=" + list.size() + "，，" + list.get(0).getSecond().size());
                setGvadapter();
                if (list.size() > 0 && list.get(0).getSecond().size() > 0) {
                    class2 = list.get(0).getId();
                    class3 = list.get(0).getSecond().get(0).getId();
                }
                getBottomData();
                layout.setRefreshing(false);
            }

            @Override
            public void onError(Object obj) {
                if (getContext() == null) {
                    return;
                }
                list.clear();
                setGvadapter();
                listBottom.clear();
                setRecyleAdapter();
                To.ee(obj);
                layout.setRefreshing(false);
            }
        }));
    }

    /**
     * 数据适配器
     */
    private void setRecyleAdapter() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            adapter = new NearRecyleAdapter(getActivity(), listBottom);
            LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(adapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        }


    }


    /**
     * 监听器
     */
    private void listener() {
        fragmentNearbyRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int index = 0;
                switch (checkedId) {
                    case R.id.fragment_nearby_rb0:
                        index = 0;
                        break;
                    case R.id.fragment_nearby_rb1:
                        index = 1;
                        break;
                    case R.id.fragment_nearby_rb2:
                        index = 2;
                        break;
                    case R.id.fragment_nearby_rb3:
                        index = 3;
                        break;
                    default:
                        break;
                }
                clearCalls();
                if (!list.isEmpty() && list.size() >= index) {
                    class2 = list.get(index).getId();
                }
                if (!list.isEmpty() && list.size() >= index && !list.get(index).getSecond().isEmpty()) {
                    class3 = list.get(index).getSecond().get(0).getId();
                }
                nearGvAdapter.upData(index);
                getBottomData();
            }
        });
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                layout.setEnabled(verticalOffset >= 0);
            }
        });
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setView();
                layout.setRefreshing(false);
            }
        });
    }

    /**
     * 初始化一些操作
     */
    private void init() {
        layout.setProgressBackgroundColorSchemeResource(R.color.app_color);
        layout.setColorSchemeResources(R.color.white);
        model = new SpecialtyModel();
    }

    /**
     * 设置适配器
     */
    private void setGvadapter() {
        if (nearGvAdapter != null) {
            nearGvAdapter.toReset(list);
        } else {
            nearGvAdapter = new NearGvAdapter(getActivity(), list, new NearGvAdapter.Callback() {
                @Override
                public void onClick(String class_3) {
                    clearCalls();
                    class3 = class_3;
                    getBottomData();
                }
            });
            fragmentNearbyGv.setAdapter(nearGvAdapter);
        }
        if (list.size() > 0) {
            radioButton0.setText(list.get(0).getMobile_name());
            radioButton0.setChecked(true);
            radioButton1.setText(list.get(1).getMobile_name());
            radioButton2.setText(list.get(2).getMobile_name());
            radioButton3.setText(list.get(3).getMobile_name());
        }
    }

    /**
     * 获取底部数据
     */
    private void getBottomData() {
        if (Bean.city_id_id == null) {
            To.ee("您还没有选择城市");
            return;
        }
        if(TextUtils.isEmpty(class2) || TextUtils.isEmpty(class3)){
            To.oo("数据尚未获取");
            return;
        }
        if (!layout.isRefreshing()) {
            layout.setRefreshing(true);
        }
        setCalls(model.getBottomData(class2, class3, Bean.city_id_id, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (getContext() == null) {
                    return;
                }
                listBottom.clear();
                listBottom.addAll((List<Specialty.DataBean>) obj);
                setRecyleAdapter();
                layout.setRefreshing(false);
            }

            @Override
            public void onError(Object obj) {
                if (getContext() == null) {
                    return;
                }
                listBottom.clear();
                setRecyleAdapter();
                To.ee(obj);
                layout.setRefreshing(false);
            }
        }));
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
        getTopFenlei();
    }
}
