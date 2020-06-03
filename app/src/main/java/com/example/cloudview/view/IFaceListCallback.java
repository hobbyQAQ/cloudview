package com.example.cloudview.view;

import com.example.cloudview.base.IBaseCallback;
import com.example.cloudview.model.FaceResult;
import com.example.cloudview.model.PhotoResult;
import com.example.cloudview.model.SortFaceResult;

import java.util.List;

public interface IFaceListCallback extends IBaseCallback {
    void onFaceListLoad(List<SortFaceResult.DataBean> photoList);
}
