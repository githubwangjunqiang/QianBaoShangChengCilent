package com.yunyouzhiyuan.qianbaoshangchengclient.util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by &{王俊强} on 2016/8/4 0004.
 */
public class GetJsonRetcode {
    public static int getRetcode(String s){
        int retcode = 0;
        try {
            JSONObject obj = new JSONObject(s);
             retcode = obj.getInt("retcode");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return retcode;
    }
    public static String getmsg(String s){
        String msg = null;
        try {
            JSONObject obj = new JSONObject(s);
            msg = obj.getString("msg");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return msg;
    }
    public static String getname(String name,String result){
        String tr = null;
        try {
            JSONObject obj = new JSONObject(result);
            tr = obj.getString(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tr;
    }

    /**
     * 获取 data 里面放的是 int
     * @param name
     * @param result
     * @return
     */
    public static int getIntname(String name,String result){
        int tr = -1;
        try {
            JSONObject obj = new JSONObject(result);
            tr = obj.getInt(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tr;
    }



}
