package com.yunyouzhiyuan.qianbaoshangchengclient.framgent_shoucang;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.baidu.location.BDLocation;
import com.yunyouzhiyuan.qianbaoshangchengclient.fragment.observer.ConcreteSubject;
import com.yunyouzhiyuan.qianbaoshangchengclient.fragment.observer.Observer;
import com.yunyouzhiyuan.qianbaoshangchengclient.fragment_dingdan.CallSetListener;

/**
 * Created by wangjunqiang on 2016/11/22.
 */
public abstract class BaseShouCangFragment extends Fragment implements Observer{
    public CallSetListener listener;//订单专用 l
    private ConcreteSubject subject;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subject = ConcreteSubject.getInstance();
        subject.attach(this);
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        subject.detach(this);
        System.gc();
        super.onDestroy();
    }

    //    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//
//        if (getUserVisibleHint()) {
//            isVisible = true;
//            onVisible();
//        } else {
//            isVisible = false;
//            onInvisible();
//        }
//    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CallSetListener) {
            listener = (CallSetListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener(activity没有实现接口)");
        }
    }

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
    public void nodifyDingdan() {

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

