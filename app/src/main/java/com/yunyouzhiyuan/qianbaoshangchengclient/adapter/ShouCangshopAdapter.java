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
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.ShouCangShop;
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
public class ShouCangshopAdapter extends RecyclerView.Adapter<ShopViewHolder> {
    private List<ShouCangShop.DataBean> list;
    private Context context;
    private LoadingDialog loadingDialog;

    public ShouCangshopAdapter(List<ShouCangShop.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
        this.loadingDialog = new LoadingDialog(context);
    }

    @Override
    public ShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShopViewHolder(LayoutInflater.from(context).
                inflate(R.layout.itme_fragmentshoucangshopp, parent, false));
    }

    @Override
    public void onBindViewHolder(ShopViewHolder holder, final int position) {
        ShouCangShop.DataBean data = list.get(position);
        ToGlide.urlRound(context, HTTPURL.IMAGE + data.getOriginal_img(), holder.ivimage, 8);
        holder.tvname.setText(data.getGoods_name() == null ? "" : data.getGoods_name());
        holder.tvoldprice.setText("商品价格￥" + data.getShop_price());
        holder.tvbuy.setText(R.string.qushangjiadianpu);
        holder.tvbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toStartInfoActviity(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 底部列表 点击跳转不同的详情页
     * @param position
     */
    private void toStartInfoActviity(int position) {
        if (list.get(position).getSc_id() == null) {
            return;
        }
        switch (list.get(position).getSc_id()) {
            case "1"://美食
                CookStorinfoActivity.startCookStorinfoActivity(context,
                        list.get(position).getStore_id());
                break;
            case "2"://外卖
                FoodOutInfoActivity.startFoodOutInfoActivity(context,
                        "",
                        list.get(position).getStore_id());
                break;
            case "3"://酒店
                HotelActivity.startHotelActivity(context, list.get(position).getSc_id());
                break;
            case "4"://KTV
                toKtv(position);
                break;
            default:
                ShopStorinfoActivity.startShopStorinfoActivity(context
                        , list.get(position).getStore_id());
                break;
        }
    }

    /**
     * 跳转KTV
     *dfdfdffsdfsdf
     * @param position
     */
    private void toKtv(final int position) {
        loadingDialog.show();
        new HomeModel().getHotelData(list.get(position).getStore_id(), new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (context == null) {
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
                KTVStorInfoActivity.startKTVStorInfoActivity(context,
                        list.get(position).getStore_id(), data.getYuding().getGoods_id(),
                        list1);
                loadingDialog.dismiss();
            }

            @Override
            public void onError(Object obj) {
                if (context == null) {
                    return;
                }
                To.ee(obj);
                loadingDialog.dismiss();
            }
        });
    }

}


class ShopViewHolder extends RecyclerView.ViewHolder {
    ImageView ivimage;
    TextView tvname, tvprice, tvoldprice, tvbuy;

    public ShopViewHolder(View view) {
        super(view);
        ivimage = (ImageView) view.findViewById(R.id.itme_shoucang_shop_ivimage);
        tvname = (TextView) view.findViewById(R.id.itme_shoucang_shop_tvname);
        tvprice = (TextView) view.findViewById(R.id.itme_shoucang_shop_tvprice);
        tvoldprice = (TextView) view.findViewById(R.id.itme_shoucang_shop_tvoldprice);
        tvbuy = (TextView) view.findViewById(R.id.itme_shoucang_shop_tvbuy);
    }
}
