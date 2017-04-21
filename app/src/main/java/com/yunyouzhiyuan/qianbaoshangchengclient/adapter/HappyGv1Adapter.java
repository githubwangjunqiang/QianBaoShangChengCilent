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
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/1/14.
 */
public class HappyGv1Adapter extends MyAdapter<ZiFenlei.DataBean> {
    private String scid;

    public HappyGv1Adapter(Activity context, List<ZiFenlei.DataBean> data, String scid) {
        super(context, data);
        this.scid = scid;
    }

    @Override
    public int getCount() {
        if (getData().size() < 11) {
            return getData().size() - 3;
        }
        return 8;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        view = getLayoutInflater().inflate(R.layout.itme_happy_gv1, null);
        final AutoImageView imageView = (AutoImageView) view.findViewById(R.id.itme_happy_gv1_ivimage);
        TextView tvname = (TextView) view.findViewById(R.id.itme_happy_gv1_tvnamne);

        if (position == getCount() - 1) {
            tvname.setText(R.string.biaoba);
            imageView.setImageResource(R.mipmap.biaoba);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    To.ss(imageView, "暂时没有跟多分类");
                }
            });
        } else {
            ToGlide.url(getContext(), HTTPURL.IMAGE + getData().get(position + 4).getImage(), imageView);
            tvname.setText(getData().get(position + 4).getMobile_name());
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String type = "ktv";
                    if (position != 0) {
                        type = "dosmxing";
                    }
                    CookListActivity.startCookListActivity(getContext(),
                            scid, getData().get(position + 4).getId(),type);
                }
            });
        }


        return view;
    }
}
