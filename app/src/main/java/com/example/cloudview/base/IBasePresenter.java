package com.example.cloudview.base;

public interface IBasePresenter<T>{
    /**
     *
     */
    void registerCallback(T callback);

    void unRegisterCallback(T callback);
}
