package com.example.cloudview.utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitCreator {
    private static  RetrofitCreator sInstance = new RetrofitCreator();
    private  Retrofit mRetrofit;

    public static RetrofitCreator getInstance() {
        return sInstance;
    }

    private RetrofitCreator() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(Constants.CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .readTimeout(Constants.READ_TIME_OUT,TimeUnit.MILLISECONDS)
                .build();
       mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
               .client(okHttpClient)//设置请求的client
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Retrofit getRetrofit(){
        return mRetrofit;
    }
}
