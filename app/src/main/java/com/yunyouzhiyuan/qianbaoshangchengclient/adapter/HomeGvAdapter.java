package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Home_HuoDong;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/1/9.
 */
public class HomeGvAdapter extends MyAdapter<Home_HuoDong.DataBean> {
    public HomeGvAdapter(Activity context, List<Home_HuoDong.DataBean> data) {
        super(context, data);

    }

    @Override
    public int getCount() {
        if (getData().size() > 2) {
            return getData().size() - 2;
        } else if (getData().size() > 6) {
            return 4;
        } else {
            return 0;
        }

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ImageView ivImage = null;
        if (view == null) {
            ivImage = (ImageView) getLayoutInflater().inflate(R.layout.itme_home_gv, parent,false);
            view = ivImage;
        }
        ivImage = (ImageView) view;
        ToGlide.url(getContext(), HTTPURL.IMAGE + getData().get(position + 2).getAd_code(), ivImage);
        return view;
    }


}
