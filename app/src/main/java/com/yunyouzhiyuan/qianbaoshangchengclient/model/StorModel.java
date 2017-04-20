package com.yunyouzhiyuan.qianbaoshangchengclient.model;

import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.CookStorInfoShop;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.StorInfo;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.StorInfoList;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.TuiJian;
import com.yunyouzhiyuan.qianbaoshangchengclient.okhttp.BaseCallback;
import com.yunyouzhiyuan.qianbaoshangchengclient.okhttp.MyOkHttpClent;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.GetJsonRetcode;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.LogUtils;

import java.io.IOException;

import okhttp3.Call;

import static com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL.collectStoreOrNo;
import static com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL.get_meishi_goods;
import static com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL.get_three_recommend;
import static com.yunyouzhiyuan.qianbaoshangchengclient.okhttp.MyOkHttpClent.newBuilder;

/**
 * Created by ${王俊强} on 2017/2/14.
 */

public class StorModel extends IModel {
    /**
     * 获取店铺基本信息
     * about_store
     * 传入：store_id  (店铺id)
     * user_id  yonghu
     */
    public Call getStorInfo(String store_id, String user_id, final AsyncCallBack callBack) {
        return MyOkHttpClent.newBuilder().url(HTTPURL.about_store).post().addParam("store_id", store_id)
                .addParam("user_id", user_id).build().enqueue(new BaseCallback.ComonCallback<StorInfo>() {
                    @Override
                    protected void onSuccess(StorInfo storInfo) {
                        callBack.onSucceed(storInfo);
                    }

                    @Override
                    protected void onError(int code, String msg) {
                        callBack.onError(msg);
                    }

                    @Override
                    protected void onFailure(Call call, IOException e) {
                        callBack.onError("网络错误");
                    }
                });

    }

    /**
     * 获取店铺商品列表
     * storeGoods
     * 传入：store_id (店铺id)  page(默认0)  sort(排序  comprehensive 综合  sales 销量    price价格)
     * cat_id（商品分类id）
     */

    public Call storeGoods(final String store_id, int page, final AsyncCallBack callBack) {
        return MyOkHttpClent.newBuilder().url(HTTPURL.storeGoods).post().addParam("store_id", store_id)
                .addParam("page", page).build().enqueue(new BaseCallback.ComonCallback<StorInfoList>() {
                    @Override
                    protected void onSuccess(StorInfoList storInfoList) {
                        callBack.onSucceed(storInfoList);
                    }

                    @Override
                    protected void onError(int code, String msg) {
                        callBack.onError(msg);
                    }

                    @Override
                    protected void onFailure(Call call, IOException e) {
                        callBack.onError("网络错误");
                    }
                });

    }

    /**
     * 收藏与取消
     * collectStoreOrNo
     * 传入：user_id （用户id）  store_id （店铺id）
     *
     * @return
     */
    public Call toShoCang(String user_id, String store_id, final AsyncCallBack callBack) {
        return MyOkHttpClent.newBuilder().url(collectStoreOrNo).post().addParam("user_id", user_id).addParam("store_id", store_id)
                .build().enqueue(new BaseCallback.ComonCallback<String>() {
                    @Override
                    protected void onSuccess(String s) {
                        if (GetJsonRetcode.getRetcode(s) == 2000) {
                            callBack.onSucceed(GetJsonRetcode.getmsg(s));
                        } else {
                            callBack.onError(GetJsonRetcode.getmsg(s));
                        }
                    }

                    @Override
                    protected void onError(int code, String msg) {
                        callBack.onError(msg);
                    }

                    @Override
                    protected void onFailure(Call call, IOException e) {
                        callBack.onError("网络错误");
                    }
                });


    }

    /**
     * 48、获取美食模块商品列表
     * get_meishi_goods
     * 传入：store_id  （店铺id）  page(默认为0)
     *
     * @return
     */
    public Call getCookBottom(String store_id, final AsyncCallBack callBack) {
        return MyOkHttpClent.newBuilder().url(get_meishi_goods).post().addParam("store_id", store_id).build()
                .enqueue(new BaseCallback.ComonCallback<CookStorInfoShop>() {
                    @Override
                    protected void onSuccess(CookStorInfoShop cookStorInfoShop) {
                        callBack.onSucceed(cookStorInfoShop.getData());
                    }

                    @Override
                    protected void onError(int code, String msg) {
                        callBack.onError(msg);
                    }

                    @Override
                    protected void onFailure(Call call, IOException e) {
                        callBack.onError("网络错误");
                    }
                });

    }

    /**
     * 64、获取分类页面推荐的三个店铺
     * get_three_recommend
     * 传入：sc_id    city_id(首页所选城市id)
     *
     * @return
     */
    public Call getTuiJian(String sc_id, String city_id, final AsyncCallBack callBack) {
        LogUtils.d("获取分类页面推荐的三个店铺参数=" + sc_id + "/" + city_id);

        return newBuilder().url(get_three_recommend).post()
                .addParam("sc_id", sc_id).addParam("city_id", city_id).build()
                .enqueue(new BaseCallback.ComonCallback<TuiJian>() {
                    @Override
                    protected void onSuccess(TuiJian tuiJian) {
                        callBack.onSucceed(tuiJian.getData());
                    }

                    @Override
                    protected void onError(int code, String msg) {
                        callBack.onError(msg);
                    }

                    @Override
                    protected void onFailure(Call call, IOException e) {
                        callBack.onError("网络错误");
                    }
                });
    }
}
