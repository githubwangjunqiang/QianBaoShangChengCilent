package com.yunyouzhiyuan.qianbaoshangchengclient.util;

import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;


/**
 * Created by 王俊强 on 2016/9/19 0019.
 */
public class To {
    private static Toast toast;
    private static Toast toast2;
    private static Toast toast3;
    private static TextView tv;
    private static TextView tv3;
    private static ImageView iviamge;
    private static String string = "可能是网络接口返回异常";

    public static void oo(Object s) {
        if(toast != null){
            toast.cancel();
            toast = null;
        }
        View view = LayoutInflater.from(App.getContext()).inflate(R.layout.toast_tv, null);
        toast = new Toast(App.getContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        TextView tvToast = (TextView) view.findViewById(R.id.toast_tvnumber);
        tvToast.setText(s == null ? string : s.toString());
        toast.show();
    }

    /**
     * Snackbar.make(tvcode,"亲 检测到您更换设备登陆，请您填写验证码",5000).show();
     */
    public static void ss(View view, Object s) {
        Snackbar.make(view, s == null ? string : s.toString(), Snackbar.LENGTH_LONG).show();
    }

    public static void dd(Object s) {
        if (toast2 == null) {
            View view = LayoutInflater.from(App.getContext()).inflate(R.layout.toast, null);
            toast2 = new Toast(App.getContext());
            toast2.setDuration(Toast.LENGTH_SHORT);
            toast2.setGravity(Gravity.CENTER, 0, 0);
            toast2.setView(view);
            tv = (TextView) view.findViewById(R.id.toast_tv);
            tv.setText(s == null ? string : s.toString());
        } else {
            tv.setText(s == null ? string : s.toString());
        }
        toast2.show();
    }
    public static void ee(Object s) {
        if (toast3 == null) {
            View view = LayoutInflater.from(App.getContext()).inflate(R.layout.toast, null);
            toast3 = new Toast(App.getContext());
            toast3.setDuration(Toast.LENGTH_SHORT);
            toast3.setGravity(Gravity.CENTER, 0, 0);
            toast3.setView(view);
            tv3 = (TextView) view.findViewById(R.id.toast_tv);
            tv3.setText(s == null ? string : s.toString());
            iviamge = (ImageView) view.findViewById(R.id.toast_iviamge);
            iviamge.setImageResource(R.mipmap.kulian);
        } else {
            tv3.setText(s == null ? string : s.toString());
        }
        toast3.show();
    }
}
