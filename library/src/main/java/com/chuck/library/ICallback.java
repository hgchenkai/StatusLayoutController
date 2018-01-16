package com.chuck.library;

/**
 * 回调接口
 * Created by chuck on 2018/1/15.
 */

public interface ICallback {
    /**
     * 刷新操作
     */
    void refreshCallback();

    /**
     * 出错时重新加载
     */
    void reloadCallback();
}
