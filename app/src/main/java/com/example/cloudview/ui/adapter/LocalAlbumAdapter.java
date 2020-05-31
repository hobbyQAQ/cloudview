package com.example.cloudview.ui.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cloudview.R;
import com.example.cloudview.base.BaseApplication;
import com.example.cloudview.model.bean.PhotoItem;
import com.example.cloudview.utils.LogUtil;
import com.example.cloudview.utils.UrlUtils;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

public class LocalAlbumAdapter extends RecyclerView.Adapter<LocalAlbumAdapter.InnerHolder> {

    private static final String TAG = "LocalAlbumAdapter";
    private List<PhotoItem> mData = new ArrayList<>();
    private List<PhotoItem> mPhotoSelected = new ArrayList<>();
    private boolean isUseCheckBox = false;

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
        CheckBox uploadCheckBox = itemView.findViewById(R.id.upload_checkbox);
        if (!isUseCheckBox) {
            uploadCheckBox.setVisibility(View.INVISIBLE);
            uploadCheckBox.setEnabled(false);
        }else {
            uploadCheckBox.setVisibility(View.VISIBLE);
            uploadCheckBox.setEnabled(true);
        }
        String url = UrlUtils.getLocalPictureUrl(mData.get(position).getPath());
        LogUtil.d(TAG, "url --> " + url);
        Glide.with(itemView.getContext()).load(url).into(cover);
        //点击打开图片查看器
        cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.cloudview.action.GPU_IMAGE_ACTION");
                intent.putExtra("photo",mData.get(position));
                itemView.getContext().startActivity(intent);
            }
        });
        //长按打开checkbox选择上传
        cover.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(BaseApplication.getContext(), "长按事件", Toast.LENGTH_SHORT).show();
                isUseCheckBox = true;
                uploadCheckBox.setVisibility(View.VISIBLE);
                uploadCheckBox.setEnabled(true);
                uploadCheckBox.setChecked(true);
                if (!mPhotoSelected.contains(mData.get(position))) {
                    mPhotoSelected.add(mData.get(position));
                }
                for (int i = 0; i < mData.size(); i++) {
                    if(i!=position)
                    {
                        notifyItemChanged(i);
                    }

                }


                return false;
            }
        });
        //设置复选框的点击事件
        uploadCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadCheckBox.isChecked()) {
                    //如果选中了就将数据添加
                    if (!mPhotoSelected.contains(mData.get(position))) {
                        mPhotoSelected.add(mData.get(position));
                    }
                }else{
                    //取消了就删除
                    if (mPhotoSelected.contains(mData.get(position))) {
                        mPhotoSelected.remove(mData.get(position));
                    }
                }
                LogUtil.d(LocalAlbumAdapter.this,"mPhotoSelected ==== "+mPhotoSelected.toString());
            }
        });
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public List<PhotoItem> getSelected() {
        return mPhotoSelected;
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        public InnerHolder(@NonNull View itemView) {
            super(itemView);

        }
    }

    public void setData(List<PhotoItem> datas) {

        mData.clear();
        mData.addAll(datas);
        notifyDataSetChanged();
    }
}
