package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.location.BDLocation;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.fragment.observer.ConcreteSubject;
import com.yunyouzhiyuan.qianbaoshangchengclient.fragment.observer.Observer;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.ActivityCollector;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.LogUtils;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by wangjunqiang on 2017/1/5.
 */
public abstract class BaseActivity extends AppCompatActivity implements Observer {
    private static final int permissionsCode = 123;
    public List<Call> calls = new ArrayList<>();
    protected ConcreteSubject subject;
    protected View viewNetwork;

    public void setCall(Call c) {
        calls.add(c);
    }

    private static PermissionListener listener;
    private boolean isFist = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        subJect();
    }

    public void addNetWorkView() {
        viewNetwork = null;
        ViewGroup viewById = (ViewGroup) this.getWindow().getDecorView().findViewById(android.R.id.content);
        ViewGroup childAt = (ViewGroup) viewById.getChildAt(0);
        viewNetwork = getLayoutInflater().inflate(R.layout.network, childAt, false);
        childAt.addView(viewNetwork);
    }

    public void removeNetWorkView() {
        ViewGroup viewById = (ViewGroup) this.getWindow().getDecorView().findViewById(android.R.id.content);
        ViewGroup childAt = (ViewGroup) viewById.getChildAt(0);
        childAt.removeView(viewNetwork);
    }

    private void subJect() {
        if (isFist) {
            isFist = false;
            subject = ConcreteSubject.getInstance();
            subject.attach(this);
        }
    }

    /**
     * 请求运行时权限
     */
    public static void requestRunTimePermissions(String[] permissions, PermissionListener mlistener) {
        Activity topActivity = ActivityCollector.getTopActivity();
        if (topActivity == null) {
            To.oo("上下文 请求失败");
            return;
        }
        LogUtils.i("topActivity!= null");
        listener = mlistener;
        List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(topActivity, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }
        if (permissionList.isEmpty()) {
            listener.onSuccess();
        } else {
            ActivityCompat.requestPermissions(topActivity,
                    permissionList.toArray(new String[permissionList.size()]), permissionsCode);
        }
    }

    private List<String> permissionList = new ArrayList<>();

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == permissionsCode && grantResults.length > 0) {
            permissionList.clear();
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    permissionList.add(permissions[i]);
                }
            }
            if (permissionList.isEmpty()) {
                listener.onSuccess();
            } else {
//                listener.onError(permissionList);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if(!permissionList.isEmpty()){
//            requestRunTimePermissions(permissionList.toArray(new String[permissionList.size()]),listener);
//        }
    }

    @Override
    protected void onDestroy() {
        clearCall();
        if (subject != null) {
            subject.detach(this);
            subject = null;
        }
        ActivityCollector.removeActivity(this);
        System.gc();
        super.onDestroy();
    }

    /**
     * 清除请求
     */
    protected void clearCall() {
        for (Call c : calls) {
            if (c != null && !c.isCanceled()) {
                c.cancel();
            }
        }
        calls.clear();
    }

    @Override
    public void upLocation(BDLocation location) {

    }

    @Override
    public void upCity() {

    }

    /**
     * 运行时权限 请求回调
     */
    public interface PermissionListener {
        void onSuccess();

        void onError(List<String> permissions);
    }

    /**
     * 测量状态栏高度
     *
     * @return
     */
    protected int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public void upImage() {

    }

    @Override
    public void nodifyPositioning() {

    }

    @Override
    public void nodifyDingdan() {

    }
}
