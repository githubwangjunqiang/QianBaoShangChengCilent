package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Youhuiquan;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.CouponBgView;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/2/7.
 */
public class YouhuiquanAdapter extends RecyclerView.Adapter<YouhuiquanViewHolder> {
    private Context context;
    private List<Youhuiquan.DataBean> list;
    private LayoutInflater inflater;
    private int type = 0;//标识 2已过期 1已使用 0未使用
    public YouhuiquanAdapter(Context context, List<Youhuiquan.DataBean> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    /**
     * 刷新数据
     * @param list
     * @param type
     */
    public void setDate(List<Youhuiquan.DataBean> list,int type){
        this.list.clear();
        this.list.addAll(list);
        this.type = type;
        notifyDataSetChanged();
    }
    /**
     * 刷新数据
     * @param list
     * @param type
     */
    public void addDate(List<Youhuiquan.DataBean> list,int type){
        this.list.addAll(list);
        this.type = type;
        notifyDataSetChanged();
    }

    @Override
    public YouhuiquanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new YouhuiquanViewHolder(inflater.inflate(R.layout.itme_youhuiquan_recyleview, parent, false));
    }

    @Override
    public void onBindViewHolder(YouhuiquanViewHolder holder, int position) {
        Youhuiquan.DataBean data = list.get(position);
        switch (type) {
            case 0:
                holder.view.setBackgroundResource(R.color.white);
                holder.tvstate.setText(R.string.weishiyong);
                break;
            case 1:
                holder.view.setBackgroundResource(R.color.background_p);
                holder.tvstate.setText(R.string.yishiyong);
                break;
            case 2:
                holder.view.setBackgroundResource(R.color.background_pp);
                holder.tvstate.setText(R.string.yiguoqi);
                break;
        }
        String a = "￥"+data.getMoney();
        String b = "\t\t"+data.getStore_name()+"\n";
        String c = "抵用券\t\t";
        String d = data.getName()+"\n";
        String e = "有效期：\t"+data.getSend_end_time()+"至"+data.getUse_end_time();
        setContent(holder.tvcontent, a, b, c, d, e);

    }

    private void setContent(TextView tv, String a, String b, String c, String d, String e) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(a);
        builder.append(b);
        builder.append(c);
        builder.append(d);
        builder.append(e);
        AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan((int) context.getResources().getDimension(R.dimen.text_size_20sp));
        builder.setSpan(absoluteSizeSpan, 0, a.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        ForegroundColorSpan span1 = new ForegroundColorSpan(ContextCompat.getColor(context, R.color.red));
        builder.setSpan(span1, 0, a.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        int index = a.length() + b.length();
        AbsoluteSizeSpan s = new AbsoluteSizeSpan((int) context.getResources().getDimension(R.dimen.text_size_14sp));
        builder.setSpan(s, a.length(), index, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        ForegroundColorSpan s2 = new ForegroundColorSpan(ContextCompat.getColor(context, R.color.color_blank));
        builder.setSpan(s2, a.length(), index, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        int index1 = index + c.length();
        AbsoluteSizeSpan s3 = new AbsoluteSizeSpan((int) context.getResources().getDimension(R.dimen.text_size_13sp));
        builder.setSpan(s3, index, index1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        ForegroundColorSpan s4 = new ForegroundColorSpan(ContextCompat.getColor(context, R.color.red));
        builder.setSpan(s4, index, index1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        index = index1+d.length();
        AbsoluteSizeSpan s5 = new AbsoluteSizeSpan((int) context.getResources().getDimension(R.dimen.text_size_13sp));
        builder.setSpan(s5, index1, index, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        ForegroundColorSpan s6 = new ForegroundColorSpan(ContextCompat.getColor(context, R.color.text_color));
        builder.setSpan(s6, index1, index, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        AbsoluteSizeSpan s7 = new AbsoluteSizeSpan((int) context.getResources().getDimension(R.dimen.text_size_12sp));
        builder.setSpan(s7, index, builder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        ForegroundColorSpan s8 = new ForegroundColorSpan(ContextCompat.getColor(context, R.color.text_color_alp));
        builder.setSpan(s8, index,builder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        tv.setText(builder);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class YouhuiquanViewHolder extends RecyclerView.ViewHolder {
    TextView tvcontent, tvstate;
    CouponBgView view;

    public YouhuiquanViewHolder(View itemView) {
        super(itemView);
        tvcontent = (TextView) itemView.findViewById(R.id.youhuiquan_tvcontent);
        tvstate = (TextView) itemView.findViewById(R.id.youhuiquan_tvstate);
        view = (CouponBgView) itemView.findViewById(R.id.youhuiquan_view);
    }
}
