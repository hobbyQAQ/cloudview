package com.example.cloudview.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cloudview.R;
import com.example.cloudview.model.SortFaceResult;
import com.example.cloudview.utils.LogUtil;
import com.example.cloudview.utils.UrlUtils;

import java.util.ArrayList;
import java.util.List;

public class FaceSampleListAdapter extends RecyclerView.Adapter<FaceSampleListAdapter.InnerHolder> {
    private List<SortFaceResult.DataBean.FacelistBean> mData = new ArrayList<>();

    public void setData(List<SortFaceResult.DataBean.FacelistBean> datas){
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
        LogUtil.d(FaceSampleListAdapter.this,"url === "+url);
        Glide.with(itemView.getContext()).load(url).into(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFaceClickListener.onFaceClick(position);
            }
        });
    }


    public OnFaceClickListener mFaceClickListener;

    public void setOnFaceClickListener(OnFaceClickListener listener){
        mFaceClickListener = listener;
    }

    public interface OnFaceClickListener {
        void onFaceClick(int position);
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
