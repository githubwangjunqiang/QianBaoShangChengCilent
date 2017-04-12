package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.KTVTuanList;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/3/9.
 */

public class TuanListAdapter extends MyAdapter<KTVTuanList.DataBean> {
    public TuanListAdapter(Context context, List<KTVTuanList.DataBean> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = getLayoutInflater().inflate(R.layout.itme_tuanlist_listview, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        holder = (ViewHolder) view.getTag();
        holder.setData(getData().get(position));
        return view;
    }

    private class ViewHolder {
        TextView tvName, tvPrice, tvNumber;

        public ViewHolder(View view) {
            tvName = (TextView) view.findViewById(R.id.itme_tuanlist_tvname);
            tvPrice = (TextView) view.findViewById(R.id.itme_tuanlist_tvprice);
            tvNumber = (TextView) view.findViewById(R.id.itme_tuanlist_tvnumber);
        }

        public void setData(KTVTuanList.DataBean data) {
            tvName.setText(data.getHouse() + "");
            tvPrice.setText("价格￥:" + data.getPrice());
            tvNumber.setText("1张");
        }

    }
}
