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
public class HappyGv3Adapter extends MyAdapter<Home_HuoDong.DataBean> {
    public HappyGv3Adapter(Activity context, List<Home_HuoDong.DataBean> data) {
        super(context, data);
    }

    @Override
    public int getCount() {
        if (getData().size() <= 3) {
            return 0;
        } else if (getData().size() > 7) {
            return 4;
        }
        return super.getCount() - 3;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        AutoImageView imageView1 = (AutoImageView) getLayoutInflater().inflate(R.layout.itme_happy_gv4, parent, false);
        ToGlide.url(getContext(), HTTPURL.IMAGE + getData().get(position +3).getAd_code(), imageView1);
        return imageView1;
    }
}
