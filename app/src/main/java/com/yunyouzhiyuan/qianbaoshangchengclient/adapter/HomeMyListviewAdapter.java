package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Home_Bottom_list;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.AutoImageView;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.util.List;


/**
 * Created by ${王俊强} on 2017/1/9.
 */
public class HomeMyListviewAdapter extends MyAdapter<Home_Bottom_list.DataBean> {
    public HomeMyListviewAdapter(Activity context, List<Home_Bottom_list.DataBean> data) {
        super(context, data);
    }

    @Override
    public int getCount() {
        return getData().size();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        Home_Bottom_list.DataBean data = getData().get(position);
        if (view == null) {
            holder = new ViewHolder();
            view = getLayoutInflater().inflate(R.layout.itme_home_my_listview, null);
            holder.iviamge = (AutoImageView) view.findViewById(R.id.itme_home_my_listview_invimage);
            holder.tvtitle = (TextView) view.findViewById(R.id.itme_home_my_listview_tvname);
            holder.tvtitle = (TextView) view.findViewById(R.id.itme_home_my_listview_tvname);
            holder.tvdist = (TextView) view.findViewById(R.id.itme_home_my_listview_tvdistance);
            holder.tvcontent = (TextView) view.findViewById(R.id.itme_home_my_listview_tvcontent);
            holder.tvprice = (TextView) view.findViewById(R.id.itme_home_my_listview_tvprice);
            holder.tvstors = (TextView) view.findViewById(R.id.itme_home_my_listview_tvstors);
            holder.tvNumber = (TextView) view.findViewById(R.id.itme_home_my_listview_tvbumber);
            view.setTag(holder);
        }
        holder = (ViewHolder) view.getTag();
        ToGlide.url(getContext(), HTTPURL.IMAGE + data.getStore_logo(), holder.iviamge);
        holder.tvtitle.setText(data.getStore_name() + "");
        holder.tvdist.setText("<" + data.getDistance() + "km");
        holder.tvcontent.setText(data.getCoupon_name() + "");
        holder.tvstors.setText(data.getStore_desccredit() + "分");
        holder.tvprice.setText("新用户专享" + data.getProm_name());
        holder.tvNumber.setText("销量：" + data.getConsump_count());

        return view;
    }

    private class ViewHolder {
        AutoImageView iviamge;
        TextView tvtitle, tvdist, tvcontent, tvprice, tvstors, tvNumber;
    }
}
