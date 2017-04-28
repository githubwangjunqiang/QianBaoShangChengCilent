package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.FoodInfo;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/2/24.
 */

public class FoodOutDingdanAdapter extends MyAdapter<FoodInfo.DataBean.InfoBean> {

    public FoodOutDingdanAdapter(Activity context, List<FoodInfo.DataBean.InfoBean> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        FoodInfo.DataBean.InfoBean data = getData().get(position);
        ViewHolder holder;
        if (view == null) {
            view = getLayoutInflater().inflate(R.layout.itme_foodout_dingdan_listview, parent,false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        holder = (ViewHolder) view.getTag();
        holder.tvName.setText(data.getGoods_name() + "");
        holder.tvNumber.setText(data.getCount() + "个");
        holder.tvPrice.setText("总价￥："+data.getShop_price());
        return view;
    }

    private class ViewHolder {
        TextView tvName, tvNumber, tvPrice;

        public ViewHolder(View v) {
            tvName = (TextView) v.findViewById(R.id.itme_foodout_ding_tvname);
            tvNumber = (TextView) v.findViewById(R.id.itme_foodout_ding_tvnumber);
            tvPrice = (TextView) v.findViewById(R.id.itme_foodout_ding_tvprice);
        }
    }
}
