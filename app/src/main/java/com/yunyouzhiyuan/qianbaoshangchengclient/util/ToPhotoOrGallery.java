package com.yunyouzhiyuan.qianbaoshangchengclient.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;

import com.yunyouzhiyuan.qianbaoshangchengclient.App;

import java.io.File;

/**
 * Created by wangjunqiang on 2016/12/9.
 */
public class ToPhotoOrGallery {
    /**
     * 调用相册
     */
    public static Intent gallery(Activity context) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        return intent;
    }

    /**
     * 调用摄像头拍照
     */
    public static File takePhotos(Activity context, Callback callback) {
        long l = SystemClock.currentThreadTimeMillis();
        File file = new File(App.getContext().getExternalCacheDir(), "qianbao_image" + l + ".jpg");
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(file));
        callback.callBack(intent);
        return file;

    }

    public interface Callback {
        void callBack(Intent in);
    }

    /**
     * 判断SD卡是否可用
     */
    public static boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
}
