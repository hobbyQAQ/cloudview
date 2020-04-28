package com.example.cloudview.Api;

import com.example.cloudview.model.PhotoResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface PhotoService {
    @GET("photo/get")
    Call<PhotoResult> getPhotoListByUserId(@Query("user")Integer id);
}
