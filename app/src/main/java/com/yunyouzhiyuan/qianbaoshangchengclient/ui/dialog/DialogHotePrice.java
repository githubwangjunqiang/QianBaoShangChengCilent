package com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appyvet.rangebar.RangeBar;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;


/**
 * Created by wangjunqiang on 2016/11/21.
 */
public class DialogHotePrice extends Dialog {
    private CallBack callBack;
    private ImageView ivBack;
    private Button btnBuxian, btnOk;
    private TextView tvprice,view;
    private RangeBar rangeBar;
    private LinearLayout linearLayout;
    private int fistPrice = 0;
    private int lastPrice = 500;

    public DialogHotePrice(Context context, CallBack callBack) {
        super(context, R.style.dialogWindowAnim);
        this.callBack = callBack;
        setContentView(R.layout.dialog_hotel_price);
    }

    public interface CallBack {
        void loadPrice(int fistPrice, int lastPrice);

        void noPrice();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ivBack = (ImageView) findViewById(R.id.dialog_hotel_price_ivback);
        btnBuxian = (Button) findViewById(R.id.dialog_hotel_price_btnbuxian);
        btnOk = (Button) findViewById(R.id.dialog_hotel_price_btnok);
        tvprice = (TextView) findViewById(R.id.dialog_hotel_price_tvprice);
        rangeBar = (RangeBar) findViewById(R.id.rangebar_hotelprice);
        view = (TextView) findViewById(R.id.dialog_price_tvtop);
        linearLayout = (LinearLayout) findViewById(R.id.dailog_lltop);
        init();
        setListener();
    }

    /**
     * 监听器
     */
    private void setListener() {
        rangeBar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
                fistPrice = Integer.parseInt(leftPinValue);
                lastPrice = Integer.parseInt(rightPinValue);
                tvprice.setText(fistPrice + "元\t\t---\t\t" + lastPrice + "元");
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnBuxian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.noPrice();
                dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.loadPrice(fistPrice, lastPrice);
                dismiss();
            }
        });
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent ev) {
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startY = ev.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        moveY = ev.getY() - startY;
                        linearLayout.scrollBy(0, -(int) moveY);
                        startY = ev.getY();
                        if (linearLayout.getScrollY() > 0) {
                            linearLayout.scrollTo(0, 0);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (linearLayout.getScrollY() < -getWindow().getAttributes().height
                                / 4 && moveY > 0) {
                            dismiss();

                        }
                        linearLayout.scrollTo(0, 0);
                        break;
                }
                return false;
            }
        });
    }

    /**
     * 操作view
     */
    private void init() {
        rangeBar.setTickEnd(500);
        rangeBar.setTickStart(0);
        rangeBar.setTickInterval(100);
        tvprice.setText("0元\t\t---\t\t500元");
    }

    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wlp.gravity = Gravity.BOTTOM;
        window.setAttributes(wlp);
        window.setWindowAnimations(R.style.dialogWindowAnim);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    float startY;
    float moveY = 0;
}
