package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.CookStorinfoActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.FoodOutInfoActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.TuiJian;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.GetWinDowHeight;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/1/11.
 */
public class CookActivityTopTowAdapter extends RecyclerView.Adapter<CookActivityTopTowViewHolder> {
    private Context context;
    private List<TuiJian.DataBean> list;
    private String type;//food  cook hotel happy ktv

    public CookActivityTopTowAdapter(Context context, List<TuiJian.DataBean> list, String type) {
        this.context = context;
        this.list = list;
        this.type = type;
    }

    @Override
    public CookActivityTopTowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new CookActivityTopTowViewHolder(LayoutInflater.from(context).inflate(R.layout.itme_cook_activity_top_tow, parent, false));
    }

    @Override
    public void onBindViewHolder(CookActivityTopTowViewHolder holder, final int position) {
        ToGlide.urlRound(context, HTTPURL.IMAGE + list.get(position).getStore_logo(), holder.imageView, 10);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (type) {
                    case "cook":
                        CookStorinfoActivity.startCookStorinfoActivity(context, list.get(position).getStore_id());
                        break;
                    case "food":
                        FoodOutInfoActivity.startFoodOutInfoActivity(context, null,
                                list.get(position).getStore_id());
                        break;
                    default:
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

class CookActivityTopTowViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;

    public CookActivityTopTowViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.itme_cook_activity_top_tow_ivimage);
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        if (layoutParams != null) {
            int w = (int) (GetWinDowHeight.getScreenWeight(App.getContext()) * 0.43);
            layoutParams.width = w;
            layoutParams.height = (int) (w * 0.6);
            imageView.setLayoutParams(layoutParams);
        }
    }
}