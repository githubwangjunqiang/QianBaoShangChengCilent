package com.yunyouzhiyuan.qianbaoshangchengclient.model;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Address;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.City;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.GetJsonRetcode;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.LogUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ${王俊强} on 2017/2/15.
 */

public class AddressModel extends IModel {
    /**
     * 获取用户地址
     * get_address_list
     * 传入：user_id
     *
     * @return
     */
    public Call getDate(String user_id, final AsyncCallBack callBack) {
        FormBody body = new FormBody.Builder().add("user_id", user_id).build();
        Request request = new Request.Builder().url(HTTPURL.get_address_list).post(body).build();
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
                if (!response.isSuccessful()) {
                    runUiOnError(response.message(), callBack);
                    return;
                }



                String string = response.body().string();
                LogUtils.d("获取用户地址" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    final List<Address.DataBean> data = new Gson().fromJson(string, Address.class).getData();
                    instance.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onSucceed(data);
                        }
                    });
                } else {
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
     * 三级联动获取地区
     * get_area
     * 传入：parent_id
     * 返回：region_id     region_name
     *
     * @return
     */
    public Call getCity(String parent_id, final AsyncCallBack callBack) {
        FormBody.Builder builder = new FormBody.Builder();
        if (!TextUtils.isEmpty(parent_id)) {
            builder.add("parent_id", parent_id);
        }
        Request request = new Request.Builder().url(HTTPURL.get_area).post(builder.build()).build();
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
                if (!response.isSuccessful()) {
                    runUiOnError(response.message(), callBack);
                    return;
                }

                String string = response.body().string();
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    final City city = new Gson().fromJson(string, City.class);
                    instance.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onSucceed(city.getData());
                        }
                    });
                } else {
                    final String s = GetJsonRetcode.getmsg(string);
                    instance.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onError(s);
                        }
                    });
                }
            }
        });
        return call;
    }

    /**
     * 添加和编辑收货地址
     * edit_address
     * 传入：user_id  consignee（收货人）  mobile （收货人电话） province（省id）  city （市id）
     * district（区id） address （详细地址） is_default  （是否默认）1是默认 0是否
     * address_id（地址id   修改时传入）
     *
     * @return
     */
    public Call addAddress(String user_id, String consignee, String mobile,
                           String province, String city, String district, String address,
                           String is_default, String address_id, final AsyncCallBack callBack) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("user_id", user_id);
        builder.add("consignee", consignee);
        builder.add("mobile", mobile);
        builder.add("province", province);
        builder.add("city", city);
        builder.add("district", district);
        builder.add("address", address);
        builder.add("is_default", is_default);
        if (!TextUtils.isEmpty(address_id)) {
            builder.add("address_id", address_id);
        }
        FormBody body = builder.build();
        final Request request = new Request.Builder().url(HTTPURL.edit_address).post(body).build();
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
                if (!response.isSuccessful()) {
                    runUiOnError(response.message(), callBack);
                    return;
                }

                final String req = response.body().string();
                LogUtils.d("添加和编辑收货地址"+req);
                if (GetJsonRetcode.getRetcode(req) == 2000) {
                    instance.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onSucceed(GetJsonRetcode.getmsg(req));
                        }
                    });
                } else {
                    instance.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onError("没做任何修改");
                        }
                    });
                }
            }
        });
        return call;
    }

    /**
     * 删除地址
     del_address
     传入：user_id     address_id
     * @return
     */
    public Call deleteAdd(String user_id, String address_id, final AsyncCallBack callBack) {
        FormBody body = new FormBody.Builder().add("user_id",user_id).add("address_id",address_id).build();
        Request request = new Request.Builder().url(HTTPURL.del_address).post(body).build();
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
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    runUiOnError(response.message(), callBack);
                    return;
                }

                String string = response.body().string();
                if(GetJsonRetcode.getRetcode(string) == 2000){
                    final String s = GetJsonRetcode.getmsg(string);
                    instance.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onSucceed(s);
                        }
                    });
                }else{
                    final String s = GetJsonRetcode.getmsg(string);
                    instance.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onError(s);
                        }
                    });
                }
                call.cancel();
            }
        });
        return call;
    }

}
