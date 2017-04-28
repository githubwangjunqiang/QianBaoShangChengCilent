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
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.ShopStorinfoActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Food_Bottom;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/2/13.
 */

public class StorListAdapter extends RecyclerView.Adapter<ListViewHolder> {
    private Context context;
    private List<Food_Bottom.DataBean> list;

    public StorListAdapter(Context context, List<Food_Bottom.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListViewHolder(LayoutInflater.from(context).
                inflate(R.layout.itme_cook_actiity_buttom_recv, parent, false));

    }

    @Override
    public void onBindViewHolder(ListViewHolder holder,  int position) {
        final int posi = position;
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopStorinfoActivity.startShopStorinfoActivity(context,
                        list.get(posi).getStore_id());
            }
        });
        ToGlide.urlRound(context, HTTPURL.IMAGE + list.get(position).getStore_logo(),
                holder.ivimage, 4);
        holder.tvname.setText(list.get(position).getStore_name() + "");
        holder.ratingBar.setRating(Float.parseFloat(list.get(position).getStore_desccredit() + ""));
        holder.tvfenshu.setText(list.get(position).getStore_desccredit() + "分");
        holder.tvnumber.setText(list.get(position).getConsump_count() + "人消费");
        holder.tvjuli.setText("距离：" + list.get(position).getDistance());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class ListViewHolder extends RecyclerView.ViewHolder {
    public ImageView ivimage;
    public TextView tvname,  tvfenshu,  tvjuli, tvnumber;
    public RatingBar ratingBar;
    public LinearLayout ll;

    public ListViewHolder(View itemView) {
        super(itemView);
        ll = (LinearLayout) itemView.findViewById(R.id.itme_near_recyle_vioew_ll);
        ivimage = (ImageView) itemView.findViewById(R.id.itme_near_recyle_vioew_ivimage);
        tvname = (TextView) itemView.findViewById(R.id.itme_near_tvname);
        tvfenshu = (TextView) itemView.findViewById(R.id.itme_near_tvfenshu);
        tvjuli = (TextView) itemView.findViewById(R.id.itme_near_tvjuli);
        tvnumber = (TextView) itemView.findViewById(R.id.itme_near_tvnumber);
        ratingBar = (RatingBar) itemView.findViewById(R.id.itme_near_ratingbar);
    }
}
