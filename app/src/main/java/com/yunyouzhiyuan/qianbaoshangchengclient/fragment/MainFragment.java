package com.yunyouzhiyuan.qianbaoshangchengclient.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.yunyouzhiyuan.qianbaoshangchengclient.fragment.observer.ConcreteSubject;
import com.yunyouzhiyuan.qianbaoshangchengclient.fragment.observer.Observer;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by wangjunqiang on 2016/11/22.
 */
public abstract class MainFragment extends Fragment implements Observer {
    protected List<Call> calls = new ArrayList<>();

    protected void clearCalls() {
        for (Call call : calls) {
            if (!call.isCanceled()) {
                call.cancel();
            }
        }
    }

    @Override
    public void upImage() {

    }

    protected void setCalls(Call call) {
        calls.add(call);
    }

    //    /**
//     * Fragment当前状态是否可见
//     */
//    protected boolean isVisible;
//
//    public boolean isok() {
//        return isok;
//    }
//
//    public void setIsok(boolean isok) {
//        this.isok = isok;
//    }
//
//    protected boolean isok = false;//是否准备好初始化操作
    protected ConcreteSubject subject;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subject = ConcreteSubject.getInstance();
        subject.attach(this);
    }

    @Override
    public void onDetach() {
        clearCalls();
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        subject.detach(this);
        clearCalls();
        System.gc();
        super.onDestroy();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {//可见
//            isVisible = true;
//            onVisible();
        } else {//不可见
//            isVisible = false;
//            onInvisible();
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof CallSetListener) {
//            listener = (CallSetListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener(activity没有实现接口)");
//        }
//    }

//    /**
//     * 可见
//     */
//    protected void onVisible() {
//        if(isok()){
//            lazyLoad();
//        }
//    }

//
//    /**
//     * 不可见
//     */
//    protected void onInvisible() {
//    }
//
//    /**
//     * 延迟加载
//     * 子类必须重写此方法
//     */
//    protected abstract void lazyLoad();

    @Override
    public void nodifyPositioning() {

    }

    @Override
    public void nodifyDingdan() {

    }
}

