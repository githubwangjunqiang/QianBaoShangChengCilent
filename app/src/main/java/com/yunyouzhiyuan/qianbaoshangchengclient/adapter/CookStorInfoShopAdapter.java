package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.CookStorInfoShop;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.Text_Size;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/2/28.
 */

public class CookStorInfoShopAdapter extends MyAdapter<CookStorInfoShop.DataBean> {
    public CookStorInfoShopAdapter(Context context, List<CookStorInfoShop.DataBean> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = getLayoutInflater().inflate(R.layout.itme_cook_info_shop_listview, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        holder = (ViewHolder) view.getTag();
        ToGlide.urlRound(getContext(), HTTPURL.IMAGE + getData().get(position).getOriginal_img(), holder.ivImage, 6);
        String a = getData().get(position).getGoods_name() + "\n";
        String b = getData().get(position).getKeywords() + "\n";
        String c = "￥:" + getData().get(position).getShop_price();
        String string = a+b+c+ "\t\t门市价:￥" + getData().get(position).getPrice();
        Text_Size.setSizeThress(App.getContext(),holder.tvContent,string,0,a.length(),
                "#646464",14,a.length(),a.length()+b.length(),"#aa646464",12,
                a.length()+b.length(),a.length()+b.length()+c.length(),"#4BCEAE",13);
        return view;
    }

    private class ViewHolder {
        TextView tvContent;
        ImageView ivImage;

        public ViewHolder(View view) {
            this.tvContent = (TextView) view.findViewById(R.id.itme_cook_shop_tvcontent);
            this.ivImage = (ImageView) view.findViewById(R.id.itme_cook_shop_ivimage);
        }
    }
}
