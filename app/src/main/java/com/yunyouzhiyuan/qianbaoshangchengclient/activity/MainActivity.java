package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.baiduMap.BaiduMapBean;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.City_id;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.User;
import com.yunyouzhiyuan.qianbaoshangchengclient.fragment.HomeFragment;
import com.yunyouzhiyuan.qianbaoshangchengclient.fragment.MyFragment;
import com.yunyouzhiyuan.qianbaoshangchengclient.fragment.NearbyFragment;
import com.yunyouzhiyuan.qianbaoshangchengclient.fragment.SpecialtyFragment;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.HomeModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.LoginModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.network.NetworkConnectChangedReceiver;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.LogUtils;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.SpService;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

import static com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean.CITYIDID;

public class MainActivity extends BaseActivity {

    @Bind(R.id.main_top_tvads)
    TextView mainTopTvads;
    @Bind(R.id.main_top_btnsou)
    Button mainTopBtnsou;
    @Bind(R.id.main_vp)
    ViewPager mainVp;
    @Bind(R.id.main_imagehome)
    ImageView mainImagehome;
    @Bind(R.id.main_tvhome)
    TextView mainTvhome;
    @Bind(R.id.main_buttom_lhome)
    LinearLayout mainButtomLhome;
    @Bind(R.id.main_image_techan)
    ImageView mainImageTechan;
    @Bind(R.id.main_ivimage_beijing)
    ImageView ivBeiJing;
    @Bind(R.id.main_tvtecahn)
    TextView mainTvtecahn;
    @Bind(R.id.main_buttom_ltechan)
    LinearLayout mainButtomLtechan;
    @Bind(R.id.main_image_fujin)
    ImageView mainImageFujin;
    @Bind(R.id.main_tvfujin)
    TextView mainTvfujin;
    @Bind(R.id.main_buttom_lfujin)
    LinearLayout mainButtomLfujin;
    @Bind(R.id.main_image_my)
    ImageView mainImageMy;
    @Bind(R.id.main_ivimage_tvhuanying)
    TextView tvHuanying;
    @Bind(R.id.main_tvmy)
    TextView mainTvmy;
    @Bind(R.id.main_buttom_lmy)
    LinearLayout mainButtomLmy;
    @Bind(R.id.main_lltop)
    LinearLayout lltop;
    @Bind(R.id.main_tvtop)
    TextView tvtop;
    private List<Fragment> fragments = new ArrayList<>();
    private List<ImageView> imageViews = new ArrayList<>();
    private List<TextView> textViews = new ArrayList<>();
    private int index = 0;
    /**
     * 声明百度地图LocationClient
     */
    public LocationClient mLocationClient = null;
    /**
     * 定位回调的监听器 自己实现
     */
    public BDLocationListener myListener = new MyLocationListener();
    private HomeModel model;
    private List<City_id.DataBean> listCity = new ArrayList<>();
    private boolean isFist = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.background)));
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//        int memoryClass = manager.getMemoryClass();
//        int largeMemoryClass = manager.getLargeMemoryClass();
//        LogUtils.i("memoryClass=" + memoryClass);
//        LogUtils.i("largeMemoryClass=" + largeMemoryClass);
        init();
        inBaiDuMap();
        setadapter();
        setListener();
        toLogin();//自动登录
        setReceiver();
        tvHuanying.setText("欢迎您\t" + inCount + "秒");
        handler.sendEmptyMessageDelayed(1024, 1500);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    private void initStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decoderView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decoderView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    /**
     * 自动登录
     */
    private void toLogin() {
        String getphone = SpService.getSP().getphone();
        String pas = SpService.getSP().getpas();
        if (TextUtils.isEmpty(pas) || TextUtils.isEmpty(getphone)) {
            return;
        }

        setCall(new LoginModel().login(getphone, pas, JPushInterface.getRegistrationID(App.getContext()), new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (isFinishing()) {
                    return;
                }
                User user = (User) obj;
                App.setUserId(user.getData().getUser_id());
            }

            @Override
            public void onError(Object obj) {
                if (isFinishing()) {
                    return;
                }

            }
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 初始化百度定位相关
     */
    private void inBaiDuMap() {
        List<String> list = new ArrayList<>();
        list.add(android.Manifest.permission.ACCESS_FINE_LOCATION);//精细位置
        list.add(android.Manifest.permission.READ_PHONE_STATE);//手机信息
        list.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);//读取扩展卡
        list.add(android.Manifest.permission.GET_ACCOUNTS);//得到用户信息
        list.add(android.Manifest.permission.READ_CONTACTS);//读取联系人
        list.add(android.Manifest.permission.CAMERA);//相机
        requestRunTimePermissions(list.toArray(new String[list.size()]), new PermissionListener() {
            @Override
            public void onSuccess() {
                LogUtils.i("成功");
                toStartLBS();
            }

            @Override
            public void onError(List<String> permissions) {
//                To.oo("您关闭了必须的权限，强烈要求您在应用设置中打开");
//                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                builder.setTitle("安全体验警告！");
//                builder.setMessage("您关闭了应用的重要权限，强烈要求您在应用设置中打开，否则应用运行会出异常");
//                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ActivityCollector.startMiPermissionCollector();
//                        dialog.dismiss();
//                    }
//                });
//                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                builder.create().show();
            }
        });
    }


    /**
     * 开始定位
     */
    private void toStartLBS() {
        mLocationClient = ((App) getApplication()).mLocationClient;     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        initLocation();
        mLocationClient.start();
    }

    /**
     * 定位参数
     */
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000 * 60 * 2;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
//        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    /**
     * 监听器
     */
    private void setListener() {
        mainVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setView(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mainTopTvads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Bean.getCity_id() != null) {
                    CityActivity.startCityActivity(MainActivity.this);
                }
            }
        });
        mainTopBtnsou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SouSuoActivity.class));
                overridePendingTransition(0, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CITYIDID://选择城市返回
                if (resultCode == CITYIDID) {
                    subject.changeCity();
                    setVeiwCity();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 设置fragment
     */
    private void setView(int position) {
        if (index != position) {
            imageViews.get(index).setSelected(false);
            imageViews.get(position).setSelected(true);
            textViews.get(index).setSelected(false);
            textViews.get(position).setSelected(true);
            index = position;
            mainVp.setCurrentItem(index);
            if (index == 3) {
                tvtop.setVisibility(View.VISIBLE);
                lltop.setVisibility(View.GONE);
            } else {
                tvtop.setVisibility(View.GONE);
                lltop.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * viewpager 设置adapter、
     */
    private void setadapter() {
        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
        mainVp.setAdapter(fragmentPagerAdapter);
        mainVp.setCurrentItem(index);
        mainVp.setOffscreenPageLimit(3);
        imageViews.get(index).setSelected(true);
        textViews.get(index).setSelected(true);
        tvtop.setVisibility(View.GONE);
    }

    private void init() {
        BaiduMapBean.setLocation(BaiduMapBean.getFileLocation());//先从序列化中读取上次的值
        fragments.add(HomeFragment.newInstance());
        fragments.add(NearbyFragment.newInstance());
        fragments.add(SpecialtyFragment.newInstance());
        fragments.add(MyFragment.newInstance());
        textViews.add(mainTvhome);
        textViews.add(mainTvtecahn);
        textViews.add(mainTvfujin);
        textViews.add(mainTvmy);
        imageViews.add(mainImagehome);
        imageViews.add(mainImageTechan);
        imageViews.add(mainImageFujin);
        imageViews.add(mainImageMy);
        model = new HomeModel();
        Bean.setCity_id(Bean.readCity());//读取集合数据
        Bean.city_id_id = SpService.getSP().getCityid();//读取城市id
        getCity();
        setVeiwCity();
    }

    /**
     * 入城市
     */
    private void setVeiwCity() {
        City_id city_id = Bean.getCity_id();
        if (city_id != null && TextUtils.isEmpty(Bean.city_id_id)) {
            Bean.city_id_id = city_id.getData().get(0).getId();
        }
        if (city_id != null && city_id.getData() != null) {
            for (City_id.DataBean da : city_id.getData()) {
                if (da.getId().equals(Bean.city_id_id)) {
                    mainTopTvads.setText(da.getName());
                    break;
                }
            }
        }
    }

    /**
     * 获取城市列表
     */
    private void getCity() {
        setCall(model.getCity(new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                listCity.clear();
                City_id obj1 = (City_id) obj;
                listCity.addAll(obj1.getData());
                Bean.setCity_id(obj1);
                setVeiwCity();
            }

            @Override
            public void onError(Object obj) {
                To.oo(obj);
            }
        }));
    }

    @OnClick({R.id.main_buttom_lhome, R.id.main_buttom_ltechan, R.id.main_buttom_lfujin, R.id.main_buttom_lmy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_buttom_lhome://首页
                setView(0);
                break;
            case R.id.main_buttom_ltechan://特产
                setView(1);
                break;
            case R.id.main_buttom_lfujin://附近
                setView(2);
                break;
            case R.id.main_buttom_lmy://我的
                setView(3);
                break;
        }
    }


    /**
     * 定位监听器
     */
    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location.getLocType() == BDLocation.TypeServerError) {
                To.oo("服务端网络定位失败");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                To.oo("网络不流畅导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                To.oo("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            } else {
                BaiduMapBean.setLocation(location);
                subject.change(location);
                LogUtils.w("定位=" + location.getLatitude());
                LogUtils.w("定位=" + location.getLongitude());
                LogUtils.w("定位=" + location.getLocationDescribe());
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {
            LogUtils.d("地图定位2=" + s + "/i=" + i);
        }
    }


    @Override
    protected void onDestroy() {
        handler.removeMessages(1024);
        handler = null;
        BaiduMapBean.saveLoc();
        fragments.clear();
        imageViews.clear();
        textViews.clear();
        listCity.clear();
        unregisterReceiver(receiver);
        receiver = null;
        listCity = null;
        if (subject != null) {
            subject.clearObserver();
        }
        subject = null;

        if (mLocationClient != null) {
            mLocationClient.stop();
            mLocationClient.unRegisterLocationListener(myListener);
        }
        myListener = null;

        android.os.Process.killProcess(Process.myPid());
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    /**
     * 监听网络变化
     */
    private void setReceiver() {
        receiver = new NetworkConnectChangedReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        filter.addAction("android.net.wifi.STATE_CHANGE");
        registerReceiver(receiver, filter);
    }

    private int inCount = 2;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1024) {
                inCount--;
                tvHuanying.setText("欢迎您\t" + inCount + "秒");
                if (inCount < 1) {
                    ivBeiJing.post(new Runnable() {
                        @Override
                        public void run() {
                            startAnimat(ivBeiJing);
                        }
                    });
                    return;
                }
                handler.sendEmptyMessageDelayed(1024, 1500);
            }
        }
    };

    private NetworkConnectChangedReceiver receiver;

    /**
     * 去掉图片
     *
     * @param ImgMark
     */
    private void startAnimat(final ImageView ImgMark) {

        int imgHeight = ImgMark.getHeight() / 5;
        int height = getWindowManager().getDefaultDisplay().getHeight();
        int curY = height / 2 + imgHeight / 2;
        int dy = (height - imgHeight) / 2;
        final AnimatorSet set = new AnimatorSet();
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
                ImgMark.setVisibility(View.GONE);
                tvHuanying.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    public void nodifyPositioning() {
        if (mLocationClient != null) {
            mLocationClient.requestLocation();
        }
    }
}
