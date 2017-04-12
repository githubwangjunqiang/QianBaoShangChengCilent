package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.photoview.DragPhotoActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${王俊强} on 2017/2/27.
 */

public class PingjiaGvAdapter extends BaseAdapter {
    private Context context;
    private List<String> list = new ArrayList<>();
    private LayoutInflater inflater;

    public PingjiaGvAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    /**
     * 设置数据
     *
     * @param ruls
     */
    public void setData(List<String> ruls) {
        list.clear();
        list.addAll(ruls);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ImageView imageView = null;
        if (view == null) {
            imageView = (ImageView) inflater.inflate(R.layout.itme_image, parent, false);
            view = imageView;
        }
        imageView = (ImageView) view;
        ToGlide.urlRound(context, HTTPURL.IMAGE + list.get(position), imageView, 4);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TapViewpagerActivity.startActivity(context, list, position);
                ArrayList<String> listurl = new ArrayList<String>();
                for (int i = 0; i < list.size(); i++) {
                    listurl.add(HTTPURL.IMAGE + list.get(i));
                }
                DragPhotoActivity.startDragPhotoActivity(context, (ImageView) v, listurl,position);
            }
        });
        return view;
    }
}
