package com.yunyouzhiyuan.qianbaoshangchengclient.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.SouSuoActivity;

/**
 * Created by ${王俊强} on 2017/1/11.
 */
public class TitleLayout extends LinearLayout {
    private Button btnSearch;//搜索
    private TextView tvtitle;//标题
    private TextView tvbianji;//编辑
    private ImageView ivPosition;//位置

    public ImageView getIvPosition() {
        return ivPosition;
    }

    public TextView getBiji() {
        return tvbianji;
    }

    /**
     * 限时标题 关闭搜索框
     *
     * @param title          要显示的标题
     * @param ishidePosition 是否关闭 右边位置按钮
     */
    public void setTitle(String title, boolean ishidePosition) {
        tvtitle.setVisibility(VISIBLE);
        btnSearch.setVisibility(GONE);
        tvtitle.setText(title);
        if (ishidePosition) {
            ivPosition.setVisibility(INVISIBLE);
        }
    }
    /**
     * 限时标题 关闭搜索框
     *
     * @param id          要显示的标题
     * @param ishidePosition 是否关闭 右边位置按钮
     */
    public void setTitle(@StringRes int id, boolean ishidePosition) {
        tvtitle.setVisibility(VISIBLE);
        btnSearch.setVisibility(GONE);
        tvtitle.setText(id);
        if (ishidePosition) {
            ivPosition.setVisibility(INVISIBLE);
        }
    }

    public TitleLayout(Context context) {
        this(context, null);
    }

    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.top_title, this);
        ImageButton ivback = (ImageButton) findViewById(R.id.top_back);
        tvtitle = (TextView) findViewById(R.id.top_title);
        tvbianji = (TextView) findViewById(R.id.top_tvbianji);
        btnSearch = (Button) findViewById(R.id.top_sousuo);
        ivPosition = (ImageView) findViewById(R.id.top_weizhi);
        ivback.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.backClick();
                }
            }
        });
        btnSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.searchClick();
                }
            }
        });
        ivPosition.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.positionClick();
                }
            }
        });
        tvbianji.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.bianjiClick();
                }
            }
        });
    }

    /**
     * 显示编辑按钮
     */
    public void showBianji(String content) {
        tvbianji.setVisibility(VISIBLE);
        if (!TextUtils.isEmpty(content)) {
            tvbianji.setText(content);
        }
        ivPosition.setVisibility(GONE);
    }

    /**
     * 设置右边显示的图片
     */
    public void setRitleBitmap(int ResId) {
        ivPosition.setImageResource(ResId);
    }


    private listener listener;

    private interface listener {
        void backClick();

        void searchClick();

        void positionClick();

        void bianjiClick();
    }

    public void setCallback(listener listener) {
        this.listener = listener;
    }

    public static class Callback implements listener {
        private Activity activity;

        public Callback(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void backClick() {
            if (activity != null) {
                activity.finish();
            }
        }

        @Override
        public void searchClick() {
            if (activity != null) {
                activity.startActivity(new Intent(activity, SouSuoActivity.class));
                activity.overridePendingTransition(0, 0);
            }
        }

        @Override
        public void positionClick() {

        }

        @Override
        public void bianjiClick() {

        }
    }
}
