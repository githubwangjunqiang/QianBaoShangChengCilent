package com.yunyouzhiyuan.qianbaoshangchengclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Pingjia;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.MyGridView;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ${王俊强} on 2017/1/17.
 */
public class EvaluateListActivityAdapter extends RecyclerView.Adapter<ViewHolder> {
    private Context context;
    private List<Pingjia.DataBean> list;

    public EvaluateListActivityAdapter(Context context, List<Pingjia.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.itme_evaluate_list, parent, false), context);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class ViewHolder extends RecyclerView.ViewHolder {
    ImageView ivImage;
    TextView tvName, tvTime, tvContent;
    MyGridView gvImages;
    PingjiaGvAdapter adapter;
    Context context;

    public ViewHolder(View itemView, Context context) {
        super(itemView);
        ivImage = (ImageView) itemView.findViewById(R.id.itme_evaluate_list_iviamge);
        tvName = (TextView) itemView.findViewById(R.id.itme_evaluate_list_tvname);
        tvTime = (TextView) itemView.findViewById(R.id.itme_evaluate_list_tvtime);
        tvContent = (TextView) itemView.findViewById(R.id.itme_evaluate_list_tvcontent);
        gvImages = (MyGridView) itemView.findViewById(R.id.itme_evaluate_gv);
        adapter = new PingjiaGvAdapter(context);
        this.context = context;
    }

    public void setData(Pingjia.DataBean data) {
        ToGlide.urlCircle(context, HTTPURL.IMAGE + data.getStore_logo(), ivImage);
        tvName.setText(data.getStore_name() + "");
        tvContent.setText(data.getContent() + "");
        tvTime.setText(data.getAdd_time() + "");
        if (TextUtils.isEmpty(data.getImg())) {
            gvImages.setVisibility(View.GONE);
        } else {
            gvImages.setVisibility(View.VISIBLE);
            String[] split = data.getImg().split(",");
            adapter.setData(Arrays.asList(split));
            gvImages.setAdapter(adapter);
        }
    }
}