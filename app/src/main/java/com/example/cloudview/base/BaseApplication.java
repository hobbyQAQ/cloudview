package com.example.cloudview.base;

import android.app.Application;
import android.content.Context;

import com.example.cloudview.model.UserResult;

public class BaseApplication extends Application {
    private static Context mContext;
    public static UserResult.DataBean sUser;

    public static UserResult.DataBean getUser() {
        return sUser;
    }

    public static void setUser(UserResult.DataBean user) {
        sUser = user;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //获取context
        mContext = getApplicationContext();
    }
    //创建一个静态的方法，以便获取context对象
    public static Context getContext(){
        return mContext;
    }
}
