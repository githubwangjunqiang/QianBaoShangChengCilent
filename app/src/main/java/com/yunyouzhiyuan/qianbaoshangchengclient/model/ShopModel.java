package com.yunyouzhiyuan.qianbaoshangchengclient.model;

import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.GetJsonRetcode;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.LogUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import static com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL.collectGoods;

/**
 * Created by ${王俊强} on 2017/3/1.
 */

public class ShopModel extends IModel {
    /**
     * 44、收藏商品
     * collectGoods
     * 传入：user_id   goods_id   type（默认0 收藏    1取消收藏）
     *
     * @return
     */
    public Call shouCangShop(String user_id, String goods_id, int type, final AsyncCallBack callBack) {
        FormBody body = new FormBody.Builder().add("user_id", user_id).add("goods_id", goods_id).add("type", type + "").build();
        Request request = new Request.Builder().url(collectGoods).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runUiOnError(null, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LogUtils.d("收藏商品" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                        runUiOnSuccess(GetJsonRetcode.getmsg(string),callBack);
                } else {
                    runUiOnError(GetJsonRetcode.getmsg(string), callBack);
                }
            }
        });
        return call;
    }

    /**
     * 58、获取商品是否收藏
     is_goods_collect
     传入：goods_id   user_id
     返回：（1 收藏   0未收藏）
     * @return
     */
    public Call loadShopShou(String goods_id, String user_id, final AsyncCallBack callBack){
        FormBody body = new FormBody.Builder().add("goods_id",goods_id).add("user_id",user_id).build();
        Request request = new Request.Builder().url(HTTPURL.is_goods_collect).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runUiOnError(null,callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LogUtils.d("获取商品是否收藏"+string);
                if(GetJsonRetcode.getRetcode(string) == 2000){
                    runUiOnSuccess(GetJsonRetcode.getIntname("data",string),callBack);
                }else{
                    runUiOnError(GetJsonRetcode.getmsg(string),callBack);
                }
            }
        });
        return call;
    }
}
