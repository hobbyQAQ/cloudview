package com.example.cloudview.ui.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.cloudview.R;
import com.example.cloudview.model.PhotoResult;
import com.example.cloudview.ui.activity.PhotoActivity;
import com.example.cloudview.utils.LogUtil;
import com.example.cloudview.utils.UrlUtils;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PhotoGridListAdapter extends RecyclerView.Adapter<PhotoGridListAdapter.InnerHolder> {

    List<PhotoResult.DataBean> mData = new ArrayList<>();
    List<PhotoResult.DataBean> mAllPhotos = new ArrayList<>();
    private int mStartPosition = 0;

    private OnSinglePhotoClickedListenner mOnSinglePhotoClickedListenner = null;

    PhotoGridListAdapter(List<PhotoResult.DataBean> datas) {
        mData.clear();
        mData.addAll(datas);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_small_online_photo, parent, false);
        InnerHolder innerHolder = new InnerHolder(view);
        innerHolder.setIsRecyclable(false);
        return innerHolder;
    }

    @Override
    public void onViewRecycled(@NonNull InnerHolder holder) {
        super.onViewRecycled(holder);
        if (holder != null) {
            if (holder != null) {
                Glide.with(holder.itemView.getContext()).clear(holder.itemView);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        View itemView = holder.itemView;
        ImageView cover = itemView.findViewById(R.id.small_online_photo_cover);

        String url = UrlUtils.path2Url(mData.get(position).getPath());
        cover.setTag(R.id.small_online_photo_cover,position);
            LogUtil.d(PhotoGridListAdapter.this,"url:"+url);
//        Picasso.with(itemView.getContext()).load(url).into(cover);
        Glide.with(itemView.getContext())
                .load(url)
                .into(cover);
        cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(itemView.getContext(), "你点击了这张照片", Toast.LENGTH_SHORT).show();
//                mOnSinglePhotoClickedListenner.onSinglePhotoClicked(UrlUtils.path2Url(mData.get(position).getPath()));

                Intent intent = new Intent(itemView.getContext(), PhotoActivity.class);
                Bundle bundle = new Bundle();
                int pos = mStartPosition + position;
                bundle.putInt("position",pos);
                bundle.putSerializable("photos", (Serializable) mAllPhotos);
                LogUtil.d(PhotoGridListAdapter.this,"数据集大小：" +
                        ""+mAllPhotos.size()+" mStartPosition："+mStartPosition+" position "+position+" pos "+pos);
                intent.putExtras(bundle);
                itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setAllPhoto(List<PhotoResult.DataBean> data) {
        Log.d("PhotoGridListAdapter.setAllPhoto",data.toString());
        this.mAllPhotos = data;
    }

    public void setStartPosition(int startPosition) {
        mStartPosition = startPosition;
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


    public void setOnSinglePhotoClickedListenner(OnSinglePhotoClickedListenner listenner) {
        this.mOnSinglePhotoClickedListenner = listenner;
    }

    interface OnSinglePhotoClickedListenner {
        void onSinglePhotoClicked(String url);
    }
}
