package com.chuck.library;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * 列表状态管理，根据数据的状态来显示相应的View
 * Created by chuck on 2018/1/12.
 */

public class StatusListController {
    /**
     * 自动的去判断数据状态，减少手动操作。
     * 如何区别是loading态还是错误的状态
     */
    private View contentView;
    private View currentView;
    private int childIndex;
    private Options options;
    private ICallback callback;
    private StatusListController(Builder builder) {
        this.currentView=this.contentView = builder.contentView;
        this.childIndex = findIndex();
        this.options=builder.options;
        this.callback=builder.callback;
        registerCallback();
    }

    /**
     * 显示loadingView
     */
    public void showLoadingView() {
        replaceView(options.getLoadingView(contentView.getContext()));
    }

    /**
     * 显示正常的内容视图
     */
    public void showContentView() {
        replaceView(contentView);
    }

    /**
     * 显示内容为空时视图
     */
    public void showEmptyView(){
        replaceView(options.getEmptyView(contentView.getContext()));
    }

    /**
     * 显示发生错误时的视图
     */
    public void showErrorView(){
        replaceView(options.getErrorView(contentView.getContext()));
    }

    private void registerCallback(){
        options.registerCallback(contentView.getContext(),callback);
    }

    private void replaceView(View view) {
        if (view == null) {
            return;
        }

        if(view==currentView){
            return;
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }

        ViewGroup contentParent = (ViewGroup) currentView.getParent();
        //先remove
        if (contentParent != null) {
            contentParent.removeViewAt(childIndex);
        }
        contentParent.addView(view,childIndex);
        this.currentView=view;
    }


    /**
     * 查询contentView在parent中的index
     * @return
     */
    private int findIndex() {
        ViewGroup parent = (ViewGroup) contentView.getParent();
        if (parent == null) {
            parent = contentView.getRootView().findViewById(android.R.id.content);
        }
        int count=parent.getChildCount();
        for(int i=0;i<count;i++){
            if(contentView==parent.getChildAt(i)){
                return i;
            }
        }
        return 0;
    }

    public static class Builder {
        /**
         * 内容视图
         */
        private View contentView;
        private Options options;
        private ICallback callback;

        public Builder(View contentView) {
            this.contentView = contentView;
        }

        public Builder callback(ICallback callback){
            this.callback=callback;
            return this;
        }

        public Builder apply(Options options){
            this.options=options;
            return this;
        }
        public StatusListController build() {
            Context context=contentView.getContext();
            if(options==null){
                options= Options.buildDefaultOptions(callback);
            }
            return new StatusListController(this);
        }
    }
}
