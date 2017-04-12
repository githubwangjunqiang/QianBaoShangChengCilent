package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjunqiang on 2016/11/19.
 */
public abstract class MyAdapter<T>
        extends BaseAdapter {
    private Context context;
    private List<T> data;
    private LayoutInflater inflater;

    public MyAdapter(Context context, List<T> data) {
        super();
        setContext(context);
        setData(data);
        setLayoutInflater();
    }

    public void setContext(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("参数Context不允许为null！！！");
        }
        this.context = context;
    }

    public Context getContext() {
        return this.context;
    }

    public void setData(List<T> data) {
        if (data == null) {
            data = new ArrayList<T>();
        }
        this.data = data;
    }
    public void addData(List<T> data) {
        if (data == null) {
            data = new ArrayList<T>();
        }
        this.data.addAll(data);
    }

    public List<T> getData() {
        return this.data;
    }

    public void setLayoutInflater() {
        inflater = LayoutInflater.from(this.context);
    }

    public LayoutInflater getLayoutInflater() {
        return this.inflater;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
