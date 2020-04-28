package com.example.cloudview.utils;

public  class UrlUtils {
    public static String getLocalPictureUrl(String old){
        return "file://"+old;
    }

    //http://192.168.1.2:8089/img/static/1/IMG_20180215_191537.jpg
    public static String path2Url(String path){

        return "http://192.168.1.2:8089/img"+path;
    }
}
