package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.CityHotel;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/3/3.
 */

public class DialogCityAdapter extends MyAdapter<CityHotel.DataBean> {

    public DialogCityAdapter(Context context, List<CityHotel.DataBean> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        TextView tvname;
        if (view == null) {
            tvname = (TextView) getLayoutInflater().inflate(R.layout.itme_sialog_hotel_city, null);
            view = tvname;
        }
        tvname = (TextView) view;
        tvname.setText(getData().get(position).getName());
        return tvname;
    }
}
