package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.CityGvAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.TitleLayout;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.SpService;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean.CITYIDID;

public class CityActivity extends BaseActivity {

    @Bind(R.id.city_activity_title)
    TitleLayout mTitle;
    @Bind(R.id.city_activity_gv)
    GridView mGridView;
    private CityGvAdapter adapter;

    public static void startCityActivity(Activity context) {
        Intent intent = new Intent(context, CityActivity.class);
        context.startActivityForResult(intent, CITYIDID);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        ButterKnife.bind(this);
        init();
        setAdapter();
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            adapter = new CityGvAdapter(this, Bean.getCity_id().getData());
            mGridView.setAdapter(adapter);
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Bean.city_id_id = Bean.getCity_id().getData().get(position).getId();
                    SpService.getSP().saveUid( Bean.city_id_id);
                    setResult(CITYIDID);
                    finish();
                }
            });
        }
    }

    /**
     * 初始化
     */
    private void init() {
        mTitle.setTitle(getResources().getString(R.string.xuanzechengshi), true);
        mTitle.setCallback(new TitleLayout.Callback(this));
    }
}
