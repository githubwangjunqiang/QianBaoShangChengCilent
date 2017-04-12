package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.AddressActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Address;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.AddressModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.LoadingDialog;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.Text_Size;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;

import java.util.List;

import okhttp3.Call;

/**
 * Created by ${王俊强} on 2017/2/15.
 */

public class AddressAdapter extends MyAdapter<Address.DataBean> {
    private AddressModel model;
    private LoadingDialog loadingDialog;

    public AddressAdapter(Activity context, List<Address.DataBean> data, AddressModel model, LoadingDialog loadingDialog) {
        super(context, data);
        this.model = model;
        this.loadingDialog = loadingDialog;
    }

    private boolean isShow = false;

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = getLayoutInflater().inflate(R.layout.itme_address_listview, null);
            holder.tvname = (TextView) convertView.findViewById(R.id.itme_address_tvname);
            holder.tvdelete = (TextView) convertView.findViewById(R.id.itme_address_tvdelete);
            holder.tvMoren = (TextView) convertView.findViewById(R.id.itme_address_tvmoren);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        String name = getData().get(position).getConsignee() + "\t\t" + getData().get(position).getMobile() + "\n";
        String str = name + getData().get(position).getAddress_name() + "\t\t" + getData().get(position).getAddress();
        Text_Size.setSize(getContext(), holder.tvname, str, 0, name.length(), "#646464", 14, name.length(), str.length(), "#bb646464", 12);
        showView(holder.tvdelete, position);
        holder.tvMoren.setVisibility(getData().get(position).getIs_default().equals("1") ? View.VISIBLE : View.GONE);
        return convertView;
    }

    /**
     * 显示隐藏删除按钮
     *
     * @param tvdelete
     */
    private void showView(TextView tvdelete, final int posiTion) {
        TranslateAnimation translateAnimation = null;
        if (isShow) {
            tvdelete.setVisibility(View.VISIBLE);
            translateAnimation = new TranslateAnimation(tvdelete.getWidth(), 0, 0, 0);
        } else {
            translateAnimation = new TranslateAnimation(0, tvdelete.getWidth(), 0, 0);
            tvdelete.setVisibility(View.GONE);
        }
        translateAnimation.setDuration(300);
        tvdelete.setAnimation(translateAnimation);
        translateAnimation.start();
        boolean b = (tvdelete.getVisibility() == View.VISIBLE);
        if (b) {
            tvdelete.setClickable(true);
            tvdelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delete(posiTion);
                }
            });
        } else {
            tvdelete.setClickable(false);
        }

    }

    /**
     * 删除
     *
     * @param posiTion
     */
    private void delete(final int posiTion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("您确定要删除吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                toDelete(posiTion);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * 联网删除地址
     */
    private void toDelete(final int posiotion) {
        loadingDialog.show();
        Call call = model.deleteAdd(App.getUserId(), getData().get(posiotion).getAddress_id(), new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                To.dd(obj);
                getData().remove(posiotion);
                notifyDataSetChanged();
                loadingDialog.dismiss();
            }

            @Override
            public void onError(Object obj) {
                To.oo(obj);
                loadingDialog.dismiss();
            }
        });
        ((AddressActivity) getContext()).setCall(call);
    }

    private class ViewHolder {
        TextView tvname, tvdelete, tvMoren;
    }
}
