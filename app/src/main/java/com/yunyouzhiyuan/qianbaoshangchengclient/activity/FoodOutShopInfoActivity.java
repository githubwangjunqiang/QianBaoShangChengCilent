package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.FoodInfo;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.TitleLayout;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.scrllview.PullToZoomScrollViewEx;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.Text_Size;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FoodOutShopInfoActivity extends BaseActivity {

    @Bind(R.id.shopinfofood_title)
    TitleLayout title;
    @Bind(R.id.shopinfofood_scview)
    PullToZoomScrollViewEx scrollViewEx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_out_shop_info);
        ButterKnife.bind(this);
        FoodInfo.DataBean.InfoBean data = (FoodInfo.DataBean.InfoBean) getIntent().getSerializableExtra("data");
        if (data == null) {
            To.oo("参数有误");
            finish();
            return;
        }
        init(data);
    }

    /**
     * 初始化
     *
     * @param data
     */
    private void init(FoodInfo.DataBean.InfoBean data) {
        ImageView zoomView = (ImageView) LayoutInflater.from(this).inflate(R.layout.foodout_shopinfo_zoomview, null, false);
        scrollViewEx.setZoomView(zoomView);
        View llcontent = LayoutInflater.from(this).inflate(R.layout.fooda_shopinfo_content_view, null);
        scrollViewEx.setScrollContentView(llcontent);
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int mScreenHeight = localDisplayMetrics.heightPixels;
        int mScreenWidth = localDisplayMetrics.widthPixels;
        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth, (int) (9.0F * (mScreenWidth / 10.0F)));
        scrollViewEx.setHeaderLayoutParams(localObject);


        TextView tvname = (TextView) llcontent.findViewById(R.id.shopinfofood_tvname);
        zoomView.setBackground(null);
        ToGlide.url(this, HTTPURL.IMAGE + data.getOriginal_img(), zoomView);
        String name = data.getGoods_name() + "\n";
        String shou = "本店价格：" + data.getShop_price() + "元";
        String price = "\t\t市场价格：" + data.getMarket_price() + "元";
        String str = name + shou + price;
        Text_Size.setSizeThress(null, tvname, str, 0, name.length(), "#646464", 14, name.length(), name.length() + shou.length(), "#" +
                "05a841", 13, name.length() + shou.length(), str.length(), "#aa646464", 12);
        title.setTitle("详情", true);
        title.setCallback(new TitleLayout.Callback(this));
    }
}
