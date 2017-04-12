package com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.GetWinDowHeight;
/**
 * Created by wangjunqiang on 2016/11/21.
 */
public class DialogHoteStorInfo extends Dialog {
    private Callback callback;
    private String goodsid;
    private String title;
    private String price;
    private LinearLayout lltoth;

    public DialogHoteStorInfo(Context context, String goodsid, Callback callback, String title, String price) {
        super(context, R.style.dialogWindowAnim);
        this.goodsid = goodsid;
        this.callback = callback;
        this.title = title;
        this.price = price;
        setContentView(R.layout.dialog_hotel_fangjian);
    }

    public interface Callback {
        void callback();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        init();
        loadData();
        mWebView.loadUrl(HTTPURL.goodshinfo + "?goods_id=" + goodsid);
        setListener();
    }

    private float fistY = 0f;
    private float fistYdd = 0f;

    /**
     * 监听器
     */
    private void setListener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.callback();
                dismiss();
            }
        });

        lltoth.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    fistY = event.getY();
                    fistYdd = event.getY();
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    float y1 = event.getY();
                    if (y1 > fistY) {
                        linearLayout.scrollBy(0, (int) -(y1 - fistY));
                        fistY = y1;
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    float y = event.getY();
                    if ((y - fistYdd) > linearLayout.getHeight() / 3) {
                        dismiss();
                    } else {
                        linearLayout.scrollTo(0, 0);
                    }
                }
                return true;
            }
        });
    }

    /**
     * 显示webview
     */
    private void loadData() {
        WebSettings webSettings = mWebView.getSettings();
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
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    private TextView tvName, tvPrice;
    private ImageView ivBack;
    private Button btnOk;
    private WebView mWebView;
    private LinearLayout linearLayout;

    /**
     * 初始化
     */
    private void init() {
        btnOk = (Button) findViewById(R.id.ll_dialog_hotel_btnok);
        tvPrice = (TextView) findViewById(R.id.ll_dialog_hotel_tvprice);
        mWebView = (WebView) findViewById(R.id.ll_dialog_hotel_webview);
        ivBack = (ImageView) findViewById(R.id.ll_dialog_hotel_ivback);
        tvName = (TextView) findViewById(R.id.ll_dialog_hotel_tvname);
        linearLayout = (LinearLayout) findViewById(R.id.ll_dialog_hotel);
        lltoth = (LinearLayout) findViewById(R.id.ll_dialog_hotel_ll);
        tvName.setText(title);
        tvPrice.setText("￥:" + price);
    }

    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = (int) (GetWinDowHeight.getScreenHeight(App.getContext()) * 0.7);
        wlp.gravity = Gravity.BOTTOM;
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(wlp);
        window.setWindowAnimations(R.style.dialogWindowAnim);
    }
}
