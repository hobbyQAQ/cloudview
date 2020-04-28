package com.example.cloudview.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.cloudview.R;
import com.example.cloudview.base.BaseFragment;
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
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.main_navigation_bar)
    public BottomNavigationBar mBottomNavigationBar;

    @BindView(R.id.iv_search)
    public ImageView searchButton;

    @BindView(R.id.iv_setting)
    public ImageView settingButton;

    @BindView(R.id.tv_title)
    public TextView titleTv;


    private HomeFragment mHomeFragment;
    private SearchFragment mSearchFragment;
    private SortFragment mSortFragment;
    private MineFragment mMineFragment;
    private FragmentManager mFm;
    private List<String> mUrls = new ArrayList<>();
    private Unbinder mBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBind = ButterKnife.bind(this);
        initPermission();
        initView();
        initEvent();
        initLocalData();
    }

    private void initLocalData() {
        Cursor cursor = getContentResolver()
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        , null, null, null, null);
        while (cursor.moveToNext()) {
            String paths = cursor.getString(cursor.getColumnIndex(MediaStore
                    .Images.Media.DATA));
            File file = new File(paths);
            String absolutePath = file.getAbsolutePath();
            mUrls.add(absolutePath);
            Log.d(TAG, "mUrls size == " + mUrls.size());
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
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
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
                        switchFragment(mMineFragment);
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

        mBottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.selector_home, R.string.text_home))
                .addItem(new BottomNavigationItem(R.drawable.selector_sort, R.string.text_sort))
                .addItem(new BottomNavigationItem(R.drawable.selector_mine, R.string.text_mine))
                .setMode(BottomNavigationBar.MODE_FIXED)
                .setBarBackgroundColor(R.color.colorPrimary)
                .setFirstSelectedPosition(0)
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

    public List<String> getData() {
        return mUrls;
    }
}
