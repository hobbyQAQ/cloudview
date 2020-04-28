package com.example.cloudview.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cloudview.R;
import com.example.cloudview.utils.LogUtil;
import com.example.cloudview.utils.UrlUtils;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

public class LocalAlbumAdapter extends RecyclerView.Adapter<LocalAlbumAdapter.InnerHolder> {

    private static final String TAG = "LocalAlbumAdapter";
    private List<String> mData = new ArrayList<>();

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_small_local_photo, parent, false);

        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        View itemView = holder.itemView;
        ImageView cover = itemView.findViewById(R.id.small_local_photo_cover);
        String url = UrlUtils.getLocalPictureUrl(mData.get(position));
        LogUtil.d(TAG, "url --> " + url);
        Glide.with(itemView.getContext()).load(url).into(cover);
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        public InnerHolder(@NonNull View itemView) {
            super(itemView);

        }
    }

    public void setData(List<String> urls) {
        Log.d(TAG, "urls " + urls.size());
            mData.clear();
            mData.addAll(urls);
        notifyDataSetChanged();
    }
}
