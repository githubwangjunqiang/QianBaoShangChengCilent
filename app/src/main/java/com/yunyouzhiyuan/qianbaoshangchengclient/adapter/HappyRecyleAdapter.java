package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.ShopStorinfoActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Food_Bottom;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.AutoImageView;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/1/14.
 */
public class HappyRecyleAdapter extends RecyclerView.Adapter<HappyViuewHolder> {
    private Context context;
    private List<Food_Bottom.DataBean> list;

    public HappyRecyleAdapter(Context context, List<Food_Bottom.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public HappyViuewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HappyViuewHolder(LayoutInflater.from(context).inflate(R.layout.itme_happy_recyle, parent, false));
    }

    @Override
    public void onBindViewHolder(HappyViuewHolder holder, int position) {
        final Food_Bottom.DataBean data = list.get(position);
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopStorinfoActivity.startShopStorinfoActivity(context,data.getStore_id());
            }
        });
        ToGlide.urlRound(context, HTTPURL.IMAGE + data.getStore_logo(), holder.imageView, 4);
        holder.bar.setRating(Float.parseFloat(data.getStore_desccredit() + ""));
        holder.tvfenshu.setText(data.getStore_desccredit() + "分");
        holder.tvname.setText(data.getStore_name());
        holder.tvprice.setText("已售：" + data.getConsump_count());
        holder.tvinfo.setText(data.getSeo_description() + "");
        holder.tvm.setText("距离：" + data.getDistance() + "km");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class HappyViuewHolder extends RecyclerView.ViewHolder {
    AutoImageView imageView;
    TextView tvname, tvprice, tvinfo, tvfenshu, tvm;
    RatingBar bar;
    LinearLayout ll;

    public HappyViuewHolder(View itemView) {
        super(itemView);
        tvname = (TextView) itemView.findViewById(R.id.itme_happy_recyle_tvname);
        ll = (LinearLayout) itemView.findViewById(R.id.itme_happy_recyle_ll);
        tvprice = (TextView) itemView.findViewById(R.id.itme_happy_recyle_tvprice);
        tvinfo = (TextView) itemView.findViewById(R.id.itme_happy_recyle_tvinfo);
        tvfenshu = (TextView) itemView.findViewById(R.id.itme_happy_recyle_tvstas);
        tvm = (TextView) itemView.findViewById(R.id.itme_happy_recyle_tvm);
        imageView = (AutoImageView) itemView.findViewById(R.id.itme_happy_recyle_image);
        bar = (RatingBar) itemView.findViewById(R.id.itme_happy_recyle_bar);
    }
}
