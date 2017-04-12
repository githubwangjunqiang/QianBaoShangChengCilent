package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.content.Context;
import android.content.Intent;
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

import java.util.List;

/**
 * Created by ${王俊强} on 2017/1/16.
 */
public class TianActivityRecyleAdapter extends RecyclerView.Adapter<TianViewHolder> {
    private Context context;
    private List<String> list;


    public TianActivityRecyleAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public TianViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TianViewHolder(LayoutInflater.from(context).inflate(R.layout.itme_tian_recyleview,parent,false));
    }

    @Override
    public void onBindViewHolder(TianViewHolder holder, int position) {
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ShopStorinfoActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class TianViewHolder extends RecyclerView.ViewHolder{
    ImageView imageView;
    TextView tvname,tvfenshu,tvadress,tvnumber;
    RatingBar bar;
    public LinearLayout ll;

    public TianViewHolder(View view) {
        super(view);
        imageView = (ImageView) view.findViewById(R.id.itme_tian_ivimage);
        ll = (LinearLayout) view.findViewById(R.id.itme_tian_ll);
        tvname = (TextView) view.findViewById(R.id.itme_tian_tvname);
        tvfenshu = (TextView) view.findViewById(R.id.itme_tian_tvfenshu);
        tvadress = (TextView) view.findViewById(R.id.itme_tian_adress);
        tvnumber = (TextView) view.findViewById(R.id.itme_tian_tvnumber);
        bar = (RatingBar) view.findViewById(R.id.itme_tian_bar);
    }
}
