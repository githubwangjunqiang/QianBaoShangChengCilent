package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;


/**
 * Created by Administrator on 2016/8/2 0002.
 */
public class WebViewActivity extends BaseActivity {
    private WebView mView;
    private ImageView ibBack;
    private TextView tvtitle;

    @Override
    protected void onDestroy() {
        mView.clearCache(false);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);


        mView = (WebView) findViewById(R.id.webviewactivity_webview);
        ibBack = (ImageView) findViewById(R.id.webView_ivback);
        tvtitle = (TextView) findViewById(R.id.webview_tvtitle);
        String url = getIntent().getStringExtra("url");
        if (getIntent().getStringExtra("title") != null) {
            tvtitle.setText(getIntent().getStringExtra("title"));
        }
        WebSettings webSettings = mView.getSettings();
        //支持javascript
        webSettings.setJavaScriptEnabled(true);
        // 设置可以支持缩放
        webSettings.setSupportZoom(true);
        // 设置出现缩放工具
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        //自适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//关闭webview中缓存
        mView.loadUrl(url);
        mView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        setlistener();

    }

    public static void startWebViewActivity(Context context, String allUrl, String title) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("url", allUrl);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }


    /**
     * 监听器
     */
    private void setlistener() {
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.this.finish();
            }
        });
        mView.setDownloadListener(new MyWebViewDownLoadListener());

    }
    /**
     * 点击超链接 进行下载任务
     */
    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                    long contentLength) {
//            Log.i("tag", "url="+url);
//            Log.i("tag", "userAgent="+userAgent);
//            Log.i("tag", "contentDisposition="+contentDisposition);
//            Log.i("tag", "mimetype="+mimetype);
//            Log.i("tag", "contentLength="+contentLength);
            //调用 系统浏览器 进行下载任务
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    /**
     * 按返回键时， 不退出程序而是返回上一浏览页面：
     *
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mView.canGoBack()) {
            mView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
