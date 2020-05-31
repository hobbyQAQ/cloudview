package com.example.cloudview.presenter.Impl;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.cloudview.Api.PhotoService;
import com.example.cloudview.base.BaseApplication;
import com.example.cloudview.model.PhotoResult;
import com.example.cloudview.model.UploadResult;
import com.example.cloudview.model.bean.PhotoItem;
import com.example.cloudview.presenter.IPhotoListPresenter;
import com.example.cloudview.utils.FileUtils;
import com.example.cloudview.utils.LogUtil;
import com.example.cloudview.utils.RetrofitCreator;
import com.example.cloudview.view.IPhotoListCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PhotoListPresenter implements IPhotoListPresenter {
    private List<IPhotoListCallback> mCallbacks = new ArrayList<>();

    private PhotoListPresenter() {

    }

    private static PhotoListPresenter sInstance = null;

    public static PhotoListPresenter getInstance() {
        if (sInstance == null) {
            sInstance = new PhotoListPresenter();
        }
        return sInstance;
    }


    @Override
    public void getPhotoListByUserId(int id) {
        //更新加载界面
        for (IPhotoListCallback callback : mCallbacks) {
            callback.onLoading();
        }
        //用Retrofit加载数据
        Retrofit retrofit = RetrofitCreator.getInstance().getRetrofit();
        PhotoService photoService = retrofit.create(PhotoService.class);
        Call<PhotoResult> task = photoService.getPhotoListByUserId(1);
        task.enqueue(new Callback<PhotoResult>() {
            @Override
            public void onResponse(Call<PhotoResult> call, Response<PhotoResult> response) {
                int code = response.code();
                if (code == HttpURLConnection.HTTP_OK) {
                    PhotoResult body = response.body();

                    for (IPhotoListCallback callback : mCallbacks) {
                        List<PhotoResult.DataBean> data = body.getData();
//                        LogUtil.d(PhotoListPresenter.this,"data ====> "+data.toString());
                        if (data != null &&data.size() != 0) {
                            callback.onPhotoListLoad(data);
                        }else{
                            callback.onEmpty();
                        }


                    }
                    LogUtil.d(PhotoListPresenter.this, "response body ==> " + body);
                } else {
                    String message = response.message();
                    LogUtil.d(PhotoListPresenter.this, "response message ==> " + message);
                }

            }
            @Override
            public void onFailure(Call<PhotoResult> call, Throwable t) {
                t.printStackTrace();
                for (IPhotoListCallback callback : mCallbacks) {
                    callback.onError();
                }
            }
        });
        //把数据传给UI  callback.方法
    }

    @Override
    public void switchViewWay(int way) {

    }

    @Override
    public void pre() {

    }

    @Override
    public void next() {

    }

    @Override
    public void upload(PhotoItem photoItem) {
        Retrofit retrofit = RetrofitCreator.getInstance().getRetrofit();
        PhotoService photoService = retrofit.create(PhotoService.class);
        MultipartBody.Part file =getPart("file",photoItem.getPath());
        Call<UploadResult> task = photoService.postFile(file,1);
        task.enqueue(new Callback<UploadResult>() {
            @Override
            public void onResponse(Call<UploadResult> call,Response<UploadResult> response) {
                //
                Log.d("UploadResult","文件上传结果" + response.body());
                Toast.makeText(BaseApplication.getContext(), "上传成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<UploadResult> call,Throwable t) {
                Log.d("UploadResult","onFailure -- > 文件上传失败 ---> " + t.toString());
                Toast.makeText(BaseApplication.getContext(), "上传失败", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private MultipartBody.Part getPart(String key,String filePath) {
        File file = new File(filePath);
        MediaType mediaType = MediaType.parse("image/jpg");
        RequestBody fileBody = RequestBody.create(mediaType,file);

        return MultipartBody.Part.createFormData(key,file.getName(),fileBody);
    }

    @Override
    public void uploads(List<Uri> paths) {
        Retrofit retrofit = RetrofitCreator.getInstance().getRetrofit();
        PhotoService photoService = retrofit.create(PhotoService.class);
        List<MultipartBody.Part> files = new ArrayList<>();
        for (Uri path : paths) {
            files.add(getPart("files", FileUtils.getFilePathByUri(BaseApplication.getContext(),path)));
        }
        Call<UploadResult> task = photoService.postFiles(files,1);
        task.enqueue(new Callback<UploadResult>() {
            @Override
            public void onResponse(Call<UploadResult> call,Response<UploadResult> response) {
                //
                Log.d("UploadResult","多文件上传结果" + response.body());
            }

            @Override
            public void onFailure(Call<UploadResult> call,Throwable t) {
                Log.d("UploadResult","onFailure -- > 多文件上传失败 ---> " + t.toString());
            }
        });
    }

    @Override
    public void download(int pid, File picFilePath) {
        Retrofit retrofit = RetrofitCreator.getInstance().getRetrofit();
        PhotoService photoService = retrofit.create(PhotoService.class);
        Call<ResponseBody> task = photoService.downloadPhotoByPid(pid);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Headers headers = response.headers();
                writeFile2Sd(response,headers,picFilePath);
                LogUtil.d(PhotoListPresenter.this,"下载成功，文件路径为"+picFilePath.getAbsolutePath());
                Toast.makeText(BaseApplication.getContext(), "下载成功，文件路径为"+picFilePath.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                LogUtil.d(PhotoListPresenter.this,"下载失败");
                Toast.makeText(BaseApplication.getContext(), "下载成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 写文件到sd卡，需要异步执行
     * @param response
     * @param headers
     * @param picFilePath
     */
    private void writeFile2Sd(final Response<ResponseBody> response, final Headers headers, File picFilePath) {

        String disposition = headers.get("Content-disposition");
        if(disposition != null) {
            int fileNameIndex = disposition.indexOf("fileName=");
            LogUtil.d(PhotoListPresenter.this,"fileNameIndex -- > " + fileNameIndex);
            String fileName = disposition.substring(fileNameIndex + "filename=".length());
            LogUtil.d(PhotoListPresenter.this,"fileName -- > " + fileName);
            LogUtil.d(PhotoListPresenter.this,"picFilePath --> " + picFilePath);
            File file = new File(picFilePath + File.separator + fileName);
            LogUtil.d(PhotoListPresenter.this,"file -- > " + file);
            FileOutputStream fos = null;
            try {
                if(!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                if(!file.exists()) {
                    file.createNewFile();
                }
                fos = new FileOutputStream(file);
                InputStream inputStream = response.body().byteStream();
                byte[] buf = new byte[1024];
                int len;
                while((len = inputStream.read(buf,0,buf.length)) != -1) {
                    fos.write(buf,0,len);
                }
            } catch(Exception e) {
                e.printStackTrace();
            } finally {
                if(fos != null) {
                    try {
                        fos.close();
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void registerCallback(IPhotoListCallback callback) {
        if (!mCallbacks.contains(callback)) {
            mCallbacks.add(callback);
        }
    }

    @Override
    public void unRegisterCallback(IPhotoListCallback callback) {
        mCallbacks.remove(callback);
    }
}
