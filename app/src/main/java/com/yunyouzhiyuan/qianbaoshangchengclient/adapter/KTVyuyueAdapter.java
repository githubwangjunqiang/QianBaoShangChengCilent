package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.KTVYuyue;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.Text_Size;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/3/8.
 */

public class KTVyuyueAdapter extends MyAdapter<KTVYuyue.DataBean> {
    public KTVyuyueAdapter(Context context, List<KTVYuyue.DataBean> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = getLayoutInflater().inflate(R.layout.itme_ktv_yuyue_listview, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        holder = (ViewHolder) view.getTag();
        holder.setData(getData().get(position));
        return view;
    }

    private class ViewHolder {
        TextView tvName;
        TextView btnOk;

        public ViewHolder(View view) {
            tvName = (TextView) view.findViewById(R.id.itme_ktv_yuyue_tvname);
            btnOk = (TextView) view.findViewById(R.id.itme_ktv_yuyue_btn);
        }

        public void setData(KTVYuyue.DataBean data) {
            String name = data.getHousename() + "\n￥";
            String price = data.getPrice() + "\t\t\t";
            String time = "时间：" + data.getHour();
            StringBuffer buffer = new StringBuffer();
            buffer.append(name).append(price).append(time);
            Text_Size.setSizeThress(App.getContext(), tvName, buffer.toString(), 0, name.length()
                    , "#646464", 14, name.length(), name.length() + price.length(), "#05a880", 18,
                    name.length() + price.length(), buffer.length(), "#bb646464", 13);
        }
    }
}
