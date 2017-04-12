package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.KTVSort;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/3/7.
 */

public class KTVBiaoqianAdapter extends RecyclerView.Adapter<KTVBiaoqianHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<KTVSort.SpecBean> list;
    private Callback callback;

    public KTVBiaoqianAdapter(Context context, List<KTVSort.SpecBean> list, Callback callback) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
        this.callback = callback;
    }

    public interface Callback {
        void onClick(int position);
    }

    @Override
    public KTVBiaoqianHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new KTVBiaoqianHolder(inflater.inflate(R.layout.itme_ktv_sp, parent, false));
    }

    @Override
    public void onBindViewHolder(KTVBiaoqianHolder holder, final int position) {
        holder.textView.setText(list.get(position).getItem());
        holder.textView.setSelected(list.get(position).isSekd());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!v.isSelected()) {
                    callback.onClick(position);
                    notfData(position);
                }
            }
        });
    }

    /**
     * 重置
     */
    private void notfData(int position) {
        for (KTVSort.SpecBean data : list) {
            data.setSekd(false);
        }
        list.get(position).setSekd(true);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class KTVBiaoqianHolder extends RecyclerView.ViewHolder {
    TextView textView;

    public KTVBiaoqianHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.itme_ktv_sp_tv);
    }
}
