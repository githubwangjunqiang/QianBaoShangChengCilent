package com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.andexert.calendarlistview.library.DatePickerController;
import com.andexert.calendarlistview.library.DayPickerView;
import com.andexert.calendarlistview.library.SimpleMonthAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.GetWinDowHeight;

import java.util.Calendar;


/**
 * Created by wangjunqiang on 2016/11/21.
 */
public class DialogHotelTime extends Dialog {
    private CallBack callBack;
    private DayPickerView dayPickerView;
    private ImageView ivBack;
    private Button btn;
    private int fistYear;
    private int fistMoth;
    private int fistDay;
    private int lastYear;
    private int lastMoth;
    private int lastDay;
    private int dayCount;
    private boolean isok;

    public DialogHotelTime(Context context, CallBack callBack) {
        super(context, R.style.dialogWindowAnim);
        this.callBack = callBack;
        setContentView(R.layout.dialog_hotel_time);
    }

    public interface CallBack {
        void loadTime(int fy, int fm, int fd, int ly, int lm, int ld,int count);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        dayPickerView = (DayPickerView) findViewById(R.id.pickerView_dialog);
        ivBack = (ImageView) findViewById(R.id.ivback_dialog);
        btn = (Button) findViewById(R.id.btn_dialog);
        init();
    }

    /**
     * 操作view
     */
    private void init() {
        dayPickerView.setController(new DatePickerController() {
            @Override
            public int getMaxYear() {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                return year + 1;
            }


            @Override
            public void onDayOfMonthSelected(int year, int month, int day) {
            }

            @Override
            public void onDateRangeSelected(SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays) {
            }

            @Override
            public void isAdd(boolean isClear, int fy, int fm, int fd, int ly, int lm, int ld,int Count) {
                isok = isClear;
                btn.setSelected(isClear);
                if (isok) {
                    fistYear = fy;
                    fistMoth = fm+1;
                    fistDay = fd;
                    lastYear = ly;
                    lastMoth = lm+1;
                    lastDay = ld;
                    dayCount = Count;
                }
            }


        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn.isSelected()) {
                    callBack.loadTime(fistYear, fistMoth, fistDay, lastYear, lastMoth, lastDay,dayCount);
                    dismiss();
                }
            }
        });
    }

    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = (int) (GetWinDowHeight.getScreenHeight(App.getContext()) * 0.7);
        wlp.gravity = Gravity.BOTTOM;
        window.setAttributes(wlp);
        window.setWindowAnimations(R.style.dialogWindowAnim);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
