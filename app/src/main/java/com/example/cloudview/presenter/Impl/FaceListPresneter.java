package com.example.cloudview.presenter.Impl;

import com.example.cloudview.Api.FaceService;
import com.example.cloudview.model.FaceResult;
import com.example.cloudview.model.PhotoResult;
import com.example.cloudview.model.SortFaceResult;
import com.example.cloudview.presenter.IFaceListPresenter;
import com.example.cloudview.utils.LogUtil;
import com.example.cloudview.utils.RetrofitCreator;
import com.example.cloudview.view.IFaceListCallback;
import com.example.cloudview.view.IPhotoListCallback;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class FaceListPresneter implements IFaceListPresenter {
    private List<IFaceListCallback> mCallbacks = new ArrayList<IFaceListCallback>();

    private FaceListPresneter() {

    }
    private static FaceListPresneter sInstance = null;

    public static FaceListPresneter getInstance() {
        if (sInstance == null) {
            sInstance = new FaceListPresneter();
        }
        return sInstance;
    }
    public void getPhotoListByUserId(int id) {

    }

//    @Override
//    public void getFaceListByUid(int uid) {
//        //retrofit访问服务器获得人脸数据
//        Retrofit retrofit = RetrofitCreator.getInstance().getRetrofit();
//        FaceService faceService = retrofit.create(FaceService.class);
//        Call<FaceResult> task = faceService.getFaceListByUserId();
//        task.enqueue(new Callback<FaceResult>() {
//            @Override
//            public void onResponse(Call<FaceResult> call, Response<FaceResult> response) {
//                int code = response.code();
//                if (code == HttpURLConnection.HTTP_OK) {
//                    FaceResult body = response.body();
//
//                    for (IFaceListCallback callback : mCallbacks) {
//                        List<FaceResult.DataBean> data = body.getData();
//                        if (data != null &&data.size() != 0) {
//                            callback.onFaceListLoad(data);
//                        }else{
//                            callback.onEmpty();
//                        }
//
//
//                    }
//                    LogUtil.d(FaceListPresneter.this, "response body ==> " + body);
//                } else {
//                    String message = response.message();
//                    LogUtil.d(FaceListPresneter.this, "response message ==> " + message);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<FaceResult> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
//    }

    @Override
    public void getSortFaceByUid(int i) {
        //retrofit访问服务器获得人脸数据
        Retrofit retrofit = RetrofitCreator.getInstance().getRetrofit();
        FaceService faceService = retrofit.create(FaceService.class);
        Call<SortFaceResult> task = faceService.getSortFace();
        task.enqueue(new Callback<SortFaceResult>() {
            @Override
            public void onResponse(Call<SortFaceResult> call, Response<SortFaceResult> response) {
                SortFaceResult body = response.body();
                for (IFaceListCallback callback : mCallbacks) {
                    List<SortFaceResult.DataBean> data = body.getData();
                    if (data != null &&data.size() != 0) {
                        callback.onFaceListLoad(data);
                    }else{
                        callback.onEmpty();
                    }
                }
                LogUtil.d(FaceListPresneter.this,"response data======"+response.body().toString());
            }

            @Override
            public void onFailure(Call<SortFaceResult> call, Throwable t) {
                LogUtil.d(FaceListPresneter.this,"onFailure");
            }
        });
    }

    @Override
    public void registerCallback(IFaceListCallback callback) {
        if (!mCallbacks.contains(callback)) {
            mCallbacks.add(callback);
        }
    }

    @Override
    public void unRegisterCallback(IFaceListCallback callback) {
        mCallbacks.remove(callback);
    }
}
