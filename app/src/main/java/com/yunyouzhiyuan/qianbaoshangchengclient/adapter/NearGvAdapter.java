package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Specialty_Fenlei;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${王俊强} on 2017/1/9.
 */
public class NearGvAdapter extends MyAdapter<Specialty_Fenlei.DataBean> {
    private Callback callBack;
    private List<Specialty_Fenlei.DataBean.SecondBean> list = new ArrayList<>();

    public NearGvAdapter(Activity context, List<Specialty_Fenlei.DataBean> data, Callback callBack) {
        super(context, data);
        this.callBack = callBack;
        if (getData().size() > 0 && getData().get(0).getSecond().size() > 0) {
            list.addAll(getData().get(0).getSecond());
            list.get(0).setChecked(true);
        }
    }

    /**
     * 重置
     */
    public void toReset(List<Specialty_Fenlei.DataBean> data) {
        list.clear();
        if (getData().size() > 0 && getData().get(0).getSecond().size() > 0) {
            list.addAll(getData().get(0).getSecond());
            list.get(0).setChecked(true);
        }
        notifyDataSetChanged();
    }

    public void upData(int position) {
        list.clear();
        if (getData().size() > position && getData().get(position).getSecond().size() > 0) {
            list.addAll(getData().get(position).getSecond());
            for (Specialty_Fenlei.DataBean.SecondBean dataBean : list) {
                dataBean.setChecked(false);
            }
            list.get(0).setChecked(true);
        }
        notifyDataSetChanged();

    }


    @Override
    public int getCount() {
        return list.size();
    }

    public interface Callback {
        void onClick(String id);
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        view = getLayoutInflater().inflate(R.layout.itme_near_gv, null);
        TextView tvname = (TextView) view.findViewById(R.id.itme_near_gv_tvname);
        Specialty_Fenlei.DataBean.SecondBean dataBean = list.get(position);
        tvname.setSelected(dataBean.isChecked());
        tvname.setText(list.get(position).getMobile_name());
        tvname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isSelected()) {
                    return;
                }
                clearChecked(position);
            }
        });
        return view;
    }

    /**
     * 清除选中
     *
     * @param position
     */
    private void clearChecked(int position) {
        if (callBack != null) {
            callBack.onClick(list.get(position).getId());
        }
        for (Specialty_Fenlei.DataBean.SecondBean dataBean : list) {
            dataBean.setChecked(false);
        }
        list.get(position).setChecked(true);
        notifyDataSetChanged();
    }
}
