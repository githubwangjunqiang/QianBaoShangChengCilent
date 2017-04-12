package com.yunyouzhiyuan.qianbaoshangchengclient.model;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Home_HuoDong;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.KTV;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.KTVSort;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.KTVTuanList;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.KTVYuyue;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.KTVshuxing;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.GetJsonRetcode;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.LogUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ${王俊强} on 2017/3/7.
 */

public class KTVModel extends IModel {
    /**
     * 53、获取KTV列表
     * get_ktv_list
     * 传入：sc_id lng 经度  lat 纬度
     *
     * @return
     */
    public Call getList(String sc_id, int page, double lng, double lat, final AsyncCallBack callBack) {
        FormBody body = new FormBody.Builder().add("sc_id", sc_id).add("page", page + "").
                add("lng", lng + "").add("lat", lat + "").build();
        Request request = new Request.Builder().url(HTTPURL.get_ktv_list).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runUiOnError(null, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LogUtils.d("获取KTV列表" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    List<KTV.DataBean> data = new Gson().fromJson(string, KTV.class).getData();
                    runUiOnSuccess(data, callBack);
                } else {
                    runUiOnError(GetJsonRetcode.getmsg(string), callBack);
                }
            }
        });
        return call;
    }

    /**
     * 54、获取ktv属性（预订）
     * get_book_ktvspec
     * 传入：store_id
     *
     * @return
     */
    public Call getShuXing(String store_id, final AsyncCallBack callBack) {
        LogUtils.d("ktv获取属性 参数=" + HTTPURL.get_book_ktvspec + "?store_id=" + store_id);
        FormBody body = new FormBody.Builder().add("store_id", store_id).build();
        Request request = new Request.Builder().url(HTTPURL.get_book_ktvspec).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runUiOnError(null, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LogUtils.d("获取ktv属性（预订）" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    KTVshuxing ktVshuxing = new Gson().fromJson(string, KTVshuxing.class);
                    List<KTVSort.RiqiBean> list = toBean(ktVshuxing.getData());
                    LogUtils.d("排完序后toBean=" + list.get(0).getDate());
                    List<KTVSort.RiqiBean> riqilist = sort(list);
                    List<KTVSort.SpecBean> splist = getSplist(ktVshuxing, "8");
                    KTVSort data = new KTVSort();
                    data.setRiqiBeen(riqilist);
                    LogUtils.d("排完序后sort=0" + riqilist.get(0).getDate());
                    data.setSpec(splist);
                    runUiOnSuccess(data, callBack);
                } else {
                    runUiOnError(GetJsonRetcode.getmsg(string), callBack);
                }
            }
        });
        return call;
    }

    /**
     * 55、通过属性获取ktv房间信息（预订）
     * ktv_house_byspec
     * 传入：goods_id   hsid   wkid
     *
     * @return
     */
    public Call getYunyue(String goods_id, String hsid, String wkid, final AsyncCallBack callBack) {
        if (TextUtils.isEmpty(goods_id) || TextUtils.isEmpty(hsid) || TextUtils.isEmpty(wkid)) {
            callBack.onError("参数缺失");
            return null;
        }
        FormBody body = new FormBody.Builder().add("goods_id", goods_id)
                .add("hsid", hsid).add("wkid", wkid).build();
        Request request = new Request.Builder().url(HTTPURL.ktv_house_byspec).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runUiOnError(null, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LogUtils.d("通过属性获取ktv房间信息（预订）" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    List<KTVYuyue.DataBean> data = new Gson().fromJson(string, KTVYuyue.class)
                            .getData();
                    runUiOnSuccess(data, callBack);
                } else {
                    runUiOnError(GetJsonRetcode.getmsg(string), callBack);
                }
            }
        });
        return call;
    }

    /**
     * 57、获取KTV团购属性列表
     * ktv_group_spec
     * 传入：goods_id
     */
    public Call getTuanGouShuxing(String goods_id, final AsyncCallBack callBack) {
        FormBody body = new FormBody.Builder().add("goods_id", goods_id).build();
        Request request = new Request.Builder().url(HTTPURL.ktv_group_spec).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runUiOnError(null, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LogUtils.d("获取KTV团购属性列表" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    KTVshuxing ktVshuxing = new Gson().fromJson(string, KTVshuxing.class);
                    List<KTVSort.RiqiBean> list = toBean(ktVshuxing.getData());
                    List<KTVSort.RiqiBean> riqilist = sort(list);
                    List<KTVSort.SpecBean> splist = getSplist(ktVshuxing, "9");
                    KTVSort data = new KTVSort();
                    data.setRiqiBeen(riqilist);
                    data.setSpec(splist);
                    runUiOnSuccess(data, callBack);
                } else {
                    runUiOnError(GetJsonRetcode.getmsg(string), callBack);
                }
            }
        });
        return call;
    }

    /**
     * 65、获取ktv团购
     * get_group_buy
     * 传入：goods_id   hrid （时间id）   wkid （默认 今天的id）
     */
    public Call getTuanList(String goods_id, String hrid, String wkid, final AsyncCallBack callBack) {
        LogUtils.i("团购list参数+" + goods_id + "," + hrid + "," + wkid);
        FormBody body = new FormBody.Builder().add("goods_id", goods_id).add("hrid", hrid).add("wkid", wkid).build();
        Request request = new Request.Builder().url(HTTPURL.get_group_buy).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runUiOnError(null, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LogUtils.d("获取ktv团购房间列表" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    List<KTVTuanList.DataBean> data = new Gson().fromJson(string, KTVTuanList.class).getData();
                    runUiOnSuccess(data, callBack);
                } else {
                    runUiOnError(GetJsonRetcode.getmsg(string), callBack);
                }
            }
        });
        return call;
    }

    /**
     * 76、获取KTV活动接口
     * get_actity_ktv
     */
    public Call getHuoDong(final AsyncCallBack callBack) {
        Request request = new Request.Builder().url(HTTPURL.get_actity_ktv).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runUiOnError(null, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LogUtils.d("获取KTV活动接口=" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    List<Home_HuoDong.DataBean> data = new Gson().fromJson(string, Home_HuoDong.class).getData();
                    runUiOnSuccess(data, callBack);
                } else {
                    runUiOnError(GetJsonRetcode.getmsg(string), callBack);
                }
            }
        });
        return call;
    }


    /**
     * 获取 标签集合 打包 小包
     *
     * @param ktVshuxing
     */
    private List<KTVSort.SpecBean> getSplist(KTVshuxing ktVshuxing, String index) {
        List<KTVSort.SpecBean> list = new ArrayList<>();
        for (int i = 0; i < ktVshuxing.getData().getSpec().size(); i++) {
            if (ktVshuxing.getData().getSpec().get(i).getSpec_id().equals(index)) {
                KTVshuxing.DataBean.SpecBean specBean = ktVshuxing.getData().getSpec().get(i);
                KTVSort.SpecBean data = new KTVSort.SpecBean();
                data.setId(specBean.getId());
                data.setItem(specBean.getItem());
                data.setSpec_id(specBean.getSpec_id());
                list.add(data);
            }
        }
        if (list.size() > 0) {
            list.get(0).setSekd(true);
        }
        return list;
    }

    /**
     * 排休
     *
     * @param sort
     */
    private List<KTVSort.RiqiBean> sort(List<KTVSort.RiqiBean> sort) {
        Set set = new TreeSet(new CustomerComparator());
        for (int i = 0; i < sort.size(); i++) {
            set.add(sort.get(i));
        }
        List<KTVSort.RiqiBean> list = new ArrayList<>();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            KTVSort.RiqiBean data = (KTVSort.RiqiBean) it.next();
            String string = data.getDate();
            String s = "";
            if (string.contains("-")) {
                s = string.replace(".", "-");
            }else{
                s = string;
            }
            data.setDate(s);
            list.add(data);
        }
        if (list.size() > 0) {
            list.get(0).setIdSechked(true);
        }
        return list;
    }

    /**
     * 转换double
     */
    private List<KTVSort.RiqiBean> toBean(KTVshuxing.DataBean dataBean) {
        List<KTVSort.RiqiBean> list = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            KTVSort.RiqiBean riqiBean = loadData(dataBean, i);
            list.add(riqiBean);
        }
        return list;
    }

    /**
     * @param dataBean
     * @param index
     * @return
     */
    private KTVSort.RiqiBean loadData(KTVshuxing.DataBean dataBean, int index) {
        KTVSort.RiqiBean data = new KTVSort.RiqiBean();
        switch (index) {
            case 0:
                data.setDate(dataBean.getRiqi().get_$0().getDate());
                data.setWeek(dataBean.getRiqi().get_$0().getWeek());
                break;
            case 1:
                data.setDate(dataBean.getRiqi().get_$1().getDate());
                data.setWeek(dataBean.getRiqi().get_$1().getWeek());
                break;
            case 2:
                data.setDate(dataBean.getRiqi().get_$2().getDate());
                data.setWeek(dataBean.getRiqi().get_$2().getWeek());
                break;
            case 3:
                data.setDate(dataBean.getRiqi().get_$3().getDate());
                data.setWeek(dataBean.getRiqi().get_$3().getWeek());
                break;
            case 4:
                data.setDate(dataBean.getRiqi().get_$4().getDate());
                data.setWeek(dataBean.getRiqi().get_$4().getWeek());
                break;
            case 5:
                data.setDate(dataBean.getRiqi().get_$5().getDate());
                data.setWeek(dataBean.getRiqi().get_$5().getWeek());
                break;
            case 6:
                data.setDate(dataBean.getRiqi().get_$6().getDate());
                data.setWeek(dataBean.getRiqi().get_$6().getWeek());
                break;
        }
        List<KTVshuxing.DataBean.SpecBean> spec = dataBean.getSpec();
        for (int i = 0; i < spec.size(); i++) {
            if (String.valueOf(index).equals(spec.get(i).getItem())) {
                data.setId(spec.get(i).getId());
                break;
            }
        }
        return data;
    }

    /**
     * 利用 TreeSet 客户化排序
     */
    private class CustomerComparator implements Comparator {

        @Override
        public int compare(Object object, Object o2) {
            double v = Double.parseDouble(((KTVSort.RiqiBean) object).getDate());
            double v1 = Double.parseDouble(((KTVSort.RiqiBean) o2).getDate());
            if (v > v1) {
                return 1;
            } else if (v < v1) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
