package com.example.cloudview.ui.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cloudview.R;
import com.example.cloudview.base.BaseApplication;
import com.example.cloudview.model.FaceResult;
import com.example.cloudview.model.PhotoResult;
import com.example.cloudview.model.SortFaceResult;
import com.example.cloudview.ui.activity.CharaterActivity;
import com.example.cloudview.utils.DateUtils;
import com.example.cloudview.utils.LogUtil;
import com.example.cloudview.utils.UrlUtils;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class FaceListAdapter extends RecyclerView.Adapter<FaceListAdapter.InnerHolder> {
    //人脸数据
    private List<SortFaceResult.DataBean> mData = new ArrayList<>();


    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_face_list, parent, false);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        View itemView = holder.itemView;
//        ImageView photoCoverIv = view.findViewById(R.id.photo_cover_iv);
        CircleImageView faceIv = itemView.findViewById(R.id.face_iv);
        TextView nameTv = itemView.findViewById(R.id.face_name);
        nameTv.setText(mData.get(position).getName());
        //设置封面
        int  coverPositon = 1;
        coverPositon = Integer.parseInt(BaseApplication.getCoverMap().get(""+mData.get(position).getCid()));
        String url = UrlUtils.path2Url(mData.get(position).getFacelist().get(coverPositon).getPath());
        Glide.with(itemView.getContext()).load(url).into(faceIv);
        faceIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(itemView.getContext(), CharaterActivity.class);
                intent.putExtra("cid",mData.get(position).getCid());
                intent.putExtra("name",mData.get(position).getName());
                intent.putExtra("faces",(Serializable) mData.get(position).getFacelist());
                itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<SortFaceResult.DataBean> faceList) {
        mData.clear();
        mData.addAll(faceList);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
