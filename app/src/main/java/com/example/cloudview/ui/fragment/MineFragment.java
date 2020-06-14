package com.example.cloudview.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.cloudview.Api.PhotoService;
import com.example.cloudview.Api.UserService;
import com.example.cloudview.R;
import com.example.cloudview.base.BaseApplication;
import com.example.cloudview.model.PhotoResult;
import com.example.cloudview.model.UserResult;
import com.example.cloudview.ui.activity.DownloadActivity;
import com.example.cloudview.ui.activity.LoveActivity;
import com.example.cloudview.ui.activity.ResultActivity;
import com.example.cloudview.utils.LogUtil;
import com.example.cloudview.utils.RetrofitCreator;
import com.example.cloudview.utils.UrlUtils;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoFragment;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.leon.lib.settingview.LSettingItem;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MineFragment extends TakePhotoFragment {

    private Unbinder mBind;
    @BindView(R.id.item_my_love)
    public LSettingItem mItemMyLove;

    @BindView(R.id.item_my_info)
    public LSettingItem mItemMyInfo;

    @BindView(R.id.item_my_setting)
    public LSettingItem mItemMySetting;

    @BindView(R.id.item_my_download)
    public LSettingItem mItemMyDownload;

    @BindView(R.id.profile_image)
    CircleImageView mProfileImage;

    @BindView(R.id.profile_nickname)
    TextView mProfileNickname;

    private FrameLayout mBaseContainer;



    // 图库选取图片标识请求码
    private static final int REQUEST_CODE_CHOOSE = 0x13;
    private static final int RESULT_OK = 1;

    //TakePhoto
    private TakePhoto mTakePhoto;
    private CropOptions.Builder mBuilder; //裁剪参数
    private CompressConfig mCompressConfig; //压缩参数
    private Uri mImageUri; //图片保存路径


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.base_fragment_layout, container,false);
        mBaseContainer = rootView.findViewById(R.id.base_container);
        mBaseContainer.addView(inflater.inflate(R.layout.fragment_mine, container, false));
        mBind = ButterKnife.bind(this,rootView);
        initView();
        loadData();
        initListener();

        return rootView;
    }


    protected void initView() {
        mTakePhoto = getTakePhoto();
        mBuilder = new CropOptions.Builder();
        mBuilder.setAspectX(800).setAspectY(800);
        mBuilder.setWithOwnCrop(true);
        File file = new File(Environment.getExternalStorageDirectory(),
                "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            boolean mkdirs = file.getParentFile().mkdirs();
            if (!mkdirs) {
                LogUtil.d(MineFragment.this, "创建文件夹失败");
            }
        }
        mImageUri = Uri.fromFile(file);
        CompressConfig config = new CompressConfig.Builder()
                .setMaxSize(102400)
                .setMaxPixel(400)
                .enableReserveRaw(true)
                .create();
        mTakePhoto.onEnableCompress(config, true);
    }


    protected void loadData() {
        if (BaseApplication.sUser.getCoverPath() != null) {
            Glide.with(mBaseContainer.getContext()).load(UrlUtils.path2Url(BaseApplication.sUser.getCoverPath())).into(mProfileImage);
        } else {
            LogUtil.d(MineFragment.this, "还未上传头像");
        }
        if (BaseApplication.sUser.getNickname() != null) {
            mProfileNickname.setText(BaseApplication.sUser.getNickname());
        }else{
            mProfileNickname.setText("user"+BaseApplication.sUser.getId());
        }

    }


    protected void initListener() {
        mItemMyLove.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                Intent intent = new Intent(getActivity(), LoveActivity.class);

                startActivity(intent);
            }
        });
        mItemMyInfo.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                Intent intent = new Intent("com.cloudview.action.MY_INFO_ACTION");
                startActivity(intent);
            }
        });
        mItemMySetting.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {

            }
        });
        mItemMyDownload.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                Intent intent = new Intent(getActivity(), DownloadActivity.class);
                startActivity(intent);
            }
        });
        mProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTakePhoto.onPickFromGalleryWithCrop(mImageUri, mBuilder.create());
            }
        });


        mProfileNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //修改昵称
//                Retrofit retrofit = RetrofitCreator.getInstance().getRetrofit();
//                UserService userService = retrofit.create(UserService.class);
//                userService.update(BaseApplication.sUser.getId())
            }
        });
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        String iconPath = result.getImage().getOriginalPath();
        //上传至云端
        Retrofit retrofit = RetrofitCreator.getInstance().getRetrofit();
        UserService userService = retrofit.create(UserService.class);
        MultipartBody.Part file =getPart("file",iconPath);
        Call<UserResult> upload = userService.upload(file, BaseApplication.sUser.getId());
        upload.enqueue(new Callback<UserResult>() {
            @Override
            public void onResponse(Call<UserResult> call, Response<UserResult> response) {
                UserResult body = response.body();
                if (body.isSuccess()) {
                    Toast.makeText(getContext(), "上传成功", Toast.LENGTH_SHORT).show();
                    BaseApplication.setUser(body.getData());
                }else{
                    Toast.makeText(getContext(), "上传失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResult> call, Throwable t) {
                Toast.makeText(getContext(), "上传失败，网络错误", Toast.LENGTH_SHORT).show();
            }
        });
        Glide.with(this).load(iconPath).into(mProfileImage);
        //Toast显示图片路径
//        showImg(result.getImages());
    }


    private MultipartBody.Part getPart(String key,String filePath) {
        File file = new File(filePath);
        MediaType mediaType = MediaType.parse("image/jpg");
        RequestBody fileBody = RequestBody.create(mediaType,file);
        return MultipartBody.Part.createFormData(key,file.getName(),fileBody);
    }

    private void showImg(ArrayList<TImage> images) {
        Intent intent = new Intent(getContext(), ResultActivity.class);
        intent.putExtra("images", images);
        startActivity(intent);
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    //    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        //以下代码为处理Android6.0、7.0动态权限所需
//        PermissionManager.TPermissionType type=PermissionManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
//        PermissionManager.handlePermissionsResult(this.getActivity(),type,invokeParam,this);
//    }

//    public TakePhoto getTakePhoto(){
//        if (mTakePhoto==null){
//            mTakePhoto= (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this,this));
//        }
//        return mTakePhoto;
//    }

//    @Override
//    public void takeSuccess(TResult result) {
//        String iconPath = result.getImage().getOriginalPath();
//        Toast.makeText(getActivity(),"imagePath:"+iconPath,Toast.LENGTH_SHORT).show();
//        Glide.with(this).load(iconPath).into(mProfileImage);
//    }

//    @Override
//    public void takeFail(TResult result, String msg) {
//        LogUtil.d(MineFragment.this,"选择失败");
//    }

//    @Override
//    public void takeCancel() {
//        LogUtil.d(MineFragment.this,"取消选择");
//    }
//    @Override
//    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
//        PermissionManager.TPermissionType type=PermissionManager.checkPermission(TContextWrap.of(this),invokeParam.getMethod());
//        if(PermissionManager.TPermissionType.WAIT.equals(type)){
//            this.invokeParam=invokeParam;
//        }
//        return type;
//    }


}
