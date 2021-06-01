package com.example.cloudview.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.cloudview.Api.PhotoService;
import com.example.cloudview.R;
import com.example.cloudview.base.BaseApplication;
import com.example.cloudview.model.PhotoResult;
import com.example.cloudview.model.SimpleResult;
import com.example.cloudview.presenter.Impl.PhotoListPresenter;
import com.example.cloudview.ui.adapter.PhotoPagerAdapter;
import com.example.cloudview.utils.LogUtil;
import com.example.cloudview.utils.RetrofitCreator;
import com.example.cloudview.utils.UrlUtils;
import com.example.cloudview.view.IPhotoListCallback;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PhotoActivity  extends AppCompatActivity implements IPhotoListCallback, PhotoPagerAdapter.OnPagerChangerListener {

    private Unbinder mBind;

    @BindView(R.id.photo_scanner)
    ViewPager mPhotoPager;

    @BindView(R.id.back_iv)
    LinearLayout mBackBtn;

    @BindView(R.id.share_iv)
    View mshareBtn;
    //编辑照片按钮
    @BindView(R.id.edit_iv)
    ImageView mEditBtn;
    //喜好
    @BindView(R.id.camera_iv)
    ImageView mLoveBtn;
    //删除
    @BindView(R.id.delete_iv)
    ImageView mDeleteBtn;
    //下载
    @BindView(R.id.download_iv)
    ImageView mDownloadBtn;


    private PhotoPagerAdapter mPhotoPagerAdapter;
    private PhotoListPresenter mPhotoListPresenter;
    private List<PhotoResult.DataBean> mPhotos;
    private int mPosition;//delete图片时的位置，很重要
    private ClipboardManager cm;
    private ClipData mClipData;
    boolean deleteFlag = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        mBind = ButterKnife.bind(this);
        Intent intent = this.getIntent();
        mPhotos = (List<PhotoResult.DataBean>) intent.getSerializableExtra("photos");
        Log.d("PhotoActivity","大小："+mPhotos.size());
        mPosition = intent.getIntExtra("position",0);
        mPosition = mPosition % mPhotos.size();
        Log.d("PhotoActivity","位置："+mPosition);
        mPhotoPagerAdapter = new PhotoPagerAdapter();
        if (mPhotoPagerAdapter != null) {
            mPhotoPagerAdapter.setData(mPhotos);
        }
        if (mPhotos != null) {
            if (mPhotos.get(mPhotoPager.getCurrentItem()).getLove() == 1) {
                mLoveBtn.setImageResource(R.mipmap.love_check);
            }
        }
        mPhotoPager.setAdapter(mPhotoPagerAdapter);
        mPhotoPager.setCurrentItem(mPosition);
        initListener();
        mPhotoListPresenter = PhotoListPresenter.getInstance();
        mPhotoListPresenter.registerCallback(this);

        mPhotoPagerAdapter.setListener(this);
        cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

    }

    private void confirmDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PhotoActivity.this);
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

    private void handleDelete() {
        deleteFlag = true;
        Retrofit retrofit = RetrofitCreator.getInstance().getRetrofit();
        PhotoService photoService = retrofit.create(PhotoService.class);
        Call<SimpleResult> delete = photoService.delete(mPhotos.get(mPosition).getId(), BaseApplication.getUser().getId());
        delete.enqueue(new Callback<SimpleResult>() {
            @Override
            public void onResponse(Call<SimpleResult> call, Response<SimpleResult> response) {

                SimpleResult body = response.body();
                Log.d("handleDelete",body.toString());
                if (body.isSuccess()) {
                    Toast.makeText(PhotoActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                    mPhotoListPresenter.getPhotoListByUserId(BaseApplication.getUser().getId());
                    finish();
                }else{
                    Toast.makeText(PhotoActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SimpleResult> call, Throwable t) {
                Toast.makeText(PhotoActivity.this, "删除失败，网络请求错误", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 初始化点击事件
     */
    private void initListener() {

        mshareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleShare();
            }
        });

        mDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDelete();
            }
        });
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //下载
        mDownloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //执行层操作，执行层控制UI层工作
                
                File picFilePath = new File("/storage/emulated/0/Pictures/cloudview");
                if (mPhotoListPresenter != null) {
                    mPhotoListPresenter.download(mPhotos.get(mPosition).getId(),picFilePath);
                }
                Toast.makeText(PhotoActivity.this, "正在下载", Toast.LENGTH_LONG).show();
            }
        });
        mLoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Retrofit retrofit = RetrofitCreator.getInstance().getRetrofit();
                PhotoService photoService = retrofit.create(PhotoService.class);
                Call<SimpleResult> task = photoService.addLove(mPhotos.get(mPhotoPager.getCurrentItem()).getId(), BaseApplication.getUser().getId());
                task.enqueue(new Callback<SimpleResult>() {
                    @Override
                    public void onResponse(Call<SimpleResult> call, Response<SimpleResult> response) {
                        SimpleResult body = response.body();
                        LogUtil.d(PhotoActivity.this,body.toString());
                        if (body != null) {
                            Toast.makeText(PhotoActivity.this, body.getMsg(), Toast.LENGTH_SHORT).show();
                            handleAddLoveSuccess();
                        }else{
                            Toast.makeText(PhotoActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SimpleResult> call, Throwable t) {
                        Toast.makeText(PhotoActivity.this, "请求失败，网络错误", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void handleShare() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PhotoActivity.this);
        String url = UrlUtils.path2Url(mPhotos.get(mPosition).getPath());
        builder.setMessage(url);
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "复制Url到剪切板", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mClipData = ClipData.newPlainText("Label", url);
                cm.setPrimaryClip(mClipData);
                dialog.dismiss();
                Toast.makeText(PhotoActivity.this, "复制成功", Toast.LENGTH_SHORT).show();
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

    private void handleAddLoveSuccess() {
        PhotoResult.DataBean photo = mPhotos.get(mPhotoPager.getCurrentItem());
        if (photo.getLove() == 0) {
            photo.setLove(1);
            mLoveBtn.setImageResource(R.mipmap.love_check);
        }else{
            photo.setLove(0);
            mLoveBtn.setImageResource(R.mipmap.love);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBind != null) {
            mBind.unbind();
        }
        if (mPhotoListPresenter != null) {
            mPhotoListPresenter.unRegisterCallback(this);
        }
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

    }

    @Override
    public void onDownload() {

    }

    @Override
    public void onError() {
        Toast.makeText(this, "网络似乎没有连接o(╥﹏╥)o", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmpty() {
        Toast.makeText(this, "网盘似乎没有数据哦亲ᕦ(･ㅂ･)ᕤ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess() {
        Toast.makeText(this, "加载成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoading() {

    }

    /**
     *  PhotoPagerAdapter.OnPagerChangerListener接口的方法，主要实现当界面切换时的事件响应
     */
    @Override
    public void onPagerChange() {
        int position = mPhotoPager.getCurrentItem();
        if (deleteFlag) {
            mPosition = position;
            deleteFlag = false;
        }
//        Toast.makeText(this, "当前位置："+position, Toast.LENGTH_SHORT).show();
        // TODO: 2021/4/22 滑动的时候，喜欢变化的很僵硬，像是误触一样怎么解决
        PhotoResult.DataBean dataBean = mPhotos.get(position);
        if (dataBean.getLove() == 0) {
            mLoveBtn.setImageResource(R.mipmap.love);
        }else{
            mLoveBtn.setImageResource(R.mipmap.love_check);
        }
    }
}
