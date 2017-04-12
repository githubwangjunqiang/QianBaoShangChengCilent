package com.yunyouzhiyuan.qianbaoshangchengclient.baiduMap;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;

/**
 * Created by ${王俊强} on 2017/3/5.
 */

public class MyOrientationListener implements SensorEventListener {

    private SensorManager mSensorManager;//传感器管理者
    private Context mContext;//上下文
    private Sensor mSensor;//方向传感器


    private float lastX;

    public MyOrientationListener(Context context) {
        this.mContext = context;
    }

    /**
     * 开始监听方向传感器
     */
    public void start() {
        mSensorManager = (SensorManager)
                mContext.getSystemService(Context.SENSOR_SERVICE);//获取系统服务（传感器服务管理者）
        if (mSensorManager != null) {
            mSensor = mSensorManager.getDefaultSensor
                    (Sensor.TYPE_ORIENTATION);//利用管理者获得方向传感器
        }else{
            To.oo("方向传感器管理者没拿到");
        }
        if (mSensor != null) {
            To.oo("方向传感器到l");
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_GAME);
        }else{
            To.oo("方向传感器没拿到");
        }

    }

    /**
     * 停止方向传感器
     */
    public void stop() {
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
        }
    }


    /**
     * z轴  x轴 y轴 变动时调用   导航箭头功能可以用
     *
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        To.oo(event.values[SensorManager.DATA_X]);
        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {//是不是方向传感器数据
            float x = event.values[SensorManager.DATA_X];
            if (Math.abs(x - lastX) > 1.0) {//如果大于1.0  大于1度 就更新ui
                if (listener != null) {
                    listener.onOrientationChanged(x);
                }
            }
            lastX = x;
        }
    }

    /**
     * 经度发生改变时调用  导航箭头功能暂时不必使用
     *
     * @param sensor
     * @param accuracy
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * 回调
     */
    public interface OnOrientationListener {
        void onOrientationChanged(float x);
    }

    public OnOrientationListener listener;

    public void setListener(OnOrientationListener listener) {
        this.listener = listener;
    }
}
