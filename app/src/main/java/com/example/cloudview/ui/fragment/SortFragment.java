package com.example.cloudview.ui.fragment;

import android.content.Intent;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.cloudview.Api.PhotoService;
import com.example.cloudview.R;
import com.example.cloudview.base.BaseApplication;
import com.example.cloudview.base.BaseFragment;
import com.example.cloudview.model.PhotoResult;
import com.example.cloudview.model.SortFaceResult;
import com.example.cloudview.presenter.Impl.FaceListPresneter;
import com.example.cloudview.ui.activity.LoveActivity;
import com.example.cloudview.ui.adapter.FaceListAdapter;
import com.example.cloudview.utils.LogUtil;
import com.example.cloudview.utils.RetrofitCreator;
import com.example.cloudview.utils.UrlUtils;
import com.example.cloudview.view.IFaceListCallback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SortFragment extends BaseFragment implements IFaceListCallback {


    @BindView(R.id.face_list)
    RecyclerView mFaceListView;


    @BindView(R.id.scenery_cover)
    ImageView mSceneryCover;

    @BindView(R.id.pet_cover)
    ImageView mAnimalCover;

    @BindView(R.id.certification_cover)
    ImageView mCreditCover;

    @BindView(R.id.building_cover)
    ImageView mBuildingCover;

    @BindView(R.id.car_cover)
    ImageView mCarCover;

    @BindView(R.id.flower_cover)
    ImageView mFlowerCover;

    @BindView(R.id.person_cover)
    ImageView mPersonCover;

    @BindView(R.id.food_cover)
    ImageView mFoodCover;

    @BindView(R.id.screenshot_cover)
    ImageView mScreenshotCover;

    @BindView(R.id.button1)
    ImageView mButton1;

    @BindView(R.id.button2)
    ImageView mButton2;

    @BindView(R.id.button3)
    ImageView mButton3;

    @BindView(R.id.swipe_refresh2)
    public SwipeRefreshLayout mSwipeRefreshLayout;


    private FaceListPresneter mFaceListPresneter;
    private FaceListAdapter mFaceListAdapter;
    private List<PhotoResult.DataBean> mScenerys = new ArrayList<>();
    private List<PhotoResult.DataBean> mAnimals = new ArrayList<>();
    private List<PhotoResult.DataBean> mCredits = new ArrayList<>();
    private List<PhotoResult.DataBean> mPersons = new ArrayList<>();
    private List<PhotoResult.DataBean> mCars = new ArrayList<>();
    private List<PhotoResult.DataBean> mBuildings = new ArrayList<>();
    private List<PhotoResult.DataBean> mFlowers = new ArrayList<>();
    private List<PhotoResult.DataBean> mFoods = new ArrayList<>();
    private List<PhotoResult.DataBean> mScreenshots = new ArrayList<>();

    private List<PhotoResult.DataBean> mLocation1 = new ArrayList<>();
    private List<PhotoResult.DataBean> mLocation2 = new ArrayList<>();
    private List<PhotoResult.DataBean> mLocation3 = new ArrayList<>();

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_sort;
    }

    @Override
    protected void initView() {
        mFaceListAdapter = new FaceListAdapter();
        mFaceListView.setAdapter(mFaceListAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this.getContext(),3);
        mFaceListView.setLayoutManager(layoutManager);
        switchUIByPageState(PageState.SUCCESS);
    }


    @Override
    protected void initListener() {

        mSceneryCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoveActivity.class);
                if (mScenerys != null) {
                    intent.putExtra("photos",(Serializable) mScenerys);
                    intent.putExtra("title","风景");
                }else{
                    Toast.makeText(getContext(), "空空如也", Toast.LENGTH_SHORT).show();
                }
                startActivity(intent);
            }
        });

        mAnimalCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoveActivity.class);
                if (mAnimals != null) {
                    intent.putExtra("photos",(Serializable) mAnimals);
                    intent.putExtra("title","动物");
                }else{
                    Toast.makeText(getContext(), "空空如也", Toast.LENGTH_SHORT).show();
                }
                startActivity(intent);
            }
        });

        mCreditCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoveActivity.class);
                if (mCredits != null) {
                    intent.putExtra("photos",(Serializable) mCredits);
                    intent.putExtra("title","证件");
                }else{
                    Toast.makeText(getContext(), "空空如也", Toast.LENGTH_SHORT).show();
                }
                startActivity(intent);
            }
        });

        mPersonCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoveActivity.class);
                if (mPersons != null) {
                    intent.putExtra("photos",(Serializable) mPersons);
                    intent.putExtra("title","人物");
                }else{
                    Toast.makeText(getContext(), "空空如也", Toast.LENGTH_SHORT).show();
                }
                startActivity(intent);
            }
        });

        mBuildingCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoveActivity.class);
                if (mBuildings != null) {
                    intent.putExtra("photos",(Serializable) mBuildings);
                    intent.putExtra("title","建筑");
                }else{
                    Toast.makeText(getContext(), "空空如也", Toast.LENGTH_SHORT).show();
                }
                startActivity(intent);
            }
        });

        mCarCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoveActivity.class);
                if (mCars != null) {
                    intent.putExtra("photos",(Serializable) mCars);
                    intent.putExtra("title","车");
                }else{
                    Toast.makeText(getContext(), "空空如也", Toast.LENGTH_SHORT).show();
                }
                startActivity(intent);
            }
        });

        mCreditCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoveActivity.class);
                if (mCredits != null) {
                    intent.putExtra("photos",(Serializable) mCredits);
                    intent.putExtra("title","证件");
                }else{
                    Toast.makeText(getContext(), "空空如也", Toast.LENGTH_SHORT).show();
                }
                startActivity(intent);
            }
        });

        mFlowerCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoveActivity.class);
                if (mFlowers != null) {
                    intent.putExtra("photos",(Serializable) mFlowers);
                    intent.putExtra("title","花卉");
                }else{
                    Toast.makeText(getContext(), "空空如也", Toast.LENGTH_SHORT).show();
                }
                startActivity(intent);
            }
        });

        mFoodCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoveActivity.class);
                if (mFoods != null) {
                    intent.putExtra("photos",(Serializable) mFoods);
                    intent.putExtra("title","食物");
                }else{
                    Toast.makeText(getContext(), "空空如也", Toast.LENGTH_SHORT).show();
                }
                startActivity(intent);
            }
        });

        mScreenshotCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoveActivity.class);
                if (mScreenshots != null) {
                    intent.putExtra("photos",(Serializable) mScreenshots);
                    intent.putExtra("title","截图");
                }else{
                    Toast.makeText(getContext(), "空空如也", Toast.LENGTH_SHORT).show();
                }
                startActivity(intent);
            }
        });

        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),LoveActivity.class);
                Retrofit retrofit = RetrofitCreator.getInstance().getRetrofit();
                PhotoService photoService = retrofit.create(PhotoService.class);
                Call<PhotoResult> task = photoService.getPhotoListByKeyword("建瓯", 1);
                task.enqueue(new Callback<PhotoResult>() {
                    @Override
                    public void onResponse(Call<PhotoResult> call, Response<PhotoResult> response) {
                        List<PhotoResult.DataBean> data = response.body().getData();
                        if (data != null) {
//                            Glide.with(getContext()).load(UrlUtils.path2Url(data.get(0).getPath())).into(mButton1);
                            LogUtil.d(SortFragment.this,"data === "+data);
                            intent.putExtra("photos",(Serializable) data);
                            intent.putExtra("title","福建 建瓯");
                        }else{
                            Toast.makeText(getContext(), "空空如也", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PhotoResult> call, Throwable t) {

                    }
                });
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);
                    }
                },100);
            }
        });
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),LoveActivity.class);
                Retrofit retrofit = RetrofitCreator.getInstance().getRetrofit();
                PhotoService photoService = retrofit.create(PhotoService.class);
                Call<PhotoResult> task = photoService.getPhotoListByKeyword("厦门", 1);
                task.enqueue(new Callback<PhotoResult>() {
                    @Override
                    public void onResponse(Call<PhotoResult> call, Response<PhotoResult> response) {
                        List<PhotoResult.DataBean> data = response.body().getData();
                        if (data != null) {
//                            Glide.with(getContext()).load(UrlUtils.path2Url(data.get(0).getPath())).into(mButton2);
                            LogUtil.d(SortFragment.this,"data === "+data);
                            intent.putExtra("photos",(Serializable) data);
                            intent.putExtra("title","福建厦门");

                        }else{
                            Toast.makeText(getContext(), "空空如也", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PhotoResult> call, Throwable t) {

                    }
                });
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);
                    }
                },100);
            }
        });
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),LoveActivity.class);
                Retrofit retrofit = RetrofitCreator.getInstance().getRetrofit();
                PhotoService photoService = retrofit.create(PhotoService.class);
                Call<PhotoResult> task = photoService.getPhotoListByKeyword("南昌", 1);
                task.enqueue(new Callback<PhotoResult>() {
                    @Override
                    public void onResponse(Call<PhotoResult> call, Response<PhotoResult> response) {
                        List<PhotoResult.DataBean> data = response.body().getData();
                        if (data != null) {
//                            Glide.with(getContext()).load(UrlUtils.path2Url(data.get(0).getPath())).into(mButton3);
                            LogUtil.d(SortFragment.this,"data === "+data);
                            intent.putExtra("photos",(Serializable) data);
                            intent.putExtra("title","江西南昌");

                        }else{
                            Toast.makeText(getContext(), "空空如也", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PhotoResult> call, Throwable t) {

                    }
                });
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);
                    }
                },100);
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handlerRefresh();
            }
        });
    }

    private void handlerRefresh() {
        loadData();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        },500);
    }

    @Override
    protected void initPresenter() {
        mFaceListPresneter = FaceListPresneter.getInstance();
        mFaceListPresneter.registerCallback(this);
        super.initPresenter();
    }

    @Override
    protected void loadData() {
        //获取分好人物的人脸照片
        mFaceListPresneter.getSortFaceByUid(BaseApplication.getUser().getId());

        Retrofit retrofit = RetrofitCreator.getInstance().getRetrofit();
        PhotoService photoService = retrofit.create(PhotoService.class);


        Call<PhotoResult> scenery = photoService.getScenery(BaseApplication.getUser().getId());
        scenery.enqueue(new Callback<PhotoResult>() {
            @Override
            public void onResponse(Call<PhotoResult> call, Response<PhotoResult> response) {
                mScenerys = response.body().getData();
                if (mScenerys != null && mScenerys.size() != 0) {
                    Glide.with(getContext()).load(UrlUtils.path2Url(mScenerys.get(0).getPath())).into(mSceneryCover);
                }
            }

            @Override
            public void onFailure(Call<PhotoResult> call, Throwable t) {

            }
        });
        Call<PhotoResult> animals = photoService.getPet(BaseApplication.getUser().getId());
        animals.enqueue(new Callback<PhotoResult>() {
            @Override
            public void onResponse(Call<PhotoResult> call, Response<PhotoResult> response) {
                mAnimals = response.body().getData();
                if (mAnimals != null && mAnimals.size() != 0) {
                    Glide.with(getContext()).load(UrlUtils.path2Url(mAnimals.get(0).getPath())).into(mAnimalCover);
                }
            }

            @Override
            public void onFailure(Call<PhotoResult> call, Throwable t) {

            }
        });
        Call<PhotoResult> credit = photoService.getCertificates(BaseApplication.getUser().getId());
        credit.enqueue(new Callback<PhotoResult>() {
            @Override
            public void onResponse(Call<PhotoResult> call, Response<PhotoResult> response) {
                mCredits = response.body().getData();
                if (mCredits != null && mCredits.size() != 0) {
                    Glide.with(getContext()).load(UrlUtils.path2Url(mCredits.get(0).getPath())).into(mCreditCover);
                }
            }

            @Override
            public void onFailure(Call<PhotoResult> call, Throwable t) {

            }
        });

        Call<PhotoResult> person = photoService.getPerson(BaseApplication.getUser().getId());
        person.enqueue(new Callback<PhotoResult>() {
            @Override
            public void onResponse(Call<PhotoResult> call, Response<PhotoResult> response) {
                mPersons = response.body().getData();
                if (mPersons != null && mPersons.size() != 0) {
                    Glide.with(getContext()).load(UrlUtils.path2Url(mPersons.get(0).getPath()))
                            .into(mPersonCover);
                }
            }

            @Override
            public void onFailure(Call<PhotoResult> call, Throwable t) {

            }
        });
        Call<PhotoResult> building = photoService.getBuilding(BaseApplication.getUser().getId());
        building.enqueue(new Callback<PhotoResult>() {
            @Override
            public void onResponse(Call<PhotoResult> call, Response<PhotoResult> response) {
                mBuildings = response.body().getData();
                if (mBuildings != null && mBuildings.size() != 0) {
                    Glide.with(getContext()).load(UrlUtils.path2Url(mBuildings.get(0).getPath())).into(mBuildingCover);
                }
            }

            @Override
            public void onFailure(Call<PhotoResult> call, Throwable t) {

            }
        });
        Call<PhotoResult> flower = photoService.getFlower(BaseApplication.getUser().getId());
        flower.enqueue(new Callback<PhotoResult>() {
            @Override
            public void onResponse(Call<PhotoResult> call, Response<PhotoResult> response) {
                mFlowers = response.body().getData();
                if (mFlowers != null && mFlowers.size() != 0) {
                    Glide.with(getContext()).load(UrlUtils.path2Url(mFlowers.get(0).getPath())).into(mFlowerCover);
                }
            }

            @Override
            public void onFailure(Call<PhotoResult> call, Throwable t) {

            }
        });

        Call<PhotoResult> food = photoService.getFood(BaseApplication.getUser().getId());
        food.enqueue(new Callback<PhotoResult>() {
            @Override
            public void onResponse(Call<PhotoResult> call, Response<PhotoResult> response) {
                mFoods = response.body().getData();
                if (mFoods != null && mFoods.size() != 0) {
                    Glide.with(getContext()).load(UrlUtils.path2Url(mFoods.get(0).getPath())).into(mFoodCover);
                }
            }

            @Override
            public void onFailure(Call<PhotoResult> call, Throwable t) {

            }
        });
        Call<PhotoResult> screenshot = photoService.getScreenshot(BaseApplication.getUser().getId());
        screenshot.enqueue(new Callback<PhotoResult>() {
            @Override
            public void onResponse(Call<PhotoResult> call, Response<PhotoResult> response) {
                mScreenshots = response.body().getData();
                if (mScreenshots != null && mScreenshots.size() != 0) {
                    Glide.with(getContext()).load(UrlUtils.path2Url(mScreenshots.get(0).getPath())).into(mScreenshotCover);
                }
            }

            @Override
            public void onFailure(Call<PhotoResult> call, Throwable t) {

            }
        });
        Call<PhotoResult> car = photoService.getCar(BaseApplication.getUser().getId());
        car.enqueue(new Callback<PhotoResult>() {
            @Override
            public void onResponse(Call<PhotoResult> call, Response<PhotoResult> response) {
                mCars = response.body().getData();
                if (mCars != null && mCars.size() != 0) {
                    Glide.with(getContext()).load(UrlUtils.path2Url(mCars.get(0).getPath())).into(mCarCover);
                }
            }

            @Override
            public void onFailure(Call<PhotoResult> call, Throwable t) {

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        mFaceListAdapter.notifyDataSetChanged();
    }

    private int backtouch = 0;

    @Override
    public void onResume() {
        super.onResume();
        mFaceListAdapter.notifyDataSetChanged();
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
