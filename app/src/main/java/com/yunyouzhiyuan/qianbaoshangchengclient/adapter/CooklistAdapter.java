package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.CookStorinfoActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.KTVStorInfoActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.ShopStorinfoActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Food_Bottom;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.KTV;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.KTV_list_data;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.HomeModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.LoadingDialog;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.util.ArrayList;
import java.util.List;

import static com.yunyouzhiyuan.qianbaoshangchengclient.activity.FoodOutInfoActivity.startFoodOutInfoActivity;

/**
 * Created by ${王俊强} on 2017/1/11.
 */
public class CooklistAdapter extends RecyclerView.Adapter<CookListViewHolder> {
    private Context context;
    private List<Food_Bottom.DataBean> list;
    private String type;
    private LoadingDialog loadingDialog;

    public CooklistAdapter(Context context, List<Food_Bottom.DataBean> list, String type) {
        this.context = context;
        this.list = list;
        this.type = type;
    }

    @Override
    public CookListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CookListViewHolder(LayoutInflater.from(context).
                inflate(R.layout.itme_cook_list_recyle, parent, false));
    }

    @Override
    public void onBindViewHolder(CookListViewHolder holder, final int position) {
        final Food_Bottom.DataBean data = list.get(position);
        ToGlide.url(context, HTTPURL.IMAGE + data.getStore_logo(), holder.ivimage);
        holder.tvname.setText(data.getStore_name() + "" + "\n人均消费￥" + data.getProm_name());
        if (data.getStore_desccredit() != null) {
            holder.ratingBar.setRating(Float.parseFloat(data.getStore_desccredit()));
            holder.tvfenshu.setText(data.getStore_desccredit());
        }
        holder.tvjuli.setText("距离：" + data.getDistance());
        holder.tvnumber.setText(data.getConsump_count() + "人消费");

        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (type) {
                    case "美食":
                        CookStorinfoActivity.startCookStorinfoActivity(context, data.getStore_id());
                        break;
                    case "外卖":
                        startFoodOutInfoActivity(context, data.getSeo_description(), data.getStore_id());
                    case "ktv":
                        toKtv(position);
                        break;
                    default:
                        ShopStorinfoActivity.startShopStorinfoActivity(context, data.getStore_id());
                        break;
                }
            }
        });
    }

    /**
     * 跳转KTV
     *
     * @param position
     */
    private void toKtv(final int position) {
        if(loadingDialog == null){
            loadingDialog = new LoadingDialog(context);
        }
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
                    if(loadingDialog != null){
                        loadingDialog.dismiss();
                    }
                    return;
                }
                KTVStorInfoActivity.startKTVStorInfoActivity(context,
                        list.get(position).getStore_id(),
                        data.getYuding().getGoods_id(),
                        list1);
                if(loadingDialog != null){
                    loadingDialog.dismiss();
                }
            }

            @Override
            public void onError(Object obj) {
                if (context == null) {
                    return;
                }
                To.ee(obj);
                if(loadingDialog != null){
                    loadingDialog.dismiss();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class CookListViewHolder extends RecyclerView.ViewHolder {
    public ImageView ivimage;
    public TextView tvname, tvfenshu, tvjuli, tvnumber;
    public RatingBar ratingBar;
    public LinearLayout ll;

    public CookListViewHolder(View itemView) {
        super(itemView);
        ll = (LinearLayout) itemView.findViewById(R.id.itme__recyle_ll);
        ivimage = (ImageView) itemView.findViewById(R.id.itme_near_recyle_vioew_ivimage);
        tvname = (TextView) itemView.findViewById(R.id.itme_near_tvname);
        tvfenshu = (TextView) itemView.findViewById(R.id.itme_near_tvfenshu);
        tvjuli = (TextView) itemView.findViewById(R.id.itme_near_tvjuli);
        tvnumber = (TextView) itemView.findViewById(R.id.itme_near_tvnumber);
        ratingBar = (RatingBar) itemView.findViewById(R.id.itme_near_ratingbar);
    }

}