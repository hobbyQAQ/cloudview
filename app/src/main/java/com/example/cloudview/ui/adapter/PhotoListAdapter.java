package com.example.cloudview.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cloudview.R;
import com.example.cloudview.model.PhotoResult;
import com.example.cloudview.utils.LogUtil;
import com.example.cloudview.utils.UrlUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.chrono.MinguoDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.InnerHolder> {
    private List<PhotoResult.DataBean> mData = new ArrayList<>();
    private SimpleDateFormat to = new SimpleDateFormat("yyyy年MM月dd日");
    private SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo_list, parent, false);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        View view = holder.itemView;
        ImageView photoCoverIv = view.findViewById(R.id.photo_cover_iv);
        TextView dateTitleTv = view.findViewById(R.id.date_title_tv);
        String url = UrlUtils.path2Url(mData.get(position).getPath());
        LogUtil.d(this, "url:" + url);
        Glide.with(view.getContext()).load(url).into(photoCoverIv);
        try {
            Date date = from.parse(mData.get(position).getDate());
            String time = to.format(date);
            dateTitleTv.setText(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<PhotoResult.DataBean> photoList) {
        mData.clear();
        mData.addAll(photoList);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
