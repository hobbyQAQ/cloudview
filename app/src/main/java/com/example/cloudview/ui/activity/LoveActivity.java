package com.example.cloudview.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloudview.Api.PhotoService;
import com.example.cloudview.R;
import com.example.cloudview.base.BaseApplication;
import com.example.cloudview.model.PhotoResult;
import com.example.cloudview.ui.adapter.PhotoSampleListAdapter;
import com.example.cloudview.ui.fragment.MineFragment;
import com.example.cloudview.utils.LogUtil;
import com.example.cloudview.utils.RetrofitCreator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoveActivity extends AppCompatActivity {

    @BindView(R.id.love_photo_list)
    RecyclerView mLoveList;
    private PhotoSampleListAdapter mAdapter;
    private List<PhotoResult.DataBean> mPhotos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love);
        initData();
        ButterKnife.bind(this);
        mAdapter = new PhotoSampleListAdapter();
        mLoveList.setAdapter(mAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this,4);
        mLoveList.setLayoutManager(layoutManager);


    }

    private void initData() {
        Retrofit retrofit = RetrofitCreator.getInstance().getRetrofit();
        PhotoService photoService = retrofit.create(PhotoService.class);
        Call<PhotoResult> task = photoService.getLoves(BaseApplication.getUser().getId());
        task.enqueue(new Callback<PhotoResult>() {
            @Override
            public void onResponse(Call<PhotoResult> call, Response<PhotoResult> response) {
                List<PhotoResult.DataBean> data = response.body().getData();
                if (data != null) {
                    LogUtil.d(LoveActivity.this,"data === "+data);
                    mPhotos = data;
                    mAdapter.setData(mPhotos);
                }else{
                    Toast.makeText(LoveActivity.this, "您还没有添加任何收藏呢", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PhotoResult> call, Throwable t) {
                LogUtil.d(LoveActivity.this,"错误信息： "+t.getMessage());
            }
        });
    }

    public void onBackHandle(View view) {
        finish();
    }
}
