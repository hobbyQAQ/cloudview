package com.example.cloudview.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.cloudview.R;
import com.example.cloudview.model.bean.PhotoItem;
import com.example.cloudview.ui.adapter.DownloadListAdapter;
import com.example.cloudview.utils.LogUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DownloadActivity extends AppCompatActivity {

    private List<PhotoItem> mPics = new ArrayList<>();
    private Context mContext;
    private RecyclerView mDownloadList;
    private DownloadListAdapter mAdapter;
    private GridLayoutManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        mContext = this;
        initData();
        mDownloadList = findViewById(R.id.download_list);
        mAdapter = new DownloadListAdapter();
        mDownloadList.setAdapter(mAdapter);
        mManager = new GridLayoutManager(this,4);
        mDownloadList.setLayoutManager(mManager);
        mAdapter.setData(mPics);
    }






    private void initData() {
        String path="Pictures/cloudview";
        String selection = MediaStore.Images.Media.DATA +" like"+"'%"+path+"%'";
        Cursor cursor = getContentResolver()
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        , new String[]{
                                MediaStore.Images.Media.DATA,
                                MediaStore.Images.Media.DISPLAY_NAME,
                                MediaStore.Images.Media.DATE_ADDED,
                                MediaStore.Images.Media._ID}
                        , selection
                        , null
                        ,null);
        while (cursor.moveToNext()) {
            PhotoItem photoItem = new PhotoItem();
            photoItem.setPath(cursor.getString(0));
            photoItem.setCreateDate(cursor.getString(1));
            photoItem.setName(cursor.getString(2));
            LogUtil.d(DownloadActivity.this,photoItem.toString());
            mPics.add(photoItem);
//            String paths = cursor.getString(cursor.getColumnIndex(MediaStore
//                    .Images.Media.DATA));
//            File file = new File(paths);
//            String absolutePath = file.getAbsolutePath();
//            mUrls.add(absolutePath);
        }
        cursor.close();
    }


    public void onBackHandle(View view) {
        finish();
    }
}
