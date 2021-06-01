package com.example.cloudview.ui.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cloudview.R;
import com.example.cloudview.base.BaseApplication;
import com.example.cloudview.model.PhotoResult;
import com.example.cloudview.model.bean.PhotoItem;
import com.example.cloudview.presenter.Impl.PhotoListPresenter;
import com.example.cloudview.ui.adapter.ImagePagerAdapter;
import com.example.cloudview.utils.Classifier;
import com.example.cloudview.utils.FileUtils;
import com.example.cloudview.utils.LogUtil;
import com.example.cloudview.utils.TensorFlowImageClassifier;
import com.example.cloudview.view.IPhotoListCallback;
import com.xinlan.imageeditlibrary.editimage.EditImageActivity;
import com.xinlan.imageeditlibrary.editimage.utils.BitmapUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LocalImageActivity extends AppCompatActivity implements View.OnClickListener, IPhotoListCallback, ImagePagerAdapter.OnPagerChangerListener {
    public static final int REQUEST_PERMISSON_SORAGE = 1;
    public static final int REQUEST_PERMISSON_CAMERA = 2;

    public static final int SELECT_GALLERY_IMAGE_CODE = 7;
    public static final int TAKE_PHOTO_CODE = 8;
    public static final int ACTION_REQUEST_EDITIMAGE = 9;
    public static final int ACTION_STICKERS_IMAGE = 10;


    private static final String MODEL_PATH = "model.tflite";
    private static final boolean QUANT = true;
    private static final String LABEL_PATH = "label.txt";
    private static final int INPUT_SIZE = 224;
    private static final int REQUEST_CODE_CHOOSE = 1;

    private String mGroupText = "未分类";

    private Classifier classifier;

    private Executor executor = Executors.newSingleThreadExecutor();

    private Map<String,Integer> numMap = new HashMap<>();


    private Unbinder mBind;


    @BindView(R.id.group_tv)
    TextView mGroupTv;

    @BindView(R.id.edit_iv)
    ImageView mEditIv;

    @BindView(R.id.camera_iv)
    ImageView mCarmeraIv;

    @BindView(R.id.class_iv)
    ImageView mClassifyIv;

    @BindView(R.id.delete_iv)
    ImageView mDeleteIv;


    @BindView(R.id.upload_iv)
    ImageView mUploadIv;

    @BindView(R.id.photo_scanner)
    ViewPager mViewPager;

    @BindView(R.id.back_container)
    View mBackBtn;

    @BindView(R.id.share_container)
    View mShareBtn;



    private PhotoListPresenter mPhotoListPresenter;

    private String mEditPath;
    private Uri photoURI = null;
    private Bitmap mainBitmap;
    private int imageWidth, imageHeight;//
    private List<PhotoItem> mPhotos = new ArrayList<>();
    //viewpager浏览照片的起始位置
    private int mPosition = 0;
    private ImagePagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_image);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        imageWidth = metrics.widthPixels;
        imageHeight = metrics.heightPixels;
        mPhotos = (List<PhotoItem>) getIntent().getSerializableExtra("photos");
        mPosition = (int) getIntent().getIntExtra("position",0);
        mBind = ButterKnife.bind(this);
        // TODO: 2020/6/9
        mPagerAdapter = new ImagePagerAdapter();
        mPagerAdapter.setData(mPhotos);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(mPosition);
        mEditPath = mPhotos.get(mPosition).getPath();
//        Glide.with(this).load(mEditPath).into(mPhotoIv);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }

        numMap.put("未分类",0);
        numMap.put("人物",1);
        numMap.put("建筑",2);
        numMap.put("车",3);
        numMap.put("证件",4);
        numMap.put("花卉",5);
        numMap.put("食物",6);
        numMap.put("宠物",7);
        numMap.put("风景",8);
        numMap.put("截图",9);
        initListener();
        initPresenter();
        mPagerAdapter.setListener(this);
        initTensorFlowAndLoadModel();
    }

    @Override
    public void onPagerChange() {
        mPosition = mViewPager.getCurrentItem();
        classify();
//        Toast.makeText(this, "当前位置："+mPosition, Toast.LENGTH_SHORT).show();
    }

    private void classify() {
        String path = mPhotos.get(mPosition).getPath();
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeFile(path,getBitmapOption(2));
//                Toast.makeText(LocalImageActivity.this, path, Toast.LENGTH_LONG).show();
        Log.d("LocalImageActivity","绝对路径:"+path);

        bitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);
        final List<Classifier.Recognition> results = classifier.recognizeImage(bitmap);
        Map<String,String> map = new HashMap();
        map.put("food","食物");
        map.put("car","车");
        map.put("building","建筑");
        map.put("certification","证件");
        map.put("scenery","风景");
        map.put("pet","宠物");
        map.put("person","人物");
        map.put("flower","花卉");
        if (!results.isEmpty()) {
            mGroupText = map.get(results.get(0).getTitle());
        }
        if(path.contains("Screenshot"))
            mGroupText = "截图";
        mGroupTv.setText(mGroupText);
    }

    private void initPresenter() {
        mPhotoListPresenter = PhotoListPresenter.getInstance();
        mPhotoListPresenter.registerCallback(this);
    }


    private void initListener() {
        mEditIv.setOnClickListener(this);
        mCarmeraIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhotoClick();
            }
        });
        mGroupTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseGroup();
            }
        });
        mClassifyIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseGroup();
            }
        });
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
                // TODO: 2020/6/9  
                boolean success = FileUtils.deleteFileNoThrow(mPhotos.get(mPosition).getPath());
                getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MediaStore.Images.Media.DATA + "=?", new String[]{mPhotos.get(mPosition).getPath()});//删除系统缩略图
                confirmDelete();

//                    finish();

            }
        });

        mUploadIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPhotoListPresenter != null) {
                    confirmUpdate();

                }
            }
        });

    }

    private void chooseGroup() {
        final String[] items = new String[]{"未分类","建筑","车","证件","花卉","食物","宠物","风景", "人物","截图"};//创建item
        AlertDialog alertDialog = new AlertDialog.Builder(LocalImageActivity.this)
                .setTitle("选择排列顺序")
                .setItems(items, new DialogInterface.OnClickListener() {//添加列表
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                mGroupText = "未分类";
                                // TODO: 2021/5/10 更新上传时的分类属性
                                break;
                            case 1:
                                mGroupText = "建筑";
                                break;
                            case 2:
                                mGroupText = "车";
                                break;
                            case 3:
                                mGroupText = "证件";
                                break;
                            case 4:
                                mGroupText = "花卉";
                                break;
                            case 5:
                                mGroupText = "食物";
                                break;
                            case 6:
                                mGroupText = "宠物";
                                break;
                            case 7:
                                mGroupText = "风景";
                                break;
                            case 8:
                                mGroupText = "人物";
                                break;
                            case 9:
                                mGroupText = "截图";
                                break;
                        }
                        mGroupTv.setText(mGroupText);
                    }
                })
                .create();
        alertDialog.show();
    }

    private BitmapFactory.Options getBitmapOption(int inSampleSize){
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;
        return options;
    }

    /**
     * 根据路径 转bitmap
     * @param urlpath
     * @return
     */
    public static Bitmap getBitMBitmap(String urlpath) {

        Bitmap map = null;
        try {
            URL url = new URL(urlpath);
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream in;
            in = conn.getInputStream();
            map = BitmapFactory.decodeStream(in);
            // TODO Auto-generated catch block
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }


    private void handleDelete() {
        mPhotos.remove(mPosition);
        if (mPhotos.size() - 1 > mPosition) {
            mPosition = mPosition + 1;
        }
        mPagerAdapter.setData(mPhotos);
        mViewPager.setCurrentItem(mPosition);
        Toast.makeText(LocalImageActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
    }

    private void confirmDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LocalImageActivity.this);
        builder.setMessage("确定要删除吗？");
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                handleDelete();
                dialog.dismiss();
            }
        });
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
    }

    private void confirmUpdate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LocalImageActivity.this);
        builder.setMessage("确定以'"+mGroupText+"'分类上传吗？");
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                handleUpload();
                dialog.dismiss();
            }
        });
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
    }



    private void handleUpload() {
        mPhotoListPresenter.upload(mPhotos.get(mPosition),numMap.get(mGroupText));
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

    /** * @param uri：图片的本地url地址 * @return Bitmap； */
    private Bitmap decodeUriAsBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
        if (mPhotoListPresenter != null) {
            mPhotoListPresenter.unRegisterCallback(this);
        }
        executor.execute(new Runnable() {
            @Override
            public void run() {
                classifier.close();
            }
        });
    }

    private void initTensorFlowAndLoadModel() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    classifier = TensorFlowImageClassifier.create(
                            getAssets(),
                            MODEL_PATH,
                            LABEL_PATH,
                            INPUT_SIZE,
                            QUANT);
                    makeButtonVisible();
                } catch (final Exception e) {
                    throw new RuntimeException("Error initializing TensorFlow!", e);
                }
            }
        });
    }

    private void makeButtonVisible() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mClassifyIv.setVisibility(View.VISIBLE);
            }
        });
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
            SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd_HHmmss");
            PhotoItem photoItem = new PhotoItem();
            photoItem.setName("IMG_"+sdf.format(new Date())
                    + ".png");
            photoItem.setPath(mEditPath);
            mPhotos.add(photoItem);
            mPagerAdapter.setData(mPhotos);
            mViewPager.setCurrentItem(mPhotos.size()-1);
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
            mEditPath = photoURI.getPath();
            SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd_HHmmss");
            PhotoItem photoItem = new PhotoItem();
            photoItem.setName("IMG_"+sdf.format(new Date())
                    + ".png");
            photoItem.setPath(newFilePath);
            mPhotos.add(photoItem);
            mPagerAdapter.setData(mPhotos);
            mViewPager.setCurrentItem(mPhotos.size()-1);
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
    public void onUpload(Boolean isSuccess) {
        Toast.makeText(this, "上传成功", Toast.LENGTH_SHORT).show();
        mPhotoListPresenter.getPhotoListByUserId(BaseApplication.getUser().getId());
        LogUtil.d(LocalImageActivity.this,"upload callback");
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
//            mPhotoIv.setImageBitmap(mainBitmap);
        }
    }// end inner class

}
