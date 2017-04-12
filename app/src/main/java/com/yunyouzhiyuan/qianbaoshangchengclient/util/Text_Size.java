package com.yunyouzhiyuan.qianbaoshangchengclient.util;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StrikethroughSpan;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;


/**
 * Created by wangjunqiang on 2016/10/31.
 */
public class Text_Size {
    /**
     * textview 显示的文字大小 分两行
     *
     * @param tv
     * @param text
     * @param one
     * @param tow
     * @param three
     * @param four
     */
    public static void setSize(Context context, TextView tv, String text, int one, int tow, String colors, int sizea, int three, int four, String colors1, int sizea1) {
        SpannableString sp = new SpannableString(text);
        DisplayMetrics ff = App.getContext().getResources().getDisplayMetrics();
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sizea, ff);
        sp.setSpan(new AbsoluteSizeSpan(size), one, tow, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(Color.parseColor(colors)), one, tow, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        int size2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sizea1, ff);
        sp.setSpan(new AbsoluteSizeSpan(size2), three, four, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(Color.parseColor(colors1)), three, four, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(sp);
    }

    public static void setXiehuaXianTow(TextView tv, String str, int fist, int tow,
                                        @ColorRes int colorid, @DimenRes int sizeId,
                                        int three, int four, @ColorRes int colorIdtow,
                                        @DimenRes int sizeId2) {

        SpannableStringBuilder builder = new SpannableStringBuilder(str);

        AbsoluteSizeSpan abso = new AbsoluteSizeSpan((int) App.getContext().getResources().
                getDimension(sizeId));
        builder.setSpan(abso, fist, tow, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        ForegroundColorSpan span2 = new ForegroundColorSpan(ContextCompat.getColor(App.getContext(),
                colorid));
        builder.setSpan(span2, fist, tow, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        AbsoluteSizeSpan abso2 = new AbsoluteSizeSpan((int) App.getContext().getResources().
                getDimension(sizeId2));
        builder.setSpan(abso2, three, four, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        ForegroundColorSpan span1 = new ForegroundColorSpan(ContextCompat.getColor(App.getContext(),
                colorIdtow));
        builder.setSpan(span1, three, four, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        builder.setSpan(strikethroughSpan, three, four, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tv.setText(builder);

    }

    /**
     * * textview 显示的文字大小 分三行
     *
     * @param context
     * @param tv
     * @param text
     * @param one
     * @param tow
     * @param colors
     * @param sizea
     * @param three
     * @param four
     * @param colors1
     * @param sizea1
     */
    public static void setSizeThress(Context context,
                                     TextView tv, String text, int one, int tow, String colors, int sizea, int three, int four, String colors1, int sizea1
            , int five, int sex, String color2, int sizes2) {


        SpannableString sp = new SpannableString(text);
        DisplayMetrics ff = App.getContext().getResources().getDisplayMetrics();
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sizea, ff);
        sp.setSpan(new AbsoluteSizeSpan(size), one, tow, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(Color.parseColor(colors)), one, tow, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        int size2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sizea1, ff);
        sp.setSpan(new AbsoluteSizeSpan(size2), three, four, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(Color.parseColor(colors1)), three, four, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        int size3 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sizes2, ff);
        sp.setSpan(new AbsoluteSizeSpan(size3), five, sex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(Color.parseColor(color2)), five, sex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(sp);
    }

    public static void setSizeFour(TextView tv, String text, int one, int tow,
                                   @ColorRes int colorID, int sizea, int three, int four,
                                   @ColorRes int colorID1, int sizea1, int five, int sex,
                                   @ColorRes int colorID2, int sizes2, int seven, int eht,
                                   @ColorRes int colorID3, int sizes3) {


        SpannableString sp = new SpannableString(text);
        DisplayMetrics ff = App.getContext().getResources().getDisplayMetrics();
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sizea, ff);
        sp.setSpan(new AbsoluteSizeSpan(size), one, tow, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(ContextCompat.getColor(App.getContext(), colorID)), one, tow, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        int size2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sizea1, ff);
        sp.setSpan(new AbsoluteSizeSpan(size2), three, four, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(ContextCompat.getColor(App.getContext(), colorID1)), three, four, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        int size3 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sizes2, ff);
        sp.setSpan(new AbsoluteSizeSpan(size3), five, sex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(ContextCompat.getColor(App.getContext(), colorID2)), five, sex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        int size4 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sizes3, ff);
        sp.setSpan(new AbsoluteSizeSpan(size4), seven, eht, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(ContextCompat.getColor(App.getContext(), colorID3)),
                seven, eht, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tv.setText(sp);
    }

    /**
     * 在textview 指定位置图换成图片
     */
    public static void setTextBitmap(Context content, TextView tv, String str, int startIndex, int endIndex, int o, int ot, int t, int tt, int h, int ht, int drawableId) {
        SpannableStringBuilder builder = new SpannableStringBuilder(str);


        if (0 != drawableId) {
            ImageSpan imageSpan = new ImageSpan(content, drawableId);
            builder.setSpan(imageSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        }

//       也可以这样写  第二种方法
//        Drawable drawable = content.getResources().getDrawable(R.mipmap.vip);
//        drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
//        ImageSpan imageSpan1 = new ImageSpan(drawable);
        AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan((int) content.getResources().getDimension(R.dimen.text_size_12sp));
        builder.setSpan(absoluteSizeSpan, o, ot, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        AbsoluteSizeSpan absoluteSizeSpan1 = new AbsoluteSizeSpan((int) content.getResources().getDimension(R.dimen.text_size_10sp));
        builder.setSpan(absoluteSizeSpan1, t, tt, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);


        AbsoluteSizeSpan absoluteSizeSpan2 = new AbsoluteSizeSpan((int) content.getResources().getDimension(R.dimen.text_size_12sp));
        builder.setSpan(absoluteSizeSpan2, h, ht, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);


        tv.setText(builder);
    }

    /**
     * 在textview 指定位置图换成图片
     */
    public static void setTextBitmap2(Context content, TextView tv, String str, int startIndex, int endIndex, int drawableId) {
        SpannableStringBuilder builder = new SpannableStringBuilder(str);


        ImageSpan imageSpan = new ImageSpan(content, drawableId);
//       也可以这样写  第二种方法
//        Drawable drawable = content.getResources().getDrawable(R.mipmap.vip);
//        drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
//        ImageSpan imageSpan1 = new ImageSpan(drawable);


        builder.setSpan(imageSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tv.setText(builder);
    }

    /**
     * 在textview 指定位置加点击监听器
     */
    public static void setTextIndexClick(Context content, TextView tv, String str, int startIndex, int endIndex, int size, String backColors, String textColor, final OnClick click) {
        SpannableStringBuilder builder = new SpannableStringBuilder(str);

        //指定位置的字体
        AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(size);
        builder.setSpan(absoluteSizeSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        //指定位置的背景色
        BackgroundColorSpan span = new BackgroundColorSpan(Color.parseColor(backColors));
        builder.setSpan(span, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        //指定位置的字体颜色
        ForegroundColorSpan span1 = new ForegroundColorSpan(Color.parseColor(textColor));
        builder.setSpan(span1, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        //指定位置的监听器
        ClickableSpan span2 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                click.cLick();
            }
        };
        builder.setSpan(span2, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        tv.setText(builder);
        //如果加了指定字符有监听 其他字符 还是不可点击的 需加上这一句
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public interface OnClick {
        void cLick();
    }
}
