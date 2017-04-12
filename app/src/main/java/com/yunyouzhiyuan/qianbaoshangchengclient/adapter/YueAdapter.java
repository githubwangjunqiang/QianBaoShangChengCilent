package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Yuee;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/2/7.
 */
public class YueAdapter extends RecyclerView.Adapter<YueViewHolder> {
    private Context context;
    private List<Yuee.DataBean.ConsumeListBean> list;
    private LayoutInflater inflater;

    public YueAdapter(Context context, List<Yuee.DataBean.ConsumeListBean> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public YueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new YueViewHolder(inflater.inflate(R.layout.itme_yue_recyleview, parent, false));
    }

    @Override
    public void onBindViewHolder(YueViewHolder holder, int position) {
        Yuee.DataBean.ConsumeListBean data = list.get(position);
        String replace = data.getChange_time().replace(" ", "\n");
        holder.tvtime.setText(replace);
        holder.tvbeizhu.setText("备注\n"+data.getDesc());
        holder.tvyue.setText("金额\n"+data.getUser_money());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class YueViewHolder extends RecyclerView.ViewHolder {
    TextView tvtime, tvbeizhu,  tvyue;

    public YueViewHolder(View itemView) {
        super(itemView);
        tvtime = (TextView) itemView.findViewById(R.id.itme_yue_tvtime);
        tvbeizhu = (TextView) itemView.findViewById(R.id.itme_yue_tvtype);
        tvyue = (TextView) itemView.findViewById(R.id.itme_yue_tvyue);
    }
}
