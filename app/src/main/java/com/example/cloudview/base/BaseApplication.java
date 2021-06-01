package com.example.cloudview.base;

import android.app.Application;
import android.content.Context;
import android.view.KeyEvent;
import android.widget.Toast;

import com.example.cloudview.model.UserResult;

import java.util.HashMap;
import java.util.Map;

public class BaseApplication extends Application {
    private static Context mContext;
    public static UserResult.DataBean sUser;

    public static Map<String,String> coverMap = new HashMap<String,String>();

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
        coverMap.put(1+"",0+"");
        coverMap.put(2+"",0+"");
        coverMap.put(3+"",0+"");

    }
    //创建一个静态的方法，以便获取context对象
    public static Context getContext(){
        return mContext;
    }
    public static Map<String,String> getCoverMap(){
        return coverMap;
    }




}
