package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;

public class SplashActivity extends BaseActivity {
    ImageView ImgMark;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 111) {
                index--;
                tvTime.setText("黔宝商城欢迎您\t" + index);
                if (index == 0) {
                    ImgMark.post(new Runnable() {
                        @Override
                        public void run() {
                            startAnimat();
                        }
                    });
                } else {
                    handler.sendEmptyMessageDelayed(111, 1000);
                }
            }
        }
    };
    private int index = 3;
    private TextView tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);


        ImgMark = (ImageView) findViewById(R.id.ivbaijing);
        tvTime = (TextView) findViewById(R.id.tvdaojishi);
        tvTime.setText("黔宝商城欢迎您\t" + index);
        handler.sendEmptyMessageDelayed(111, 1000);
    }

    @Override
    public void upImage() {

    }

    @Override
    protected void onDestroy() {
        if (handler != null) {
            handler.removeMessages(111);
        }
        handler = null;
        super.onDestroy();
    }

    private void startAnimat() {

        int imgHeight = ImgMark.getHeight() / 5;
        int height = getWindowManager().getDefaultDisplay().getHeight();
        int curY = height / 2 + imgHeight / 2;
        int dy = (height - imgHeight) / 2;
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator animatorTranslate = ObjectAnimator.ofFloat(ImgMark, "translationY", 0, dy);
        ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(ImgMark, "ScaleX", 1f, 0.2f);
        ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(ImgMark, "ScaleY", 1f, 0.2f);
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(ImgMark, "alpha", 1f, 0.5f);
        set.play(animatorTranslate)
                .with(animatorScaleX).with(animatorScaleY).with(animatorAlpha);
        set.setDuration(800);
        set.setInterpolator(new AccelerateInterpolator());
        set.start();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                finish();
                overridePendingTransition(0, 0);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
