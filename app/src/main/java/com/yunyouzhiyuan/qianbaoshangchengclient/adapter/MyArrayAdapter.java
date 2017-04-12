package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Nearby_Fenlei;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/3/17.
 */

public class MyArrayAdapter extends MyAdapter<Nearby_Fenlei.DataBean.SencondBean> {


    public MyArrayAdapter(Context context, List<Nearby_Fenlei.DataBean.SencondBean> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        TextView textView ;
        if(view == null){
            textView = (TextView) getLayoutInflater().inflate(R.layout.popuwindow_itme,null);
            view = textView;
        }
        textView = (TextView) view;
        textView.setText(getData().get(position).getMobile_name());
        return view;
    }
}
