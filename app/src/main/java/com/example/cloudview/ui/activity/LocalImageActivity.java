package com.example.cloudview.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cloudview.R;
import com.example.cloudview.model.PhotoResult;
import com.example.cloudview.model.bean.PhotoItem;
import com.example.cloudview.presenter.IPhotoListPresenter;
import com.example.cloudview.presenter.Impl.PhotoListPresenter;
import com.example.cloudview.utils.FileUtils;
import com.example.cloudview.view.IPhotoListCallback;
import com.xinlan.imageeditlibrary.editimage.EditImageActivity;
import com.xinlan.imageeditlibrary.editimage.utils.BitmapUtils;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LocalImageActivity extends AppCompatActivity implements View.OnClickListener, IPhotoListCallback {
    public static final int REQUEST_PERMISSON_SORAGE = 1;
    public static final int REQUEST_PERMISSON_CAMERA = 2;

    public static final int SELECT_GALLERY_IMAGE_CODE = 7;
    public static final int TAKE_PHOTO_CODE = 8;
    public static final int ACTION_REQUEST_EDITIMAGE = 9;
    public static final int ACTION_STICKERS_IMAGE = 10;

    private Unbinder mBind;
    @BindView(R.id.edit_iv)
    ImageView mEditIv;

    @BindView(R.id.delete_iv)
    ImageView mDeleteIv;

    @BindView(R.id.camera_iv)
    ImageView mCameraIv;

    @BindView(R.id.upload_iv)
    ImageView mUploadIv;

    @BindView(R.id.photo_scanner)
    ImageView mPhotoIv;

    @BindView(R.id.back_container)
    View mBackBtn;

    @BindView(R.id.share_container)
    View mShareBtn;


    private PhotoListPresenter mPhotoListPresenter;

    private String mEditPath;
    private Uri photoURI = null;
    private View mTakenPhoto;//拍摄照片用于编辑
    private Bitmap mainBitmap;
    private int imageWidth, imageHeight;//
    private PhotoItem mPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_image);
        mPhoto = (PhotoItem) getIntent().getSerializableExtra("photo");
        mBind = ButterKnife.bind(this);
        mEditPath = mPhoto.getPath();
        Glide.with(this).load(mEditPath).into(mPhotoIv);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }


        initListener();
        initPresenter();

    }

    private void initPresenter() {
        mPhotoListPresenter = PhotoListPresenter.getInstance();
        mPhotoListPresenter.registerCallback(this);
    }

    private void initListener() {
        mEditIv.setOnClickListener(this);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mShareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LocalImageActivity.this, "你点击了分享按钮", Toast.LENGTH_SHORT).show();
            }
        });
        mDeleteIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean success = FileUtils.deleteFileNoThrow(mPhoto.getPath());
                if (success) {
                    Toast.makeText(LocalImageActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                    finish();
                }{
                    Toast.makeText(LocalImageActivity.this, "删除失败", Toast.LENGTH_SHORT).show();

                }
            }
        });

        mUploadIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LocalImageActivity.this, "正在上传...", Toast.LENGTH_LONG).show();
                if (mPhotoListPresenter != null) {
                    mPhotoListPresenter.upload(mPhoto);
                }
            }
        });

        mCameraIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhotoClick();
                Toast.makeText(LocalImageActivity.this, "你上传了拍照按钮", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 拍摄照片
     */
    protected void takePhotoClick() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestTakePhotoPermissions();
        } else {
            doTakePhoto();
        }//end if
    }

    /**
     * 请求拍照权限
     */
    private void requestTakePhotoPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSON_CAMERA);
            return;
        }
        doTakePhoto();
    }
    /**
     * 拍摄照片
     */
    private void doTakePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = FileUtils.genEditFile();
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = Uri.fromFile(photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, TAKE_PHOTO_CODE);
            }

            //startActivityForResult(takePictureIntent, TAKE_PHOTO_CODE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
        if (mPhotoListPresenter != null) {
            mPhotoListPresenter.unRegisterCallback(this);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_iv:
                editImageClick();
                break;

        }
    }

    /**
     * 编辑选择的图片
     *
     * @author panyi
     */
    private void editImageClick() {
        File outputFile = FileUtils.genEditFile();
        EditImageActivity.start(this,mEditPath,outputFile.getAbsolutePath(),ACTION_REQUEST_EDITIMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // System.out.println("RESULT_OK");
            switch (requestCode) {
                case TAKE_PHOTO_CODE://拍照返回
                    handleTakePhoto(data);
                    break;
                case ACTION_REQUEST_EDITIMAGE://
                    handleEditorImage(data);
                    break;
            }// end switch
        }
    }


    /**
     * 处理拍照返回
     *
     * @param data
     */
    private void handleTakePhoto(Intent data) {
        if (photoURI != null) {//拍摄成功
            mEditPath = photoURI.getPath();
            startLoadTask();
        }
    }

    private void startLoadTask() {
        LoadImageTask task = new LoadImageTask();
        task.execute(mEditPath);
    }
    private void handleEditorImage(Intent data) {
        String newFilePath = data.getStringExtra(EditImageActivity.EXTRA_OUTPUT);
        boolean isImageEdit = data.getBooleanExtra(EditImageActivity.IMAGE_IS_EDIT, false);

        if (isImageEdit){
            Toast.makeText(this, getString(R.string.save_path, newFilePath), Toast.LENGTH_LONG).show();
        }else{//未编辑  还是用原来的图片
            newFilePath = data.getStringExtra(EditImageActivity.FILE_PATH);
        }
        //System.out.println("newFilePath---->" + newFilePath);
        //File file = new File(newFilePath);
        //System.out.println("newFilePath size ---->" + (file.length() / 1024)+"KB");
        Log.d("image is edit", isImageEdit + "");
        LoadImageTask loadTask = new LoadImageTask();
        loadTask.execute(newFilePath);



    }

    @Override
    public void onPhotoListLoad(List<PhotoResult.DataBean> photoList) {

    }

    @Override
    public void onSwitchViewWay() {

    }

    @Override
    public void onNext(PhotoResult.DataBean photo) {

    }

    @Override
    public void onPre(PhotoResult.DataBean photo) {

    }

    @Override
    public void onUpload() {

    }

    @Override
    public void onDownload() {

    }

    @Override
    public void onError() {

    }

    @Override
    public void onEmpty() {

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onLoading() {

    }


    private final class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            return BitmapUtils.getSampledBitmap(params[0], imageWidth / 4, imageHeight / 4);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        protected void onCancelled(Bitmap result) {
            super.onCancelled(result);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            if (mainBitmap != null) {
                mainBitmap.recycle();
                mainBitmap = null;
                System.gc();
            }
            mainBitmap = result;
            mPhotoIv.setImageBitmap(mainBitmap);
        }
    }// end inner class

}
