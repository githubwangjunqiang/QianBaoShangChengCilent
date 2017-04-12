package com.yunyouzhiyuan.qianbaoshangchengclient.entiy;

/**
 * Created by ${王俊强} on 2017/3/8.
 */

public class KTVgvTime {
    private String time;
    private boolean isChecked;

    public KTVgvTime(String time, boolean isChecked) {
        this.time = time;
        this.isChecked = isChecked;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
