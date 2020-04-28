package com.example.cloudview.presenter.Impl;

import android.util.Log;

import com.example.cloudview.Api.PhotoService;
import com.example.cloudview.model.PhotoResult;
import com.example.cloudview.presenter.IPhotoListPresenter;
import com.example.cloudview.utils.LogUtil;
import com.example.cloudview.utils.RetrofitCreator;
import com.example.cloudview.view.IPhotoListCallback;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PhotoListPresenter implements IPhotoListPresenter {
    private List<IPhotoListCallback> mCallbacks = new ArrayList<>();

    private PhotoListPresenter() {

    }

    private static PhotoListPresenter sInstance = null;

    public static PhotoListPresenter getInstance() {
        if (sInstance == null) {
            sInstance = new PhotoListPresenter();
        }
        return sInstance;
    }


    @Override
    public void getPhotoListByUserId(int id) {
        //更新加载界面
        for (IPhotoListCallback callback : mCallbacks) {
            callback.onLoading();
        }
        //用Retrofit加载数据
        Retrofit retrofit = RetrofitCreator.getInstance().getRetrofit();
        PhotoService photoService = retrofit.create(PhotoService.class);
        Call<PhotoResult> task = photoService.getPhotoListByUserId(1);
        task.enqueue(new Callback<PhotoResult>() {
            @Override
            public void onResponse(Call<PhotoResult> call, Response<PhotoResult> response) {
                int code = response.code();
                if (code == HttpURLConnection.HTTP_OK) {
                    PhotoResult body = response.body();

                    for (IPhotoListCallback callback : mCallbacks) {
                        List<PhotoResult.DataBean> data = body.getData();
                        if (data != null &&data.size() != 0) {
                            callback.onPhotoListLoad(data);
                        }else{
                            callback.onEmpty();
                        }


                    }
                    LogUtil.d(PhotoListPresenter.this, "response body ==> " + body);
                } else {
                    String message = response.message();
                    LogUtil.d(PhotoListPresenter.this, "response message ==> " + message);
                }

            }
            @Override
            public void onFailure(Call<PhotoResult> call, Throwable t) {
                t.printStackTrace();
                for (IPhotoListCallback callback : mCallbacks) {
                    callback.onError();
                }
            }
        });
        //把数据传给UI  callback.方法
        // TODO: 2020/4/25 明天加载数据，显示到recyclerview内，写一个item_photo_list
    }

    @Override
    public void registerCallback(IPhotoListCallback callback) {
        if (!mCallbacks.contains(callback)) {
            mCallbacks.add(callback);
        }
    }

    @Override
    public void unRegisterCallback(IPhotoListCallback callback) {
        mCallbacks.remove(callback);
    }
}
