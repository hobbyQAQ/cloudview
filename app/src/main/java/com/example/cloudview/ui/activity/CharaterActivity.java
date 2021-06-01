package com.example.cloudview.ui.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cloudview.Api.PhotoService;
import com.example.cloudview.R;
import com.example.cloudview.base.BaseApplication;
import com.example.cloudview.model.PhotoResult;
import com.example.cloudview.model.SimpleResult;
import com.example.cloudview.model.SortFaceResult;
import com.example.cloudview.presenter.Impl.PhotoListPresenter;
import com.example.cloudview.ui.adapter.PhotoListAdapter;
import com.example.cloudview.utils.DateUtils;
import com.example.cloudview.utils.LogUtil;
import com.example.cloudview.utils.RetrofitCreator;
import com.example.cloudview.utils.UrlUtils;
import com.example.cloudview.view.IPhotoListCallback;
import com.hb.dialog.myDialog.MyAlertInputDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CharaterActivity extends AppCompatActivity implements IPhotoListCallback {

    private static final int REQUEST_CODE = 1;
    private static final int RESULT_CODE = 101;
    @BindView(R.id.charater_text_hint)
    TextView mCharaterTextHint;
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

    @BindView(R.id.charater_name)
    TextView charaterTv;
    private int mCid;
    private List<SortFaceResult.DataBean.FacelistBean> mFaces = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_CODE) {
            //设置本页人脸封面
            int position = data.getIntExtra("position", 0);
            Glide.with(this).load(UrlUtils.path2Url(mFaces.get(position).getPath())).into(faceCover);
            //返回位置数据给人脸分类页
            Map<String, String> coverMap = BaseApplication.getCoverMap();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                coverMap.replace("" + mCid, position + "");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_charater);
        mBind = ButterKnife.bind(this);
        mFaces = (List<SortFaceResult.DataBean.FacelistBean>) getIntent().getSerializableExtra("faces");

        mCharaterTextHint.setText("一共有"+mFaces.size()+"张照片");

        mCid = getIntent().getIntExtra("cid", 1);
        String name = getIntent().getStringExtra("name");
        mCharater = new SortFaceResult.DataBean(mCid, name, mFaces);
        charaterTv.setText(mCharater.getName());
        int coverPosition = Integer.parseInt(BaseApplication.getCoverMap().get("" + mCid));
        Glide.with(this).load(UrlUtils.path2Url(mCharater.getFacelist().get(coverPosition).getPath())).into(faceCover);
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
        charaterTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MyAlertInputDialog myAlertInputDialog = new MyAlertInputDialog(CharaterActivity.this).builder()
                        .setTitle("请输入")
                        .setEditText("");
                myAlertInputDialog.setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeCharaterName(myAlertInputDialog.getResult());
                        myAlertInputDialog.dismiss();
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myAlertInputDialog.dismiss();
                    }
                });
                myAlertInputDialog.show();
            }
        });
        faceCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CharaterActivity.this, FaceActivity.class);
                intent.putExtra("faces", (Serializable) mFaces);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void changeCharaterName(String result) {
        Retrofit retrofit = RetrofitCreator.getInstance().getRetrofit();
        PhotoService photoService = retrofit.create(PhotoService.class);
        Call<SimpleResult> simpleResultCall = photoService.changeCharaterName(mCid, result);
        simpleResultCall.enqueue(new Callback<SimpleResult>() {
            @Override
            public void onResponse(Call<SimpleResult> call, Response<SimpleResult> response) {
                SimpleResult body = response.body();
                if (body.isSuccess()) {
                    charaterTv.setText(result);
                    Toast.makeText(CharaterActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(CharaterActivity.this, "更新失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SimpleResult> call, Throwable t) {
                Toast.makeText(CharaterActivity.this, "更新失败", Toast.LENGTH_SHORT).show();
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
        //给照片排下序，treemap自动排序了
        DateUtils.sort(photoList);
        LogUtil.d(CharaterActivity.this, photoList.toString());
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
    public void onUpload(Boolean isSuccess) {
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
