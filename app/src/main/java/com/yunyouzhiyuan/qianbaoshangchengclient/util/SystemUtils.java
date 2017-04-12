package com.yunyouzhiyuan.qianbaoshangchengclient.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;

import java.util.Iterator;
import java.util.List;

/**
 * Created by wangjunqiang on 2016/11/19.
 */
public class SystemUtils {
    public SystemUtils() {
    }

    /**
     * 应用进程是否存活
     *
     * @param context
     * @param appname
     * @return
     */
    public boolean isAppRunning(Context context, String appname) {
        if (TextUtils.isEmpty(appname)) {
            return false;
        } else {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List infos = am.getRunningAppProcesses();
            if (infos == null) {
                return false;
            } else {
                Iterator i$ = infos.iterator();

                ActivityManager.RunningAppProcessInfo info;
                do {

                    if (!i$.hasNext()) {
                        return false;
                    }

                    info = (ActivityManager.RunningAppProcessInfo) i$.next();
                    LogUtils.e("isAppRunning"+info.processName);
                } while (!info.processName.equals(appname));

                return true;
            }
        }
    }

    /**
     * 获取当前进程的名字
     *
     * @param context
     * @return
     */
    public String getCurrentProcessName(Context context) {
        int pid = Process.myPid();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List infos = am.getRunningAppProcesses();
        if (infos == null) {
            return null;
        } else {
            Iterator i$ = infos.iterator();

            ActivityManager.RunningAppProcessInfo info;
            do {
                if (!i$.hasNext()) {
                    return null;
                }

                info = (ActivityManager.RunningAppProcessInfo) i$.next();
            } while (info.pid != pid);

            return info.processName;
        }
    }

    /**
     * 应用程序 是否在前台
     *
     * @param context
     * @param packageName
     * @return
     */
    public boolean isAppRunningOnTop(Context context, String packageName) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List taskInfo;
        if (Build.VERSION.SDK_INT > 20) {
            taskInfo = am.getRunningAppProcesses();
            Iterator componentInfo = taskInfo.iterator();

            while (true) {
                ActivityManager.RunningAppProcessInfo processInfo;
                do {
                    if (!componentInfo.hasNext()) {
                        return isInBackground;
                    }

                    processInfo = (ActivityManager.RunningAppProcessInfo) componentInfo.next();
                } while (processInfo.importance != 100);

                String[] arr$ = processInfo.pkgList;
                int len$ = arr$.length;

                for (int i$ = 0; i$ < len$; ++i$) {
                    String activeProcess = arr$[i$];
                    if (activeProcess.equals(context.getPackageName())) {
                        isInBackground = false;
                    }
                }
            }
        } else {
            taskInfo = am.getRunningTasks(1);
            ComponentName var10 = ((ActivityManager.RunningTaskInfo) taskInfo.get(0)).topActivity;
            if (var10.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }
}
