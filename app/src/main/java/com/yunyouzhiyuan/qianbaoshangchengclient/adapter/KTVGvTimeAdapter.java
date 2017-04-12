package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.KTVgvTime;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.LogUtils;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/3/8.
 */

public class KTVGvTimeAdapter extends MyAdapter<KTVgvTime> {
    private Callback callback;

    public KTVGvTimeAdapter(Context context, List<KTVgvTime> data, Callback callback) {
        super(context, data);
        this.callback = callback;
    }

    public interface Callback {
        void onClick(int position, String time);
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = getLayoutInflater().inflate(R.layout.itme_ktv_gvtime, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        holder = (ViewHolder) view.getTag();
        holder.tv.setText(getData().get(position).getTime());
        holder.tv.setSelected(getData().get(position).isChecked());
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!v.isSelected()) {
                    toChecked(position);
                    callback.onClick(position, getData().get(position).getTime());
                }
            }
        });
        return view;
    }

    private class ViewHolder {
        TextView tv;

        public ViewHolder(View v) {
            tv = (TextView) v.findViewById(R.id.ktv_time_tvtime);
        }
    }

    /**
     * 重置
     *
     * @param position
     */
    private void toChecked(int position) {
        for (KTVgvTime time : getData()) {
            time.setChecked(false);
        }
        LogUtils.d("position="+position+"/getdata().size="+getData().size());
        if (getData().size() > position) {
            getData().get(position).setChecked(true);
            notifyDataSetChanged();
        } else {
            To.ee("数据出错");
        }

    }
}
