package com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.FoodInfo;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.DecimalCalculate;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wangjunqiang on 2016/11/21.
 */
public class DialogCartFood extends Dialog {
    private TextView tvClear;
    private ListView listView;
    private CallBack callBack;
    private int y;
    private List<FoodInfo.DataBean> list;
    private Adapter adapter;
    private LinearLayout view;
    private RelativeLayout rlTop;

    public DialogCartFood(Context context, CallBack callBack, int y, List<FoodInfo.DataBean> list) {
        super(context, R.style.dialogWindowAnim);
        this.callBack = callBack;
        this.y = y;
        this.list = list;
        setContentView(R.layout.dialog_cart_food);
    }

    public interface CallBack {
        void clearCart();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        tvClear = (TextView) findViewById(R.id.dialog_cart_food_tvclear);
        rlTop = (RelativeLayout) findViewById(R.id.fooddialog_cartrl);
        listView = (ListView) findViewById(R.id.dialog_cart_food_listview);
        view = (LinearLayout) findViewById(R.id.dialog_cart_food_ll);
        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clearCart();
            }
        });
        List<FoodInfo.DataBean.InfoBean> datas = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).getInfo().size(); j++) {
                if (list.get(i).getInfo().get(j).getCount() > 0) {
                    datas.add(list.get(i).getInfo().get(j));
                }
            }
        }
        adapter = new Adapter(getContext(), datas);
        listView.setAdapter(adapter);
//        listView.setClickable(false);
//        listView.setPressed(false);
//        listView.setEnabled(false);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent ev) {
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startY = ev.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        moveY = ev.getY() - startY;
                        view.scrollBy(0, -(int) moveY);
                        startY = ev.getY();
                        if (view.getScrollY() > 0) {
                            view.scrollTo(0, 0);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (view.getScrollY() < -getWindow().getAttributes().height / 4 && moveY > 0) {
                            dismiss();
                        }
                        view.scrollTo(0, 0);
                        break;
                }
                return false;
            }
        });
    }

//    /**
//     * 清空购物车
//     */
//    private void clearCart() {
//        Dialog dialog = new AlertDialog.Builder(getContext())
//                .setMessage("您确定要清空购物车吗？")
//                .setPositiveButton("确定", new OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        adapter.clearCart();
//                        dialog.dismiss();
//                    }
//                })
//                .setNegativeButton("取消", new OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                })
//                .create();
//        dialog.show();
//    }


    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wlp.gravity = Gravity.BOTTOM;
        wlp.y = y;
        window.setAttributes(wlp);
        window.setWindowAnimations(R.style.dialogWindowAnim);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    private class Adapter extends BaseAdapter {
        private Context context;
        private List<FoodInfo.DataBean.InfoBean> list;

        private Adapter(Context context, List<FoodInfo.DataBean.InfoBean> list) {
            this.context = context;
            this.list = list;
        }

        public void clearCart() {
            list.clear();
            callBack.clearCart();
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            ViewHolder holder;
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.itme_dialog_cart_food, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            }
            holder = (ViewHolder) view.getTag();
            holder.tvname.setText(list.get(position).getGoods_name());
            holder.tvnumber.setText(list.get(position).getCount() + "个");
            holder.tvprice.setText("￥" + DecimalCalculate.mul(Double.parseDouble(list.get(position).getShop_price()), (double) list.get(position).getCount()));
            return view;
        }

        private class ViewHolder {
            public TextView tvname, tvprice, tvnumber;

            public ViewHolder(View view) {
                tvname = (TextView) view.findViewById(R.id.itme_dialog_cartfood_tvname);
                tvprice = (TextView) view.findViewById(R.id.itme_dialog_cartfood_tvprice);
                tvnumber = (TextView) view.findViewById(R.id.itme_dialog_cartfood_tvnumber);
            }
        }
    }

    float startY;
    float moveY = 0;

//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                startY = ev.getY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                moveY = ev.getY() - startY;
//                view.scrollBy(0, -(int) moveY);
//                startY = ev.getY();
//                if (view.getScrollY() > 0) {
//                    view.scrollTo(0, 0);
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                if (view.getScrollY() < -this.getWindow().getAttributes().height / 4 && moveY > 0) {
//                    this.dismiss();
//
//                }
//                view.scrollTo(0, 0);
//                break;
//        }
//        return super.onTouchEvent(ev);
//    }

}
