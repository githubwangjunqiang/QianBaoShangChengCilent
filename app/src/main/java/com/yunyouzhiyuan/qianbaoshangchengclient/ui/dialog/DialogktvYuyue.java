package com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.KTVGvTimeAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.KTVYuyue;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.KTVgvTime;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.LogUtils;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wangjunqiang on 2016/11/21.
 */
public class DialogktvYuyue extends Dialog {
    private CallBack callBack;
    private String time;
    private String key;
    private String goodName;
    private String housename;
    private String riqi;
    private List<KTVgvTime> list = new ArrayList<>();
    private KTVYuyue.DataBean data;
    private int count;
    private LinearLayout view;
    private String lastTime;

    public DialogktvYuyue(Context context, CallBack callBack, String riqi, KTVYuyue.DataBean data) {
        super(context, R.style.dialogTime);
        this.callBack = callBack;
        this.riqi = riqi;
        this.data = data;
        this.time = data.getHour();
        this.key = data.getKey();
        this.goodName = data.getHousename();
        this.housename = data.getHousename();
        this.count = Integer.parseInt(data.getGoods_attr() == null ? "-1" : data.getGoods_attr());
        setContentView(R.layout.dialog_ktv_yuyue);
    }

    public interface CallBack {
        void loadTime(String key, KTVYuyue.DataBean data, String time);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        init();
        getList();
        setListener();
    }

    /**
     * 获取集合
     */
    private void getList() {
        if (TextUtils.isEmpty(time)) {
            dismiss();
            return;
        }
        String[] split = time.split("-");
        if (split.length != 2) {
            To.oo("时间段错误");
            return;
        }
        lastTime = split[1];
        String s = split[0].substring(0, 2);
        String s1 = split[1].substring(0, 2);
        if (s1.equals("00")) {
            s1 = "24";
        }

        int fist = Integer.parseInt(s);
        int last = Integer.parseInt(s1);
        if (last < 10 && fist > last) {
            last += 24;
        }
        for (int i = fist; i < last; i++) {
            if (last > 24) {
                KTVgvTime data = new KTVgvTime((i - 24) + ":00", false);
                list.add(data);
                KTVgvTime data1 = new KTVgvTime((i - 24) + ":30", false);
                list.add(data1);
            } else {
                KTVgvTime data = new KTVgvTime(i + ":00", false);
                list.add(data);
                KTVgvTime data1 = new KTVgvTime(i + ":30", false);
                list.add(data1);
            }
        }
        LogUtils.d("计算后的list时间=" + list.size());
        setAdapter();

    }

    /**
     * 监听器
     */
    private void setListener() {
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(key)) {
                    dismiss();
                    return;
                }
                if (v.isSelected()) {
                    callBack.loadTime(key, data, timeData);
                    dismiss();
                }
            }
        });
    }

    private String timeData;

    /**
     * 设置适配器
     */
    private void setAdapter() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            adapter = new KTVGvTimeAdapter(getContext(), list, new KTVGvTimeAdapter.Callback() {
                @Override
                public void onClick(int position, String time) {
                    if (count == -1) {
                        To.ee("参数错误");
                        dismiss();
                        return;
                    }
                    String lastTim = "";
                    if (position + (count * 2) >= list.size()) {
                        lastTim = lastTime + "结束,不足" + count + "小时，按三小时收费";
                        tvTime.setTextColor(ContextCompat.getColor(getContext(), R.color.red));
                    } else {
                        tvTime.setTextColor(ContextCompat.getColor(getContext(), R.color.blue_p));
                        lastTim = list.get(position + 6).getTime() + "结束";
                    }

                    timeData = time;
                    tvTime.setText("今天(" + riqi + ")" + time + "开唱->" + lastTim);
                    btnOk.setSelected(true);
                }
            });
            gvTime.setAdapter(adapter);
        }
    }

    private TextView tvName, tvContent, tvTime;
    private GridView gvTime;
    private Button btnOk;
    private KTVGvTimeAdapter adapter;

    /**
     * 初始化
     */
    private void init() {
        tvName = (TextView) findViewById(R.id.dialog_ktv_yu_tvname);
        tvContent = (TextView) findViewById(R.id.dialog_ktv_yu_tvcontent);
        tvTime = (TextView) findViewById(R.id.dialog_ktv_yu_tvtime);
        gvTime = (GridView) findViewById(R.id.dialog_ktv_yu_gv);
        btnOk = (Button) findViewById(R.id.dialog_ktv_yu_btn);
        view = (LinearLayout) findViewById(R.id.dialog_time_ll);

        tvName.setText(goodName + "");
        tvContent.setText(housename + "");
    }

    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wlp.gravity = Gravity.BOTTOM;
        window.setAttributes(wlp);
        window.setWindowAnimations(R.style.dialogTime);
    }

    @Override
    public void dismiss() {
        time = null;
        key = null;
        goodName = null;
        housename = null;
        if (list != null) {
            list.clear();
        }
        list = null;
        data = null;
        super.dismiss();
    }

    float startY;
    float moveY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveY = ev.getY() - startY;
                view.scrollBy(0, -(int) moveY);
                startY = ev.getY();
                if (view.getScrollY() > 0) {
                    view.scrollTo(0, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (view.getScrollY() < -this.getWindow().getAttributes().height / 4 && moveY > 0) {
                    this.dismiss();

                }
                view.scrollTo(0, 0);
                break;
        }
        return super.onTouchEvent(ev);
    }
}
