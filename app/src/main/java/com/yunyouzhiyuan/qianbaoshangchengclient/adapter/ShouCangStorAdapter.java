package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.CookStorinfoActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.FoodOutInfoActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.HotelActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.KTVStorInfoActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.ShopStorinfoActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.KTV;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.KTV_list_data;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.ShouCang;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.HomeModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.LoadingDialog;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ${王俊强} on 2017/2/6.
 */
public class ShouCangStorAdapter extends RecyclerView.Adapter<StorViewHolder> {
    private Context content;
    private List<ShouCang.DataBean> list;
    private LoadingDialog loadingDialog;

    public ShouCangStorAdapter(Context content, List<ShouCang.DataBean> list) {
        this.content = content;
        this.list = list;
        this.loadingDialog = new LoadingDialog(content);
    }

    @Override
    public StorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StorViewHolder(LayoutInflater.from(content).
                inflate(R.layout.itme_fragmentshoucang_stor, parent, false));
    }

    @Override
    public void onBindViewHolder(StorViewHolder holder, final int position) {
        final int posi = position;
        final ShouCang.DataBean data = list.get(position);
        ToGlide.url(content, HTTPURL.IMAGE + data.getStore_logo(), holder.ivimage);
        holder.tvinfo.setText(data.getStore_name() == null ? "" : data.getStore_name());
        holder.tvnumber.setText("已有" + (data.getCollectsum() == null ? "" : data.getCollectsum()) + "收藏");
        holder.tvinStor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toStartInfoActviity(posi);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 底部列表 点击跳转不同的详情页
     *
     * @param position
     */
    private void toStartInfoActviity(int position) {
        switch (list.get(position).getSc_id()) {
            case "1"://美食
                CookStorinfoActivity.startCookStorinfoActivity(content,
                        list.get(position).getStore_id());
                break;
            case "2"://外卖
                FoodOutInfoActivity.startFoodOutInfoActivity(content,
                        "",
                        list.get(position).getStore_id());
                break;
            case "3"://酒店
                HotelActivity.startHotelActivity(content, list.get(position).getSc_id());
                break;
            case "4"://KTV
                toKtv(position);
                break;
            default:
                ShopStorinfoActivity.startShopStorinfoActivity(content
                        , list.get(position).getStore_id());
                break;
        }
    }

    /**
     * 跳转KTV
     *
     * @param position
     */
    private void toKtv(final int position) {
        loadingDialog.show();
        new HomeModel().getHotelData(list.get(position).getStore_id(), new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (content == null) {
                    return;
                }
                KTV_list_data.DataBean data = (KTV_list_data.DataBean) obj;
                List<KTV.DataBean.GoodsListBean> list1 = new ArrayList<KTV.DataBean.GoodsListBean>();
                KTV.DataBean.GoodsListBean dataData = new KTV.DataBean.GoodsListBean();
                for (KTV_list_data.DataBean.TuangouBean ddata : data.getTuangou()) {
                    dataData.setGoods_id(ddata.getGoods_id());
                    dataData.setGoods_name(ddata.getGoods_name());
                    dataData.setMarket_price(ddata.getMarket_price());
                    dataData.setSales_sum(ddata.getSales_sum());
                    dataData.setShop_price(ddata.getShop_price());
                    dataData.setStore_cat_id1(ddata.getStore_cat_id1());
                    dataData.setOriginal_img(ddata.getOriginal_img());
                    dataData.setProm_type(ddata.getProm_type());
                    list1.add(dataData);
                }
                if (data.getYuding() == null || list1.isEmpty()) {
                    To.ee("该店铺的资料尚未完善");
                    loadingDialog.dismiss();
                    return;
                }
                KTVStorInfoActivity.startKTVStorInfoActivity(content,
                        list.get(position).getStore_id(), data.getYuding().getGoods_id(),
                        list1 );
                loadingDialog.dismiss();
            }

            @Override
            public void onError(Object obj) {
                if (content == null) {
                    return;
                }
                To.ee(obj);
                loadingDialog.dismiss();
            }
        });
    }
}

class StorViewHolder extends RecyclerView.ViewHolder {
    public ImageView ivimage;
    public TextView tvinfo, tvnumber, tvinStor;

    public StorViewHolder(View itemView) {
        super(itemView);
        ivimage = (ImageView) itemView.findViewById(R.id.itme_shoucang_stor_ivimage);
        tvinfo = (TextView) itemView.findViewById(R.id.itme_shoucang_stor_tvname);
        tvnumber = (TextView) itemView.findViewById(R.id.itme_shoucang_stor_tvnumber);
        tvinStor = (TextView) itemView.findViewById(R.id.itme_shoucang_stor_tvinstor);
    }
}
