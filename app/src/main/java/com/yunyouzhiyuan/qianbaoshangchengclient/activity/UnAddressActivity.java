package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Address;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.AddressModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.TitleLayout;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.Dialog_date_se_3;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.LoadingDialog;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class UnAddressActivity extends BaseActivity {

    @Bind(R.id.add_adr_title)
    TitleLayout addAdrTitle;
    @Bind(R.id.add_adr_etname)
    EditText addAdrEtname;
    @Bind(R.id.add_adr_etphoe)
    EditText addAdrEtphoe;
    @Bind(R.id.add_adr_tvcity)
    TextView addAdrTvcity;
    @Bind(R.id.add_adr_etaddress)
    EditText addAdrEtaddress;
    @Bind(R.id.add_adr_cmoren)
    CheckBox addAdrCmoren;
    private Dialog_date_se_3 dialog;
    private String id1, id2, id3;
    private LoadingDialog loadingDialog;
    private AddressModel model;
    private String addOrunId;
    private Address.DataBean data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_un_address);
        ButterKnife.bind(this);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        addAdrTitle.setTitle("添加/修改地址", true);
        addAdrTitle.showBianji("保存");
        addAdrTitle.setCallback(new TitleLayout.Callback(this) {
            @Override
            public void bianjiClick() {
                toAdd();
            }
        });
        dialog = new Dialog_date_se_3(this, new Dialog_date_se_3.CallBack() {
            @Override
            public void callBack(String name, String i1, String i2, String i3) {
                id1 = i1;
                id2 = i2;
                id3 = i3;
                addAdrTvcity.setText(name);
            }
        });
        loadingDialog = new LoadingDialog(this);
        model = new AddressModel();
        data = (Address.DataBean) getIntent().getSerializableExtra("data");
        if(null != data){
            addOrunId = data.getAddress_id();
            id1 = data.getProvince();
            id2 = data.getCity();
            id3 = data.getDistrict();
            setView();
        }

    }

    /**
     * 写入要修改的信息
     */
    private void setView() {
        addAdrEtname.setText(data.getConsignee());
        addAdrEtphoe.setText(data.getMobile());
        addAdrTvcity.setText(data.getAddress_name());
        addAdrEtaddress.setText(data.getAddress());
        addAdrCmoren.setChecked(data.getIs_default().equals("1"));
    }

    /**
     * 点击保存
     */
    private void toAdd() {
        String name = addAdrEtname.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            To.oo("请填写姓名");
            return;
        }
        String phoe = addAdrEtphoe.getText().toString().trim();
        if (TextUtils.isEmpty(phoe)) {
            To.oo("请填写电话");
            return;
        }
        if (TextUtils.isEmpty(id1)) {
            To.oo("请选择省份");
            return;
        }
        if (TextUtils.isEmpty(id2)) {
            To.oo("请选择城市");
            return;
        }
        if (TextUtils.isEmpty(id3)) {
            To.oo("请选择区县");
            return;
        }
        String addressinfo = addAdrEtaddress.getText().toString().trim();
        if (TextUtils.isEmpty(addressinfo)) {
            To.oo("请填写详细地址");
            return;
        }
        loadingDialog.show();
        Call call = model.addAddress(App.getUserId(), name, phoe, id1, id2, id3,
                addressinfo, addAdrCmoren.isChecked() ? "1" : "0", addOrunId, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (isFinishing()) {
                    return;
                }
                To.dd(obj);
                setResult(Bean.ADD_ADDRESS);
                finish();
                loadingDialog.dismiss();

            }

            @Override
            public void onError(Object obj) {
                if (isFinishing()) {
                    return;
                }
                To.oo(obj);
                loadingDialog.dismiss();
            }
        });
        setCall(call);


    }

    @OnClick(R.id.add_adr_tvcity)
    public void onClick() {
        dialog.show();
    }
}
