package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Home_HuoDong;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.AutoImageView;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/1/14.
 */
public class HappyGv2Adapter extends MyAdapter<Home_HuoDong.DataBean> {
    public HappyGv2Adapter(Activity context, List<Home_HuoDong.DataBean> data) {
        super(context, data);
    }

    @Override
    public int getCount() {
        if (getData().size() > 3) {
            return 3;
        }
        return super.getCount();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        AutoImageView imageView = (AutoImageView) getLayoutInflater().inflate(R.layout.itme_happy_gv2, null);
        ToGlide.url(getContext(), HTTPURL.IMAGE + getData().get(position).getAd_code(), imageView);
        return imageView;
    }
}
