package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Hotel_Fagnjian;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.Text_Size;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/3/6.
 */

public class HotelFangjianAdapter extends MyAdapter<Hotel_Fagnjian.DataBean> {
    public HotelFangjianAdapter(Context context, List<Hotel_Fagnjian.DataBean> data, Callback callback) {
        super(context, data);
        this.callback = callback;
    }

    private Callback callback;

    public interface Callback {
        void onClick(String id, String title, String price,int poition);

        void onClickBtn(int position);

    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = getLayoutInflater().inflate(R.layout.itme_hotel_fangjian_listview, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        holder = (ViewHolder) view.getTag();
        holder.setData(getData().get(position));
        holder.tvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClick(getData().get(position).getGoods_id(),
                        getData().get(position).getGoods_name(),
                        getData().get(position).getShop_price(),position);
            }
        });
        holder.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClickBtn(position);
            }
        });
        return view;
    }

    private class ViewHolder {
        TextView tvContent;
        Button btnOk;

        public ViewHolder(View view) {
            tvContent = (TextView) view.findViewById(R.id.itme_hotel_tvcontent);
            btnOk = (Button) view.findViewById(R.id.itme_hotel_btnyuyue);
        }

        public void setData(Hotel_Fagnjian.DataBean data) {
            /**
             * 清新绿色主题房\n双床房|不含早|有窗\n￥000\n房间数：00
             */
            StringBuffer buffer = new StringBuffer();
            String name = data.getGoods_name() + "\n";
            buffer.append(name);
            String biaoqian = data.getKeywords() + "\n";
            buffer.append(biaoqian);
            String price = "￥" + data.getShop_price() + "\n";
            buffer.append(price);
            String fangjainshu = "房间数:" + data.getStore_count() + "\t\t类型：" + (data.getCat_id2().equals("50") ? "全日制" : "钟点房");
            buffer.append(fangjainshu);
            Text_Size.setSizeFour(tvContent, buffer.toString(), 0, name.length(), R.color.text_color, 14
                    , name.length(), name.length() + biaoqian.length(), R.color.text_color_alp, 12,
                    name.length() + biaoqian.length(), name.length() + biaoqian.length() + price.length(),
                    R.color.blue_p, 16, name.length() + biaoqian.length() + price.length(), buffer.length()
                    , R.color.app_color, 13);
        }
    }
}
