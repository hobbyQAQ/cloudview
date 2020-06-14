package com.example.cloudview.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.cloudview.R;
import com.example.cloudview.base.BaseApplication;
import com.example.cloudview.base.BaseFragment;
import com.example.cloudview.model.UserResult;
import com.example.cloudview.model.bean.PhotoItem;
import com.example.cloudview.ui.fragment.HomeFragment;
import com.example.cloudview.ui.fragment.MineFragment;
import com.example.cloudview.ui.fragment.SearchFragment;
import com.example.cloudview.ui.fragment.SortFragment;
import com.example.cloudview.utils.LogUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int LOAD_ID = 1;
    @BindView(R.id.main_navigation_bar)
    public BottomNavigationBar mBottomNavigationBar;

    @BindView(R.id.iv_search)
    public ImageView searchButton;

    @BindView(R.id.iv_setting)
    public ImageView settingButton;

    @BindView(R.id.tv_title)
    public TextView titleTv;

    @BindView(R.id.myToolbar)
    public Toolbar mToolbar;


    private HomeFragment mHomeFragment;
    private SearchFragment mSearchFragment;
    private SortFragment mSortFragment;
    private MineFragment mMineFragment;
    private FragmentManager mFm;
    private List<String> mUrls = new ArrayList<>();
    private List<PhotoItem> mPics = new ArrayList<>();
    private Unbinder mBind;

    private UserResult.DataBean mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBind = ButterKnife.bind(this);
        //用户登录后获取用户信息
        mUser = (UserResult.DataBean)getIntent().getSerializableExtra("user");
        ((BaseApplication)getApplication()).setUser(mUser);
        initPermission();
        initView();
        initEvent();
        initLocalData();
//        initLoaderManager();
    }
    //也可以放到子线程去加载数据
    private void initLoaderManager() {
        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(LOAD_ID, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @NonNull
            @Override
            public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
                if (id == LOAD_ID) {
                    return new CursorLoader(MainActivity.this,MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    ,new String[]{"_data","date_added"},null,null,"date_added desc");
                }
                return null;
            }

            @Override
            public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
                if (cursor != null) {
                    String[] cursorNames = cursor.getColumnNames();
                    while (cursor.moveToNext()){
                        for (String cursorName : cursorNames) {
                            LogUtil.d(MainActivity.this,"cursorName"+"==="+cursor.getString(cursor.getColumnIndex(cursorName)));
                        }

                    }
                }
            }

            @Override
            public void onLoaderReset(@NonNull Loader<Cursor> loader) {

            }
        });
    }

    private void initLocalData() {
        Cursor cursor = getContentResolver()
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        , new String[]{
                                MediaStore.Images.Media.DATA,
                                MediaStore.Images.Media.DISPLAY_NAME,
                                MediaStore.Images.Media.DATE_ADDED,
                                MediaStore.Images.Media._ID}
                                , null
                        , null
                        , "date_added desc");
        while (cursor.moveToNext()) {
            PhotoItem photoItem = new PhotoItem();
            photoItem.setPath(cursor.getString(0));
            photoItem.setCreateDate(cursor.getLong(1));
            photoItem.setName(cursor.getString(2));
//            LogUtil.d(MainActivity.this,photoItem.toString());
            mPics.add(photoItem);
//            String paths = cursor.getString(cursor.getColumnIndex(MediaStore
//                    .Images.Media.DATA));
//            File file = new File(paths);
//            String absolutePath = file.getAbsolutePath();
//            mUrls.add(absolutePath);
        }
        cursor.close();
    }

    /**
     * android 6.0 以上需要动态申请权限
     */
    private void initPermission() {
        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                // 进入到这里代表没有权限.

            }
        }
        String[] tmpList = new String[toApplyList.size()];
        //toArray是集合转数组
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 1);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 此处为android 6.0以上动态授权的回调，用户自行实现。
    }

    private void initEvent() {
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        //toolbar的title也跟着改变
        mBottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                LogUtil.d(MainActivity.this, "position == " + position);
                switch (position) {
                    case 0:
                        switchFragment(mHomeFragment);
                        titleTv.setText(R.string.text_home);
                        break;
                    case 1:
                        switchFragment(mSortFragment);
                        titleTv.setText(R.string.text_sort);
                        break;
                    case 2:
                        FragmentTransaction fragmentTransaction = mFm.beginTransaction();
                        fragmentTransaction.replace(R.id.main_page_content, mMineFragment);
                        fragmentTransaction.commit();
                        titleTv.setText(R.string.text_mine);
                        break;
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }

    private void switchFragment(BaseFragment targetFragment) {
        FragmentTransaction fragmentTransaction = mFm.beginTransaction();
        fragmentTransaction.replace(R.id.main_page_content, targetFragment);
        fragmentTransaction.commit();
    }


    private void initView() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

        mBottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.selector_home, R.string.text_home))
                .addItem(new BottomNavigationItem(R.drawable.selector_sort, R.string.text_sort))
                .addItem(new BottomNavigationItem(R.drawable.selector_mine, R.string.text_mine))
                .setMode(BottomNavigationBar.MODE_FIXED)
                .setBarBackgroundColor(R.color.color_bottom)
                .setFirstSelectedPosition(0)
                .setActiveColor(R.color.colorPrimary)
                .setInActiveColor(R.color.color_not_selected)
                .initialise();
        mHomeFragment = new HomeFragment();
        mSortFragment = new SortFragment();
        mSearchFragment = new SearchFragment();
        mMineFragment = new MineFragment();
        mFm = getSupportFragmentManager();
        switchFragment(mHomeFragment);
    }

    @Override
    protected void onDestroy() {
        if (mBind != null) {
            mBind.unbind();
        }
        super.onDestroy();
    }

    public List<PhotoItem> getData() {
        return mPics;
    }
}
