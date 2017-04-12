package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.City_id;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/3/10.
 */

public class CityGvAdapter extends MyAdapter<City_id.DataBean> {
    public CityGvAdapter(Context context, List<City_id.DataBean> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = getLayoutInflater().inflate(R.layout.itme_cityid_gv, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        holder = (ViewHolder) view.getTag();
        holder.setData(getData().get(position));
        return view;
    }

    private class ViewHolder {
        TextView tvName;

        public ViewHolder(View view) {
            tvName = (TextView) view.findViewById(R.id.itme_city_gv_tvname);
        }

        public void setData(City_id.DataBean data) {
            tvName.setText(data.getName());
        }
    }
}
