package com.example.cloudview.Api;

import com.example.cloudview.model.PhotoResult;
import com.example.cloudview.model.SimpleResult;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface PhotoService {
    @GET("photo/get")
    Call<PhotoResult> getPhotoListByUserId(@Query("user")Integer id);

    @GET("photo/download")
    Call<ResponseBody> downloadPhotoByPid(@Query("pid")Integer pid);

    @Multipart
    @POST("photo/uploads")
    Call<SimpleResult> postFiles(@Part List<MultipartBody.Part> files, @Query("uid")Integer uid);

    @Multipart
    @POST("photo/upload")
    Call<SimpleResult> postFile(@Part MultipartBody.Part file, @Query("type")Integer type, @Query("uid")Integer uid);

    @GET("photo/get/by/face")
    Call<PhotoResult> getPhotoListByCid(@Query("cid")Integer cid);

    @GET("photo/search")
    Call<PhotoResult> getPhotoListByKeyword(@Query("keyword")String keyword, @Query("uid")int id);
    @GET("photo/get/loves")
    Call<PhotoResult> getLoves(@Query("uid")int uid);


    @GET("photo/add/love")
    Call<SimpleResult> addLove(@Query("pid")int pid, @Query("uid")int uid);


    @GET("photo/get/noclassify")
    Call<PhotoResult> getNoclassify(@Query("uid")int uid);

    @GET("photo/get/person")
    Call<PhotoResult> getPerson(@Query("uid")int uid);

    @GET("photo/get/building")
    Call<PhotoResult> getBuilding(@Query("uid")int uid);

    @GET("photo/get/car")
    Call<PhotoResult> getCar(@Query("uid")int uid);

    @GET("photo/get/certificates")
    Call<PhotoResult> getCertificates(@Query("uid")int uid);


    @GET("photo/get/flower")
    Call<PhotoResult> getFlower(@Query("uid")int uid);

    @GET("photo/get/food")
    Call<PhotoResult> getFood(@Query("uid")int uid);


    @GET("photo/get/pet")
    Call<PhotoResult> getPet(@Query("uid")int uid);

    @GET("photo/get/scenery")
    Call<PhotoResult> getScenery(@Query("uid")int uid);

    @GET("photo/get/screenshot")
    Call<PhotoResult> getScreenshot(@Query("uid")int uid);

    @GET("photo/get/by/face")
    Call<PhotoResult> getByFace(@Query("uid")int uid,@Query("cid")int cid);

    @GET("photo/delete")
    Call<SimpleResult> delete(@Query("pid")int pid, @Query("uid")int uid);

    @GET("face/update/name")
    Call<SimpleResult> changeCharaterName(@Query("cid")int cid, @Query("faceName")String result);
}
