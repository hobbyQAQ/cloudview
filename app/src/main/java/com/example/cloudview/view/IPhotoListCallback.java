package com.example.cloudview.view;

import com.example.cloudview.base.IBaseCallback;
import com.example.cloudview.model.PhotoResult;

import java.util.List;

public interface IPhotoListCallback extends IBaseCallback {

    void onPhotoListLoad(List<PhotoResult.DataBean> photoList);
}

