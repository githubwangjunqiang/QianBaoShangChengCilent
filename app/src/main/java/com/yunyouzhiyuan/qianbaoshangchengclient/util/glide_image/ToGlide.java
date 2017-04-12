package com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;

/**
 * Created by ${王俊强} on 2017/1/6.
 */
public class ToGlide {

    /**
     缓存策略
     Glide.with( context ).load(imageUrl).skipMemoryCache(true).into(imageViewInternet )
     ;//跳过内存缓存
     Glide.with( context ).load(imageUrl).diskCacheStrategy(DiskCacheStrategy.NONE)
     .into( imageViewInternet );//跳过硬盘缓存

     DiskCacheStrategy.NONE 什么都不缓存
     DiskCacheStrategy.SOURCE 仅仅只缓存原来的全分辨率的图像
     DiskCacheStrategy.RESULT 仅仅缓存最终的图像，即降低分辨率后的（或者是转换后的）
     DiskCacheStrategy.ALL 缓存所有版本的图像（默认行为）


     .优先级，设置图片加载的顺序：
     Glide.with(context).load(imageUrl).priority( Priority.HIGH).into( imageView);



     */


    /**
     * 加载普通网络图片
     *
     * @param context 上下文（Activiy fragment ）
     * @param url     图片路径  网络图片
     * @param view
     */
    public static void url(Context context, String url, final ImageView view) {
        if (TextUtils.isEmpty(url) || context == null || view == null) {
            return;
        }
//        Glide.with(context).load(url).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL)
// .placeholder(R.mipmap.meinv).error(R.mipmap.meinv).into(view);
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.meinv).error(R.mipmap.meinv).into(view);
    }

    private class my extends BitmapImageViewTarget {

        public my(ImageView view) {
            super(view);
        }
    }

    /**
     * 加载普通网络图片
     *
     * @param context 上下文（Activiy fragment ）
     * @param url     图片路径  网络图片
     * @param view
     */
    public static void url(Fragment context, String url, final ImageView view) {
        if (TextUtils.isEmpty(url) || context == null || view == null) {
            return;
        }
//        Glide.with(context).load(url).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL)
// .placeholder(R.mipmap.meinv).error(R.mipmap.meinv).into(view);
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.meinv).error(R.mipmap.meinv).into(view);
    }

    /**
     * 加载普通网络图片 带尺寸
     *
     * @param context 上下文
     * @param url     网络地址
     * @param view    显示的控件 imageview
     * @param width   期望图片的宽度
     * @param height  期望图片的高度
     */
    public static void url(Context context, String url, final ImageView view, int width,
                           int height) {
        if (TextUtils.isEmpty(url) || context == null || view == null) {
            return;
        }
        Glide.with(context).load(url).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(width, height).placeholder(R.mipmap.meinv).error(R.mipmap.meinv)
                .into(view);

    }

    /**
     * 加载普通网络图片 带尺寸
     *
     * @param context 上下文
     * @param url     网络地址
     * @param view    显示的控件 imageview
     * @param width   期望图片的宽度
     * @param height  期望图片的高度
     */
    public static void url(Fragment context, String url, final ImageView view, int width,
                           int height) {
        if (TextUtils.isEmpty(url) || context == null || view == null) {
            return;
        }
        Glide.with(context).load(url).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(width, height).placeholder(R.mipmap.meinv).error(R.mipmap.meinv)
                .into(view);

    }

    /**
     * 加载普通网络图片  圆形图片
     *
     * @param context 上下文（Activiy fragment ）
     * @param url     图片路径  网络图片  圆形图片
     * @param view
     */
    public static void urlCircle(Context context, String url, final ImageView view) {
        if (TextUtils.isEmpty(url) || context == null || view == null) {
            return;
        }
        Glide.with(context).load(url).placeholder(R.mipmap.meinv).error(R.mipmap.meinv)
                .transform(new GlideCircleTransform(context)).into(view);
    }

    /**
     * 加载普通网络图片  圆形图片
     *
     * @param context 上下文（Activiy fragment ）
     * @param url     图片路径  网络图片  圆形图片
     * @param view
     */
    public static void urlCircle(Fragment context, String url, final ImageView view) {
        if (TextUtils.isEmpty(url) || context == null || view == null) {
            return;
        }
        Glide.with(context).load(url).placeholder(R.mipmap.meinv).error(R.mipmap.meinv)
                .transform(new GlideCircleTransform(App.getContext())).into(view);
    }

    /**
     * 加载普通网络图片  圆角图片
     *
     * @param context 上下文（Activiy fragment ）
     * @param url     图片路径  网络图片  圆角图片
     * @param view
     */
    public static void urlRound(Context context, String url, final ImageView view, int dp) {
        if (TextUtils.isEmpty(url) || context == null || view == null) {
            return;
        }
        Glide.with(context).load(url).placeholder(R.mipmap.meinv).error(R.mipmap.meinv)
                .transform(new GlideRoundTransform(context, dp)).into(view);
    }

    /**
     * 加载普通网络图片  圆角图片
     *
     * @param context 上下文（Activiy fragment ）
     * @param url     图片路径  网络图片  圆角图片
     * @param view
     */
    public static void urlRound(Fragment context, String url, final ImageView view, int dp) {
        if (TextUtils.isEmpty(url) || context == null || view == null) {
            return;
        }
        Glide.with(context).load(url).placeholder(R.mipmap.meinv).error(R.mipmap.meinv)
                .transform(new GlideRoundTransform(App.getContext(), dp)).into(view);
    }

    /**
     * 本地图片
     *
     * @param context 上下文（Activiy fragment ）
     * @param uri     图片路径  本地图片
     * @param view
     */
    public static void url(Context context, Uri uri, final ImageView view, int width,
                           int height) {
        if (uri == null || context == null || view == null) {
            return;
        }
        Glide.with(context).load(uri).fitCenter().override(width, height)
                .placeholder(R.mipmap.meinv).error(R.mipmap.meinv).into(view);
    }

    /**
     * 本地图片
     *
     * @param context 上下文（Activiy fragment ）
     * @param id     图片路径  缓存id文件
     * @param view
     */
    public static void url(Context context, @DrawableRes int id, final ImageView view) {
        if (id == 0 || context == null || view == null) {
            return;
        }
        Glide.with(context).load(id).centerCrop().placeholder(R.mipmap.meinv)
                .error(R.mipmap.meinv).into(view);
    }

    /**
     * 本地图片
     *
     * @param context 上下文（Activiy fragment ）
     * @param uri     图片路径  本地图片
     * @param view
     */
    public static void url(Fragment context, Uri uri, final ImageView view, int width,
                           int height) {
        if (uri == null || context == null || view == null) {
            return;
        }
        Glide.with(context).load(uri).fitCenter().override(width, height)
                .placeholder(R.mipmap.meinv).error(R.mipmap.meinv).into(view);
    }

    /**
     * 本地图片   带尺寸
     *
     * @param context 上下文（Activiy fragment ）
     * @param uri     图片路径  本地图片
     * @param view
     */
    public static void url(Context context, Uri uri, final ImageView view) {
        if (uri == null || context == null || view == null) {
            return;
        }
        Glide.with(context).load(uri).placeholder(R.mipmap.meinv).error(R.mipmap.meinv)
                .into(view);
    }

    /**
     * 本地图片   带尺寸
     *
     * @param context 上下文（Activiy fragment ）
     * @param uri     图片路径  本地图片
     * @param view
     */
    public static void url(Fragment context, Uri uri, final ImageView view) {
        if (uri == null || context == null || view == null) {
            return;
        }
        Glide.with(context).load(uri).fitCenter().placeholder(R.mipmap.meinv)
                .error(R.mipmap.meinv).into(view);
    }

    /**
     * 显示gif动画,asGif()判断是否是gif动画
     *
     * @param context 上下文（Activiy fragment ）
     * @param url     图片路径  网络图片
     * @param view
     */
    public static void gif(Context context, String url, final ImageView view) {
        if (TextUtils.isEmpty(url) || context == null || view == null) {
            return;
        }
        Glide.with(context).load(url).asGif().placeholder(R.mipmap.meinv)
                .error(R.mipmap.meinv).into(view);
    }

    /**
     * 显示gif动画,asGif()判断是否是gif动画
     *
     * @param context 上下文（Activiy fragment ）
     * @param url     图片路径  网络图片
     * @param view
     */
    public static void gif(Fragment context, String url, final ImageView view) {
        if (TextUtils.isEmpty(url) || context == null || view == null) {
            return;
        }
        Glide.with(context).load(url).asGif().placeholder(R.mipmap.meinv)
                .error(R.mipmap.meinv).into(view);
    }

    /**
     * 显示gif动画,asGif()判断是否是gif动画
     *
     * @param context 上下文（Activiy fragment ）
     * @param url     图片路径  本地图片
     * @param view
     */
    public static void gif(Context context, Uri url, final ImageView view) {
        if (url == null || context == null || view == null) {
            return;
        }
        Glide.with(context).load(url).asGif().placeholder(R.mipmap.meinv)
                .error(R.mipmap.meinv).into(view);
    }

    /**
     * 显示gif动画,asGif()判断是否是gif动画
     *
     * @param context 上下文（Activiy fragment ）
     * @param url     图片路径  本地图片
     * @param view
     */
    public static void gif(Fragment context, Uri url, final ImageView view) {
        if (url == null || context == null || view == null) {
            return;
        }
        Glide.with(context).load(url).asGif().placeholder(R.mipmap.meinv)
                .error(R.mipmap.meinv).into(view);
    }

    /**
     * 显示本地视频剧照
     */
    public static void video(Context context, Uri uri, final ImageView view) {
        if (uri == null || context == null || view == null) {
            return;
        }
        Glide.with(context).load(uri).placeholder(R.mipmap.meinv)
                .error(R.mipmap.meinv).into(view);
    }

    /**
     * 显示本地视频剧照
     */
    public static void video(Fragment context, Uri uri, final ImageView view) {
        if (uri == null || context == null || view == null) {
            return;
        }
        Glide.with(context).load(uri).placeholder(R.mipmap.meinv).error(R.mipmap.meinv)
                .into(view);
    }

    /**
     * 显示网络视频剧照
     */
    public static void video(Context context, String url, final ImageView view) {
        if (url == null || context == null || view == null) {
            return;
        }
        Glide.with(context).load(url).placeholder(R.mipmap.meinv).error(R.mipmap.meinv)
                .into(view);
    }

    /**
     * 显示网络视频剧照
     */
    public static void video(Fragment context, String url, final ImageView view) {
        if (url == null || context == null || view == null) {
            return;
        }
        Glide.with(context).load(url).placeholder(R.mipmap.meinv).error(R.mipmap.meinv)
                .into(view);
    }

    /**
     * 回调图片  drawble
     *
     * @param context
     * @param url
     * @param view
     * @param w
     * @param h
     */
    public static void callDrawble(Context context, String url, final ImageView view,
                                   int w, int h, final CallDrawable callDrawable) {
        if (TextUtils.isEmpty(url) || context == null || view == null) {
            return;
        }

        Glide.with(context).load(url).into(new SimpleTarget<GlideDrawable>(w, h) {
            @Override
            public void onResourceReady(GlideDrawable resource,
                                        GlideAnimation<? super GlideDrawable> glideAnimation) {
                if (null != view) {
                    view.setImageDrawable(resource);
                }
                callDrawable.callback(resource);
            }
        });
    }

    /**
     * 回调图片  drawble
     *
     * @param context
     * @param url
     * @param view
     * @param w
     * @param h
     */
    public static void callDrawble(Fragment context, String url, final ImageView view,
                                   int w, int h, final CallDrawable callDrawable) {
        if (TextUtils.isEmpty(url) || context == null || view == null) {
            return;
        }

        Glide.with(context).load(url).into(new SimpleTarget<GlideDrawable>(w, h) {
            @Override
            public void onResourceReady(GlideDrawable resource
                    , GlideAnimation<? super GlideDrawable> glideAnimation) {
                if (null != view) {
                    view.setImageDrawable(resource);
                }
                callDrawable.callback(resource);
            }
        });
    }

    /**
     * 回调图片  bitmap
     *
     * @param context
     * @param url
     * @param view
     * @param w
     * @param h
     */
    public static void callBitmap(Context context, String url, final ImageView view,
                                  int w, int h, final CallBitmap callBitmap) {
        if (TextUtils.isEmpty(url) || context == null || view == null) {
            return;
        }

        Glide.with(context).load(url).into(new SimpleTarget<GlideDrawable>(w, h) {
            @Override
            public void onResourceReady(GlideDrawable resource,
                                        GlideAnimation<? super GlideDrawable> glideAnimation) {
                if (null != view) {
                    view.setImageDrawable(resource);
                }
                BitmapDrawable drawable1 = (BitmapDrawable) (Drawable) resource;
                callBitmap.callback(drawable1.getBitmap());
            }
        });
    }

    /**
     * 回调图片  bitmap
     *
     * @param context
     * @param url
     * @param view
     * @param w
     * @param h
     */
    public static void callBitmap(Fragment context, String url, final ImageView view,
                                  int w, int h, final CallBitmap callBitmap) {
        if (TextUtils.isEmpty(url) || context == null || view == null) {
            return;
        }

        Glide.with(context).load(url).into(new SimpleTarget<GlideDrawable>(w, h) {
            @Override
            public void onResourceReady(GlideDrawable resource,
                                        GlideAnimation<? super GlideDrawable> glideAnimation) {
                if (null != view) {
                    view.setImageDrawable(resource);
                }
                BitmapDrawable drawable1 = (BitmapDrawable) (Drawable) resource;
                callBitmap.callback(drawable1.getBitmap());
            }
        });
    }


    public interface CallDrawable {
        void callback(Drawable drawable);
    }

    public interface CallBitmap {
        void callback(Bitmap bitmap);
    }

}
