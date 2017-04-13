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
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.ShopStorinfoActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Food_Bottom;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.util.List;

import static com.yunyouzhiyuan.qianbaoshangchengclient.activity.FoodOutInfoActivity.startFoodOutInfoActivity;

/**
 * Created by ${王俊强} on 2017/1/11.
 */
public class CooklistAdapter extends RecyclerView.Adapter<CookListViewHolder> {
    private Context context;
    private List<Food_Bottom.DataBean> list;
    private String type;

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
    public void onBindViewHolder(CookListViewHolder holder, int position) {
        final Food_Bottom.DataBean data = list.get(position);
        ToGlide.url(context, HTTPURL.IMAGE + data.getStore_logo(), holder.ivimage);
        holder.tvname.setText(data.getStore_name() + "" + "\n" + data.getSeo_description());
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
                        break;
                    case "休闲娱乐":
                        ShopStorinfoActivity.startShopStorinfoActivity(context, data.getStore_id());
                        break;
                    default:
                        ShopStorinfoActivity.startShopStorinfoActivity(context, data.getStore_id());
                        break;
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