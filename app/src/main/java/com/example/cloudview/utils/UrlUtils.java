package com.example.cloudview.utils;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public  class UrlUtils {
    /**
     * 获取本地文件的url
     * @param old
     * @return
     */
    public static String getLocalPictureUrl(String old){
        return "file://"+old;
    }

    /**
     * http://localhost:8089/upload/photo/1/IMG_20180215_191537.jpg
     * 通过照片相对路径获取url
     * @param path
     * @return
     */
    public static String path2Url(String path){

        return Constants.BASE_URL+"upload/"+path;
    }



}
