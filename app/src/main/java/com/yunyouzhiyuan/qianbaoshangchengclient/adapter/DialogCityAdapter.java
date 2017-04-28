package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.CityHotel;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.LogUtils;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/3/3.
 */

public class DialogCityAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private List<CityHotel.DataBean> list;
    private Callback callback;

    public interface Callback {
        void callBack(String city_id, String level, String name);
    }

    public DialogCityAdapter(Context context, List<CityHotel.DataBean> data, Callback callback) {
        this.context = context;
        this.list = data;
        this.callback = callback;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        TextView tvname;
        tvname = (TextView) LayoutInflater.from(context)
                .inflate(R.layout.itme_sialog_hotel_city, parent, false);

        return new MyViewHolder(tvname);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final int posi = position;
        try {
            holder.textView.setText(list.get(position).getName());
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.callBack(list.get(posi).getId(), list.get(posi).getLevel(),
                            list.get(posi).getName());
                }
            });
        } catch (RuntimeException e) {
            LogUtils.e(e.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {
    TextView textView;

    public MyViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView;
    }
}
