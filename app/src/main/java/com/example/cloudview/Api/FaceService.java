package com.example.cloudview.Api;

import com.example.cloudview.model.FaceResult;
import com.example.cloudview.model.PhotoResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FaceService {
    //目前用一号用户做测试
    @GET("face/get/all")
    Call<FaceResult> getFaceListByUserId();
}
