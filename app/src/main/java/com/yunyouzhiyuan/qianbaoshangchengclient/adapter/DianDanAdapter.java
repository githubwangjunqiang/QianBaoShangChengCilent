package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.LookQRcodeActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Dingdan;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.SpService;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.Utils;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/2/7.
 */
public class DianDanAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<Dingdan.DataBean.OrderListBean> list;
    private LayoutInflater inflater;
    private Callback callback;
    private String loodmore;

    public void setLoodmore(String loodmore) {
        this.loodmore = loodmore;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public int getGroupTypeCount() {
        return super.getGroupTypeCount() + 1;
    }

    @Override
    public int getGroupType(int groupPosition) {
        if (groupPosition == list.size()) {
            return 1;
        }
        return super.getGroupType(groupPosition);
    }

    public interface Callback {
        void onClickPingjia(String store_id, String order_id);

        void onPayment(int position);

        void deleteReFund(String order_id, String order_sn, String goods_id, String storid);
    }

    public DianDanAdapter(Context context, List<Dingdan.DataBean.OrderListBean> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return list.size() + 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (groupPosition == list.size()) {
            return 0;
        }
        return list.get(groupPosition).getGoods_list().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        if (getGroupType(groupPosition) == 1) {
            return null;
        }
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (getGroupType(groupPosition) == 1) {
            return null;
        }
        return list.get(groupPosition).getGoods_list().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {
        ViewHolder_G holder;
        TextView textView;
        if (view == null) {
            if (getGroupType(groupPosition) == 0) {
                holder = new ViewHolder_G();
                view = inflater.inflate(R.layout.itme_fragment_diandan_listview_g, null);
                holder.tvstorname = (TextView) view.findViewById(R.id.itme_dingdan_tvstorname);
                holder.tvTime = (TextView) view.findViewById(R.id.itme_dingdan_tvtime);
                view.setTag(holder);
            } else if (getGroupType(groupPosition) == 1) {
                textView = new TextView(context);
                int dimension = Utils.dip2px(context,
                        10);
                textView.setGravity(Gravity.CENTER);
                textView.setPadding(dimension, dimension, dimension, dimension);
                textView.setTextSize(13);
                textView.setTextColor(ContextCompat.getColor(context, R.color.text_color));
                textView.setBackgroundResource(R.color.background);
                view = textView;
            }
        }


        if (getGroupType(groupPosition) == 0) {
            holder = (ViewHolder_G) view.getTag();
            holder.tvstorname.setText(list.get(groupPosition).getStore_name());
            holder.tvTime.setText(list.get(groupPosition).getAdd_time());
        } else {
            textView = (TextView) view;
            textView.setText(R.string.zhengzaijiazai);
            textView.setTag("textview");
        }


        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        ViewHolder_C holder;
        Dingdan.DataBean.OrderListBean.GoodsListBean data = list.get(groupPosition).getGoods_list().get(childPosition);
        if (view == null) {
            holder = new ViewHolder_C();
            view = inflater.inflate(R.layout.itme_fragment_dingdan_listview_c, null);
            holder.image = (ImageView) view.findViewById(R.id.dingdan_ivimage);
            holder.tvshopname = (TextView) view.findViewById(R.id.dingdan_tvshopname);
            holder.tvprice = (TextView) view.findViewById(R.id.dingdan_tvprice);
            holder.tvnumber = (TextView) view.findViewById(R.id.dingdan_tvnumber);
            holder.tvbtn = (TextView) view.findViewById(R.id.dingdan_tvbtn);
            holder.tvRefund = (TextView) view.findViewById(R.id.dingdan_tvrefund);
            holder.tvQrCode = (TextView) view.findViewById(R.id.dingdan_tvqrcode);
            view.setTag(holder);
        }
        holder = (ViewHolder_C) view.getTag();
        ToGlide.url(context, HTTPURL.IMAGE + data.getOriginal_img(), holder.image);
        holder.tvshopname.setText(data.getGoods_name());
        setpeice(data, holder.tvprice);
        holder.tvnumber.setText("数量：" + data.getGoods_num());
        holder.tvRefund.setVisibility(View.GONE);
        holder.tvQrCode.setVisibility(View.GONE);
        setBtnback(holder.tvbtn, holder.tvRefund, list.get(groupPosition)
                , childPosition, groupPosition, holder.tvQrCode);
        return view;
    }

    /**
     * 设置按钮颜色
     *
     * @param tvbtn
     * @param tvQrCode
     */
    private void setBtnback(TextView tvbtn, TextView tvRefund, final Dingdan.DataBean.OrderListBean data,
                            final int position, final int grPosition, TextView tvQrCode) {
        if (!((data.getGoods_list().size() - 1) == position)) {
            tvbtn.setVisibility(View.GONE);
            tvRefund.setVisibility(View.GONE);
            tvQrCode.setVisibility(View.GONE);
        } else {
            tvbtn.setVisibility(View.VISIBLE);
            if (data.getRefund() == 1) {//不是退款订单
                if (TextUtils.equals("1", data.getIs_comment())) {//（0未评价 1已评价）,
                    tvbtn.setText(R.string.yiwancheng);
                    tvbtn.setBackgroundResource(R.color.text_color_alp);
                } else if (TextUtils.equals("2", data.getOrder_status())) {//（0未确认  1已确认  2已完成 ）
                    tvbtn.setText(R.string.daipingjia);
                    tvbtn.setBackgroundResource(R.drawable.dingdan_btn_yiwancheng_p);
                } else if (TextUtils.equals("0", data.getPay_status())) {//（0待支付  1已支付  ）
                    tvbtn.setText(R.string.daihzifu);
                    tvbtn.setBackgroundResource(R.drawable.dingdan_btn_daizhifu_p);
                } else if (TextUtils.equals(data.getShipping_status(), "0")) {//（0待发货  1已发货）
                    tvbtn.setText(R.string.daifahuo);
                    tvbtn.setBackgroundResource(R.drawable.dingdan_btn_jinxingzhong_p);
                    tvRefund.setVisibility(View.VISIBLE);
                    tvQrCode.setVisibility(View.VISIBLE);
                } else {
                    tvbtn.setText(R.string.jinxingzhong);
                    tvbtn.setBackgroundResource(R.color.text_color_alp);
                    tvRefund.setVisibility(View.VISIBLE);
                    tvQrCode.setVisibility(View.VISIBLE);
                }

            } else if (data.getRefund() == 0) {//退款中
                tvbtn.setText(R.string.tuikuanzhong);
                tvbtn.setBackgroundResource(R.color.blue);
            } else {
                tvbtn.setText(R.string.yituikuan);//已退款
                tvbtn.setBackgroundResource(R.color.text_color_alp);
            }
            tvRefund.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getVisibility() == View.VISIBLE) {
                        if (callback != null) {
                            StringBuffer buffer = new StringBuffer();
                            for (int i = 0; i < data.getGoods_list().size(); i++) {
                                if (data.getGoods_list().size() - 1 == i) {
                                    buffer.append(data.getGoods_list().get(i).getGoods_id());
                                } else {
                                    buffer.append(data.getGoods_list().get(i).getGoods_id()).append(",");
                                }

                            }
                            if (buffer.length() < 1) {
                                To.ee("未获取到id");
                                return;
                            }
                            callback.deleteReFund(data.getOrder_id(), data.getOrder_sn(),
                                    buffer.toString(), data.getStore_id());
                        }
                    }
                }
            });

            tvbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String trim = ((TextView) v).getText().toString().trim();
                    if (TextUtils.equals(context.getString(R.string.daizhifu), trim)) {//待支付
                        if (callback != null) {
                            callback.onPayment(grPosition);
                        }

                    } else if (TextUtils.equals(context.getString(R.string.querenshouhuo), trim)) {//确认收货
                    } else if (TextUtils.equals(context.getString(R.string.daipingjia), trim)) {//已确认收货 已完成 （待评价）
                        if (callback != null) {
                            callback.onClickPingjia(data.getStore_id(), data.getOrder_id());
                        }
                    }

                }

            });
            tvQrCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LookQRcodeActivity.startLookQRActivity(context, SpService.getSP().getphone()
                            , data.getOrder_sn());
                }
            });

        }
    }


    /**
     * 写入价格
     */

    private void setpeice(Dingdan.DataBean.OrderListBean.GoodsListBean data, TextView tv) {
        //"￥"+data.getGoods_price()+"\t￥"+data.getMarket_price()
        String string1 = "￥" + data.getGoods_price();
        String string2 = "\t￥" + data.getMarket_price();
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(string1);
        builder.append(string2);

        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan((int) context.getResources().getDimension(R.dimen.text_size_13sp));
        builder.setSpan(sizeSpan, 0, string1.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(context, R.color.red));
        builder.setSpan(colorSpan, 0, string1.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        AbsoluteSizeSpan sizeSpan1 = new AbsoluteSizeSpan((int) context.getResources().getDimension(R.dimen.text_size_12sp));
        builder.setSpan(sizeSpan1, string1.length(), builder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(ContextCompat.getColor(context, R.color.text_color_alp));
        builder.setSpan(colorSpan1, string1.length(), builder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        builder.setSpan(strikethroughSpan, string1.length() + 1, builder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tv.setText(builder);
    }

    /**
     * 店铺的viewhodler
     */
    private class ViewHolder_G {
        TextView tvstorname, tvTime;
    }

    /**
     * 店铺内商品的viewhodler
     */
    private class ViewHolder_C {
        ImageView image;
        TextView tvshopname, tvprice, tvnumber, tvbtn, tvRefund, tvQrCode;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
