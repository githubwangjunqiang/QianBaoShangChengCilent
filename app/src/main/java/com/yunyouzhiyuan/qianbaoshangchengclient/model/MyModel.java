package com.yunyouzhiyuan.qianbaoshangchengclient.model;

import com.google.gson.Gson;
import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.KeFu;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.UserInfo;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.GetJsonRetcode;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.LogUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ${王俊强} on 2017/2/15.
 */

public class MyModel extends IModel {
    /**
     *、获取客服电话
     get_kefu_tel
     * @return
     */
    public Call getKeFu(final AsyncCallBack callBack){
        Request request = new Request.Builder().url(HTTPURL.get_kefu_tel).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                instance.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onError(App.getContext().getString(R.string.qingqiushibai));
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                if(GetJsonRetcode.getRetcode(string) == 2000){
                     List<KeFu.DataBean> list = new Gson().fromJson(string,KeFu.class).getData();
                    final List<String> strings = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        strings.add(list.get(i).getValue());
                    }
                    list.clear();
                    instance.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onSucceed(strings);
                        }
                    });
                }else{
                    final String msg = GetJsonRetcode.getmsg(string);
                    instance.post(new Runnable() {
                        @Override
                        public void run() {
                           callBack.onSucceed(msg);
                        }
                    });
                }
            }
        });
        return call;
    }

    /**
     * 、获取个人信息
     get_user_info
     传入：user_id
     * @return
     */
    public Call getUserInfo(String user_id, final AsyncCallBack callBack){
        FormBody body = new FormBody.Builder().add("user_id",user_id).build();
        Request request = new Request.Builder().url(HTTPURL.get_user_info).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                instance.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onError(App.getContext().getString(R.string.qingqiushibai));
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LogUtils.d("获取个人信息"+string);
                if(GetJsonRetcode.getRetcode(string) == 2000){
                    final UserInfo userInfo = new Gson().fromJson(string,UserInfo.class);
                    instance.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onSucceed(userInfo.getData());
                        }
                    });
                }else{
                    final String getmsg = GetJsonRetcode.getmsg(string);
                    instance.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onError(getmsg);
                        }
                    });
                }
            }
        });
        return call;
    }

    /**
     * 修改个人基本信息
     BaseInfoEdit
     传参：user_id （用户id）  head_pic（头像）   nickname（昵称）    birthday()
     * @return
     */
    public Call unDate(String user_id, String head_pic, String nickname, String birthday, final AsyncCallBack callBack){
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("user_id",user_id);
        builder.add("head_pic",head_pic);
        builder.add("nickname",nickname);
        builder.add("birthday",birthday);
        Request request = new Request.Builder().url(HTTPURL.BaseInfoEdit).post(builder.build()).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                instance.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onError(App.getContext().getString(R.string.qingqiushibai));
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                if(GetJsonRetcode.getRetcode(string) == 2000){
                    instance.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onSucceed(GetJsonRetcode.getmsg(string));
                        }
                    });
                }else{
                    instance.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onError(GetJsonRetcode.getmsg(string));
                        }
                    });
                }
            }
        });
        return call;
    }
}
