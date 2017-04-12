package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.StorPingjia;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.AutoImageView;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.MyGridView;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/2/27.
 */

public class StorPingjiaAdapter extends MyAdapter<StorPingjia.DataBeanX.DataBean> {
    public StorPingjiaAdapter(Context context, List<StorPingjia.DataBeanX.DataBean> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        StorPingjia.DataBeanX.DataBean data = getData().get(position);
        if (view == null) {
            view = getLayoutInflater().inflate(R.layout.itme_storpingjia_listview, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        holder = (ViewHolder) view.getTag();
        List<String> img = getData().get(position).getImg();
        if (img.isEmpty()||TextUtils.isEmpty(img.get(0))) {
            holder.gv.setVisibility(View.GONE);
        } else {
            holder.gv.setVisibility(View.VISIBLE);
            holder.adapter.setData(img);
        }
        ToGlide.urlCircle(getContext(), HTTPURL.IMAGE + getData().get(position).getHead_pic(), holder.ivimage);
        holder.tvname.setText(getData().get(position).getNickname() == null ? "" : getData().get(position).getNickname());
        holder.tvtiem.setText(getData().get(position).getAdd_time() == null ? "" : getData().get(position).getAdd_time());
        holder.tvfenshu.setText(getData().get(position).getGoods_rank() + "分");
        holder.bar.setRating(Float.parseFloat(getData().get(position).getGoods_rank() + ""));
        holder.tvContent.setText(getData().get(position).getContent()+"");
        return view;
    }

    private class ViewHolder {
        AutoImageView ivimage;
        TextView tvname, tvtiem, tvfenshu,tvContent;
        RatingBar bar;
        MyGridView gv;
        PingjiaGvAdapter adapter;
        public ViewHolder(View view) {
            ivimage = (AutoImageView) view.findViewById(R.id.itme_storpinglist_iviamge);
            tvname = (TextView) view.findViewById(R.id.itme_storpinglist_tvname);
            tvtiem = (TextView) view.findViewById(R.id.itme_storpinglist_tvtime);
            tvfenshu = (TextView) view.findViewById(R.id.itme_storpinglist_tvfenshu);
            tvContent = (TextView) view.findViewById(R.id.itme_storpinglist_tvcontent);
            bar = (RatingBar) view.findViewById(R.id.itme_storpinglist_bar);
            gv = (MyGridView) view.findViewById(R.id.itme_storpinglist_gv);
            adapter = new PingjiaGvAdapter(getContext());
            gv.setAdapter(adapter);
        }
    }
}
