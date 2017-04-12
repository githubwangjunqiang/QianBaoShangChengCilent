package com.yunyouzhiyuan.qianbaoshangchengclient.fragment_dingdan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.baidu.location.BDLocation;
import com.yunyouzhiyuan.qianbaoshangchengclient.fragment.observer.ConcreteSubject;
import com.yunyouzhiyuan.qianbaoshangchengclient.fragment.observer.Observer;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

import static com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean.DINGDANWANCHENG;

/**
 * Created by wangjunqiang on 2016/11/22.
 */
public abstract class BaseFragment extends Fragment implements Observer {
    /**
     * Fragment当前状态是否可见
     */
    protected boolean isVisible;
    private ConcreteSubject subject;
    private List<Call> calls = new ArrayList<>();

    public void setCalls(Call calls) {
        this.calls.add(calls);
    }

    public void clearCalls() {
        if (!this.calls.isEmpty()) {
            for (int i = 0; i < calls.size(); i++) {
                if (calls.get(i) != null && !calls.get(i).isCanceled()) {
                    calls.get(i).cancel();
                }
            }
        }
    }

    public boolean isok() {
        return isok;
    }

    public void setIsok(boolean isok) {
        this.isok = isok;
    }

    protected boolean isok = false;//是否准备好初始化操作

    @Override
    public void onDestroy() {
        subject.detach(this);
        clearCalls();
        super.onDestroy();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subject = ConcreteSubject.getInstance();
        subject.attach(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DINGDANWANCHENG && resultCode == DINGDANWANCHENG) {
            lazyLoad();
        }
    }

    /**
     * 可见
     */
    protected void onVisible() {
        if (isok()) {
            lazyLoad();
        }
    }


    /**
     * 不可见
     */
    protected void onInvisible() {
    }

    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    protected abstract void lazyLoad();

    @Override
    public void nodifyDingdan() {
        To.dd("您有一个订单已经消费");
        lazyLoad();
    }

    @Override
    public void nodifyPositioning() {

    }

    @Override
    public void upImage() {

    }

    @Override
    public void upCity() {

    }

    @Override
    public void upLocation(BDLocation location) {

    }
}

