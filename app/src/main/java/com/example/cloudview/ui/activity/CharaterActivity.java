package com.example.cloudview.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cloudview.R;
import com.example.cloudview.model.PhotoResult;
import com.example.cloudview.model.SortFaceResult;
import com.example.cloudview.presenter.Impl.PhotoListPresenter;
import com.example.cloudview.ui.adapter.PhotoListAdapter;
import com.example.cloudview.utils.DateUtils;
import com.example.cloudview.utils.LogUtil;
import com.example.cloudview.utils.UrlUtils;
import com.example.cloudview.view.IPhotoListCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class CharaterActivity extends AppCompatActivity implements IPhotoListCallback {

    private SortFaceResult.DataBean mCharater;

    private List<PhotoResult> mPhotoResults = new ArrayList<>();
    private Unbinder mBind;
    private PhotoListPresenter mPhotoListPresenter;
    private PhotoListAdapter mPhotoListAdapter;

    @BindView(R.id.back_container)
    LinearLayout mBackBtn;

    @BindView(R.id.charater_photo_list)
    RecyclerView mListRv;

    @BindView(R.id.face_cover)
    CircleImageView faceCover;
    private int mCid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_charater);
        mBind = ButterKnife.bind(this);
        List<SortFaceResult.DataBean.FacelistBean> faces = (List<SortFaceResult.DataBean.FacelistBean>) getIntent().getSerializableExtra("faces");
        mCid = getIntent().getIntExtra("cid",1);
        String name = getIntent().getStringExtra("name");
        mCharater = new SortFaceResult.DataBean(mCid,name,faces);
        Glide.with(this).load(UrlUtils.path2Url(mCharater.getFacelist().get(0).getPath())).into(faceCover);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        layoutManager1.setOrientation(RecyclerView.VERTICAL);
        mListRv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = 8;
                outRect.bottom = 8;
            }
        });
        mListRv.setLayoutManager(layoutManager1);
        mPhotoListAdapter = new PhotoListAdapter();
        mListRv.setAdapter(mPhotoListAdapter);

        initListener();
        initPresenter();
        initData();
    }

    private void initData() {
        mPhotoListPresenter.getPhotoListByCid(mCid);
    }

    private void initListener() {
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initPresenter() {
        mPhotoListPresenter = PhotoListPresenter.getInstance();
        mPhotoListPresenter.registerCallback(this);
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
    public void onPhotoListLoad(List<PhotoResult.DataBean> photoList) {
        //给照片排下序
        DateUtils.sort(photoList);
        LogUtil.d(CharaterActivity.this,photoList.toString());
        //给适配器设置数据
        mPhotoListAdapter.setData(photoList);
        mPhotoListAdapter.notifyDataSetChanged();
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
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDownload() {

    }

    @Override
    public void onError() {
        Toast.makeText(this, "加载错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmpty() {
        Toast.makeText(this, "数据为空", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess() {
        Toast.makeText(this, "加载成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoading() {
        Toast.makeText(this, "正在加载", Toast.LENGTH_SHORT).show();
    }
}
