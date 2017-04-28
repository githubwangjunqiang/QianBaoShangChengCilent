package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.CookListActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.ZiFenlei;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.AutoImageView;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/1/14.
 */
public class HappyGv0Adapter extends MyAdapter<ZiFenlei.DataBean> {
    private String sc_id;
    public HappyGv0Adapter(Activity context, List<ZiFenlei.DataBean> data, String sc_id) {
        super(context, data);
        this.sc_id = sc_id;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        view = getLayoutInflater().inflate(R.layout.itme_happy_gv0, parent,false);
        AutoImageView imageView = (AutoImageView) view.findViewById(R.id.itme_happy_gv0_ivimage);
        TextView tvname = (TextView) view.findViewById(R.id.itme_happy_gv0_tvname);
        ToGlide.url(getContext(), HTTPURL.IMAGE + getData().get(position).getImage(), imageView);
        tvname.setText(getData().get(position).getMobile_name());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CookListActivity.startCookListActivity(getContext(), sc_id,
                        getData().get(position).getId(), "休闲娱乐");
            }
        });
        return view;
    }
}
