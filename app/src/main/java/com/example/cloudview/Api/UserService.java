package com.example.cloudview.Api;

import com.example.cloudview.model.UserResult;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface UserService {
    //目前用一号用户做测试
    @GET("user/login")
    Call<UserResult> login(@Query("account")String account ,@Query("password") String password);

    @Multipart
    @POST("user/upload")
    Call<UserResult> upload(@Part MultipartBody.Part file, @Query("uid")Integer uid);

    @GET("user/update")
    Call<UserResult> update(@Query("uid")Integer uid, @Query("nickname")String nickname);
}
