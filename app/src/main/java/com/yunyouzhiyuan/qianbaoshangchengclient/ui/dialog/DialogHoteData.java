package com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.HotelDataAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.baiduMap.BaiduMapBean;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Hotel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.HotelModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.DividerItemDecoration;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.TitleLayout;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.swlayout.SwipyRefreshLayout;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.swlayout.SwipyRefreshLayoutDirection;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;


/**
 * Created by wangjunqiang on 2016/11/21.
 */
public class DialogHoteData extends Dialog {
    private TitleLayout titleLayout;
    private SwipyRefreshLayout layout;
    private RecyclerView recyclerView;
    private HotelModel model;
    private List<Call> calls = new ArrayList<>();
    private List<Hotel.DataBean> list = new ArrayList<>();
    private HotelDataAdapter adapter;

    private String sc_id;//酒店分类
    private String addressLinv;//地址二级
    private String addressId;//地址id
    private int page = 0;//分页
    private String class_id2;//全日制，钟点房
    private String guanJianZi;//搜索关键字
    private String price;//价格区域

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    private Callback callback;

    public DialogHoteData(Context context, HotelModel model, String sc_id, String addressLinv, String addressId, String class_id2, String guanJianZi, String price) {
        super(context, R.style.dialogWindowAnim);
        this.model = model;
        this.sc_id = sc_id;
        this.addressLinv = addressLinv;
        this.addressId = addressId;
        this.class_id2 = class_id2;
        this.guanJianZi = guanJianZi;
        this.price = price;
        setContentView(R.layout.dialog_hotel_data);
    }

    public interface Callback {
        void callback(String storid);
    }

    @Override
    public void dismiss() {
        if (!calls.isEmpty()) {
            for (Call c : calls) {
                if (!c.isCanceled()) {
                    c.cancel();
                }
            }
        }
        calls.clear();
        list.clear();
        calls = null;
        list = null;
        adapter = null;
        super.dismiss();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        init();
        setListener();
        setadapter();
        layout.setRefreshing(true);
        getData();
    }

    /**
     * 适配器
     */
    private void setadapter() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            adapter = new HotelDataAdapter(getContext(), list, new HotelDataAdapter.Callback() {
                @Override
                public void onItmeClick(String storId) {
                    if (callback != null) {
                        callback.callback(storId);
                    }
                }
            });
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
            recyclerView.setAdapter(adapter);
        }
    }

    /**
     * 监听器
     */
    private void setListener() {
        layout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if (direction == SwipyRefreshLayoutDirection.TOP) {//顶部刷新
                    page = 0;
                    getData();
                } else {
                    getData();
                }
            }
        });
    }

    /**
     * 获取信息
     */
    private void getData() {
        calls.add(model.toLoadHotel(sc_id, addressLinv, addressId, page + "", class_id2, BaiduMapBean.getLocation().getLongitude() + "", BaiduMapBean.getLocation().getLatitude() + "", guanJianZi, price, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (page == 0) {
                    list.clear();
                }
                list.addAll((List<Hotel.DataBean>) obj);
                setadapter();
                page++;
                layout.setRefreshing(false);
            }

            @Override
            public void onError(Object obj) {
                if (page == 0) {
                    To.oo(obj);
                    list.clear();
                    setadapter();
                }
                layout.setRefreshing(false);
            }
        }));
    }

    /**
     * 操作view
     */
    private void init() {
        titleLayout = (TitleLayout) findViewById(R.id.hotel_data_title);
        layout = (SwipyRefreshLayout) findViewById(R.id.hotel_data_layout);
        recyclerView = (RecyclerView) findViewById(R.id.hotel_data_recyle);
        layout.setProgressBackgroundColor(R.color.app_color);
        layout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.white));

        titleLayout.setTitle("酒店列表", true);
        titleLayout.setCallback(new TitleLayout.Callback(null) {
            @Override
            public void backClick() {
                dismiss();
            }
        });
    }

    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);
        window.setWindowAnimations(R.style.dialogWindowAnim);
    }


}
