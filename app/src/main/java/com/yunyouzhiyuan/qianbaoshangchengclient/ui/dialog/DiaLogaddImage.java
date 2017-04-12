package com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;


/**
 * Created by wangjunqiang on 2016/11/21.
 */
public class DiaLogaddImage extends Dialog {
    private TextView tv0, tv1;
    private CallBack callBack;

    public DiaLogaddImage(Context context, CallBack callBack) {
        super(context, R.style.Dialog);
        this.callBack = callBack;
        setContentView(R.layout.dialog_image);
    }

    public interface CallBack {
        /**
         *   点击添加照片
         * @param type   0=拍照  1 = 相册
         */
        void callBack(int type);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        tv0 = (TextView) findViewById(R.id.dialog_city_tv0);
        tv1 = (TextView) findViewById(R.id.dialog_city_tv1);
        tv0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.callBack(0);
                dismiss();
            }
        });
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.callBack(1);
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
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

}
