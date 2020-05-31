package com.example.cloudview.presenter;

import com.example.cloudview.base.IBasePresenter;
import com.example.cloudview.view.IFaceListCallback;
import com.example.cloudview.view.IPhotoListCallback;

public interface IFaceListPresenter extends IBasePresenter<IFaceListCallback> {
    /**
     *  根据人物id获取照片
     */
    void getPhotoListByUserId(int id);

    /**
     *  根据uid获取人脸
     */
    void getFaceListByUid(int uid);


}
