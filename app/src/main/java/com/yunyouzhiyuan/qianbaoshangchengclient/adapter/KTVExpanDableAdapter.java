package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.KTV;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/1/14.
 */
public class KTVExpanDableAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<KTV.DataBean> list;

    public KTVExpanDableAdapter(Context context, List<KTV.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getGoods_list().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getGoods_list().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {
        ViewHolder_G holder_g;
        if (view == null) {
            holder_g = new ViewHolder_G();
            view = LayoutInflater.from(context).inflate(R.layout.itme_ktv_expandablelistview, null);
            holder_g.imageView = (ImageView) view.findViewById(R.id.itme_ktv_expanblelistview_ivimage);
            holder_g.tvname = (TextView) view.findViewById(R.id.itme_ktv_expanblelistview_tvname);
            holder_g.tvstas = (TextView) view.findViewById(R.id.itme_ktv_expanblelistview_tvstas);
            holder_g.tvprice = (TextView) view.findViewById(R.id.itme_ktv_expanblelistview_tvprice);
            holder_g.tvm = (TextView) view.findViewById(R.id.itme_ktv_expanblelistview_tvm);
            holder_g.bar = (RatingBar) view.findViewById(R.id.itme_ktv_expanblelistview_bar);
            view.setTag(holder_g);
        }
        holder_g = (ViewHolder_G) view.getTag();
        KTV.DataBean data = list.get(groupPosition);
        ToGlide.urlRound(context, HTTPURL.IMAGE + data.getStore_logo(), holder_g.imageView, 4);
        holder_g.tvname.setText(data.getStore_name() + "");
        holder_g.bar.setRating(Float.parseFloat(data.getStore_desccredit() + ""));
        holder_g.tvstas.setText(data.getStore_desccredit() + "分");
        holder_g.tvprice.setText("起始价￥:" + data.getGprice() + "/人");
        holder_g.tvm.setText(data.getDistrict() + "\t距离：" + data.getDistance() + "km");

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        ViewHolder_C holder_c;
        if (view == null) {
            holder_c = new ViewHolder_C();
            view = LayoutInflater.from(context).inflate(R.layout.itme_ktv_expandablelistview_chid, null);
            holder_c.tvprice = (TextView) view.findViewById(R.id.itme_ktv_expanblelistview_chid_tvprice);
            holder_c.tvyishou = (TextView) view.findViewById(R.id.itme_ktv_expanblelistview_chid_tvyishou);
            view.setTag(holder_c);
        }
        holder_c = (ViewHolder_C) view.getTag();
        KTV.DataBean.GoodsListBean data = list.get(groupPosition).getGoods_list().get(childPosition);
        setText(holder_c.tvprice, data);
        holder_c.tvyishou.setText("已售：" + data.getSales_sum());
        return view;
    }

    private void setText(TextView tv, KTV.DataBean.GoodsListBean data) {
        String a = "\t￥:" + data.getShop_price() + "\t";
        String b = "￥" + data.getMarket_price();
        String c = "\t\t\t" + data.getGoods_name();
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(a).append(b).append(c);

        AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan((int) context.getResources().getDimension(R.dimen.text_size_14sp));
        builder.setSpan(absoluteSizeSpan, 0, a.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        ForegroundColorSpan span1 = new ForegroundColorSpan(ContextCompat.getColor(context, R.color.blue_p));
        builder.setSpan(span1, 0, a.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan((int) context.getResources().getDimension(R.dimen.text_size_12sp));
        builder.setSpan(sizeSpan, a.length(), a.length() + b.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        ForegroundColorSpan span = new ForegroundColorSpan(ContextCompat.getColor(context, R.color.text_color_alp));
        builder.setSpan(span, a.length(), a.length() + b.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        builder.setSpan(strikethroughSpan, a.length(), a.length() + b.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        AbsoluteSizeSpan sizeSpan1 = new AbsoluteSizeSpan((int) context.getResources().getDimension(R.dimen.text_size_13sp));
        builder.setSpan(sizeSpan1, a.length() + b.length(), builder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        ForegroundColorSpan span2 = new ForegroundColorSpan(ContextCompat.getColor(context, R.color.text_color));
        builder.setSpan(span2, a.length() + b.length(), builder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        tv.setText(builder);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private class ViewHolder_G {
        private ImageView imageView;
        private TextView tvname, tvstas, tvprice, tvm;
        private RatingBar bar;
    }

    private class ViewHolder_C {
        private TextView tvprice, tvyishou;
    }

}
