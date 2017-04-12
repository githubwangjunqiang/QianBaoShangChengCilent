package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xys.libzxing.zxing.encoding.EncodingUtils;
import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.TitleLayout;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.GetWinDowHeight;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.LogUtils;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.MD5Utils;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LookQRcodeActivity extends BaseActivity {

    @Bind(R.id.lookqr_title)
    TitleLayout lookqrTitle;
    @Bind(R.id.lookqr_ivimage)
    ImageView lookqrIvimage;
    @Bind(R.id.lookqr_tvordersn)
    TextView lookqrTvordersn;
    @Bind(R.id.look_layout)
    SwipeRefreshLayout lookLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_qrcode);
        ButterKnife.bind(this);
        if (TextUtils.isEmpty(getIntent().getStringExtra("phone")) ||
                TextUtils.isEmpty(getIntent().getStringExtra("order_sn"))) {
            To.ee("请您刷新数据，生成二维码界面参数缺失");
            finish();
            return;
        }
        init();

        showQrCode();
        setListener();
    }

    /**
     * 监听器
     */
    private void setListener() {
        lookLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showQrCode();
            }
        });
    }

    /**
     * 初始化
     */
    private void init() {
        lookqrTitle.setTitle(R.string.yanzhengma, true);
        lookqrTitle.setCallback(new TitleLayout.Callback(this));
        lookqrTvordersn.setText("您的订单号：" + getIntent().getStringExtra("order_sn"));
        lookqrTvordersn.setVisibility(View.INVISIBLE);
        lookLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.white)
                , ContextCompat.getColor(this, R.color.background_p));
        lookLayout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this, R.color.app_color));
    }

    /**
     * 显示二维码
     */
    private void showQrCode() {
        new MyQrCodeTask().execute();
    }

    private File file;

    private class MyQrCodeTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            lookLayout.setRefreshing(true);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            int width = GetWinDowHeight.getScreenWeight(App.getContext()) / 2;
            LogUtils.d("二维码宽度=" + width);

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            String strings = MD5Utils.md5Code(new StringBuffer()
                    .append(getIntent().getStringExtra("order_sn"))
                    .append(getIntent().getStringExtra("phone")).toString());
            strings = strings + "," + getIntent().getStringExtra("order_sn");
            if (TextUtils.isEmpty(strings)) {
                return false;
            }
            file = new File(App.getContext().getExternalFilesDir("image"), "erweima.jpg");
            if (file.exists()) {
                file.delete();
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            boolean qrCode = EncodingUtils.createQRCode(strings, 500, 500, bitmap
                    , file.getPath());
            if (qrCode && file.length() > 0) {
                return true;
            }
            return false;
        }


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                ToGlide.url(LookQRcodeActivity.this, Uri.fromFile(file), lookqrIvimage);
            } else {
                To.ee("生成二维码失败请重试");
                lookqrIvimage.setImageResource(R.mipmap.ic_launcher);
            }
            lookLayout.setRefreshing(false);
        }
    }

    public static void startLookQRActivity(Context context, String phone, String order_sn) {
        Intent intent = new Intent(context, LookQRcodeActivity.class);
        intent.putExtra("phone", phone);
        intent.putExtra("order_sn", order_sn);
        context.startActivity(intent);
    }

    /**
     * 显示订单号
     *
     * @param view
     */
    public void showorder_sn(View view) {
        lookqrTvordersn.setVisibility(View.VISIBLE);
        lookqrTvordersn.setText("您的订单号：" + getIntent().getStringExtra("order_sn"));
    }

    @Override
    public void nodifyDingdan() {
        super.nodifyDingdan();
        To.dd("店家扫描完成，您消费成功");
        finish();
    }
}
