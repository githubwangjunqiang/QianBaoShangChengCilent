package com.yunyouzhiyuan.qianbaoshangchengclient.util;

import android.os.CountDownTimer;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by wangjunqiang on 2016/10/28.
 */
public class Time_down extends CountDownTimer {
    private TextView tv;
    public Time_down(long millisInFuture, long countDownInterval, TextView tv) {
        super(millisInFuture, countDownInterval);
        this.tv = tv;
        this.tv.setClickable(false);
    }

    @Override
    public void onTick(long millisUntilFinished) {
      tv.setText("倒计时："+formatDuring(millisUntilFinished));
    }

    @Override
    public void onFinish() {
        tv.setText("获取验证码");
        tv.setClickable(true);
    }

    public String formatDuring(Date begin, Date end) {
        return formatDuring(end.getTime() - begin.getTime());
    }

    public String formatDuring(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
//        return days + " 天 " + hours + " 时 " + minutes + " 分 "
//                + seconds + " 秒 ";
        return seconds + " 秒 ";
    }

}
