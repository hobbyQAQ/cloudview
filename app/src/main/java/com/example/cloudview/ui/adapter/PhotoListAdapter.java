package com.example.cloudview.ui.adapter;

import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.cloudview.R;
import com.example.cloudview.model.PhotoResult;
import com.example.cloudview.utils.DateUtils;
import com.example.cloudview.utils.LogUtil;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.InnerHolder> {
    //这是所有的照片的数据
    private List<PhotoResult.DataBean> mData = new ArrayList<>();
    //这是按照时间分号类的数据
    private Map<Integer, List<PhotoResult.DataBean>>  mSortData = new HashMap<>();
    //初始化的开始位置，用来传给照片查看器应该查看哪张照片
    private int mStartPosition = 0;
    private List<Integer> mKeys = new ArrayList<>();

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo_list, parent, false);
        InnerHolder innerHolder = new InnerHolder(itemView);
        innerHolder.setIsRecyclable(false);
        return innerHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        LogUtil.d(PhotoListAdapter.this,"list position="+position+"   list size --> "+mSortData.get(position).size());
        View view = holder.itemView;
//        ImageView photoCoverIv = view.findViewById(R.id.photo_cover_iv);
        TextView dateTitleTv = view.findViewById(R.id.date_title_tv);
        RecyclerView gridList = view.findViewById(R.id.photo_list_view);
        //通过list中的键就能得到通过时间分类的值
        try {
            String dateStr = DateUtils.theirTime2ourTime(mSortData.get(position).get(0).getDate());
            dateTitleTv.setText(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //每一行中数据不同
        PhotoGridListAdapter photoGridListAdapter = new PhotoGridListAdapter(mSortData.get(position));
        //同时要将每一行中第一张照片属于所有照片的第几张照片传过去
        photoGridListAdapter.setStartPosition(mStartPosition);
        mStartPosition = mStartPosition + mSortData.get(position).size();
        LogUtil.d(PhotoListAdapter.this,"开始位置 ==== "+mStartPosition+"");
        photoGridListAdapter.setAllPhoto(mData);
        gridList.setAdapter(photoGridListAdapter);
        gridList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = 8;
                outRect.bottom = 8;
                outRect.left = 8;
                outRect.right = 8;
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(),4,RecyclerView.VERTICAL,false);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
//        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        gridList.setLayoutManager(gridLayoutManager);


//        String url = UrlUtils.path2Url(mData.get(position).getPath());
//        LogUtil.d(this, "url:" + url);
//        Glide.with(view.getContext()).load(url).into(photoCoverIv);

    }

    @Override
    public int getItemCount() {
        return mSortData.size();
    }

    public void setData(List<PhotoResult.DataBean> photoList) {
        mData.clear();
        mData.addAll(photoList);
        //把键重Mapper中取出来放到list中
        try {
            mSortData = DateUtils.sortPhotoByDate(photoList);
            Set<Map.Entry<Integer, List<PhotoResult.DataBean>>> entries = mSortData.entrySet();
            for (Map.Entry<Integer, List<PhotoResult.DataBean>> entry : entries) {
                mKeys.add(entry.getKey());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
