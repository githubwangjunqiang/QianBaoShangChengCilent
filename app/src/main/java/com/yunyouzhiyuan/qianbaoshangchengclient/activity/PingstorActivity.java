package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.tangxiaolv.telegramgallery.GalleryActivity;
import com.tangxiaolv.telegramgallery.GalleryConfig;
import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.PingGvPhotoAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.DingDanModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.MyGridView;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.TitleLayout;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.DiaLogaddImage;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.LoadingDialog;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.GetJsonRetcode;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.PhotoTask;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.ToPhotoOrGallery;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean.DINGDANWANCHENG;
import static com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean.TAKEPHOTO;
import static com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean.TAKEPHOTOGALLERY;

public class PingstorActivity extends BaseActivity {

    @Bind(R.id.poingjiastor_title)
    TitleLayout titleLayout;
    @Bind(R.id.poingjiastor_tvpingfen)
    TextView tvpingfen;
    @Bind(R.id.poingjiastor_bar)
    RatingBar ratingBar;
    @Bind(R.id.poingjiastor_tvjiaochja)
    TextView tvStas;
    @Bind(R.id.poingjiastor_edittexct)
    EditText etContent;
    @Bind(R.id.poingjiastor_gv)
    MyGridView gv;
    @Bind(R.id.poingjiastor_btnok)
    Button btnOk;
    private PingGvPhotoAdapter adapter;
    private List<Uri> list = new ArrayList<>();
    private File file;
    private LoadingDialog loadingDialog;
    private DingDanModel model;
    private List<AsyncTask<Uri, Void, String>> datas = new ArrayList<>();

    public static void startPingstorActivity(Context context, String storid, String ordson_id) {
        Intent intent = new Intent(context, PingstorActivity.class);
        intent.putExtra("storid", storid);
        intent.putExtra("ordson_id", ordson_id);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        list.clear();
        if (file != null) {
            file.delete();
            file = null;
        }
        loadingDialog = null;
        model = null;
        adapter = null;
        for (AsyncTask<Uri, Void, String> task : datas) {
            if (!task.isCancelled()) {
                task.cancel(true);
            }
        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pingstor);
        ButterKnife.bind(this);
        if (TextUtils.isEmpty(getIntent().getStringExtra("storid")) ||
                TextUtils.isEmpty(getIntent().getStringExtra("ordson_id"))) {
            To.oo("本地传参有误");
            finish();
            return;
        }
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        titleLayout.setTitle("评价订单", true);
        setListener();
        setGVAdater();
        loadingDialog = new LoadingDialog(this);
        model = new DingDanModel();
    }

    /**
     * 设置gvadapter
     */
    private void setGVAdater() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            adapter = new PingGvPhotoAdapter(this, list, new PingGvPhotoAdapter.Callback() {
                @Override
                public void addPhoto() {
                    toAddImage();
                }
            });
            gv.setAdapter(adapter);
        }

    }

    /**
     * 添加照片
     */
    private void toAddImage() {
        new DiaLogaddImage(this, new DiaLogaddImage.CallBack() {
            @Override
            public void callBack(int type) {
                if (type == 0) {//拍照
                    file = ToPhotoOrGallery.takePhotos(null, new ToPhotoOrGallery.Callback() {
                        @Override
                        public void callBack(Intent in) {
                            startActivityForResult(in, TAKEPHOTO);
                        }
                    });
                } else {
                    String[] fiel = new String[]{"image/gif", "image/png"};
                    int count = 7 - list.size();
//                    GalleryActivity.openActivity(PingstorActivity.this, fiel, false, count, TAKEPHOTOGALLERY);
                    GalleryActivity.openActivity(PingstorActivity.this, TAKEPHOTOGALLERY,
                            new GalleryConfig.Build().filterMimeTypes(fiel)
                                    .limitPickPhoto(count).singlePhoto(false).build());
                }
            }
        }).show();
    }

    /**
     * 监听器
     */
    private void setListener() {
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating < 0.5) {
                    To.oo("亲 最低给半颗星吧，店家也不容易呢");
                    tvStas.setText("较差");
                    ratingBar.setRating(0.5f);
                } else if (rating <= 2) {
                    tvStas.setText("较差");
                } else if (rating < 4) {
                    tvStas.setText("中等");
                } else if (rating <= 5) {
                    tvStas.setText("好评");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKEPHOTO://拍照返回
                if (resultCode == RESULT_OK && file != null && file.length() > 0) {
                    list.add(Uri.fromFile(file));
                    setGVAdater();
                } else {
                    To.oo("拍照取消");
                }
                break;
            case TAKEPHOTOGALLERY://图库返回
                if (resultCode == RESULT_OK) {
                    List<String> paths = (List<String>) data.getSerializableExtra(GalleryActivity.PHOTOS);
                    for (int i = 0; i < paths.size(); i++) {
                        list.add(Uri.fromFile(new File(paths.get(i))));
                        setGVAdater();
                    }
                } else {
                    To.oo("相册选择取消");
                }
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.poingjiastor_btnok, R.id.poingjiastor_tvdeletepahot})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.poingjiastor_tvdeletepahot://点击删除
                list.clear();
                setGVAdater();
                break;
            case R.id.poingjiastor_btnok://点击确定
                toOk();
                break;
            default:
                break;
        }
    }

    /**
     * 点击确定
     */
    private void toOk() {
        float rating = ratingBar.getRating();
        String trim = etContent.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            To.oo("请填写评论内容");
            return;
        }
        loadingDialog.show();
        if (!list.isEmpty()) {
            toOutImage(rating, trim);
        } else {
            toSubmit(rating, trim, null);
        }
    }

    /**
     * 上传图片
     *
     * @param rating
     * @param trim
     */
    private void toOutImage(final float rating, final String trim) {
        final List<String> strings = new ArrayList<>();
        final int[] connt = {list.size()};
        for (int i = 0; i < list.size(); i++) {
            AsyncTask<Uri, Void, String> data = new PhotoTask(App.getContext(), new PhotoTask.TaskCallback() {
                @Override
                public void onPreExecute() {

                }

                @Override
                public void onProgressUpdate(Object bitmap) {

                }

                @Override
                public void onError() {
                    connt[0]--;
                    if (0 == connt[0]) {
                        toSubmit(rating, trim, strings);
                    }
                }

                @Override
                public void onSuccess(String string) {
                    strings.add(GetJsonRetcode.getname("data", string));
                    connt[0]--;
                    if (0 == connt[0]) {
                        toSubmit(rating, trim, strings);
                    }
                }
            }).execute(list.get(i));
            datas.add(data);
        }
    }

    /**
     * 提交
     *
     * @param rating
     * @param trim
     * @param strings
     */
    private void toSubmit(float rating, String trim, List<String> strings) {
        StringBuilder builder = null;
        if (strings != null && !strings.isEmpty()) {
            builder = new StringBuilder();
            for (int i = 0; i < strings.size(); i++) {
                builder.append(strings.get(i) + ",");
            }
            builder.deleteCharAt(builder.length() - 1);
        }
        Call call = model.addComment(App.getUserId(), getIntent().getStringExtra("ordson_id"), getIntent().getStringExtra("storid"), rating + "", trim, builder == null ? null : builder.toString(), new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (isFinishing()) {
                    return;
                }
                To.dd(obj);
                setResult(DINGDANWANCHENG);
                finish();
                loadingDialog.dismiss();
            }

            @Override
            public void onError(Object obj) {
                if (isFinishing()) {
                    return;
                }
                To.oo(obj);
                loadingDialog.dismiss();
            }
        });
        setCall(call);
    }
}
