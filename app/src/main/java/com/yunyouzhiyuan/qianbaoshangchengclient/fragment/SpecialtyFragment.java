package com.yunyouzhiyuan.qianbaoshangchengclient.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.baidu.location.BDLocation;
import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.MyArrayAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.SpeciatlyRecyleAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.baiduMap.BaiduMapBean;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Nearby;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Nearby_Fenlei;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.NearbyModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.DividerItemDecoration;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.LogUtils;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.RecyleViewButtom;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 */
public class SpecialtyFragment extends MainFragment {
    @Bind(R.id.specialty_layout)
    SwipeRefreshLayout layout;
    @Bind(R.id.fragmenta_specialty_ck3)
    CheckBox ck3;
    @Bind(R.id.fragmenta_specialty_rv)
    RecyclerView recyclerView;
    @Bind(R.id.checkBox)
    CheckBox ck0;
    @Bind(R.id.checkBox2)
    CheckBox ck1;
    @Bind(R.id.checkBox3)
    CheckBox ck2;
    @Bind(R.id.sp_linlayout)
    LinearLayout linearLayout;
    private View view;
    private SpeciatlyRecyleAdapter speciatlyRecyleAdapter;
    private NearbyModel model;
    private List<Nearby_Fenlei.DataBean> list = new ArrayList<>();
    private View viewPopupWindow;
    private ListView listView;
    private PopupWindow popupWindow;
    private MyArrayAdapter adapter;
    private List<Nearby_Fenlei.DataBean.SencondBean> listData = new ArrayList<>();
    private String id;//分类id
    private int page = 0;
    private List<Nearby.DataBean> listBottoms = new ArrayList<>();
    private boolean looding = true;
    private int index = 0;//标记点击帅选框

    @Override
    public void onDestroy() {
        listData.clear();
        listBottoms.clear();
        listBottoms = null;
        listData = null;
        model = null;
        adapter = null;
        viewPopupWindow = null;
        view = null;
        listView = null;
        popupWindow = null;
        speciatlyRecyleAdapter = null;
        super.onDestroy();
    }

    public SpecialtyFragment() {
        // Required empty public constructor
    }

    public static SpecialtyFragment newInstance() {
        SpecialtyFragment fragment = new SpecialtyFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_specialty, container, false);
            ButterKnife.bind(this, view);
            initView(inflater);
            setListener();
            getData();
        }
        return view;
    }

    /**
     * 获取数据
     */
    private void getData() {
        id = null;
        layout.setRefreshing(true);
        setCalls(model.getFenLei(new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (getContext() == null) {
                    return;
                }
                list.clear();
                list.addAll((List<Nearby_Fenlei.DataBean>) obj);
                setTopView();
                getBottomData();
            }

            @Override
            public void onError(Object obj) {
                if (getContext() == null) {
                    return;
                }
                To.oo(obj);
                list.clear();
                layout.setRefreshing(false);
            }
        }));
    }

    /**
     * 获取底部列表数据
     */
    private void getBottomData() {
        if (BaiduMapBean.getLocation() == null) {
            layout.setRefreshing(false);
            To.ee("亲您手机还没有定位成功");
            return;
        }
        if (TextUtils.isEmpty(Bean.city_id_id)) {
            layout.setRefreshing(false);
            To.ee("亲您还没有选择城市，请左上角选择城市");
            return;
        }
        layout.setRefreshing(true);
        setCalls(model.getBottomData(page, BaiduMapBean.getLocation().getLatitude() + "",
                BaiduMapBean.getLocation().getLongitude() + "", id, Bean.city_id_id, new IModel.AsyncCallBack() {
                    @Override
                    public void onSucceed(Object obj) {
                        if (getContext() == null) {
                            return;
                        }
                        listBottoms.clear();
                        listBottoms.addAll((List<Nearby.DataBean>) obj);
                        setData();
                        page++;
                        layout.setRefreshing(false);
                        looding = true;
                    }

                    @Override
                    public void onError(Object obj) {
                        if (getContext() == null) {
                            return;
                        }
                        if (page == 0) {
                            listBottoms.clear();
                            setData();
                            To.ee(obj);
                        }
                        layout.setRefreshing(false);
                    }
                }));
    }

    /**
     * 写入顶部四大分类数据
     */
    private void setTopView() {
        if (list.size() > 3) {
            ck0.setText(list.get(0).getSc_name());
            ck1.setText(list.get(1).getSc_name());
            ck2.setText(list.get(2).getSc_name());
            ck3.setText(list.get(3).getSc_name());
            ck0.setSelected(false);
            ck1.setSelected(false);
            ck2.setSelected(false);
            ck3.setSelected(false);
        }
    }

    /**
     * 监听器
     */
    private void setListener() {
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
//                WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
//                params.alpha = 1f;
//                getActivity().getWindow().setAttributes(params);
                ck0.setChecked(false);
                ck1.setChecked(false);
                ck2.setChecked(false);
                ck3.setChecked(false);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long d) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    id = listData.get(position).getId();
                    popupWindow.dismiss();
                    page = 0;
                    getBottomData();
                    setCheckedText(listData.get(position).getMobile_name(), position);
                }
            }
        });
        viewPopupWindow.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                return false;
            }
        });
    }

    /**
     * 选中文字
     */
    private void setCheckedText(String name, int position) {
        ck0.setSelected(false);
        ck1.setSelected(false);
        ck2.setSelected(false);
        ck3.setSelected(false);
        if (position == 0) {
            ck0.setText(list.get(0).getSc_name());
            ck1.setText(list.get(1).getSc_name());
            ck2.setText(list.get(2).getSc_name());
            ck3.setText(list.get(3).getSc_name());
        }
        switch (index) {
            case 0://美食
                if (position != 0) {
                    ck0.setText(name);
                    ck0.setSelected(true);
                }
                break;
            case 1://酒店
                if (position != 0) {
                    ck1.setText(name);
                    ck1.setSelected(true);
                }
                break;
            case 2://ktv
                if (position != 0) {
                    ck2.setText(name);
                    ck2.setSelected(true);
                }
                break;
            case 3://休闲娱乐
                if (position != 0) {
                    ck3.setText(name);
                    ck3.setSelected(true);
                }
                break;
        }

    }

    /**
     * 初始化
     *
     * @param inflater
     */
    private void initView(LayoutInflater inflater) {
        layout.setProgressBackgroundColorSchemeResource(R.color.app_color);
        layout.setColorSchemeResources(R.color.white);
        model = new NearbyModel();
        //popuwindow
        viewPopupWindow = inflater.inflate(R.layout.popumevu, null);
        listView = (ListView) viewPopupWindow.findViewById(R.id.populixtview);
        popupWindow = new PopupWindow(viewPopupWindow,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        popupWindow.setOutsideTouchable(true);
        adapter = new MyArrayAdapter(getActivity(), listData);
        listView.setAdapter(adapter);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#55000000")));
    }

    /**
     * 设置data
     */
    private void setData() {
        if (speciatlyRecyleAdapter != null) {
            speciatlyRecyleAdapter.notifyDataSetChanged();
        } else {
            speciatlyRecyleAdapter = new SpeciatlyRecyleAdapter(getActivity(), listBottoms);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
            recyclerView.setAdapter(speciatlyRecyleAdapter);
            recyclerView.addOnScrollListener(new RecyleViewButtom(new RecyleViewButtom.Listener() {
                @Override
                public void last(int position) {
                    if (looding) {
                        looding = false;
                        getBottomData();
                    }
                }
            }));
        }
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    @Override
    public void upLocation(BDLocation location) {
        if (speciatlyRecyleAdapter == null && view != null) {
            getBottomData();
        }
    }

    @Override
    public void upCity() {
        if (view != null) {
            page = 0;
            getBottomData();
        }
    }

    @OnClick({R.id.checkBox, R.id.checkBox2, R.id.checkBox3, R.id.fragmenta_specialty_ck3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.checkBox://美食
                index = 0;
                break;
            case R.id.checkBox2://酒店
                index = 1;
                break;
            case R.id.checkBox3://ktv
                index = 2;
                break;
            case R.id.fragmenta_specialty_ck3://休闲娱乐
                index = 3;
                break;
        }
        showPopuWindow(view, index);
    }


    /**
     * 筛选框 适配器
     */
    private void showPopuWindow(View v, int position) {
//        popupWindow.setAnimationStyle(R.style.popuwindow);
        ck0.setChecked(false);
        ck1.setChecked(false);
        ck2.setChecked(false);
        ck3.setChecked(false);
        ((CheckBox) v).setChecked(true);
        listData.clear();
        Nearby_Fenlei.DataBean.SencondBean sencondBean = new Nearby_Fenlei.DataBean.SencondBean();
        sencondBean.setId(null);
        sencondBean.setMobile_name("全部");
        listData.add(sencondBean);
        if (list.size() > 3 && list.get(position).getSencond().size() > 0) {
            listData.addAll(list.get(position).getSencond());
        }
        adapter.notifyDataSetChanged();
//        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
//        params.alpha = 0.7f;
//        getActivity().getWindow().setAttributes(params);
        popupWindow.setAnimationStyle(R.style.popupAnimation);
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                popupWindow.showAsDropDown(v);
                LogUtils.d("<<"+24);
            } else {
                int[] loaction = new int[2];
                linearLayout.getLocationOnScreen(loaction);
                int y = loaction[1];
                LogUtils.d("pupowindow 的高度y="+y+"/"+getStatusBarHeight());

                Log.e("12345", "showPopuWindow: "+ y);
                popupWindow.showAtLocation(linearLayout,Gravity.NO_GRAVITY, 0, linearLayout.getHeight()+ Utils.dip2px(App.getContext(),46) + getStatusBarHeight());
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            LogUtils.d(""+e.getMessage());
        }

    }

    protected int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
