package com.yunyouzhiyuan.qianbaoshangchengclient.util;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by ${王俊强} on 2017/1/16.
 */
public class RecyleViewButtom extends RecyclerView.OnScrollListener {
    public Callback callback;
    private boolean isLooding = true;
    private int lastPosition1;

    public RecyleViewButtom(Callback callback) {
        this.callback = callback;
    }

    public interface Callback {
        void last(int position);

        void fist(int position);

        /**
         * 第一个itme  不可见
         *
         * @param position
         */
        void unfist(int position);
    }


    public static class Listener implements Callback {

        @Override
        public void last(int position) {

        }

        @Override
        public void fist(int position) {

        }

        @Override
        public void unfist(int position) {

        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        //当前RecyclerView显示出来的最后一个的item的position
        int lastPosition = -1;
        int fistPosition = -1;

        //当前状态为停止滑动状态SCROLL_STATE_IDLE时
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                //通过LayoutManager找到当前显示的最后的item的position
                fistPosition = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
                lastPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();


            } else if (layoutManager instanceof LinearLayoutManager) {
                fistPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();


            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                //因为StaggeredGridLayoutManager的特殊性可能导致最后显示的item存在多个，所以这里取到的是一个数组
                //得到这个数组后再取到数组中position值最大的那个就是最后显示的position值了
                int[] fistPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                ((StaggeredGridLayoutManager) layoutManager).findFirstCompletelyVisibleItemPositions(fistPositions);
                ((StaggeredGridLayoutManager) layoutManager).findLastCompletelyVisibleItemPositions(lastPositions);

                fistPosition = findMin(fistPositions);
                lastPosition = findMax(lastPositions);
            }

            //时判断界面显示的最后item的position是否等于itemCount总数-1也就是最后一个item的position
            //如果相等则说明已经滑动到最后了
            if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1) {
                if (isLooding) {
                    callback.last(lastPosition);
                    lastPosition1 = lastPosition;
                    isLooding = false;
                }
                if (lastPosition1 != lastPosition) {
                    isLooding = true;
                }
            } else if (fistPosition == 0) {//第一个可见了
                callback.fist(lastPosition);
            } else {
                callback.unfist(lastPosition);
            }
////                        if(lastPosition == 0){
////                            layout.setEnabled(false);
////                        }else{
////                            layout.setEnabled(false);
////                        }

        }
    }

    //找到数组中的最大值
    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    //找到数组中的最小值
    private int findMin(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value < max) {
                max = value;
            }
        }
        return max;
    }
}
