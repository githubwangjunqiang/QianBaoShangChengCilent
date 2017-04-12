package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.CookActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.FoodOutActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.HappyActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.HotelActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.KTVActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.StorListActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HomeStore_category;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.GetWinDowHeight;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/1/9.
 */
public class recyleViewCaidanAdapter extends RecyclerView.Adapter<CaidanViewHolder> {
    private Context context;
    private List<HomeStore_category.DataBean> list;

    public recyleViewCaidanAdapter(Context context, List<HomeStore_category.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public CaidanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itme_home_caidan_recyle, null);
        CaidanViewHolder holder = new CaidanViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CaidanViewHolder holder, final int position) {
        final HomeStore_category.DataBean data = list.get(position);
        holder.tvname.setText(data.getSc_name());
        ToGlide.url(context, HTTPURL.IMAGE + data.getSc_icon(), holder.ivimage);
        holder.ivimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (data.getSc_id()) {
                    case "1":
                        CookActivity.startCookActivity(context, data.getSc_id());//美食
                        break;
                    case "2":
                        FoodOutActivity.startFoodOutActivity(context, data.getSc_id());//外卖
                        break;
                    case "3":
                        HotelActivity.startHotelActivity(context, data.getSc_id());//酒店
                        break;
                    case "4":
                        KTVActivity.startKTVActivity(context, data.getSc_id());//KTV
                        break;
                    case "5":
                        HappyActivity.startHappyActivity(context, data.getSc_id());//休闲娱乐
                        break;
                    default:
                        StorListActivity.startActivity(context, data.getSc_name(),data.getSc_id());
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

class CaidanViewHolder extends RecyclerView.ViewHolder {
    public TextView tvname;
    public ImageView ivimage;

    public CaidanViewHolder(View itemView) {
        super(itemView);
        tvname = (TextView) itemView.findViewById(R.id.itme_home_caidan_recyle_tvname);
        ivimage = (ImageView) itemView.findViewById(R.id.itme_home_caidan_recyle_ivimage);
        ViewGroup.LayoutParams layoutParams = ivimage.getLayoutParams();
        layoutParams.width = GetWinDowHeight.getScreenWeight(App.getContext()) / 5;
        ivimage.setLayoutParams(layoutParams);

    }
}
