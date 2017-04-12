package com.yunyouzhiyuan.qianbaoshangchengclient.util;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王俊强 on 2016/10/26.
 */
public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (activity != null && !activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    public static Activity getTopActivity() {
        if (activities.isEmpty()) {
            return null;
        } else {
            return activities.get(activities.size() - 1);
        }
    }

    /**
     * 跳转小米系统的管理授权界面
     */
    public static void startMiPermissionCollector() {
        if (isMIUI()) {
            PackageManager pm = getTopActivity().getPackageManager();
            PackageInfo info = null;
            try {
                info = pm.getPackageInfo(getTopActivity().getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            Intent i = new Intent("miui.intent.action.APP_PERM_EDITOR");
            i.setClassName("com.android.settings", "com.miui.securitycenter.permission.AppPermissionsEditor");
            i.putExtra("extra_package_uid", info.applicationInfo.uid);
            try {
                getTopActivity().startActivity(i);
            } catch (Exception e) {
                Toast.makeText(getTopActivity(), "只有MIUI才可以设置哦", Toast.LENGTH_SHORT).show();
            }
        }else{
            Intent intent = new Intent(Settings.ACTION_SETTINGS);//系统设置界面
            getTopActivity().startActivity(intent);
        }
    }

    public static boolean isMIUI() {
        boolean result = false;
        String manufacturer = Build.MANUFACTURER;
        if (manufacturer.equals("Xiaomi")) {
            result = true;
        }
        return result;
    }


}
