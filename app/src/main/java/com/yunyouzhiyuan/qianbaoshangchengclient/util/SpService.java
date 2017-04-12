package com.yunyouzhiyuan.qianbaoshangchengclient.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.yunyouzhiyuan.qianbaoshangchengclient.App;


public class SpService {
    private Context context;
    private static SpService sp;

    private SpService(Context context) {
        this.context = context;
    }

    public static SpService getSP() {
        if (sp == null) {
            sp = new SpService(App.getContext());
            return sp;
        } else {
            return sp;
        }
    }

    /**
     * 存入登陆后 返回的信息
     *
     * @param user_id
     * @param phone
     * @param pas
     * @return
     */
    public boolean saveUid(String user_id, String phone, String pas) {
        SharedPreferences preferences = context.getSharedPreferences("qianbaoclient_app", Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString("uid", user_id);
        editor.putString("phone", phone);
        editor.putString("pas", pas);
        return editor.commit();
    }
    /**
     * 存入ctid
     */
    public boolean saveUid(String ctid) {
        SharedPreferences preferences = context.getSharedPreferences("qianbaoclient_app", Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString("ctid", ctid);
        return editor.commit();
    }


//    /**
//     * 是否第一次使用
//     *
//     * @param isFist true是第一次使用  false 不是第一次
//     * @return
//     */
//    public boolean saveAutomatic(boolean isFist) {
//        SharedPreferences preferences = context.getSharedPreferences("liushao_app", Context.MODE_PRIVATE);
//        Editor editor = preferences.edit();
//        editor.putBoolean("saveAutomatic", isFist);
//        return editor.commit();
//    }

    /**
     * 获取uid
     * @return
     */
    public String getUserid() {
        return getStringName("uid", "qianbaoclient_app");
    }
    /**
     * 获取cityid
     * @return
     */
    public String getCityid() {
        return getStringName("ctid", "qianbaoclient_app");
    }

//    /**
//     * 获取是否第一次登陆 保存的信息
//     * @return
//     */
//    public boolean isAutomatic() {
//        SharedPreferences preferences = context.getSharedPreferences("liushao_app", Context.MODE_PRIVATE);
//        return preferences.getBoolean("saveAutomatic", true);
//    }

    /**
     * 获取密码
     *
     * @return
     */
    public String getpas() {
        return getStringName("pas", "qianbaoclient_app");
    }

    /**
     * 获取电话号码
     */
    public String getphone() {
        return getStringName("phone", "qianbaoclient_app");
    }


    /**
     * 根据名称 获取值
     *
     * @param name
     * @param content
     * @return
     */
    private String getStringName(String name, String content) {
        SharedPreferences preferences = context.getSharedPreferences(content, Context.MODE_PRIVATE);
        String string = preferences.getString(name, "");
        return string;
    }

    /**
     * clear 存入的 user信息
     */
    public void clearUser() {
        SharedPreferences preferences = context.getSharedPreferences("qianbaoclient_app", Context.MODE_PRIVATE);
        preferences.edit().clear();
    }

}
