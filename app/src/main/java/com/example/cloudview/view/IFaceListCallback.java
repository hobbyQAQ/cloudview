package com.example.cloudview.view;

import com.example.cloudview.base.IBaseCallback;
import com.example.cloudview.model.FaceResult;
import com.example.cloudview.model.PhotoResult;

import java.util.List;

public interface IFaceListCallback extends IBaseCallback {
    void onFaceListLoad(List<FaceResult.DataBean> photoList);
}
