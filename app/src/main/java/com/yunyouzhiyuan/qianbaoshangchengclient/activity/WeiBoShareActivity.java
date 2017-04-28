package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.AccessTokenKeeper;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.exception.WeiboException;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Constants;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.QQ;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.LoadingDialog;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;

import java.io.ByteArrayOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.yunyouzhiyuan.qianbaoshangchengclient.R.mipmap.weixinshare;

/**
 * Created by ${王俊强} on 2017/4/13.
 */

public class WeiBoShareActivity extends BaseActivity implements IWeiboHandler.Response {
    @Bind(R.id.dialog_share_gv)
    GridView gvShare;
    @Bind(R.id.dialog_share_ll)
    LinearLayout view;
    private int GVNUMBER = 3;//分享三方的数量
    /**
     * 分享链接
     */
    private final String SHAREURL = "http://a.app.qq.com/o/simple.jsp?pkgname=com.yunyouzhiyuan.qianbaoshangchengclient";
    private LoadingDialog looding;
    private final String title = "好友在召唤你";
    private final String content = "黔宝商城海量大优惠！还在等什么？快来补刀吧......";
    private final String IMAGEURL = "http://www.bm37.com/item/4/220/TB1dTaIFVXXXXbLXVXXXXXXXXXX_!!0-item_pic.jpg";


    /**
     * 微博微博分享接口实例
     */
    private IWeiboShareAPI mWeiboShareAPI = null;
    private boolean weiboAppInstalled;
    private boolean isWeixin = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_share);
        ButterKnife.bind(this);
        init(savedInstanceState);
        setAdapter();
        setListener();

    }

    /**
     * 设置适配器
     */
    private void setListener() {
        looding.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return false;
            }
        });
        gvShare.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://微信
                        weixinShare(SHAREURL);
                        break;
                    case 1://QQ
                        qqShare();
                        break;
                    case 2://微博
                        sinaShare();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 监听器
     */
    private void setAdapter() {
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
                convertView = LayoutInflater.from(WeiBoShareActivity.this).inflate(R.layout.itme_dialog_share,
                        parent, false);
                ImageView imageView = (ImageView) convertView.findViewById(R.id.dialog_share_ivimage);
                TextView textView = (TextView) convertView.findViewById(R.id.dialog_share_tvname);

                switch (position) {
                    case 0://微信
                        imageView.setImageResource(weixinshare);
                        textView.setText(R.string.weixin);
                        break;
                    case 1://QQ
                        imageView.setImageResource(R.mipmap.qq);
                        textView.setText(R.string.qq);
                        break;
                    case 2://微博
                        imageView.setImageResource(R.mipmap.weibo);
                        textView.setText(R.string.xinlangweibo);
                        break;
                    default:
                        break;
                }
                return convertView;
            }
        });

    }

    /**
     * 初始化
     *
     * @param savedInstanceState
     */
    private void init(Bundle savedInstanceState) {
        looding = new LoadingDialog(this);
        gvShare.setNumColumns(GVNUMBER > 3 ? 3 : GVNUMBER);


        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, Constants.APP_KEY);
        mWeiboShareAPI.registerApp();
        weiboAppInstalled = mWeiboShareAPI.isWeiboAppInstalled();
        if (savedInstanceState != null) {
            mWeiboShareAPI.handleWeiboResponse(getIntent(), this);
        }
    }

    /**
     * QQ分享
     */
    private void qqShare() {
        qqListener = new QQListener();
        Tencent mTencent = Tencent.createInstance(Bean.QQ_APPID, this.getApplicationContext());
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);//标题
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, content);//内容
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, SHAREURL);//点击跳转链接
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, IMAGEURL);//图片 可以是gif 类型
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, getString(R.string.app_name));//应用标志
//                    params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,  "其他附加功能");
        mTencent.shareToQQ(WeiBoShareActivity.this, params, qqListener);
    }

    private QQListener qqListener;

    private class QQListener implements IUiListener {

        @Override
        public void onComplete(Object o) {
            QQ qq = new Gson().fromJson(o.toString(), QQ.class);
            if ("sucess".equals(qq.getMsg())) {
                To.dd("分享成功");
            }
            callThisActivity();
        }

        @Override
        public void onError(UiError uiError) {
            To.oo(uiError.errorMessage);
        }

        @Override
        public void onCancel() {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (qqListener != null) {
            Tencent.onActivityResultData(requestCode, resultCode, data, qqListener);
        }
    }

    /**
     * 新浪微博
     */
    private void sinaShare() {
        looding.show();
        // 1. 初始化微博的分享消息
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        weiboMessage.textObject = getTextObj();
        weiboMessage.imageObject = getImageObj(null);
        // 2. 初始化从第三方到微博的消息请求
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;
        // 3. 发送请求消息到微博，唤起微博分享界面
        if (weiboAppInstalled) {
            mWeiboShareAPI.sendRequest(WeiBoShareActivity.this, request);
        } else {
            AuthInfo authInfo = new AuthInfo(this, Constants.APP_KEY,
                    Constants.REDIRECT_URL, Constants.SCOPE);
            Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(getApplicationContext());
            String token = "";
            if (accessToken != null) {
                token = accessToken.getToken();
            }
            mWeiboShareAPI.sendRequest(this, request, authInfo, token, new WeiboAuthListener() {

                @Override
                public void onWeiboException(WeiboException arg0) {
                    looding.dismiss();
                    To.oo("分享失败");
                }

                @Override
                public void onComplete(Bundle bundle) {
                    // TODO Auto-generated method stub
                    Oauth2AccessToken newToken = Oauth2AccessToken.parseAccessToken(bundle);
                    AccessTokenKeeper.writeAccessToken(getApplicationContext(), newToken);
//                    Toast.makeText(getApplicationContext(), "onAuthorizeComplete token = "
//                            + newToken.getToken(), Toast.LENGTH_SHORT).show();
                    looding.dismiss();
                    To.dd("分享成功");
                    callThisActivity();
                }

                @Override
                public void onCancel() {
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (looding != null && looding.isShowing() && isWeixin) {
            looding.dismiss();
        }
    }

    /**
     * 微信分享
     */
    private void weixinShare(String url) {
        looding.show();
        isWeixin = true;
        IWXAPI api = WXAPIFactory.createWXAPI(this, Bean.WE_XIN_APPID);
        api.registerApp(Bean.WE_XIN_APPID);
        WXWebpageObject wxWebpageObject = new WXWebpageObject();
        wxWebpageObject.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(wxWebpageObject);
        msg.title = title;
        msg.description = content;
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.ic_launcher);
        Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap, 60, 60, true);//如果是大图 必须创建原图的缩略图
        bitmap.recycle();//缩略图创建 把原图释放掉
        msg.thumbData = Bitmap2Bytes(bitmap1, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction(this.getPackageName());//自定义唯一标识 用于请求微信的标识来表示本应用
        req.message = msg;
//        req.scene = SendMessageToWX.Req.WXSceneTimeline;//朋友圈
        req.scene = SendMessageToWX.Req.WXSceneSession;//好友
        if (api != null) {
            api.sendReq(req);
        } else {
            To.oo("微信初始化失败");
        }
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
        String text = SHAREURL + content;
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
    private ImageObject getImageObj(String path) {
        ImageObject imageObject = new ImageObject();
//        BitmapDrawable bitmapDrawable = (BitmapDrawable) mImageView.getDrawable();
        //设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
//        Bitmap bitmap = BitmapFactory.decodeFile(path);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        imageObject.setImageObject(bitmap);
        return imageObject;
    }

    @Override
    public void onResponse(BaseResponse baseResp) {
        if (baseResp != null) {
            switch (baseResp.errCode) {
                case WBConstants.ErrorCode.ERR_OK:
                    To.dd(getString(R.string.weibosdk_demo_toast_share_success));
                    if (looding != null && looding.isShowing()) {
                        looding.dismiss();
                    }
                    callThisActivity();
                    break;
                case WBConstants.ErrorCode.ERR_CANCEL:
                    To.ee(getString(R.string.weibosdk_demo_toast_share_canceled));
                    if (looding != null && looding.isShowing()) {
                        looding.dismiss();
                    }
                    break;
                case WBConstants.ErrorCode.ERR_FAIL:
                    To.ee(getString(R.string.weibosdk_demo_toast_share_failed) +
                            "Error Message: " + baseResp.errMsg);
                    if (looding != null && looding.isShowing()) {
                        looding.dismiss();
                    }
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        callThisActivity();
        super.onBackPressed();
    }

    /**
     * 关闭activity
     */
    private void callThisActivity() {
        if (looding != null && looding.isShowing()) {
            looding.dismiss();
        }
        finish();
        overridePendingTransition(0, R.anim.tr_out);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // 从当前应用唤起微博并进行分享后，返回到当前应用时，需要在此处调用该函数
        // 来接收微博客户端返回的数据；执行成功，返回 true，并调用
        // {@link IWeiboHandler.Response#onResponse}；失败返回 false，不调用上述回调
        if (mWeiboShareAPI != null) {
            mWeiboShareAPI.handleWeiboResponse(intent, this);
        }
    }

    /**
     * view点击事件
     *
     * @param view
     */
    public void finshactivty(View view) {
        callThisActivity();
    }

    float startY;
    float moveY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveY = ev.getY() - startY;
                view.scrollBy(0, -(int) moveY);
                startY = ev.getY();
                if (view.getScrollY() > 0) {
                    view.scrollTo(0, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (view.getScrollY() < -this.getWindow().getAttributes().height / 4 && moveY > 0) {
                    callThisActivity();
                }
                view.scrollTo(0, 0);
                break;
        }
        return super.onTouchEvent(ev);
    }
}
