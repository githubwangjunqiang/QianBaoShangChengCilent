package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.ShopStorinfoActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.ShouCang;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.util.List;



/**
 * Created by ${王俊强} on 2017/2/6.
 */
public class ShouCangStorAdapter extends RecyclerView.Adapter<StorViewHolder> {
    private Context content;
    private List<ShouCang.DataBean> list;

    public ShouCangStorAdapter(Context content, List<ShouCang.DataBean> list) {
        this.content = content;
        this.list = list;
    }

    @Override
    public StorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StorViewHolder(LayoutInflater.from(content).
                inflate(R.layout.itme_fragmentshoucang_stor, parent, false));
    }

    @Override
    public void onBindViewHolder(StorViewHolder holder, int position) {
        final ShouCang.DataBean data = list.get(position);
        ToGlide.urlRound(content, HTTPURL.IMAGE+data.getStore_logo(),holder.ivimage,8);
        holder.tvinfo.setText(data.getStore_name()==null?"":data.getStore_name());
        holder.tvnumber.setText("已有"+(data.getCollectsum()==null?"":data.getCollectsum())+"收藏");
        holder.tvinStor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopStorinfoActivity.startShopStorinfoActivity(content,data.getStore_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}

class StorViewHolder extends RecyclerView.ViewHolder {
    public ImageView ivimage;
    public TextView tvinfo,tvnumber,tvinStor;
    public StorViewHolder(View itemView) {
        super(itemView);
        ivimage = (ImageView) itemView.findViewById(R.id.itme_shoucang_stor_ivimage);
        tvinfo = (TextView) itemView.findViewById(R.id.itme_shoucang_stor_tvname);
        tvnumber = (TextView) itemView.findViewById(R.id.itme_shoucang_stor_tvnumber);
        tvinStor = (TextView) itemView.findViewById(R.id.itme_shoucang_stor_tvinstor);
    }
}
