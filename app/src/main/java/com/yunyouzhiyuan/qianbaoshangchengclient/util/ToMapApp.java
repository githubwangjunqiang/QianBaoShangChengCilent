package com.yunyouzhiyuan.qianbaoshangchengclient.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;

import java.net.URISyntaxException;


/**
 * Created by wangjunqiang on 2016/12/1.
 */
public class ToMapApp {
    /**
     * 调用系统的地图app 导航  示例 百度 腾讯 高德
     *
     * @param MyLat       我的经度
     * @param MyLongat    我的维度
     * @param MyAdressStr 我的位置名称
     * @param toLat       目的地 精度
     * @param toLongat    目的地 维度
     * @param toAdressStr 目的地名称
     */
    public static void toPushApp(Context context, String MyLat, String MyLongat,
                                 String MyAdressStr, String toLat, String toLongat,
                                 String toAdressStr,int a) {
        Intent intent = new Intent();
        if (SystemUtil.isInstalled(context, "com.baidu.BaiduMap")) {//百度地图

            try {
                intent = Intent.parseUri("intent://map/direction?" +
                        "origin=latlng:" + MyLat + "," + MyLongat +
                        "|name:" + MyAdressStr +
                        "&destination=latlng:" + toLat + "," + toLongat +
                        "|name:" + toAdressStr +
                        "&mode=driving" +
                        "&src=Name|AppName" +
                        "#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end", 0);
            } catch (URISyntaxException e) {
                Log.e("12345", "URISyntaxException : " + e.getMessage());
                e.printStackTrace();
                To.oo(e.getMessage());
                return;
            }

            context.startActivity(intent);
        } else if (SystemUtil.isInstalled(context, "com.autonavi.minimap")) {//高德地图
            intent.setData(Uri
                    .parse("androidamap://route?" +
                            "sourceApplication=softname" +
                            "&slat=" + MyLat +
                            "&slon=" + MyLongat +
                            "&dlat=" + toLat +
                            "&dlon=" + toLongat +
                            "&dname=" + toAdressStr +
                            "&dev=0" +
                            "&m=0" +
                            "&t=2"));
            context.startActivity(intent);
        } else {
            String downloadUri =  //下载链接 如果 三个地图都没有装 提示用户安装
                    "http://softroute.map.qq.com/downloadfile?cid=00001";

            String baseUrl = "qqmap://map/";//腾讯的包名
            String searchPlace = "search?keyword=酒店&" + //搜索导航  检索关键字 进行导航
                    "bound=39.907293,116.368935,39.914996,116.379321";
            String searchAround = "search?keyword=肯德基&" + //搜索导航检索关键字进行导（1000米范围）
                    "center=39.908491,116.374328&radius=1000";
            //这里 from 和 to 不能不写或者空白符
            //有地址导航  （busPlan公交 drivePlan驾车）
            String busPlan = "routeplan?type=bus&" +
                    "from=我的位置&fromcoord=" + MyLat + "," + MyLongat + "&to="
                    + toAdressStr + "&" +
                    "tocoord=" + toLat + "," + toLongat + "&policy=2";
            String drivePlan = "routeplan?type=drive&" +
                    "from=我的位置&fromcoord=" + MyLat + "," + MyLongat +
                    "&to=" + toAdressStr + "&" +
                    "tocoord=" + toLat + "," + toLongat + "&policy=1";
            String tencnetUri = (a == 1?drivePlan: busPlan);//默认 开启腾讯地图公交

            tencnetUri = baseUrl + tencnetUri + "&referer="
                    + context.getResources().getString(R.string.app_name);
            try {
                intent = Intent.parseUri(tencnetUri, 0);
            } catch (URISyntaxException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                To.oo(e.getMessage());
                return;
            }
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                ComponentName name = intent.resolveActivity(context.getPackageManager());
                context.startActivity(intent);
            } else {
                try {
                    intent = Intent.parseUri(downloadUri, 0);//没有 安装 即提示用户下载
                    context.startActivity(intent);
                } catch (URISyntaxException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    To.oo(e.getMessage());
                }
            }
        }

    }
}

