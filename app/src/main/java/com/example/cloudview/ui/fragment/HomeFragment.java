package com.example.cloudview.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.net.Uri;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.cloudview.R;
import com.example.cloudview.base.BaseApplication;
import com.example.cloudview.base.BaseFragment;
import com.example.cloudview.model.PhotoResult;
import com.example.cloudview.model.bean.PhotoItem;
import com.example.cloudview.presenter.Impl.PhotoListPresenter;
import com.example.cloudview.ui.activity.MainActivity;
import com.example.cloudview.ui.adapter.LocalAlbumAdapter;
import com.example.cloudview.ui.adapter.PhotoListAdapter;
import com.example.cloudview.utils.DateUtils;
import com.example.cloudview.utils.LogUtil;
import com.example.cloudview.view.IPhotoListCallback;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

import butterknife.BindView;

public class HomeFragment extends BaseFragment implements IPhotoListCallback {

    private static final String TAG = "HomeFragment";
    private static final int REQUEST_CODE_CHOOSE = 101;
    public static final int RESULT_OK           = -1;
    @BindView(R.id.list_local_photo_album)
    public RecyclerView mLocalList;

    @BindView(R.id.photo_scan_reyclerview)
    public RecyclerView mPhotoList;

    @BindView(R.id.select_upload_button)
    public Button mButton;

    @BindView(R.id.quick_upload_button)
    public Button mQuickButton;

    @BindView(R.id.change_sort_way)
    public ImageView mChangeSortWay;

    @BindView(R.id.sort_way_title)
    public TextView mTextView;

//    @BindView(R.id.photo_result_refresh_layout)
//    public TwinklingRefreshLayout mTwinklingRefreshLayout;

        @BindView(R.id.swipe_refresh)
    public SwipeRefreshLayout mSwipeRefreshLayout;

    private List<PhotoItem> mPics = new ArrayList<>();
    private PhotoListPresenter mPhotoListPresenter;
    private PhotoListAdapter mPhotoListAdapter;
    private List<Uri> mSelectedPhoto = new ArrayList<>();
    private LocalAlbumAdapter mLocalAlbumAdapter;
    private List<PhotoItem> mPhotoUploads = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    private LinearLayoutManager mLayoutManager1;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //获取选好的图片数据
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelectedPhoto = Matisse.obtainResult(data);
            Log.d("Matisse", "mSelectedPhoto: " + mSelectedPhoto);
        }
//        mTwinklingRefreshLayout.setPureScrollModeOn();
        if (mPhotoListPresenter != null) {

            mPhotoListPresenter.uploads(mSelectedPhoto);
        }
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mPics = ((MainActivity) context).getData();
    }

    @Override
    protected void onRetryClick() {
        mPhotoListPresenter.getPhotoListByUserId(1);
    }

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_home;
    }


    @Override
    protected void initListener() {
//        mTwinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
//            @Override
//            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
//                super.onLoadMore(refreshLayout);
//                handlerRefresh();
//            }
//
//        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getContext(), "更新界面", Toast.LENGTH_SHORT).show();
                handlerRefresh();

            }
        });
        mChangeSortWay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items3 = new String[]{"时间升序",  "时间降序"};//创建item
                AlertDialog alertDialog3 = new AlertDialog.Builder(getContext())
                        .setTitle("选择排列顺序")
                        .setItems(items3, new DialogInterface.OnClickListener() {//添加列表
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if( i == 0){
                                    mLayoutManager1.setReverseLayout(false);
                                    mTextView.setText("时间升序");
                                }else {
                                    mLayoutManager1.setReverseLayout(true);
                                    mTextView.setText("时间降序");
                                }
                            }
                        })
                        .create();
                alertDialog3.show();
//                if (mLayoutManager1.getReverseLayout() == true) {
//                    mLayoutManager1.setReverseLayout(false);
//                }else{
//                    mLayoutManager1.setReverseLayout(true);
//                }

            }
        });
        mQuickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPhotoUploads = mLocalAlbumAdapter.getSelected();
                if (mPhotoUploads.size() == 0) {
                    Toast.makeText(getContext(), "长按本地图片选择上传", Toast.LENGTH_SHORT).show();
                }
                for (PhotoItem photoUpload : mPhotoUploads) {
                    mSelectedPhoto.contains(new File(photoUpload.getPath()).toURI());
                }
                if (mPhotoListPresenter != null) {
                    mPhotoListPresenter.uploads(mSelectedPhoto);
                }
            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "你点击了选择上传", Toast.LENGTH_SHORT).show();
                Matisse.from(HomeFragment.this)
                        .choose(MimeType.ofImage())
                        .countable(true)
                        .maxSelectable(1)
                        .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new GlideEngine())
                        .showPreview(false) // Default is `true`
                        .forResult(REQUEST_CODE_CHOOSE);
            }
        });
    }

    private void handlerRefresh() {
        mPhotoListPresenter.getPhotoListByUserId(BaseApplication.getUser().getId());
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        },500);
    }





    private int  backtouch = 0;

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_BACK){
                    backtouch ++;
                    if(backtouch == 2){
                        getActivity().finish();
                    }else{
                        Toast.makeText(getActivity(), "再按一次退出", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void initView() {
        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mLocalAlbumAdapter = new LocalAlbumAdapter();
        mLocalList.setAdapter(mLocalAlbumAdapter);
        mLocalList.setLayoutManager(mLayoutManager);
        //解决每9个Item就会重复显示状态问题
        mLocalList.setItemViewCacheSize(500);

        if (mPics != null) {
            mLocalAlbumAdapter.setData(mPics);
        }
        mLayoutManager1 = new LinearLayoutManager(getContext());
        mLayoutManager1.setOrientation(RecyclerView.VERTICAL);
        mPhotoList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = 8;
                outRect.bottom = 8;
            }
        });
        mPhotoList.setLayoutManager(mLayoutManager1);
        mPhotoListAdapter = new PhotoListAdapter();
        mPhotoList.setAdapter(mPhotoListAdapter);

    }

    @Override
    protected void initPresenter() {
        super.initPresenter();
        mPhotoListPresenter = PhotoListPresenter.getInstance();
        mPhotoListPresenter.registerCallback(this);
    }

    /**
     * 加载数据
     */
    @Override
    protected void loadData() {
        //目前用一号用户做测试
        mPhotoListPresenter.getPhotoListByUserId(BaseApplication.getUser().getId());
        switchUIByPageState(PageState.SUCCESS);
    }

    @Override
    public void onError() {
        LogUtil.d(HomeFragment.this,"onError...");
//            switchUIByPageState(PageState.ERROR);
        Toast.makeText(this.getActivity().getBaseContext(), "网络似乎没有连接o(╥﹏╥)o", Toast.LENGTH_LONG).show();

    }



    @Override
    public void onEmpty() {
        LogUtil.d(HomeFragment.this,"onEmpty...");
        switchUIByPageState(PageState.EMPTY);
    }

    @Override
    public void onSuccess() {
        LogUtil.d(HomeFragment.this,"onSuccess...");
        switchUIByPageState(PageState.SUCCESS);
    }

    @Override
    public void onLoading() {
        LogUtil.d(HomeFragment.this,"Loading...");
        switchUIByPageState(PageState.LOADING);
    }

    @Override
    public void onPhotoListLoad(List<PhotoResult.DataBean> photoList) {
        LogUtil.d(HomeFragment.this,"PhotoListLoad callback");
        //给照片排下序,treemap自动排序了
        DateUtils.sort(photoList);
        LogUtil.d(HomeFragment.this,photoList.toString());
        //给适配器设置数据
        mPhotoListAdapter.setData(photoList);
        switchUIByPageState(PageState.SUCCESS);
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
        Log.d("HomeFragment","upload callback");
        //通知界面更新
//        handlerRefresh();

        Log.d("HomeFragment","已通知界面更新");
    }

    @Override
    public void onDownload() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        release();
    }

    @Override
    protected void release() {
        if (mPhotoListPresenter != null) {
            mPhotoListPresenter.unRegisterCallback(this);
        }
    }

}
