package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.ShopInfoActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.StorInfo;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.StorInfoList;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.ToShopinfo;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/1/16.
 */
public class StorinfoRecyleAdapter extends RecyclerView.Adapter<StorinfoViewHolder> {
    private Context context;
    private List<StorInfoList.DataBean.GoodsListBean> list;
    private StorInfo DataStor;

    public StorinfoRecyleAdapter(Context context, List<StorInfoList.DataBean.GoodsListBean> list,
                                 StorInfo stor) {
        this.context = context;
        this.list = list;
        this.DataStor = stor;
    }

    @Override
    public StorinfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StorinfoViewHolder(LayoutInflater.from(context).inflate(R.layout.itme_stor_recyleview, parent, false));
    }

    @Override
    public void onBindViewHolder(StorinfoViewHolder holder, int position) {
        final StorInfoList.DataBean.GoodsListBean data = list.get(position);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToShopinfo dat = new ToShopinfo();
                dat.setGoodsId(data.getGoods_id() + "");
                dat.setImageUrl(data.getOriginal_img() + "");
                dat.setSalesSum(data.getSales_sum() + "");
                dat.setShopNmae(data.getGoods_name() + "");
                dat.setShopPingFen(Float.parseFloat(data.getCommentsum() == null ? "0" : data.getCommentsum()));
                dat.setStorId(DataStor.getData().getStore_id() + "");
                dat.setShopPrice(data.getPrice() + "");
                dat.setStorName(DataStor.getData().getStore_name() + "");
                dat.setStorType("");
                dat.setLat(DataStor.getData().getLat());
                dat.setLng(DataStor.getData().getLng());
                ShopInfoActivity.startShopInfoActivity(context, dat);
            }
        });
        ToGlide.url(context, HTTPURL.IMAGE + data.getOriginal_img(), holder.imageView);
        holder.tvname.setText(data.getGoods_name());
        String stas = data.getCommentsum();
        if (!TextUtils.isEmpty(stas)) {
            holder.bar.setRating(Float.parseFloat(stas));
            String substring = stas.substring(0, stas.lastIndexOf(".") + 2);
            holder.tvfenshu.setText(substring);
        }
        holder.tvnumber.setText(data.getSales_sum() + "人消费");
        holder.tvwifi.setText(data.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class StorinfoViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView tvname, tvfenshu, tvnumber, tvwifi;
    RatingBar bar;
    LinearLayout layout;

    public StorinfoViewHolder(View view) {
        super(view);
        imageView = (ImageView) view.findViewById(R.id.itme_stor_ivimage);
        tvname = (TextView) view.findViewById(R.id.itme_stor_tvname);
        tvfenshu = (TextView) view.findViewById(R.id.itme_stor_tvfenshu);
        tvnumber = (TextView) view.findViewById(R.id.itme_stor_tvnumber);
        tvwifi = (TextView) view.findViewById(R.id.itme_stor_tvwifi);
        bar = (RatingBar) view.findViewById(R.id.itme_tian_bar);
        layout = (LinearLayout) view.findViewById(R.id.itme_stor_layout);
    }
}
