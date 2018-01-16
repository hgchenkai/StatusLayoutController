package com.chuck.library;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 配置
 * Created by chuck on 2018/1/13.
 */

public class Options {
    private static final String NULL_LOADING="loadingView";
    private static final String NULL_EMPTY="emptyView";
    private static final String NULL_ERROR="errorView";
    private int loadingResId;
    private int emptyResId;
    private int errorResId;
    private View loadingView;
    private View emptyView;
    private View errorView;
    private int emptyRefreshId;
    private int errorReloadId;

    private Options(Builder builder){
       this.loadingView=builder.loadingView;
       this.emptyView=builder.emptyView;
       this.errorView=builder.errorView;
       this.loadingResId=builder.loadingResId;
       this.emptyResId=builder.emptyResId;
       this.errorResId=builder.errorResId;
       this.emptyRefreshId=builder.emptyRefreshId;
       this.errorReloadId=builder.errorReloadId;
    }

    public View getLoadingView(Context context) {
        return loadingView=createView(context,loadingResId,loadingView,NULL_LOADING);
    }

    public View getEmptyView(Context context) {
        return emptyView=createView(context,emptyResId,emptyView,NULL_EMPTY);
    }

    public View getErrorView(Context context) {
        return errorView=createView(context,errorResId,errorView,NULL_ERROR);
    }

    public void registerCallback(Context context,final ICallback callback){
        if(callback==null){
            return;
        }
        emptyView=getEmptyView(context);
        View emptyRefresh=emptyView.findViewById(emptyRefreshId);
        errorView=getErrorView(context);
        View errorReload=errorView.findViewById(errorReloadId);
        emptyRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.refreshCallback();
            }
        });
        errorReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.reloadCallback();
            }
        });
    }

    private View createView(Context context,int resId,View targetView,String tag){
       if(targetView==null){
           targetView=inflater(context, resId);
           if(targetView==null){
               throw new NullPointerException(String.format("%s == null",tag));
           }
       }
       return targetView;
    }

    private View inflater(Context context,int resId) {
        View view=LayoutInflater.from(context).inflate(resId,null);
        return view;
    }

    public static class Builder{
        /**
         * 加载中布局layout Id
         */
        @LayoutRes
        private int loadingResId;
        /**
         * 空布局layout Id
         */
        @LayoutRes
        private int emptyResId;
        /**
         * 发生错误布局layout Id
         */
        @LayoutRes
        private int errorResId;
        /**
         * 空布局刷新按钮Id
         */
        @IdRes
        private int emptyRefreshId;
        /**
         * 错误布局重新加载
         */
        private int errorReloadId;
        private View loadingView;
        private View emptyView;
        private View errorView;
        private ICallback callback;

        public Builder callback(ICallback callback){
            this.callback=callback;
            return this;
        }

        public Builder loadingResId(@LayoutRes int resId){
           this.loadingResId=resId;
           return this;
        }

        public Builder emptyResId(@LayoutRes int resId){
            this.emptyResId=resId;
            return this;
        }

        public Builder errorResId(@LayoutRes int resId){
           this.errorResId=resId;
           return this;
        }

        public Builder emptyRefreshId(@IdRes int refreshId){
            this.emptyRefreshId=refreshId;
            return this;
        }

        public Builder errorReloadId(@IdRes int errorReloadId){
            this.errorReloadId=errorReloadId;
            return this;
        }

        public Builder loadingView(View view){
            this.loadingView=view;
            return this;
        }

        public Builder emptyView(View view){
            this.emptyView=view;
            return this;
        }

        public Builder errorView(View view){
            this.errorView=view;
            return this;
        }

        public Options build(){
            return new Options(this);
        }
    }

    public static Options buildDefaultOptions(ICallback callback){
        return new Builder()
                .loadingResId(R.layout.layout_loading_def)
                .errorResId(R.layout.layout_error_def)
                .emptyResId(R.layout.layout_empty_def)
                .errorReloadId(R.id.tv_error_reload)
                .emptyRefreshId(R.id.tv_empty_refresh)
                .callback(callback)
                .build();
    }
}
