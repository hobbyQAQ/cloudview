package com.example.cloudview.ui.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cloudview.R;
import com.example.cloudview.model.PhotoResult;
import com.example.cloudview.ui.activity.PhotoActivity;
import com.example.cloudview.utils.LogUtil;
import com.example.cloudview.utils.UrlUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PhotoSampleListAdapter extends RecyclerView.Adapter<PhotoSampleListAdapter.InnerHolder> {
    private List<PhotoResult.DataBean> mData = new ArrayList();

    public void setData(List<PhotoResult.DataBean> datas){
        mData.clear();
        mData.addAll(datas);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_small_online_photo,parent,false);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        View itemView = holder.itemView;
        ImageView view = itemView.findViewById(R.id.small_online_photo_cover);
        String url = UrlUtils.path2Url(mData.get(position).getPath());
        LogUtil.d(PhotoSampleListAdapter.this,"url === "+url);
        Glide.with(itemView.getContext()).load(url).into(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(itemView.getContext(), PhotoActivity.class);
                intent.putExtra("photos",(Serializable) mData);
                intent.putExtra("position",position);
                itemView.getContext().startActivity(intent);
            }
        });
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
}
