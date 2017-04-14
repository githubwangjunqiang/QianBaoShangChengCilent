package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.model.LatLng;
import com.baidu.navisdk.adapter.BNOuterLogUtil;
import com.baidu.navisdk.adapter.BNOuterTTSPlayerCallback;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNaviSettingManager;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.baiduMap.BaiduMapBean;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.TitleLayout;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.LogUtils;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.ToMapApp;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.baidu.navisdk.adapter.PackageUtil.getSdcardDir;


public class BaiduMapActivity extends BaseActivity {
    @Bind(R.id.map_title)
    TitleLayout mapTitle;
    @Bind(R.id.bmapView)
    MapView mMapView;
    private BaiduMap baiduMap;
    private BitmapDescriptor bitmapDescriptor;
    private Overlay overlay, overlayStor;
    private LatLng ll;//当前位置
    private LatLng latLngnew;//新的位置
    private LatLng latStor;//新的位置
    public static List<Activity> activityList = new LinkedList<Activity>();
    public static final String ROUTE_PLAN_NODE = "routePlanNode";
    private String mSDCardPath = null;
    private static final String APP_FOLDER_NAME = "黔宝商城客户端";
    //    private MyOrientationListener myOrientationListener;
    private float mCurrentX;

    public static void startBaiduMapActivity(Context context, String lat, String lng) {
        Intent intent = new Intent(context, BaiduMapActivity.class);
        intent.putExtra("lat", lat);
        intent.putExtra("lng", lng);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidu_map);
        ButterKnife.bind(this);
        if (BaiduMapBean.getLocation() == null) {
            To.ee("亲 您的位置未能定位，您是否关闭了本应用位置权限");
            finish();
            return;
        }
        ll = new LatLng(BaiduMapBean.getLocation().getLatitude(),
                BaiduMapBean.getLocation().getLongitude());
        latLngnew = new LatLng(Double.parseDouble(getIntent().getStringExtra("lat")),
                Double.parseDouble(getIntent().getStringExtra("lng")));
        if (latLngnew == null) {
            To.ee("店家位置获取异常");
            finish();
            return;
        }
        latStor = latLngnew;
        init();
        setlistener();
        getlocation();
        toMapAnition(latLngnew);
        toBitmapStor(latStor);
    }

    /**
     * 初始化组件信息
     */
    private void init() {
//        myOrientationListener = new MyOrientationListener(this);

        baiduMap = mMapView.getMap();
//        // 隐藏logo
//        View child = mMapView.getChildAt(1);
//        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)){
//            child.setVisibility(View.INVISIBLE);
//        }
        baiduMap.setMyLocationEnabled(true);
        //自定义图标
        bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.wodeweizhinew);

        mapTitle.setTitle("店家地址", true);
        mapTitle.showBianji("其他地图");
        mapTitle.setCallback(new TitleLayout.Callback(this) {
            @Override
            public void bianjiClick() {
                toOtherMap();
            }
        });

        activityList.add(this);
// 打开log开关
        BNOuterLogUtil.setLogSwitcher(true);
        if (initDirs()) {
            initNavi();
        }
    }

    /**
     * 其他地图
     */
    private void toOtherMap() {
        ToMapApp.toPushApp(this, BaiduMapBean.getLocation().getLatitude() + "",
                BaiduMapBean.getLocation().getLongitude() + "",
                "我的位置", latLngnew.latitude + "", latLngnew.longitude + "", "目的地", 1);
    }

    /**
     * 获取定位信息
     */
    private void getlocation() {
//        myOrientationListener.start();
//        myOrientationListener.setListener(new MyOrientationListener.OnOrientationListener() {
//            @Override
//            public void onOrientationChanged(float x) {
//                mCurrentX = x;
//            }
//        });
        MyLocationData data = new MyLocationData.Builder()
//                .direction(mCurrentX)
                .accuracy(BaiduMapBean.getLocation().getRadius())
                .latitude(BaiduMapBean.getLocation().getLatitude())
                .longitude(BaiduMapBean.getLocation().getLongitude())
                .build();
        baiduMap.setMyLocationData(data);
        //自定义图标
        MyLocationConfiguration myLocationConfiguration =
                new MyLocationConfiguration
                        (MyLocationConfiguration.LocationMode.NORMAL, true, bitmapDescriptor);
        baiduMap.setMyLocationConfigeration(myLocationConfiguration);
        if (ll != null) {
            toMapAnition(ll);
        }
    }

    /**
     * 地图移动中心点
     */
    private void toMapAnition(LatLng ll) {
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(14.0f);
        baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }


    /**
     * 监听器
     */
    private void setlistener() {
        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                latLngnew = latLng;
                toBitmap(latLng);
                toMapAnition(latLng);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                latLngnew = new LatLng(mapPoi.getPosition().latitude,
                        mapPoi.getPosition().longitude);
                toBitmap(latLngnew);
                toMapAnition(latLngnew);
                return false;
            }
        });
    }

    //构建Marker图标
    private BitmapDescriptor bitmap = BitmapDescriptorFactory
            .fromResource(R.drawable.dingweiweizhi);
    //构建Marker图标
    private BitmapDescriptor bitmapStor = BitmapDescriptorFactory
            .fromResource(R.drawable.mudidi);

    /**
     * 生成marker
     * 用户点击
     *
     * @param latLng
     */
    private void toBitmap(LatLng latLng) {
        if (overlay != null) {
            overlay.remove();
        }
        MarkerOptions ooD = new MarkerOptions().position(latLng).icon(bitmap)
                .zIndex(0).period(10);
        // 生长动画
        ooD.animateType(MarkerOptions.MarkerAnimateType.grow);
        overlay = baiduMap.addOverlay(ooD);
    }

    /**
     * 生成marker
     * 店家位置
     *
     * @param latLng
     */
    private void toBitmapStor(LatLng latLng) {
        if (overlayStor != null) {
            overlayStor.remove();
        }
        MarkerOptions ooD = new MarkerOptions().position(latLng).icon(bitmapStor)
                .zIndex(0).period(10);
        // 生长动画
        ooD.animateType(MarkerOptions.MarkerAnimateType.grow);
        overlayStor = baiduMap.addOverlay(ooD);
    }


    @Override
    protected void onDestroy() {
//        myOrientationListener.stop();
        baiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        if (ttsHandler != null) {
            ttsHandler.removeMessages(BaiduNaviManager.TTSPlayMsgType.PLAY_START_MSG);
            ttsHandler.removeMessages(BaiduNaviManager.TTSPlayMsgType.PLAY_END_MSG);
            ttsHandler = null;
        }
        bitmapDescriptor.recycle();
        bitmap.recycle();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }


    /**
     * 点击我的位置
     *
     * @param v
     */
    public void wodeding(View v) {
        toMapAnition(ll);
    }

    /**
     * 点击导航
     *
     * @param v
     */
    public void daohang(View v) {
        if (BaiduNaviManager.isNaviInited()) {
            toShowMenuDapohang(v);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getGroupId() == 0) {
            if (item.getItemId() == 1) {//店铺位置
                routeplanToNavi(true, latStor);
            } else if (item.getItemId() == 2) {//选中位置
                routeplanToNavi(true, latLngnew);
            }
        }
        return super.onContextItemSelected(item);
    }

    /**
     * 点击模拟导航
     *
     * @param v
     */
    public void moni(View v) {
        if (BaiduNaviManager.isNaviInited()) {
            toShowMenuDapohang(v);
        }
    }

    /**
     * 显示导航终点菜单
     * @param v
     */
    private void toShowMenuDapohang(View v) {
        v.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(0, 1, 0, R.string.dianpuweizhi);
                if (latLngnew != latStor) {
                    menu.add(0, 2, 0, R.string.xuanzhongweizhi);
                }
            }
        });
        v.showContextMenu();
    }

    /**
     * 规划路线 -》导航开始
     *
     * @param isMoni 模拟导航 true =真实导航  false=模拟
     */
    private void routeplanToNavi(boolean isMoni, LatLng latLng) {
        BNRoutePlanNode.CoordinateType type = BNRoutePlanNode.CoordinateType.GCJ02;

        BNRoutePlanNode sNode = null;//起点
        BNRoutePlanNode eNode = null;//终点
        LocationClient mLocationClient = ((App) getApplication())
                .mLocationClient;//声明LocationClient类
        BDLocation slocation = mLocationClient.getBDLocationInCoorType
                (BaiduMapBean.getLocation(), BDLocation.BDLOCATION_BD09LL_TO_GCJ02);
        BDLocation loc = new BDLocation();
        loc.setLatitude(latLng.latitude);
        loc.setLongitude(latLng.longitude);
        BDLocation elocation = mLocationClient.getBDLocationInCoorType
                (loc, BDLocation.BDLOCATION_BD09LL_TO_GCJ02);
        sNode = new BNRoutePlanNode(slocation.getLongitude(),
                slocation.getLatitude(), "我的位置", null, type);
        eNode = new BNRoutePlanNode(elocation.getLongitude(),
                elocation.getLatitude(), "目的地", null, type);

        if (sNode != null && eNode != null) {
            List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
            list.add(sNode);
            list.add(eNode);

            BaiduNaviManager.getInstance().launchNavigator(this,
                    list, 1, isMoni, new DemoRoutePlanListener(sNode));
        }
    }

    /**
     * 店家商家
     *
     * @param view
     */
    public void shangjia(View view) {
        toMapAnition(latStor);
    }


    /**
     * 规划路线成功失败回掉
     */
    public class DemoRoutePlanListener implements BaiduNaviManager.RoutePlanListener {

        private BNRoutePlanNode mBNRoutePlanNode = null;

        public DemoRoutePlanListener(BNRoutePlanNode node) {
            mBNRoutePlanNode = node;
        }

        @Override
        public void onJumpToNavigator() {
            /**
             * 设置途径点以及resetEndNode会回调该接口
             */
            for (Activity ac : activityList) {

                if (ac.getClass().getName().endsWith("BNDemoGuideActivity")) {

                    return;
                }
            }
            Intent intent = new Intent(BaiduMapActivity.this, BNDemoGuideActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(ROUTE_PLAN_NODE, (BNRoutePlanNode) mBNRoutePlanNode);
            intent.putExtras(bundle);
            startActivity(intent);

        }

        @Override
        public void onRoutePlanFailed() {
            To.ee("算路失败，导航停止");
        }
    }


    /**
     * 语音回掉
     */
    private BNOuterTTSPlayerCallback mTTSCallback = new BNOuterTTSPlayerCallback() {

        @Override
        public void stopTTS() {
            // TODO Auto-generated method stub
            Log.e("test_TTS", "stopTTS");
        }

        @Override
        public void resumeTTS() {
            // TODO Auto-generated method stub
            LogUtils.e("test_TTS" + "resumeTTS");
        }

        @Override
        public void releaseTTSPlayer() {
            // TODO Auto-generated method stub
            LogUtils.e("test_TTS" + "releaseTTSPlayer");
        }

        @Override
        public int playTTSText(String speech, int bPreempt) {
            // TODO Auto-generated method stub
            LogUtils.e("test_TTS" + "playTTSText" + "_" + speech + "_" + bPreempt);

            return 1;
        }

        @Override
        public void phoneHangUp() {
            // TODO Auto-generated method stub
            LogUtils.e("test_TTS" + "phoneHangUp");
        }

        @Override
        public void phoneCalling() {
            // TODO Auto-generated method stub
            LogUtils.e("test_TTS" + "phoneCalling");
        }

        @Override
        public void pauseTTS() {
            // TODO Auto-generated method stub
            LogUtils.e("test_TTS" + "pauseTTS");
        }

        @Override
        public void initTTSPlayer() {
            // TODO Auto-generated method stub
            LogUtils.e("test_TTS" + "initTTSPlayer");
        }

        @Override
        public int getTTSState() {
            // TODO Auto-generated method stub
            LogUtils.e("test_TTS" + "getTTSState");
            return 1;
        }
    };
    String authinfo = null;

    /**
     * 效验key 成功  然后初始化百度导航引擎
     */
    private void initNavi() {

        BNOuterTTSPlayerCallback ttsCallback = null;

        BaiduNaviManager.getInstance().init(this, mSDCardPath, APP_FOLDER_NAME, new BaiduNaviManager.NaviInitListener() {
            @Override
            public void onAuthResult(int status, String msg) {
                if (0 == status) {
                    authinfo = "key校验成功!";
                } else {
                    authinfo = "key校验失败, " + msg;
                }
                BaiduMapActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(BaiduMapActivity.this, authinfo, Toast.LENGTH_LONG).show();
                    }
                });
            }

            public void initSuccess() {
//                Toast.makeText(BaiduM?apActivity.this, "百度导航引擎初始化成功", Toast.LENGTH_SHORT).show();
                initSetting();
            }

            public void initStart() {
//                Toast.makeText(BaiduMapActivity.this, "百度导航引擎初始化开始", Toast.LENGTH_SHORT).show();
            }

            public void initFailed() {
                Toast.makeText(BaiduMapActivity.this, "百度导航引擎初始化失败", Toast.LENGTH_SHORT).show();
            }


        }, null, ttsHandler, ttsPlayStateListener);

    }

    /**
     * 导航界面的标配 可以修改 最好用默认值
     */
    private void initSetting() {
        // 设置是否双屏显示
        BNaviSettingManager.setShowTotalRoadConditionBar(BNaviSettingManager.PreViewRoadCondition.ROAD_CONDITION_BAR_SHOW_ON);
        // 设置导航播报模式
        BNaviSettingManager.setVoiceMode(BNaviSettingManager.VoiceMode.Veteran);
        // 是否开启路况
        BNaviSettingManager.setRealRoadCondition(BNaviSettingManager.RealRoadCondition.NAVI_ITS_ON);
    }

    /**
     * 内部TTS播报状态回传handler
     */
    private Handler ttsHandler = new Handler() {
        public void handleMessage(Message msg) {
            int type = msg.what;
            switch (type) {
                case BaiduNaviManager.TTSPlayMsgType.PLAY_START_MSG: {
                    LogUtils.d("Handler : TTS play start");
                    break;
                }
                case BaiduNaviManager.TTSPlayMsgType.PLAY_END_MSG: {
                    LogUtils.d("Handler : TTS play end");
                    break;
                }
                default:
                    break;
            }
        }
    };

    /**
     * 百度导航dome中的土司 暂时不启用
     *
     * @param msg
     */
    public void showToastMsg(final String msg) {
        BaiduMapActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(BaiduMapActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 内部TTS播报状态回调接口
     */
    private BaiduNaviManager.TTSPlayStateListener ttsPlayStateListener = new BaiduNaviManager.TTSPlayStateListener() {

        @Override
        public void playEnd() {
            LogUtils.d("TTSPlayStateListener : TTS play end");
        }

        @Override
        public void playStart() {
            LogUtils.d("TTSPlayStateListener : TTS play start");
        }
    };

    /**
     * 在sd卡根目录创建
     *
     * @return
     */
    private boolean initDirs() {
        mSDCardPath = getSdcardDir();
        if (mSDCardPath == null) {
            return false;
        }
        File f = new File(mSDCardPath, APP_FOLDER_NAME);
        if (!f.exists()) {
            try {
                f.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}

