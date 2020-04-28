package com.example.cloudview.base;

public interface IBaseCallback {
    /**
     * 网络错误
     */
    void onError();

    /**
     * 数据为空
     */
    void onEmpty();

    /**
     * 成功
     */
    void onSuccess();

    /**
     * 数据加载中
     */
    void onLoading();
}
