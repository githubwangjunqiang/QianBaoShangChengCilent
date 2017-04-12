package com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.DingDanOver;


/**
 * Created by wangjunqiang on 2016/11/21.
 */
public class DiaLogDingdanOk extends Dialog {
    private TextView tvoldPrice, tvyouhuiPrice, tvnewprice, tvquxiao, tvok, tvFee;
    private CallBack callBack;
    private DingDanOver.DataBean data;

    public DiaLogDingdanOk(Context context, CallBack callBack, DingDanOver.DataBean data) {
        super(context, R.style.Dialog);
        this.callBack = callBack;
        this.data = data;
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_dingdanok);
    }

    public interface CallBack {
        void callOk();

        void callOver();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        tvoldPrice = (TextView) findViewById(R.id.tvold_price);
        tvyouhuiPrice = (TextView) findViewById(R.id.tvyou_price);
        tvnewprice = (TextView) findViewById(R.id.tvnew_price);
        tvquxiao = (TextView) findViewById(R.id.tvquxiao);
        tvok = (TextView) findViewById(R.id.tvok);
        tvFee = (TextView) findViewById(R.id.tvyou_postfee);
        int postFee = data.getPostFee();
        if (postFee != 0) {
            int postFee1 = (Integer) postFee;
            tvFee.setText("配送费用：\t\t\t\t￥" + postFee1);
            tvFee.setVisibility(View.VISIBLE);
        }

        tvoldPrice.setText("商品总价：\t\t\t\t￥" + data.getGoodsFee());
        tvyouhuiPrice.setText("优惠用券：\t\t\t\t￥" + data.getCouponFee());
        tvnewprice.setText("本次付款：\t\t\t\t￥" + data.getPayables());
        tvok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.callOk();
                dismiss();
            }
        });
        tvquxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.callOver();
                dismiss();
            }
        });
    }

    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

}
