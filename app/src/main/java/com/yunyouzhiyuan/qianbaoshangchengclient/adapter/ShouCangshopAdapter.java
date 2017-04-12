package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.ShouCangShop;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/2/6.
 */
public class ShouCangshopAdapter extends RecyclerView.Adapter<ShopViewHolder> {
    private List<ShouCangShop.DataBean> list;
    private Context context;
    public ShouCangshopAdapter(List<ShouCangShop.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShopViewHolder(LayoutInflater.from(context).
                inflate(R.layout.itme_fragmentshoucangshopp,parent,false));
    }

    @Override
    public void onBindViewHolder(ShopViewHolder holder, int position) {
        ShouCangShop.DataBean data = list.get(position);
        ToGlide.urlRound(context, HTTPURL.IMAGE+data.getOriginal_img(),holder.ivimage,8);
        holder.tvname.setText(data.getGoods_name()==null?"":data.getGoods_name());
        holder.tvoldprice.setText("商品价格￥"+data.getShop_price());
        holder.ivimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ShopInfoActivity.startShopInfoActivity(context,"");
            }
        });
        holder.tvbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                To.oo("去购买");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class ShopViewHolder extends RecyclerView.ViewHolder {
    ImageView ivimage;
    TextView tvname,tvprice,tvoldprice,tvbuy;
    public ShopViewHolder(View view) {
        super(view);
        ivimage = (ImageView) view.findViewById(R.id.itme_shoucang_shop_ivimage);
        tvname = (TextView) view.findViewById(R.id.itme_shoucang_shop_tvname);
        tvprice = (TextView) view.findViewById(R.id.itme_shoucang_shop_tvprice);
        tvoldprice = (TextView) view.findViewById(R.id.itme_shoucang_shop_tvoldprice);
        tvbuy = (TextView) view.findViewById(R.id.itme_shoucang_shop_tvbuy);
    }
}
