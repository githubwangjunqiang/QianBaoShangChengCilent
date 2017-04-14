package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
import android.widget.Toast;

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
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Constants;
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
    private String SHAREURL = "http://t.cn/RX2fJI3";//分享链接
    private LoadingDialog looding;
    private String title = "让我们互相伤害吧";
    private String content = "黔宝商城海量大甩卖！还在等什么？快来补刀吧......";
    private AsyncTask<Void, Void, String> execute;
    /**
     * 微博微博分享接口实例
     */
    private IWeiboShareAPI mWeiboShareAPI = null;
    private boolean weiboAppInstalled;

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
                if (execute != null && !execute.isCancelled()) {
                    execute.cancel(true);
                }
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


        // 创建微博分享接口实例
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, Constants.APP_KEY);
        // 注册第三方应用到微博客户端中，注册成功后该应用将显示在微博的应用列表中。
        // 但该附件栏集成分享权限需要合作申请，详情请查看 Demo 提示
        // NOTE：请务必提前注册，即界面初始化的时候或是应用程序初始化时，进行注册
        mWeiboShareAPI.registerApp();
      weiboAppInstalled = mWeiboShareAPI.isWeiboAppInstalled();

        // 当 Activity 被重新初始化时（该 Activity 处于后台时，可能会由于内存不足被杀掉了），
        // 需要调用 {@link IWeiboShareAPI#handleWeiboResponse} 来接收微博客户端返回的数据。
        // 执行成功，返回 true，并调用 {@link IWeiboHandler.Response#onResponse}；
        // 失败返回 false，不调用上述回调
        if (savedInstanceState != null) {
            mWeiboShareAPI.handleWeiboResponse(getIntent(), this);
        }
    }

    /**
     * QQ分享
     */
    private void qqShare() {

    }

    /**
     * 新浪微博
     */
    private void sinaShare() {
//        execute = new AsyncTask<Void, Void, String>() {
//            @Override
//            protected void onPreExecute() {
//                looding.show();
//            }
//
//            @Override
//            protected String doInBackground(Void... params) {
//                File file = new File(App.getContext().getExternalCacheDir().getAbsolutePath(), "app.jpg");
//                try {
//                    if (file.exists()) {
//                        file.delete();
//                        file.createNewFile();
//                    }
//                    boolean qrCode = EncodingUtils.createQRCode(SHAREURL, 500, 500,
//                            BitmapFactory.decodeResource(WeiBoShareActivity.this.getResources(),
//                                    R.mipmap.ic_launcher), file.getPath());
//                    if (qrCode && file.length() > 0) {
//                        return file.getPath();
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                if (TextUtils.isEmpty(s)) {
//                    looding.dismiss();
//                    To.ee("分享失败");
//                    return;
//                }
//                // 获取微博客户端相关信息，如是否安装、支持 SDK 的版本
//                boolean isInstalledWeibo = mWeiboShareAPI.isWeiboAppInstalled();
//                int supportApiLevel = mWeiboShareAPI.getWeiboAppSupportAPI();
//                // 1. 初始化微博的分享消息
//                WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
//                weiboMessage.textObject = getTextObj();
//                weiboMessage.imageObject = getImageObj(s);
//                // 2. 初始化从第三方到微博的消息请求
//                SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
//                // 用transaction唯一标识一个请求
//                request.transaction = String.valueOf(System.currentTimeMillis());
//                request.multiMessage = weiboMessage;
//                // 3. 发送请求消息到微博，唤起微博分享界面
//                if (isInstalledWeibo) {
//                    mWeiboShareAPI.sendRequest(WeiBoShareActivity.this, request);
//                } else {
//                    AuthInfo authInfo = new AuthInfo(WeiBoShareActivity.this,
//                            Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
//                    Oauth2AccessToken accessToken =
//                            AccessTokenKeeper.readAccessToken(getApplicationContext());
//                    String token = "";
//                    if (accessToken != null) {
//                        token = accessToken.getToken();
//                    }
//                    mWeiboShareAPI.sendRequest(WeiBoShareActivity.this,
//                            request, authInfo, token, new WeiboAuthListener() {
//
//                                @Override
//                                public void onWeiboException(WeiboException arg0) {
//                                    To.oo("失败");
//                                }
//
//                                @Override
//                                public void onComplete(Bundle bundle) {
//                                    // TODO Auto-generated method stub
//                                    Oauth2AccessToken newToken =
//                                            Oauth2AccessToken.parseAccessToken(bundle);
//                                    AccessTokenKeeper.writeAccessToken(getApplicationContext(),
//                                            newToken);
//                                    Toast.makeText(getApplicationContext(),
//                                            "onAuthorizeComplete token = " + newToken.getToken(),
//                                            Toast.LENGTH_SHORT).show();
//                                    callThisActivity();
//                                }
//
//                                @Override
//                                public void onCancel() {
//                                }
//                            });
//                }
//            }
//        }.execute();
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
        } else{
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
                }

                @Override
                public void onComplete(Bundle bundle) {
                    // TODO Auto-generated method stub
                    Oauth2AccessToken newToken = Oauth2AccessToken.parseAccessToken(bundle);
                    AccessTokenKeeper.writeAccessToken(getApplicationContext(), newToken);
                    Toast.makeText(getApplicationContext(), "onAuthorizeComplete token = "
                            + newToken.getToken(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancel() {
                }
            });
        }
    }


    /**
     * 微信分享
     */
    private void weixinShare(String url) {
        looding.show();
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
            callThisActivity();
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
        String text = "http://www.baidu.com";
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
        To.oo("会滴哦啊");
        if (baseResp != null) {
            switch (baseResp.errCode) {
                case WBConstants.ErrorCode.ERR_OK:
                    Toast.makeText(this, R.string.weibosdk_demo_toast_share_success, Toast.LENGTH_LONG).show();
                    if (looding != null && looding.isShowing()) {
                        looding.dismiss();
                    }
                    callThisActivity();
                    break;
                case WBConstants.ErrorCode.ERR_CANCEL:
                    Toast.makeText(this, R.string.weibosdk_demo_toast_share_canceled, Toast.LENGTH_LONG).show();
                    if (looding != null && looding.isShowing()) {
                        looding.dismiss();
                    }
                    break;
                case WBConstants.ErrorCode.ERR_FAIL:
                    Toast.makeText(this,
                            this.getString(R.string.weibosdk_demo_toast_share_failed) + "Error Message: " + baseResp.errMsg,
                            Toast.LENGTH_LONG).show();
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
