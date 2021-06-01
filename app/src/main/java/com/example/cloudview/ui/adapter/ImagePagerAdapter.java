package com.example.cloudview.ui.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.cloudview.model.PhotoResult;
import com.example.cloudview.model.bean.PhotoItem;
import com.example.cloudview.utils.UrlUtils;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

/**
 * 本地图片的浏览器
 */
public class ImagePagerAdapter extends PagerAdapter {

    private ArrayList<PhotoItem> mData = new ArrayList<>();

    private OnPagerChangerListener mListener;

    public void setListener(OnPagerChangerListener listener){
        mListener = listener;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(container.getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        photoView.setLayoutParams(layoutParams);
        Glide.with(container.getContext()).load(mData.get(position).getPath()).into(photoView);
        container.addView(photoView);
        mListener.onPagerChange();
        return photoView;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    public void setData(List<PhotoItem> list) {
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }

    public interface OnPagerChangerListener {
        void onPagerChange();
    }
}
