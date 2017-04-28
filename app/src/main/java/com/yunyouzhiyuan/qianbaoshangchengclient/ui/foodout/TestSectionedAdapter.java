package com.yunyouzhiyuan.qianbaoshangchengclient.ui.foodout;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.FoodOutShopInfoActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.FoodInfo;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.Text_Size;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.util.List;


public class TestSectionedAdapter extends SectionedBaseAdapter {

    private Context mContext;
    private List<FoodInfo.DataBean> list;
    private Callback callback;

    public interface Callback {
        void addCart(View v, int section, int position);

        void deleteCart(View v, int section, int position);
    }

    public TestSectionedAdapter(Context context, List<FoodInfo.DataBean> rightStr, Callback callback) {
        this.mContext = context;
        this.list = rightStr;
        this.callback = callback;
    }

    @Override
    public Object getItem(int section, int position) {
        return list.get(section).getInfo().get(position);
    }

    @Override
    public long getItemId(int section, int position) {
        return position;
    }

    @Override
    public int getSectionCount() {
        return list.size();
    }

    @Override
    public int getCountForSection(int section) {
        return list.get(section).getInfo().size();
    }

    @Override
    public View getItemView(final int section, final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final FoodInfo.DataBean.InfoBean data = list.get(section).getInfo().get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, null);
            holder.iviamge = (ImageView) convertView.findViewById(R.id.itme_list_iviamge);
            holder.ivjia = (ImageView) convertView.findViewById(R.id.itme_list_ivjiajia);
            holder.tvname = (TextView) convertView.findViewById(R.id.itme_list_tvname);
            holder.ivjian = (ImageView) convertView.findViewById(R.id.itme_list_tv__);
            holder.tvnumber = (TextView) convertView.findViewById(R.id.itme_list_tvnumber);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String str1 = data.getGoods_name() + "\n";
        String str2 = "店铺价:" + data.getShop_price();
        String str3 = str1 + str2 + "\n市场价:" + data.getMarket_price();

        Text_Size.setSizeThress(mContext, holder.tvname, str3, 0, str1.length(), "#646464", 13, str1.length(), str1.length() + str2.length(), "#E96D5B", 11, str1.length() + str2.length(), str3.length(), "#aa646464", 11);
        ToGlide.url(mContext, HTTPURL.IMAGE + data.getOriginal_img(), holder.iviamge);

        if (data.getCount() > 0) {
            holder.ivjian.setVisibility(View.VISIBLE);
            holder.tvnumber.setVisibility(View.VISIBLE);
            holder.tvnumber.setText(data.getCount() + "");
        } else {
            holder.ivjian.setVisibility(View.GONE);
            holder.tvnumber.setVisibility(View.GONE);
            holder.tvnumber.setText(data.getCount() + "");
        }

        addCart(data, section, position, holder.ivjia, holder.ivjian, holder.tvnumber);
        deleteCart(data, section, position, holder.ivjian, holder.tvnumber);

        holder.iviamge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FoodOutShopInfoActivity.class);
                intent.putExtra("data", data);
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    /**
     * 点击添加
     *
     * @param data
     * @param section
     * @param position
     */
    private void addCart(final FoodInfo.DataBean.InfoBean data, final int section,
                         final int position, ImageView ivJia,
                         final ImageView ivjian, final TextView tvNumber) {
        ivJia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getCount() < 1) {
                    show(true, ivjian, tvNumber);
                }
                callback.addCart(v, section, position);
            }
        });
    }

    /**
     * 点击减少
     *
     * @param data
     * @param section
     * @param position
     */
    private void deleteCart(final FoodInfo.DataBean.InfoBean data, final int section,
                            final int position,
                            final ImageView ivjian, final TextView tvNumber) {
        ivjian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getCount() == 1) {
                    show(false, ivjian, tvNumber);
                }
                callback.deleteCart(v, section, position);
            }
        });
    }

    private class ViewHolder {
        private ImageView iviamge, ivjia, ivjian;
        private TextView tvname, tvnumber;
    }

    /**
     * 显示隐藏 减号 购物项
     *
     * @param show true->显示    false->隐藏
     */
    private void show(boolean show, final ImageView ivjian, final TextView tvnumber) {
        TranslateAnimation animation = null;
        TranslateAnimation animation2 = null;
        AnimationSet set = new AnimationSet(true);
        if (show) {
            animation = new TranslateAnimation(ivjian.getWidth() + tvnumber.getWidth(),
                    0, 0, 0);
            animation.setDuration(300);
            ivjian.setAnimation(animation);
            animation2 = new TranslateAnimation(tvnumber.getWidth(), 0, 0, 0);
            animation2.setDuration(300);
            tvnumber.setAnimation(animation2);
            ivjian.setVisibility(View.VISIBLE);
            tvnumber.setVisibility(View.VISIBLE);
        } else {
            animation = new TranslateAnimation(0,
                    ivjian.getWidth() + tvnumber.getWidth(), 0, 0);
            animation.setDuration(200);
            ivjian.setAnimation(animation);
            animation2 = new TranslateAnimation(0, tvnumber.getWidth(), 0, 0);
            animation2.setDuration(200);
            tvnumber.setAnimation(animation2);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    ivjian.setVisibility(View.GONE);
                    tvnumber.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
        set.addAnimation(animation);
        set.addAnimation(animation2);
        set.start();

    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        ViewHolder_D holder_d = null;
        if (convertView == null) {
            holder_d = new ViewHolder_D();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.header_item, null);
            holder_d.tvname = (TextView) convertView.findViewById(R.id.header_itme_tvname);
            convertView.setTag(holder_d);
        } else {
            holder_d = (ViewHolder_D) convertView.getTag();
        }
        convertView.setClickable(false);
        holder_d.tvname.setText(list.get(section).getCat_name());
        return convertView;
    }

    private class ViewHolder_D {
        private TextView tvname;
    }

}
