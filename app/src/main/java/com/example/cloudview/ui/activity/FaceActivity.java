package com.example.cloudview.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cloudview.R;
import com.example.cloudview.model.SortFaceResult;
import com.example.cloudview.ui.adapter.FaceSampleListAdapter;

import java.util.ArrayList;
import java.util.List;

public class FaceActivity extends AppCompatActivity implements FaceSampleListAdapter.OnFaceClickListener {

    private RecyclerView mFaceList;

    private List<SortFaceResult.DataBean.FacelistBean> mFaces = new ArrayList<>();
    private FaceSampleListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face);
        mFaceList = findViewById(R.id.face_photo_list);
        mFaces = (List<SortFaceResult.DataBean.FacelistBean>)getIntent().getSerializableExtra("faces");
        mAdapter = new FaceSampleListAdapter();
        mFaceList.setAdapter(mAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this,4);
        mFaceList.setLayoutManager(layoutManager);
        mAdapter.setData(mFaces);
        mAdapter.setOnFaceClickListener(this);
//        mFaceList.setAdapter();
    }

    public void onBackHandle(View view) {
        finish();
    }

    @Override
    public void onFaceClick(int position) {
        Intent intent = new Intent();
        intent.putExtra("position",position);
        setResult(101,intent);
        finish();
    }
}
