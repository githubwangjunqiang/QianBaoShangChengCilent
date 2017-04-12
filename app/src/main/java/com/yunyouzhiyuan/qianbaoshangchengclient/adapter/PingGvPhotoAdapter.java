package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.app.Activity;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.TapViewpagerActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.AutoImageView;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/2/25.
 */

public class PingGvPhotoAdapter extends MyAdapter<Uri> {
    private Callback callback;

    public PingGvPhotoAdapter(Activity context, List<Uri> data, Callback callback) {
        super(context, data);
        this.callback = callback;
    }

    @Override
    public int getCount() {
        return super.getCount() + 1;
    }

    public interface Callback {
        void addPhoto();
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        AutoImageView imageView;
        if (view == null) {
            imageView = (AutoImageView) getLayoutInflater().inflate(R.layout.itme_ping_gv, parent,false);
            view = imageView;
        }
        imageView = (AutoImageView) view;
        if (position != 0) {
            ToGlide.url(getContext(), getData().get(position - 1), imageView);
        } else {
            imageView.setImageResource(R.mipmap.addphoto);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0) {
                    if (getData().size() == 7) {
                        To.oo("只能上传7张");
                        return;
                    }
                    callback.addPhoto();
                }else{
                    TapViewpagerActivity.startActivityUril(getContext(),getData(),position-1);
                }
            }
        });
        return imageView;
    }
}
