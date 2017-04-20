package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.AddressAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Address;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.AddressModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.TitleLayout;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.LoadingDialog;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

import static com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean.ADD_ADDRESS;

public class AddressActivity extends BaseActivity {

    @Bind(R.id.address_title)
    TitleLayout addressTitle;
    @Bind(R.id.address_listview)
    ListView addressListview;
    @Bind(R.id.address_layout)
    SwipeRefreshLayout addressLayout;
    private AddressAdapter adapter;
    private AddressModel model;
    private List<Address.DataBean> list = new ArrayList<>();
    private LoadingDialog loadingDialog;
    private boolean isDingdan;

    @Override
    protected void onDestroy() {
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                if ("1".equals(list.get(i).getIs_default())) {
                    Bean.saveAdress(list.get(i),App.getUserId());
                }
            }
        }
        list.clear();
        adapter = null;
        addressTitle = null;
        loadingDialog = null;
        model = null;
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);
        isDingdan = getIntent().getBooleanExtra("isdingdan", false);
        init();
        getData();
        setListener();
    }

    /**
     * 获取信息
     */
    private void getData() {
        addressLayout.setRefreshing(true);
        Call date = model.getDate(App.getUserId(), new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {

                list.clear();
                list.addAll((List<Address.DataBean>) obj);
                setAdapter();
                addressLayout.setRefreshing(false);
            }

            @Override
            public void onError(Object obj) {
                if(AddressActivity.this != null){
                    To.oo(obj);
                    setAdapter();
                    addressLayout.setRefreshing(false);
                }
            }
        });
        setCall(date);
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            adapter = new AddressAdapter(this, list, model, loadingDialog);
            addressListview.setAdapter(adapter);
        }
    }

    /**
     * 监听器
     */
    private void setListener() {
        addressLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        addressListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!adapter.isShow()) {
                    if (isDingdan) {
                        Intent intent = getIntent();
                        intent.putExtra("address", list.get(position));
                        setResult(Bean.DING_ADDRESS, intent);
                        finish();
                    } else {
                        Intent intent = new Intent(AddressActivity.this, UnAddressActivity.class);
                        intent.putExtra("data", list.get(position));
                        startActivityForResult(intent, ADD_ADDRESS);
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_ADDRESS && resultCode == ADD_ADDRESS) {
            getData();
        }
    }

    /**
     * 初始化
     */
    private void init() {
        addressLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.white));
        addressLayout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this, R.color.app_color));
        addressTitle.setTitle(getString(R.string.shouhuodizhi), true);
        addressTitle.showBianji("编辑");
        addressTitle.setCallback(new TitleLayout.Callback(this) {
            @Override
            public void bianjiClick() {
                if (adapter != null) {
                    if (adapter.isShow()) {
                        adapter.setShow(false);
                        addressTitle.showBianji("编辑");
                    } else {
                        adapter.setShow(true);
                        addressTitle.showBianji("完成");
                    }
                }
            }
        });

        model = new AddressModel();
        loadingDialog = new LoadingDialog(this);
    }

    /**
     * 添加地址
     *
     * @param view
     */
    public void addAddress(View view) {
        startActivityForResult(new Intent(this, UnAddressActivity.class), ADD_ADDRESS);
    }
}
