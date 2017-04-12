package com.yunyouzhiyuan.qianbaoshangchengclient.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

public class SystemUtil {

    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     *
     * b百度包名     com.baidu.BaiduMap
     * 高德地图报包名    com.autonavi.minimap
     * @param packageName：应用包名
     * @return
     */
    public static boolean isInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String pkName = packageInfos.get(i).packageName;
                if (pkName.equals(packageName)) return true;
            }
        }
        return false;
    }

}