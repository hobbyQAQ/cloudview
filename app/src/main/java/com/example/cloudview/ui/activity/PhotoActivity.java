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

import com.example.cloudview.R;
import com.example.cloudview.model.PhotoResult;
import com.example.cloudview.presenter.Impl.PhotoListPresenter;
import com.example.cloudview.ui.adapter.PhotoPagerAdapter;
import com.example.cloudview.view.IPhotoListCallback;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PhotoActivity  extends AppCompatActivity implements IPhotoListCallback {

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
        mPhotoPager.setAdapter(mPhotoPagerAdapter);
        mPhotoPager.setCurrentItem(mPosition);
        initListener();
        mPhotoListPresenter = PhotoListPresenter.getInstance();
        mPhotoListPresenter.registerCallback(this);
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
                
                File picFilePath = PhotoActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                if (mPhotoListPresenter != null) {
                    mPhotoListPresenter.download(mPhotos.get(mPosition).getId(),picFilePath);
                }
                Toast.makeText(PhotoActivity.this, "正在下载", Toast.LENGTH_LONG).show();
            }
        });
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
}
