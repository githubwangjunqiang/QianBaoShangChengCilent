package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/1/10.
 */
public class NearListViewAdapter extends MyAdapter<String> {

    public NearListViewAdapter(Activity context, List<String> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        ViewHolder holder = null;
        if (itemView == null) {
            holder = new ViewHolder();
            itemView = getLayoutInflater().inflate(R.layout.itme_near_recyle_view, null);
            holder.ivimage = (ImageView) itemView.findViewById(R.id.itme_near_recyle_vioew_ivimage);
            holder.tvname = (TextView) itemView.findViewById(R.id.itme_near_recyle_vioew_tvname);
            holder.tvjian = (TextView) itemView.findViewById(R.id.itme_near_recyle_vioew_tvtuijian);
            holder.tvwai = (TextView) itemView.findViewById(R.id.itme_near_recyle_vioew_tvwai);
            holder.tvfenshu = (TextView) itemView.findViewById(R.id.itme_near_recyle_vioew_tvfenshu);
            holder.tvjuli = (TextView) itemView.findViewById(R.id.itme_near_recyle_vioew_juli);
            holder.tvnumber = (TextView) itemView.findViewById(R.id.itme_near_recyle_vioew_tvnumber);
            holder.ratingBar = (RatingBar) itemView.findViewById(R.id.itme_near_recyle_vioew_ratbar);
            itemView.setTag(holder);
        }
        holder = (ViewHolder) itemView.getTag();

        return itemView;
    }

    public class ViewHolder {
        public ImageView ivimage;
        public TextView tvname, tvjian, tvwai, tvfenshu, tvaddress, tvjuli, tvnumber, tvjiashao;
        public RatingBar ratingBar;
    }
}
