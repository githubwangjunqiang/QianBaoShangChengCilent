package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Home_HuoDong;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.Text_Size;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/1/14.
 */
public class KtvGvAdapter extends MyAdapter<Home_HuoDong.DataBean> {

    public KtvGvAdapter(Activity context, List<Home_HuoDong.DataBean> data) {
        super(context, data);
    }

    @Override
    public int getCount() {
        if(getData().size()>3){
            return 3;
        }
        return super.getCount();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = getLayoutInflater().inflate(R.layout.itme_ktv_gv, null);
        TextView tvname = (TextView) view.findViewById(R.id.itme_ktv_gv_tvtitle);
        ImageView iviamge = (ImageView) view.findViewById(R.id.itme_ktv_gv_ivimage);
        String te = "天天满减\n每天都有新优惠";
        Text_Size.setSize(getContext(), tvname, te, 0, 4, "#6BCE68", 14, 4, te.length(), "#bb646464", 12);
        ToGlide.urlRound(getContext(), HTTPURL.IMAGE+getData().get(position).getAd_code(), iviamge,4);
        return view;
    }
}
