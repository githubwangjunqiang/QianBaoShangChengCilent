package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.HotelStorInfoActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Food_Bottom;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.AutoImageView;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/1/13.
 */
public class HotelRecyleAdapter extends RecyclerView.Adapter<HotelViewHolder> {
    private Context context;
    private List<Food_Bottom.DataBean> list;
    private LayoutInflater layoutInflater;

    public HotelRecyleAdapter(Context context, List<Food_Bottom.DataBean> list) {
        this.context = context;
        this.list = list;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public HotelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HotelViewHolder(layoutInflater.inflate(R.layout.itme_hotel_recyleview, parent, false));
    }

    @Override
    public void onBindViewHolder(HotelViewHolder holder, int position) {
        final Food_Bottom.DataBean data = list.get(position);
        ToGlide.urlRound(context, HTTPURL.IMAGE + data.getStore_logo(), holder.imageView, 4);
        String te = data.getStore_name() + "\t\t\t";
        setname(te, holder.tvname);
        holder.tvprice.setText(data.getSeo_description() + "");
        holder.tvstas.setText("已售：" + data.getConsump_count());
        holder.tvm.setText("距离：" + data.getDistance());


        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HotelStorInfoActivity.startHotelStorInfoActivity(context, data.getStore_id(),
                        0 + "", 0 + "", 0, 0);
            }
        });
    }

    /**
     * 酒店明
     *
     * @param te
     */
    private void setname(String te, TextView tv) {
        SpannableStringBuilder builder = new SpannableStringBuilder(te);
        ImageSpan imageSpan = new ImageSpan(context, R.mipmap.ding);
        builder.setSpan(imageSpan, te.length() - 3, te.length() - 2, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        ImageSpan imageSpan1 = new ImageSpan(context, R.mipmap.zhong);
        builder.setSpan(imageSpan1, te.length() - 1, te.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan((int) context.getResources().getDimension(R.dimen.text_size_13sp));
        builder.setSpan(absoluteSizeSpan, 0, te.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tv.setText(builder);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}

class HotelViewHolder extends RecyclerView.ViewHolder {
    AutoImageView imageView;
    TextView tvname, tvprice, tvstas, tvm;
    LinearLayout ll;

    public HotelViewHolder(View itemView) {
        super(itemView);
        imageView = (AutoImageView) itemView.findViewById(R.id.itme_hotel_recyleview_image);
        ll = (LinearLayout) itemView.findViewById(R.id.itme_hotel_recyleview_ll);
        tvname = (TextView) itemView.findViewById(R.id.itme_hotel_recyleview_tvname);
        tvprice = (TextView) itemView.findViewById(R.id.itme_hotel_recyleview_tvprice);
        tvstas = (TextView) itemView.findViewById(R.id.itme_hotel_recyleview_tvstas);
        tvm = (TextView) itemView.findViewById(R.id.itme_hotel_recyleview_mm);
    }
}
