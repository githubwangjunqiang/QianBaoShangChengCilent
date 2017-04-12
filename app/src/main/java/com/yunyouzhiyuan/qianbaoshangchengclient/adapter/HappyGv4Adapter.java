package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.ShopStorinfoActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.TuiJian;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.Text_Size;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/1/14.
 */
public class HappyGv4Adapter extends MyAdapter<TuiJian.DataBean> {
    public HappyGv4Adapter(Activity context, List<TuiJian.DataBean> data) {
        super(context, data);
    }

    @Override
    public View getView(final int position, View v, ViewGroup parent) {
        v = getLayoutInflater().inflate(R.layout.itme_happy_gv3, null);
        ImageView imageView = (ImageView) v.findViewById(R.id.itme_happy_gv3_image);
        TextView tvprice = (TextView) v.findViewById(R.id.itme_happy_gv3_tvprice);
        String price = "价格￥:" + getData().get(position).getGprice();
        String name = "\n" + getData().get(position).getStore_name();
        StringBuilder builder = new StringBuilder();
        builder.append(price).append(name);
        Text_Size.setSize(getContext(), tvprice, builder.toString(),
                0, price.length(), "#ca1f1f",
                11, price.length(), builder.length(), "#67CDFF", 13);
        ToGlide.url(getContext(), HTTPURL.IMAGE + getData().get(position).getStore_logo(), imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopStorinfoActivity.startShopStorinfoActivity(getContext(),getData().get(position).getStore_id());
            }
        });

        return v;
    }
}
