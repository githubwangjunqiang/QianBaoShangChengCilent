package com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;


/**
 * Created by wangjunqiang on 2016/11/21.
 */
public class DiaLogHotelGuan extends Dialog {
    private CallBack callBack;
    private EditText etContent;
    private TextView tv0, tv1;
    private String content;

    public DiaLogHotelGuan(Context context, CallBack callBack, String content) {
        super(context, R.style.Dialog);
        this.callBack = callBack;
        this.content = content;
        setContentView(R.layout.dialog_hotel_guanjianzi);
    }

    public interface CallBack {
        void callBack(String string);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        etContent = (EditText) findViewById(R.id.dialog_city_et);
        etContent.setText(content);
        tv0 = (TextView) findViewById(R.id.dialog_city_tv0);
        tv1 = (TextView) findViewById(R.id.dialog_city_tv1);
        tv0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = etContent.getText().toString().trim();
                if (TextUtils.isEmpty(text)) {
                    To.oo("您没任何输入");
                    callBack.callBack(null);
                }else{
                    callBack.callBack(text);
                }
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
