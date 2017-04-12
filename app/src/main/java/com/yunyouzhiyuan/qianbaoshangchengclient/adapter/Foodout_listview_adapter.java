package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.FoodInfo;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/2/8.
 */
public class Foodout_listview_adapter extends BaseAdapter {
    private Context context;
    private List<FoodInfo.DataBean> leftStr;
    private Callback callback;

    public Foodout_listview_adapter(Context context, List<FoodInfo.DataBean> leftStr, Callback callback) {
        this.context = context;
        this.leftStr = leftStr;
        this.callback = callback;
    }

    public interface Callback {
        void onClick(int position);
    }

    @Override
    public int getCount() {
        return leftStr.size();
    }

    @Override
    public Object getItem(int arg0) {
        return leftStr.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup arg2) {
        Holder holder;
        if (view == null) {
            holder = new Holder();
            view = LayoutInflater.from(context).inflate(R.layout.left_list_item, null);
            holder.left_list_item = (TextView) view.findViewById(R.id.left_list_item_tv);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        holder.left_list_item.setText(leftStr.get(position).getCat_name());
        holder.left_list_item.setSelected(leftStr.get(position).isXuanzhong());
        holder.left_list_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClick(position);
            }
        });
        return view;
    }

    private class Holder {
        TextView left_list_item;
    }
}
