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
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.CookListActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.ZiFenlei;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.GetWinDowHeight;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/1/11.
 */
public class CookRecyleViewTopOneAdapter extends RecyclerView.Adapter<CookRecyleViewTopOneViewHolder> {
    private Context context;
    private List<ZiFenlei.DataBean> list;
    private String scId;

    public CookRecyleViewTopOneAdapter(Context context, List<ZiFenlei.DataBean> list, String scId) {
        this.context = context;
        this.list = list;
        this.scId = scId;
    }

    @Override
    public CookRecyleViewTopOneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new CookRecyleViewTopOneViewHolder(LayoutInflater.from(context).inflate(R.layout.itme_cook_activity_top_one, parent, false));
    }

    @Override
    public void onBindViewHolder(CookRecyleViewTopOneViewHolder holder,  int position) {
        final  int pos = position;
        holder.tvname.setText(list.get(position).getMobile_name());
        ToGlide.url(context, HTTPURL.IMAGE + list.get(position).getImage(), holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CookListActivity.startCookListActivity(context,
                        scId,list.get(pos).getId(), "美食");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class CookRecyleViewTopOneViewHolder extends RecyclerView.ViewHolder {
    public TextView tvname;
    public ImageView imageView;

    public CookRecyleViewTopOneViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.itme_cook_activity_top_one_ivimage);
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.width = GetWinDowHeight.getScreenWeight(App.getContext()) / 4;
            imageView.setLayoutParams(layoutParams);
        }
        tvname = (TextView) itemView.findViewById(R.id.itme_cook_activity_top_one_tvname);

    }
}
