package com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.AccessTokenKeeper;
import com.sina.weibo.sdk.exception.WeiboException;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Constants;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;

import java.io.ByteArrayOutputStream;

import static com.yunyouzhiyuan.qianbaoshangchengclient.R.mipmap.weixinshare;


/**
 * Created by wangjunqiang on 2016/11/21.
 */
public class DialogShareSDK extends BaseDiaLog {
    private GridView gvShare;
    private LinearLayout llTop;
    private int GVNUMBER = 3;//分享三方的数量
    private String SHAREURL = "http://t.cn/RX2fJI3";//分享链接
    private Activity activity;
    private LoadingDialog looding;

    public DialogShareSDK(Activity context) {
        super(context, R.style.dialogWindowAnim);
        activity = context;
        setContentView(R.layout.dialog_share);
    }

    @Override
    public void dismiss() {
        if (looding != null && looding.isShowing()) {
            looding.dismiss();
        }
        super.dismiss();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        init();
        setadapter();
    }

    /**
     * 适配器
     */
    private void setadapter() {
        gvShare.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return GVNUMBER;
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
            public View getView(int position, View convertView, ViewGroup parent) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.itme_dialog_share,
                        parent, false);
                ImageView imageView = (ImageView) convertView.findViewById(R.id.dialog_share_ivimage);
                TextView textView = (TextView) convertView.findViewById(R.id.dialog_share_tvname);

                switch (position) {
                    case 0://微信
                        imageView.setImageResource(weixinshare);
                        textView.setText(R.string.weixin);
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                weixinShare(SHAREURL);
                            }
                        });
                        break;
                    case 1://QQ
                        imageView.setImageResource(R.mipmap.qq);
                        textView.setText(R.string.qq);
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                qqShare();
                            }
                        });
                        break;
                    case 2://微博
                        imageView.setImageResource(R.mipmap.weibo);
                        textView.setText(R.string.xinlangweibo);
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sinaShare();
                            }
                        });
                        break;
                    default:
                        break;
                }
                return convertView;
            }
        });
    }

    String title = "让我们互相伤害吧";
    String content = "黔宝商城海量大甩卖！还在等什么？快来补刀吧......";

    /**
     * 新浪微博
     */
    private void sinaShare() {
        looding.show();
        /**
         * 微博分享的接口实例
         * 创建微博 SDK 接口实例
         * */
        IWeiboShareAPI mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(getContext(),
                Constants.APP_KEY);
        // 获取微博客户端相关信息，如是否安装、支持 SDK 的版本
        boolean isInstalledWeibo = mWeiboShareAPI.isWeiboAppInstalled();
        int supportApiLevel = mWeiboShareAPI.getWeiboAppSupportAPI();
        // 注册到新浪微博
        mWeiboShareAPI.registerApp();
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        weiboMessage.textObject = getTextObj();
        weiboMessage.imageObject = getImageObj();
        // 2. 初始化从第三方到微博的消息请求
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;
        // 3. 发送请求消息到微博，唤起微博分享界面
        if (isInstalledWeibo) {
            mWeiboShareAPI.sendRequest(activity, request);
        } else {
            AuthInfo authInfo = new AuthInfo(getContext(),
                    Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
            Oauth2AccessToken accessToken =
                    AccessTokenKeeper.readAccessToken(App.getContext());
            String token = "";
            if (accessToken != null) {
                token = accessToken.getToken();
            }
            mWeiboShareAPI.sendRequest(activity, request, authInfo, token, new WeiboAuthListener() {

                @Override
                public void onWeiboException(WeiboException arg0) {
                    if (looding != null && looding.isShowing()) {
                        looding.dismiss();
                    }
                    Toast.makeText(App.getContext(), "微博分享失败", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onComplete(Bundle bundle) {
                    // TODO Auto-generated method stub
                    Oauth2AccessToken newToken = Oauth2AccessToken.parseAccessToken(bundle);
                    AccessTokenKeeper.writeAccessToken(App.getContext(), newToken);
                    if (looding != null && looding.isShowing()) {
                        looding.dismiss();
                    }
                    dismiss();
                    To.dd(newToken.getToken());
                }

                @Override
                public void onCancel() {
                    if (looding != null && looding.isShowing()) {
                        looding.dismiss();
                    }
                }
            });
        }
    }

    /**
     * QQ分享
     */
    private void qqShare() {

    }


    /**
     * 微信分享
     */
    private void weixinShare(String url) {
        looding.show();
        IWXAPI api = WXAPIFactory.createWXAPI(getContext(), Bean.WE_XIN_APPID);
        api.registerApp(Bean.WE_XIN_APPID);
        WXWebpageObject wxWebpageObject = new WXWebpageObject();
        wxWebpageObject.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(wxWebpageObject);
        msg.title = title;
        msg.description = content;
        Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(),
                R.mipmap.ic_launcher);
        Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap, 60, 60, true);//如果是大图 必须创建原图的缩略图
        bitmap.recycle();//缩略图创建 把原图释放掉
        msg.thumbData = Bitmap2Bytes(bitmap1, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction(getContext().getPackageName());//自定义唯一标识 用于请求微信的标识来表示本应用
        req.message = msg;
//        req.scene = SendMessageToWX.Req.WXSceneTimeline;//朋友圈
        req.scene = SendMessageToWX.Req.WXSceneSession;//好友
        if (api != null) {
            api.sendReq(req);
            dismiss();
        } else {
            To.oo("微信初始化失败");
        }
    }

    /**
     * 操作view
     */
    private void init() {
        looding = new LoadingDialog(getContext());
        gvShare = (GridView) findViewById(R.id.dialog_share_gv);
        llTop = (LinearLayout) findViewById(R.id.dialog_share_ll);
        gvShare.setNumColumns(GVNUMBER > 3 ? 3 : GVNUMBER);
    }

    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wlp.gravity = Gravity.BOTTOM;
        window.setAttributes(wlp);
        window.setWindowAnimations(R.style.dialogWindowAnim);
    }

    @Override
    public View setView() {
        return llTop;
    }

    public byte[] Bitmap2Bytes(Bitmap bm, boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 80, output);
        if (needRecycle) {
            bm.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis())
                : type + System.currentTimeMillis();
    }

    /**
     * 创建文本消息对象。
     *
     * @return 文本消息对象。
     */
    private TextObject getTextObj() {
        TextObject textObject = new TextObject();
        textObject.text = getSharedText();
        return textObject;
    }

    /**
     * 获取分享的文本模板。
     *
     * @return 分享的文本模板
     */
    private String getSharedText() {
//        int formatId = R.string.weibosdk_demo_share_text_template;
//        String format = getString(formatId);
        String text = title;
//        if (mTextCheckbox.isChecked() || mImageCheckbox.isChecked()) {
//            text = "@大屁老师，这是一个很漂亮的小狗，朕甚是喜欢-_-!!";
//        }
        return text;
    }

    /**
     * 创建图片消息对象。
     *
     * @return 图片消息对象。
     */
    private ImageObject getImageObj() {
        ImageObject imageObject = new ImageObject();
//        BitmapDrawable bitmapDrawable = (BitmapDrawable) mImageView.getDrawable();
        //设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.ic_launcher);
        imageObject.setImageObject(bitmap);
        return imageObject;
    }
}
