package com.example.cloudview.presenter;

import com.example.cloudview.base.IBasePresenter;
import com.example.cloudview.view.IPhotoListCallback;

public interface IPhotoListPresenter extends IBasePresenter<IPhotoListCallback> {

    /**
     *  根据用户id获取用户在云端的所有照片
     */
    void getPhotoListByUserId(int id);
}
