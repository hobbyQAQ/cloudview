package com.example.cloudview.view;

import com.example.cloudview.base.IBaseCallback;
import com.example.cloudview.model.PhotoResult;

import java.util.List;

public interface IPhotoListCallback extends IBaseCallback {

    void onPhotoListLoad(List<PhotoResult.DataBean> photoList);

    void onSwitchViewWay();
    void onNext(PhotoResult.DataBean photo);
    void onPre(PhotoResult.DataBean photo);

    void onUpload();
    void onDownload();
}

