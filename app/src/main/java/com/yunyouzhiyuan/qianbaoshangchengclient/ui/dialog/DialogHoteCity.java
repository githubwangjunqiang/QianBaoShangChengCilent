package com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.DialogCityAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.CityHotel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.HotelModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.TitleLayout;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.xlistview.XListView;
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
    private XListView listView;
    private int page = 0;
    private HotelModel model;
    private List<Call> calls = new ArrayList<>();
    private DialogCityAdapter adapter;
    private List<CityHotel.DataBean> list = new ArrayList<>();

    public DialogHoteCity(Context context, HotelModel model, CallBack callBack) {
        super(context, R.style.dialogWindowAnim);
        this.model = model;
        this.callBack = callBack;
        setContentView(R.layout.dialog_hotel_city);
    }

    public interface CallBack {
        void callBack(String city_id, String level,String name);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        init();
        setListener();
        setadapter();
        listView.startRefresh();
    }

    /**
     * 适配器
     */
    private void setadapter() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            adapter = new DialogCityAdapter(getContext(), list);
            listView.setAdapter(adapter);
        }
    }

    /**
     * 监听器
     */
    private void setListener() {
        listView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                page = 0;
                getData();
            }

            @Override
            public void onLoadMore() {
                getData();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                callBack.callBack(list.get(position-1).getId(), list.get(position-1).getLevel(),list.get(position-1).getName());
                dismiss();
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
                listView.stopLoadMore();
                listView.stopRefresh(true);
            }

            @Override
            public void onError(Object obj) {
                if (page == 0) {
                    list.clear();
                    setadapter();
                }
                To.oo(obj);
                listView.stopLoadMore();
                listView.stopRefresh(false);
            }
        });
        calls.add(li);
    }

    /**
     * 操作view
     */
    private void init() {
        titleLayout = (TitleLayout) findViewById(R.id.diapog_hotel_city_title);
        listView = (XListView) findViewById(R.id.diapog_hotel_city_listview);
        titleLayout.setTitle("选择酒店目的地", true);
        titleLayout.setCallback(new TitleLayout.Callback(null) {
            @Override
            public void backClick() {
                dismiss();
            }
        });
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(true);
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
