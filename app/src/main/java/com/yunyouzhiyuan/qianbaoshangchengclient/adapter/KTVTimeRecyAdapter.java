package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.KTVSort;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.GetWinDowHeight;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.LogUtils;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/3/7.
 */

public class KTVTimeRecyAdapter extends RecyclerView.Adapter<KTVTimeHolder> {
    private List<KTVSort.RiqiBean> list;
    private Context context;
    private LayoutInflater inflater;
    private Callback callback;

    public KTVTimeRecyAdapter(List<KTVSort.RiqiBean> list, Context context, Callback callback) {
        this.list = list;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.callback = callback;
    }

    @Override
    public KTVTimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new KTVTimeHolder(inflater.inflate(R.layout.itme_ktv_time_recy, parent, false));
    }

    @Override
    public void onBindViewHolder(KTVTimeHolder holder, final int position) {
        LogUtils.d("日期对象=" + list.get(position).getDate());
        holder.textView.setText(list.get(position).getWeek() + "\n" + list.get(position).getDate());
        holder.textView.setSelected(list.get(position).isIdSechked());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!v.isSelected()) {
                    callback.onClick(position);
                    notyfData(position);
                }
            }
        });
    }

    /**
     * 充值选中
     *
     * @param position
     */
    private void notyfData(int position) {
        for (KTVSort.RiqiBean data : list) {
            data.setIdSechked(false);
        }
        list.get(position).setIdSechked(true);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface Callback {
        void onClick(int position);
    }
}

class KTVTimeHolder extends RecyclerView.ViewHolder {
    TextView textView;

    public KTVTimeHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.itme_ltv_time_tv);
        ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.width = GetWinDowHeight.getScreenWeight(App.getContext()) / 5;
            textView.setLayoutParams(layoutParams);
        }
    }
}
