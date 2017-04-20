package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Hotel;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/3/3.
 */

public class HotelDataAdapter extends RecyclerView.Adapter<HotelDataViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<Hotel.DataBean> list;
    private Callback callback;

    public interface Callback {
        void onItmeClick(String storId);
    }

    public HotelDataAdapter(Context context, List<Hotel.DataBean> list, Callback callback) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.list = list;
        this.callback = callback;
    }

    @Override
    public HotelDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HotelDataViewHolder(inflater.inflate(R.layout.itme_hotel_data_recyle, parent, false));
    }

    @Override
    public void onBindViewHolder(HotelDataViewHolder holder, final int position) {
        holder.setData(context, list.get(position));
        holder.llView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callback!=null){
                    callback.onItmeClick(list.get(position).getStore_id());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class HotelDataViewHolder extends RecyclerView.ViewHolder {
    ImageView ivIamge;
    TextView tvName,  tvPrice;
    LinearLayout llView;

    public HotelDataViewHolder(View itemView) {
        super(itemView);
        ivIamge = (ImageView) itemView.findViewById(R.id.hotel_data_ivimage);
        tvName = (TextView) itemView.findViewById(R.id.hotel_data_tvname);
//        tvContent = (TextView) itemView.findViewById(R.id.hotel_data_tvcontent);
        tvPrice = (TextView) itemView.findViewById(R.id.hotel_data_tvprice);
        llView = (LinearLayout) itemView.findViewById(R.id.dialog_hotel_data_ll);
    }

    public void setData(Context context, Hotel.DataBean data) {
        ToGlide.urlRound(context, HTTPURL.IMAGE + data.getStore_logo(), ivIamge, 4);
        tvName.setText(data.getStore_name() + "");
//        tvContent.setText(data.getSeo_description() + "");
        tvPrice.setText("人均价格：￥" + data.getGprice() + "\n距离：" + data.getDistance() + "km");
    }
}
