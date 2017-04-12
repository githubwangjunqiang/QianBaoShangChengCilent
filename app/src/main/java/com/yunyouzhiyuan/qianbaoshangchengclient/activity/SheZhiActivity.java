package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.UserInfo;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.MyModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.AutoImageView;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.TitleLayout;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.DiaLogaddImage;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.LoadingDialog;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.GetJsonRetcode;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.LogUtils;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.PhotoUploadTask;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.ToPhotoOrGallery;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.Utils;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean.PHOTO_REQUEST_CUT;
import static com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean.PHOTO_REQUEST_GALLERY;
import static com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean.SHEZHI;
import static com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean.SHEZHI_PHOTO;

public class SheZhiActivity extends BaseActivity {

    @Bind(R.id.shezhi_title)
    TitleLayout shezhiTitle;
    @Bind(R.id.shezhi_iviamge)
    AutoImageView shezhiIviamge;
    @Bind(R.id.shezhi_tvphoto)
    TextView shezhiTvphoto;
    @Bind(R.id.shezhi_etname)
    EditText shezhiEtname;
    @Bind(R.id.shezhi_tvbirday)
    TextView shezhiTvbirday;
    @Bind(R.id.shezhi_tvaddress)
    TextView shezhiTvaddress;
    @Bind(R.id.shezhi_layout)
    SwipeRefreshLayout layout;
    @Bind(R.id.shezhi_logout)
    Button shezhiLogout;
    private UserInfo.DataBean data;
    private File file;//临时保存拍照文件
    private ByteArrayOutputStream baos;
    private InputStream is;
    private LoadingDialog loadingDialog;
    private boolean isback = false;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 152) {
                String string = (String) msg.obj;
                LogUtils.e("图片返回的路径" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    isback = true;
                    data.setHead_pic(GetJsonRetcode.getname("data", string));
                    setView();
                }
                To.oo(GetJsonRetcode.getmsg(string));
                loadingDialog.dismiss();
            }
        }
    };
    private MyModel model;

    @Override
    protected void onDestroy() {

        handler.removeMessages(152);
        handler = null;
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (baos != null) {
            try {
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_she_zhi);
        ButterKnife.bind(this);
        init();
        setListener();
        setView();
    }

    /**
     * 写入信息
     */
    private void setView() {
        LogUtils.e("图片返回的路径" + HTTPURL.IMAGE + data.getHead_pic());
        ToGlide.urlCircle(this, HTTPURL.IMAGE + data.getHead_pic(), shezhiIviamge);
        shezhiEtname.setText(data.getNickname());
        shezhiTvbirday.setText(data.getBirthday());
    }

    /**
     * 监听器
     */
    private void setListener() {
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setView();
                layout.setRefreshing(false);
            }
        });

    }

    /**
     * 初始化
     */
    private void init() {
        data = (UserInfo.DataBean) getIntent().getSerializableExtra("userinfo");
        if (data == null) {
            finish();
            To.oo("信息为null");
            return;
        }
        shezhiTitle.setTitle(getString(R.string.shezhijieain), true);
        shezhiTitle.setCallback(new TitleLayout.Callback(this) {
            @Override
            public void backClick() {
                back();
            }
        });
        layout.setColorSchemeColors(ContextCompat.getColor(this, R.color.white));
        layout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this, R.color.app_color));
        setView();
        loadingDialog = new LoadingDialog(this);
        model = new MyModel();
    }

    /**
     * 退出检查是否保存
     */
    private void back() {
        String trim = shezhiEtname.getText().toString().trim();
        if (!TextUtils.equals(trim, data.getNickname())) {
            isback = true;
            data.setNickname(trim);
        }
        if (isback) {
            if (TextUtils.isEmpty(data.getHead_pic())) {
                To.oo("请选择头像");
                return;
            }
            if (TextUtils.isEmpty(data.getNickname())) {
                To.oo("请填写姓名");
                return;
            }
            if (TextUtils.isEmpty(data.getBirthday())) {
                To.oo("请选择生日  ");
                return;
            }
            loadingDialog.show();
            loadingDialog.setText("正在保存，请稍后......");
            Call call = model.unDate(App.getUserId(), data.getHead_pic(), data.getNickname(), data.getBirthday(), new IModel.AsyncCallBack() {
                @Override
                public void onSucceed(Object obj) {
                    if (isFinishing()) {
                        return;
                    }
                    To.oo(obj);
                    loadingDialog.dismiss();
                    Intent intent = getIntent();
                    intent.putExtra("userinfo", data);
                    setResult(SHEZHI, intent);
                    finish();
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
        } else {
            finish();
        }
    }

    /**
     * 点击监听器
     *
     * @param view
     */
    @OnClick({R.id.shezhi_logout, R.id.shezhi_iviamge, R.id.shezhi_tvphoto, R.id.shezhi_tvbirday, R.id.shezhi_tvaddress})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shezhi_iviamge://点击头像
                List<String> list = new ArrayList<>();
                list.add(data.getHead_pic());
                TapViewpagerActivity.startActivity(this, list, 0);
                break;
            case R.id.shezhi_tvphoto://修改头像
                DiaLogaddImage diaLogaddImage = new DiaLogaddImage(this, new DiaLogaddImage.CallBack() {
                    @Override
                    public void callBack(int type) {
                        if (0 == type) {//拍照
                            file = ToPhotoOrGallery.takePhotos(SheZhiActivity.this, new ToPhotoOrGallery.Callback() {
                                @Override
                                public void callBack(Intent in) {
                                    startActivityForResult(in, SHEZHI_PHOTO);
                                }
                            });
                        } else {//相册
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
                        }
                    }
                });
                diaLogaddImage.show();
                break;
            case R.id.shezhi_tvbirday://点击生日
                editBirthday();
                break;
            case R.id.shezhi_tvaddress://点击地址
                startActivity(new Intent(SheZhiActivity.this, AddressActivity.class));
                break;
            case R.id.shezhi_logout://退出登录
                logOut();
                break;
        }
    }

    /**
     * 退出登录
     */
    private void logOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("您确定要退出账号重新登陆吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                App.setUserId(null);
                setResult(SHEZHI);
                finish();
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SHEZHI_PHOTO://拍照返回
                if (resultCode == RESULT_OK && file.length() > 0) {
                    crop(Uri.fromFile(file));
                }
                break;
            case PHOTO_REQUEST_GALLERY://相册返回
                if (resultCode == RESULT_OK && data != null) {
                    crop(data.getData());
                }
                break;
            case PHOTO_REQUEST_CUT://裁剪返回
                if (resultCode == RESULT_OK && data != null) {
                    Bitmap bitmap = data.getParcelableExtra("data");
                    if (bitmap != null) {
                        toOutImage(bitmap);
                    } else {
                        To.oo("选择图片失败");
                    }
                } else {
                    To.oo("选择图片失败");
                }
                break;
            default:
                break;
        }
    }

    /**
     * 上传图片
     *
     * @param bitmap
     */
    private void toOutImage(Bitmap bitmap) {
        loadingDialog.show();
        baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        is = new ByteArrayInputStream(baos.toByteArray());
        PhotoUploadTask put = new PhotoUploadTask(
                HTTPURL.fileUpload, is,
                this, handler);
        put.start();
        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 去裁剪界面
     *
     * @param uri
     */
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", Utils.dip2px(App.getContext(), 60));
        intent.putExtra("outputY", Utils.dip2px(App.getContext(), 60));
        // 图片格式
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    /**
     * 修改生日
     */
    private void editBirthday() {
        Calendar instance = Calendar.getInstance();
        int year = instance.get(Calendar.YEAR);
        int moth = instance.get(Calendar.MONTH);
        int day = instance.get(Calendar.DAY_OF_MONTH);

        String birday = data.getBirthday();
        String[] split = birday.split("-");
        if (split.length == 3) {
            year = Integer.parseInt(split[0]);
            moth = Integer.parseInt(split[1]) - 1;
            day = Integer.parseInt(split[2]);
        }
        final DatePickerDialog datePickerDialog = new DatePickerDialog(SheZhiActivity.this, null, year, moth, day);
        datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface d, int which) {
                if (which != DialogInterface.BUTTON_POSITIVE) {
                    return;
                }
                d.dismiss();
                DatePicker datePicker = datePickerDialog.getDatePicker();
                final String bir = datePicker.getYear() + "-" + (datePicker.getMonth() + 1) + "-" + datePicker.getDayOfMonth();
                String birthday = data.getBirthday();
                boolean equals = birthday.equals(bir);
                if (!equals) {
                    isback = true;
                    data.setBirthday(bir);
                    setView();
                }
            }
        });
        datePickerDialog.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
