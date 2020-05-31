package com.example.cloudview.presenter;

import android.net.Uri;

import com.example.cloudview.base.IBasePresenter;
import com.example.cloudview.model.bean.PhotoItem;
import com.example.cloudview.view.IPhotoListCallback;

import java.io.File;
import java.util.List;

public interface IPhotoListPresenter extends IBasePresenter<IPhotoListCallback> {

    /**
     *  根据用户id获取用户在云端的所有照片
     */
    void getPhotoListByUserId(int id);

    /**
     * 查看方式
     */
    void switchViewWay(int way);


    /**
     * 上一张
     */
    void pre();

    /**
     * 下一张
     */
    void next();

    /**
     * 上传
     */
    void upload(PhotoItem photo);

    /**
     * 上传多个
     */
    void uploads(List<Uri> paths);

    /**
     * 下载
     *  param pid 照片id
     */
    void download(int pid, File picFilePath);

    /**
     *
     */
}
