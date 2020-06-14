package com.example.cloudview.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloudview.Api.PhotoService;
import com.example.cloudview.R;
import com.example.cloudview.base.BaseApplication;
import com.example.cloudview.model.PhotoResult;
import com.example.cloudview.ui.adapter.PhotoSampleListAdapter;
import com.example.cloudview.utils.LogUtil;
import com.example.cloudview.utils.RetrofitCreator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.search_icon)
    ImageView mSearchIcon;
    @BindView(R.id.search_input)
    EditText mSearchInput;
    @BindView(R.id.search_input_delete)
    ImageView mSearchInputDelete;
    @BindView(R.id.search_btn)
    TextView mSearchBtn;
    @BindView(R.id.search_photo_list)
    RecyclerView mSearchPhotoList;
    @BindView(R.id.search_container)
    FrameLayout mSearchContainer;
    private PhotoSampleListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initListener();
        mAdapter = new PhotoSampleListAdapter();
        mSearchPhotoList.setAdapter(mAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this,4);
        mSearchPhotoList.setLayoutManager(layoutManager);
    }

    private void initListener() {
        mSearchInputDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchInput.setText("");
            }
        });

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //此处直接开启网络请求请求数据
                Retrofit retrofit = RetrofitCreator.getInstance().getRetrofit();
                PhotoService photoService = retrofit.create(PhotoService.class);
                String keyword = mSearchInput.getText().toString();
                Call<PhotoResult> task = photoService.getPhotoListByKeyword(keyword, BaseApplication.getUser().getId());
                task.enqueue(new Callback<PhotoResult>() {
                    @Override
                    public void onResponse(Call<PhotoResult> call, Response<PhotoResult> response) {
                        PhotoResult result = response.body();
                        List<PhotoResult.DataBean> data = result.getData();
                        if (data != null) {
                            mAdapter.setData(data);
                            LogUtil.d(SearchActivity.this,"date === " + data.toString());
                        }else{
                            Toast.makeText(SearchActivity.this, "搜索结果为空", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<PhotoResult> call, Throwable t) {
                        LogUtil.d(SearchActivity.this,"网络请求失败");
                    }
                });
            }
        });
    }
}
