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
    @BindView(R.id.text_hint)
    TextView mTextHint;
    private PhotoSampleListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initListener();
        mAdapter = new PhotoSampleListAdapter();
        mSearchPhotoList.setAdapter(mAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
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
                switch (keyword){
                    case "我":
                        handleSearchCharater(keyword,photoService);
                        break;
                    case "妹妹":
                        handleSearchCharater(keyword,photoService);
                        break;
                    case "余福财":
                        handleSearchCharater(keyword,photoService);
                        break;
                    case "建筑":
                        handleGetBuildings(photoService);
                        break;
                    case "车":
                        handleSearchCars(photoService);
                        break;
                    case "证件":
                        handleSearchcCredit(photoService);
                        break;
                    case "花卉":
                        handleGetFlowers(photoService);
                    case "花":
                        handleGetFlowers(photoService);
                        break;
                    case "食物":
                        handleSearchFoods(photoService);
                    case "小吃":
                        handleSearchFoods(photoService);
                        break;
                    case "截图":
                        handleSearchScreenshots(photoService);
                        break;
                    case "宠物":
                        handleSearchPets(photoService);
                        break;
                    case "猫":
                        handleSearchPets(photoService);
                        break;
                    case "狗":
                        handleSearchPets(photoService);
                        break;
                    case "风景":
                        handleSearchScenery(photoService);
                        break;
                    default:
                        handleSearch(keyword,photoService);

                }

            }
        });
    }

    private void handleGetBuildings(PhotoService photoService) {
        Call<PhotoResult> task = photoService.getBuilding( BaseApplication.getUser().getId());
        doTask("建筑", task);
    }

    private void handleSearchCars(PhotoService photoService) {
        Call<PhotoResult> task = photoService.getCar( BaseApplication.getUser().getId());
        doTask("车", task);
    }

    private void handleGetFlowers(PhotoService photoService) {
        Call<PhotoResult> task = photoService.getFlower( BaseApplication.getUser().getId());
        doTask("花卉", task);
    }

    private void handleSearchFoods(PhotoService photoService) {
        Call<PhotoResult> task = photoService.getFood( BaseApplication.getUser().getId());
        doTask("美食", task);
    }


    private void handleSearchScreenshots(PhotoService photoService) {
        Call<PhotoResult> task = photoService.getScreenshot( BaseApplication.getUser().getId());
        doTask("截图", task);
    }

    private void handleSearchPets(PhotoService photoService) {
        Call<PhotoResult> task = photoService.getPet( BaseApplication.getUser().getId());
        doTask("宠物", task);
    }

    private void handleSearch(String keyword, PhotoService photoService) {
        Call<PhotoResult> task = photoService.getPhotoListByKeyword(keyword, BaseApplication.getUser().getId());
        doTask(keyword, task);
    }

    private void doTask(String keyword, Call<PhotoResult> task) {
        task.enqueue(new Callback<PhotoResult>() {
            @Override
            public void onResponse(Call<PhotoResult> call, Response<PhotoResult> response) {
                PhotoResult result = response.body();
                List<PhotoResult.DataBean> data = result.getData();
                if (data != null) {
                    mAdapter.setData(data);
                    mTextHint.setText("关键词为"+"'"+keyword+"'"+"的搜索结果为如下");
                    mTextHint.setVisibility(View.VISIBLE);
                    LogUtil.d(SearchActivity.this, "date === " + data.toString());
                } else {
                    Toast.makeText(SearchActivity.this, "搜索结果为空", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<PhotoResult> call, Throwable t) {
                LogUtil.d(SearchActivity.this, "网络请求失败");
            }
        });
    }


    private void handleSearchScenery(PhotoService photoService) {
        Call<PhotoResult> task = photoService.getScenery( BaseApplication.getUser().getId());
        doTask("风景", task);
    }

    private void handleSearchcCredit(PhotoService photoService) {

        Call<PhotoResult> task = photoService.getCertificates( BaseApplication.getUser().getId());
        doTask("证件", task);
    }

    private void handleGetAninals(PhotoService photoService) {
        Call<PhotoResult> task = photoService.getPet( BaseApplication.getUser().getId());
        doTask("动物", task);
    }

    private void handleSearchCharater(String keyword, PhotoService photoService) {
        int cid = 1;
        if(keyword.equals("妹妹")){
            cid = 2;
        }
        if(keyword.equals("我兄弟")){
            cid = 3;
        }
        Call<PhotoResult> task = photoService.getByFace( BaseApplication.getUser().getId(),1);
        doTask("我", task);
    }
}
