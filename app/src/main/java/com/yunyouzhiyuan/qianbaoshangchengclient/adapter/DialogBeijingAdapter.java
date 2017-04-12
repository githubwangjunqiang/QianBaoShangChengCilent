package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.City;

import java.util.List;


/**
 * Created by wangjunqiang on 2016/10/15.
 */
public class DialogBeijingAdapter extends BaseAdapter {
    private Context context;
    private List<City.DataBean> list;


    public DialogBeijingAdapter(Context context, List<City.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position).getRegion_id();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder holder ;
        CheckedTextView textView;
        if (convertView == null) {
//            holder = new ViewHolder();
            textView = (CheckedTextView) LayoutInflater.from(context).inflate(R.layout.spinner, null);
//            holder.textView = (CheckedTextView) convertView.findViewById(R.id.spinnerA_tv);
//            convertView.setTag(holder);
            convertView = textView;
        }
//        else {
//            holder = (ViewHolder) convertView.getTag();
//        }
        textView = (CheckedTextView) convertView;
        textView.setText(list.get(position).getRegion_name());
//        holder.textView.setText(list.get(position).getRegion_name());
        return textView;
    }
//
//    class ViewHolder {
//        CheckedTextView textView;
//    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();

    }
}
