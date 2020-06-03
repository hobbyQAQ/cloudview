package com.example.cloudview.Api;

import com.example.cloudview.model.FaceResult;
import com.example.cloudview.model.SortFaceResult;
import com.example.cloudview.model.UserResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserService {
    //目前用一号用户做测试
    @GET("user/login")
    Call<UserResult> login(@Query("account")String account ,@Query("password") String password);


}
