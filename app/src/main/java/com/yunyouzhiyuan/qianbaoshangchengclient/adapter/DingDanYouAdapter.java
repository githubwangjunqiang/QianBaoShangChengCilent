package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.DingDanYouhuiquan;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.CouponBgView;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.Text_Size;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/2/24.
 */

public class DingDanYouAdapter extends RecyclerView.Adapter<YouViewHolder> {
    private Context context;
    private List<DingDanYouhuiquan.DataBean> list;
    private LayoutInflater inflater;
    private Callback callback;

    public interface Callback {
        void onClick(String id,String name);
    }

    public DingDanYouAdapter(Context context, List<DingDanYouhuiquan.DataBean> list, LayoutInflater inflater, Callback callback) {
        this.context = context;
        this.list = list;
        this.inflater = inflater;
        this.callback = callback;
    }

    @Override
    public YouViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new YouViewHolder(inflater.inflate(R.layout.itme_dingdan_youhuiquan_recyleview, parent, false));
    }

    @Override
    public void onBindViewHolder(YouViewHolder holder, int position) {
        final DingDanYouhuiquan.DataBean data = list.get(position);
        String a = "￥" + data.getMoney()+"\n";
        String b = "\t\t" + data.getName() + "\n";
        String d = data.getCondition();
        String str = a + b + d;
        Text_Size.setSizeThress(null, holder.tvTitle, str, 0, a.length(), "#ff0000", 16, a.length(), a.length() + b.length(), "#646464", 13, a.length() + b.length(), str.length(), "#aa646464", 13);
        holder.couponBgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClick(data.getId(),data.getName());
            }
        });
        holder.tvyi.setText("未使用");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class YouViewHolder extends RecyclerView.ViewHolder {
    CouponBgView couponBgView;
    TextView tvTitle, tvyi;

    public YouViewHolder(View itemView) {
        super(itemView);
        couponBgView = (CouponBgView) itemView.findViewById(R.id.dingdan_youhuiquan_view);
        tvTitle = (TextView) itemView.findViewById(R.id.dingdan_youhuiquan_tvcontent);
        tvyi = (TextView) itemView.findViewById(R.id.dingdan_youhuiquan_tvstate);
    }
}
