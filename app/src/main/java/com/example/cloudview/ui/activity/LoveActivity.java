package com.example.cloudview.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloudview.R;
import com.example.cloudview.model.PhotoResult;
import com.example.cloudview.ui.adapter.PhotoSampleListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoveActivity extends AppCompatActivity {

    @BindView(R.id.love_photo_list)
    RecyclerView mLoveList;
    @BindView(R.id.title_head)
    TextView titleHead;
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
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        mLoveList.setLayoutManager(layoutManager);
    }

    private void initData() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPhotos = (List<PhotoResult.DataBean>) getIntent().getSerializableExtra("photos");
                String title = getIntent().getStringExtra("title");
                titleHead.setText(title);
                if (mPhotos != null) {
                    mAdapter.setData(mPhotos);
                }
            }
        }, 200);

    }

    public void onBackHandle(View view) {
        finish();
    }
}
