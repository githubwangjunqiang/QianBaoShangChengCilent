package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.ValueCallback;
import android.widget.Button;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.google.gson.Gson;
import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.DingDanOver;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HtmlData;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.DingDanModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.TitleLayout;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.LoadingDialog;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.LogUtils;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean.LOGIN;

public class WebViewHuoDongActivity extends BaseActivity {

    @Bind(R.id.huodong_titile)
    TitleLayout huodongTitile;
    @Bind(R.id.webView)
    BridgeWebView webView;
    @Bind(R.id.button)
    Button button;
    private LoadingDialog loadingDialog;

    public static void startWebViewHuoDongActivity(Context context, String url) {
        Intent intent = new Intent(context, WebViewHuoDongActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_huo_dong);
        ButterKnife.bind(this);
        if (TextUtils.isEmpty(getIntent().getStringExtra("url"))) {
            To.oo("本地传参为空");
            finish();
            return;
        }
        LogUtils.d("html5=" + getIntent().getStringExtra("url"));
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 初始化操作
     */
    private void init() {
        huodongTitile.setTitle("天天活动", true);
        huodongTitile.setCallback(new TitleLayout.Callback(this));
        loadingDialog = new LoadingDialog(this);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                webView.callHandler("functionInJs", "data from Java", new CallBackFunction() {
//
//                    @Override
//                    public void onCallBack(String data) {
//                        // TODO Auto-generated method stub
//                        LogUtils.d("reponse data from js " + data);
//                    }
//                });
//            }
//        });
        webView.setDefaultHandler(new DefaultHandler());
//        webView.setWebChromeClient(new WebChromeClient() {
//
//            @SuppressWarnings("unused")
//            public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType, String capture) {
//                this.openFileChooser(uploadMsg);
//            }
//
//            @SuppressWarnings("unused")
//            public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType) {
//                this.openFileChooser(uploadMsg);
//            }
//
//            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
//                mUploadMessage = uploadMsg;
////                pickFile();
//            }
//        });

        webView.loadUrl(getIntent().getStringExtra("url"));

        webView.registerHandler("mustgo", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                LogUtils.d("js调用java返回参数=" + data);
                toStartDingdan(data);
//                function.onCallBack("submitFromWeb exe, response data 中文 from Java");
            }

        });

//        User user = new User();
//        Location location = new Location();
//        location.address = "SDU";
//        user.location = location;
//
//        user.name = "大头鬼";

//        webView.callHandler("functionInJs", new Gson().toJson(user), new CallBackFunction() {
//            @Override
//            public void onCallBack(String data) {
//
//            }
//        });

//        webView.send("hello");
        loadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    clearCall();
                }
                return false;
            }
        });
    }

    /**
     * html 回掉 去订单界面
     *
     * @param data
     */
    private void toStartDingdan(String data) {
        if (TextUtils.isEmpty(App.getUserId())) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("huodong", data);
            startActivityForResult(intent, LOGIN);
        }
        final HtmlData htmlData = new Gson().fromJson(data, HtmlData.class);
        loadingDialog.show();
        setCall(new DingDanModel().outDingDan(App.getUserId(), htmlData.getGoods_id(), "1", null
                , null, false, null, null, new IModel.AsyncCallBack() {
                    @Override
                    public void onSucceed(Object obj) {
                        if (isFinishing()) {
                            return;
                        }
                        DingDanOver.DataBean data = (DingDanOver.DataBean) obj;
                        ToHUoDongDingdanActivity.startToDingdanActivity(WebViewHuoDongActivity.this
                        ,htmlData.getGoods_name(),htmlData.getGoods_id(),"1",data.getPayables()+""
                        ,data.getPayables()+"");
                        loadingDialog.dismiss();
                    }

                    @Override
                    public void onError(Object obj) {
                        if (isFinishing()) {
                            return;
                        }
                        To.ee(obj);
                        loadingDialog.dismiss();
                    }
                }));
    }


//    int RESULT_CODE = 0;

    ValueCallback<Uri> mUploadMessage;

//    static class Location {
//        String address;
//    }
//
//    static class User {
//        String name;
//        Location location;
//        String testStr;
//    }

//    public void pickFile() {
//        Intent chooserIntent = new Intent(Intent.ACTION_GET_CONTENT);
//        chooserIntent.setType("image/*");
//        startActivityForResult(chooserIntent, RESULT_CODE);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
////        if (requestCode == RESULT_CODE) {
////            if (null == mUploadMessage) {
////                return;
////            }
////            Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
////            mUploadMessage.onReceiveValue(result);
////            mUploadMessage = null;
////        }
//    }
//


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case LOGIN://登陆返回
                if (resultCode == LOGIN && data != null && TextUtils.isEmpty(
                        data.getStringExtra("huodong"))) {
                    toStartDingdan(data.getStringExtra("huodong"));
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
