package com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.DialogCityAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.CityHotel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.HotelModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.SectionDecoration;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.TitleLayout;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.swlayout.SwipyRefreshLayout;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.swlayout.SwipyRefreshLayoutDirection;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.LogUtils;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;


/**
 * Created by wangjunqiang on 2016/11/21.
 */
public class DialogHoteCity extends Dialog {
    private CallBack callBack;
    private TitleLayout titleLayout;
    private RecyclerView listView;
    private int page = 0;
    private HotelModel model;
    private List<Call> calls = new ArrayList<>();
    private DialogCityAdapter adapter;
    private List<CityHotel.DataBean> list = new ArrayList<>();
    private SwipyRefreshLayout layout;

    public DialogHoteCity(Context context, HotelModel model, CallBack callBack) {
        super(context, R.style.dialogWindowAnim);
        this.model = model;
        this.callBack = callBack;
        setContentView(R.layout.dialog_hotel_city);
    }

    public interface CallBack {
        void callBack(String city_id, String level, String name);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        init();
        setListener();
        setadapter();
    }

    /**
     * 适配器
     */
    private void setadapter() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            adapter = new DialogCityAdapter(getContext(), list, new DialogCityAdapter.Callback() {
                @Override
                public void callBack(String city_id, String level, String name) {
                    callBack.callBack(city_id, level, name);
                    dismiss();
                }
            });
            listView.setLayoutManager(new LinearLayoutManager(getContext()));
            listView.setAdapter(adapter);
            listView.addItemDecoration(new SectionDecoration(getContext(), new SectionDecoration.DecorationCallback() {
                @Override
                public String getGroupId(int position) {
                    String name = list.get(position).getBiaoji();
                    if (!TextUtils.isEmpty(name)) {
                        LogUtils.d(name);
                        return name;
                    }
                    return "-1";
                }

                @Override
                public String getGroupFirstLine(int position) {
                    String name = list.get(position).getBiaoji();
                    if (!TextUtils.isEmpty(name)) {
                        LogUtils.d(name);
                        return name;
                    }
                    return "";
                }
            }));
        }
    }

    /**
     * 监听器
     */
    private void setListener() {
        layout.setDirection(SwipyRefreshLayoutDirection.BOTH);
        layout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if (direction == SwipyRefreshLayoutDirection.TOP) {
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
        final Call li = model.getList(page, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (page == 0) {
                    list.clear();
                }
                list.addAll((List<CityHotel.DataBean>) obj);
                setadapter();
                page++;
                layout.setRefreshing(false);
            }

            @Override
            public void onError(Object obj) {
                if (page == 0) {
                    list.clear();
                    setadapter();
                }
                To.oo(obj);
                layout.setRefreshing(false);
            }
        });
        calls.add(li);
    }

    /**
     * 操作view
     */
    private void init() {
        titleLayout = (TitleLayout) findViewById(R.id.diapog_hotel_city_title);
        listView = (RecyclerView) findViewById(R.id.hotel_city_recyleview);
        layout = (SwipyRefreshLayout) findViewById(R.id.hotel_city_layout);
        layout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.white));
        layout.setProgressBackgroundColor(R.color.app_color);
        titleLayout.setTitle("选择酒店目的地", true);
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
        layout.setRefreshing(true);
        getData();
        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);
        window.setWindowAnimations(R.style.dialogWindowAnim);
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
}
