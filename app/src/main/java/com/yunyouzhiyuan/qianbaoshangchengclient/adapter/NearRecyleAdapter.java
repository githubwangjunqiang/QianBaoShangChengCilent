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
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.ShopStorinfoActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Specialty;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.util.List;


/**
 * Created by ${王俊强} on 2017/1/10.
 */
public class NearRecyleAdapter extends RecyclerView.Adapter<NearViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Specialty.DataBean> list;

    public NearRecyleAdapter(Context context, List<Specialty.DataBean> list) {
        this.context = context;
        this.list = list;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public NearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.itme_near_recyle_view, parent, false);
        NearViewHolder holder = new NearViewHolder(view, context);
        return holder;
    }

    @Override
    public void onBindViewHolder(NearViewHolder holder, final int position) {
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopStorinfoActivity.startShopStorinfoActivity(context,list.get(position).getStore_id());
            }
        });
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class NearViewHolder extends RecyclerView.ViewHolder {
    public ImageView ivimage;
    public TextView tvname, tvfenshu, tvjuli, tvnumber;
    public RatingBar ratingBar;
    public LinearLayout ll;
    private Context mContecxt;


    public NearViewHolder(View itemView, Context mContecxt) {
        super(itemView);
        ivimage = (ImageView) itemView.findViewById(R.id.itme_near_recyle_ivimage);
        ll = (LinearLayout) itemView.findViewById(R.id.itme_near_recyle__ll);
        tvname = (TextView) itemView.findViewById(R.id.itme_near_recyle_vioew_tvname);
        tvfenshu = (TextView) itemView.findViewById(R.id.itme_near_recyle_vioew_tvfenshu);
        tvjuli = (TextView) itemView.findViewById(R.id.itme_near_recyle_vioew_juli);
        tvnumber = (TextView) itemView.findViewById(R.id.itme_near_recyle_vioew_tvnumber);
        ratingBar = (RatingBar) itemView.findViewById(R.id.itme_near_recyle_vioew_ratbar);
        this.mContecxt = mContecxt;
    }

    public void setData(Specialty.DataBean data) {
        ToGlide.urlRound(mContecxt, HTTPURL.IMAGE + data.getStore_logo(), ivimage, 4);
        tvname.setText(data.getStore_name() == null ? "" : data.getStore_name());
        if (!TextUtils.isEmpty(data.getStore_desccredit())) {
            ratingBar.setRating(Float.parseFloat(data.getStore_desccredit()));
        }
        if (!TextUtils.isEmpty(data.getStore_desccredit())) {
            tvfenshu.setText(data.getStore_desccredit() + "分");
        }
        if (!TextUtils.isEmpty(data.getConsump_count())) {
            tvnumber.setText(data.getConsump_count() + "人消费");
        }
        tvjuli.setText("距离:" + data.getDistance() + "km");
    }
}
