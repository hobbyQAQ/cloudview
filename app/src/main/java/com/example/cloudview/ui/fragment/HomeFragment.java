package com.example.cloudview.ui.fragment;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloudview.R;
import com.example.cloudview.base.BaseFragment;
import com.example.cloudview.model.PhotoResult;
import com.example.cloudview.presenter.IPhotoListPresenter;
import com.example.cloudview.presenter.Impl.PhotoListPresenter;
import com.example.cloudview.ui.activity.MainActivity;
import com.example.cloudview.ui.adapter.LocalAlbumAdapter;
import com.example.cloudview.ui.adapter.PhotoListAdapter;
import com.example.cloudview.utils.LogUtil;
import com.example.cloudview.view.IPhotoListCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeFragment extends BaseFragment implements IPhotoListCallback {

    private static final String TAG = "HomeFragment";
    @BindView(R.id.list_local_photo_album)
    public RecyclerView mLocalList;

    @BindView(R.id.photo_scan_reyclerview)
    public RecyclerView mPhotoList;

    private List<String> mUrls = new ArrayList<>();
    private PhotoListPresenter mPhotoListPresenter;
    private PhotoListAdapter mPhotoListAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mUrls = ((MainActivity) context).getData();
        Log.d(TAG, "mUrls size == " + mUrls.size());
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
    protected void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        LocalAlbumAdapter localAlbumAdapter = new LocalAlbumAdapter();
        mLocalList.setAdapter(localAlbumAdapter);
        mLocalList.setLayoutManager(layoutManager);
        if (mUrls != null) {
            localAlbumAdapter.setData(mUrls);
        }
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        layoutManager1.setOrientation(RecyclerView.VERTICAL);
        mPhotoList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = 8;
                outRect.bottom = 8;
            }
        });
        mPhotoList.setLayoutManager(layoutManager1);
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
        mPhotoListPresenter.getPhotoListByUserId(1);
        switchUIByPageState(PageState.SUCCESS);
    }

    @Override
    public void onError() {
        LogUtil.d(HomeFragment.this,"onError...");
            switchUIByPageState(PageState.ERROR);
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
        LogUtil.d(HomeFragment.this,"onLoading...");
        switchUIByPageState(PageState.LOADING);
    }

    @Override
    public void onPhotoListLoad(List<PhotoResult.DataBean> photoList) {
        LogUtil.d(HomeFragment.this,"onPhotoListLoad...");
        //给适配器设置数据
        mPhotoListAdapter.setData(photoList);
        switchUIByPageState(PageState.SUCCESS);
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
