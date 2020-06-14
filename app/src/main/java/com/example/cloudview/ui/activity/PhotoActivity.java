package com.example.cloudview.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
    LinearLayout mshareBtn;
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
    private int mPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        mBind = ButterKnife.bind(this);
        Intent intent = this.getIntent();
        mPhotos = (List<PhotoResult.DataBean>) intent.getSerializableExtra("photos");
        mPosition = intent.getIntExtra("position",0);
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
    }

    /**
     * 初始化点击事件
     */
    private void initListener() {
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
    public void onUpload() {

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

    @Override
    public void onPagerChange() {
        int position = mPhotoPager.getCurrentItem();
        PhotoResult.DataBean dataBean = mPhotos.get(position);
        if (dataBean.getLove() == 0) {
            mLoveBtn.setImageResource(R.mipmap.love);
        }else{
            mLoveBtn.setImageResource(R.mipmap.love_check);
        }
    }
}
