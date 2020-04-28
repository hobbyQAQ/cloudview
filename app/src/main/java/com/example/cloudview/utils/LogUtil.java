package com.example.cloudview.utils;

import android.util.Log;

public class LogUtil {

    private static int currentLevel = 4;
    private static int debugLev = 1;
    private static int infoLev = 2;
    private static int warmingLev = 3;
    private static int errorLev = 4;

    public static void d(Object object,String log){
        if (currentLevel >= debugLev) {
            Log.d(object.getClass().getSimpleName(),log);
        }
    }

    public static void i(Object object,String log){
        if (currentLevel >= infoLev) {
            Log.d(object.getClass().getSimpleName(),log);
        }
    }

    public static void w(Object object,String log){
        if (currentLevel >= errorLev) {
            Log.d(object.getClass().getSimpleName(),log);
        }
    }

    public static void e(Object object,String log){
        if (currentLevel >= debugLev) {
            Log.d(object.getClass().getSimpleName(),log);
        }
    }

}
