package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
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
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Nearby;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.HomeModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.LoadingDialog;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;


/**
 * Created by ${王俊强} on 2017/1/10.
 */
public class SpeciatlyRecyleAdapter extends RecyclerView.Adapter<SpiatlyViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Nearby.DataBean> list;
    private LoadingDialog loadingDialog;
    private HomeModel model;
    private Call hotelData;

    public SpeciatlyRecyleAdapter(Context context, List<Nearby.DataBean> list) {
        this.context = context;
        this.list = list;
        this.layoutInflater = LayoutInflater.from(context);
        loadingDialog = new LoadingDialog(context);
        model = new HomeModel();
        setLodingListener();
    }

    private void setLodingListener() {
        loadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (hotelData != null && !hotelData.isCanceled()) {
                        hotelData.cancel();
                    }
                }
                return false;
            }
        });
    }


    @Override
    public SpiatlyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.itme_near_recyle_view, parent, false);
        SpiatlyViewHolder holder = new SpiatlyViewHolder(view, context);
        return holder;
    }

    @Override
    public void onBindViewHolder(SpiatlyViewHolder holder, final int position) {
        holder.setData(list.get(position));
        holder.llBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toStartInfoActviity(list.get(position));
            }
        });
    }

    /**
     * 底部列表 点击跳转不同的详情页
     *
     * @param dataBean
     */
    private void toStartInfoActviity(Nearby.DataBean dataBean) {
        switch (dataBean.getSc_id()) {
            case "1"://美食
                CookStorinfoActivity.startCookStorinfoActivity(context,
                        dataBean.getStore_id());
                break;
            case "2"://外卖
                FoodOutInfoActivity.startFoodOutInfoActivity(context,
                        dataBean.getDistance() + "",
                        dataBean.getStore_id());
                break;
            case "3"://酒店
                HotelActivity.startHotelActivity(context
                        , dataBean.getSc_id());
                break;
            case "4"://KTV
                toKtv(dataBean);
                break;
            default:
                ShopStorinfoActivity.startShopStorinfoActivity(context
                        , dataBean.getStore_id());
                break;
        }
    }

    /**
     * 跳转KTV
     *
     * @param dataBean
     */
    private void toKtv(final Nearby.DataBean dataBean) {
        loadingDialog.show();
        hotelData = model.getHotelData(dataBean.getStore_id(), new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                KTV_list_data.DataBean data = (KTV_list_data.DataBean) obj;
                List<KTV.DataBean.GoodsListBean> list = new ArrayList<KTV.DataBean.GoodsListBean>();
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
                    list.add(dataData);
                }

                KTVStorInfoActivity.startKTVStorInfoActivity(context,
                        dataBean.getStore_id(), data.getYuding().getGoods_id(),
                        list);
                loadingDialog.dismiss();
            }

            @Override
            public void onError(Object obj) {
                To.ee(obj);
                loadingDialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class SpiatlyViewHolder extends RecyclerView.ViewHolder {
    public ImageView ivimage;
    public TextView tvname, tvjian, tvwai, tvfenshu, tvjuli, tvnumber;
    public RatingBar ratingBar;
    private Context context;
    public LinearLayout llBottom;

    public SpiatlyViewHolder(View itemView, Context context) {
        super(itemView);
        ivimage = (ImageView) itemView.findViewById(R.id.itme_near_recyle_ivimage);
        llBottom = (LinearLayout) itemView.findViewById(R.id.itme_near_recyle__ll);
        tvname = (TextView) itemView.findViewById(R.id.itme_near_recyle_vioew_tvname);
        tvjian = (TextView) itemView.findViewById(R.id.itme_near_recyle_vioew_tvtuijian);
        tvwai = (TextView) itemView.findViewById(R.id.itme_near_recyle_vioew_tvwai);
        tvfenshu = (TextView) itemView.findViewById(R.id.itme_near_recyle_vioew_tvfenshu);
        tvjuli = (TextView) itemView.findViewById(R.id.itme_near_recyle_vioew_juli);
        tvnumber = (TextView) itemView.findViewById(R.id.itme_near_recyle_vioew_tvnumber);
        ratingBar = (RatingBar) itemView.findViewById(R.id.itme_near_recyle_vioew_ratbar);
        this.context = context;
    }

    public void setData(Nearby.DataBean data) {
        ToGlide.url(context, HTTPURL.IMAGE + data.getStore_logo(), ivimage);
        tvname.setText(data.getStore_name() + "");
        tvfenshu.setText(data.getStore_desccredit() + "分");
        ratingBar.setRating(Float.parseFloat(data.getStore_desccredit()));
        tvnumber.setText(data.getConsump_count() + "人消费");
        tvjuli.setText("距离：" + data.getDistance() + "km");
    }

}