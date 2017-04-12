package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.KTV;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.AutoImageView;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/3/8.
 */

public class KTVTuanGouAdapter extends MyAdapter<KTV.DataBean.GoodsListBean> {
    public KTVTuanGouAdapter(Context context, List<KTV.DataBean.GoodsListBean> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = getLayoutInflater().inflate(R.layout.itme_ktv_tuangoulistivew, null);
            holder = new ViewHolder(view, getContext());
            view.setTag(holder);
        }
        holder = (ViewHolder) view.getTag();
        holder.setData(getData().get(position));
        return view;
    }

    private class ViewHolder {
        AutoImageView ivimage;
        TextView tvName, tvPrice, tvYishou;
        private Context context;

        public ViewHolder(View view, Context context) {
            ivimage = (AutoImageView) view.findViewById(R.id.itme_ktv_tuan_iviamge);
            tvName = (TextView) view.findViewById(R.id.itme_ktv_tuan_tvname);
            tvPrice = (TextView) view.findViewById(R.id.itme_ktv_tuan_tvprice);
            tvYishou = (TextView) view.findViewById(R.id.itme_ktv_tuan_tvyishou);
            this.context = context;
        }

        public void setData(KTV.DataBean.GoodsListBean data) {
            ToGlide.urlRound(context, HTTPURL.IMAGE + data.getOriginal_img(), ivimage, 4);
            tvName.setText(data.getGoods_name());
            tvYishou.setText("已售：" + data.getSales_sum());
            String price = "￥" + data.getShop_price() + "\t\t\t";
            String oldP = "原价￥：" + data.getMarket_price();

            SpannableStringBuilder builder = new SpannableStringBuilder();
            builder.append(price).append(oldP);

            AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan((int) context.getResources().
                    getDimension(R.dimen.text_size_16sp));
            builder.setSpan(absoluteSizeSpan, 0, price.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            ForegroundColorSpan span1 = new ForegroundColorSpan(ContextCompat.getColor(context,
                    R.color.blue_p));
            builder.setSpan(span1, 0, price.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            AbsoluteSizeSpan abso = new AbsoluteSizeSpan((int) context.getResources().
                    getDimension(R.dimen.text_size_12sp));
            builder.setSpan(abso, price.length(), builder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            ForegroundColorSpan span2 = new ForegroundColorSpan(ContextCompat.getColor(context,
                    R.color.text_color_alp));
            builder.setSpan(span2, price.length(), builder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
            builder.setSpan(strikethroughSpan, price.length(), builder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            tvPrice.setText(builder);
        }
    }
}
