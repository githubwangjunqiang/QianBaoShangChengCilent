package com.yunyouzhiyuan.qianbaoshangchengclient.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Bean.WE_XIN_APPID);
        api.handleIntent(getIntent(), this);
    }

    /**
     * 微信发送的请求将回调到onReq方法
     *
     * @param baseReq
     */
    @Override
    public void onReq(BaseReq baseReq) {

    }

    /**
     * 发送到微信请求的响应结果将回调到onResp方法
     *
     * @param baseResp
     */
    @Override
    public void onResp(BaseResp baseResp) {
        String result = "";
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = "成功";
//                Bean.resp = resp;
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "取消";
//                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "被拒绝";
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:
                result = "返回";
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
        finish();
    }
}
