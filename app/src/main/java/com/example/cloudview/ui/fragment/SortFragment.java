package com.example.cloudview.ui.fragment;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloudview.R;
import com.example.cloudview.base.BaseFragment;
import com.example.cloudview.model.FaceResult;
import com.example.cloudview.model.SortFaceResult;
import com.example.cloudview.presenter.Impl.FaceListPresneter;
import com.example.cloudview.ui.adapter.FaceListAdapter;
import com.example.cloudview.view.IFaceListCallback;

import java.util.List;

import butterknife.BindView;

public class SortFragment extends BaseFragment implements IFaceListCallback {


    @BindView(R.id.face_list)
    RecyclerView mFaceListView;
    private FaceListPresneter mFaceListPresneter;
    private FaceListAdapter mFaceListAdapter;

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_sort;
    }

    @Override
    protected void initView() {
        mFaceListAdapter = new FaceListAdapter();
        mFaceListView.setAdapter(mFaceListAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this.getContext(),3);

//        mFaceListView.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
//                outRect.top = 8;
//                outRect.bottom = 8;
//                outRect.left = 8;
//                outRect.right = 8;
//            }
//        });
        mFaceListView.setLayoutManager(layoutManager);
        switchUIByPageState(PageState.SUCCESS);
    }


    @Override
    protected void initPresenter() {
        mFaceListPresneter = FaceListPresneter.getInstance();
        mFaceListPresneter.registerCallback(this);
        super.initPresenter();
    }

    @Override
    protected void loadData() {
        mFaceListPresneter.getSortFaceByUid(1);
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

    @Override
    public void onFaceListLoad(List<SortFaceResult.DataBean> photoList) {
        mFaceListAdapter.setData(photoList);
    }
}
