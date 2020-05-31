package com.example.cloudview.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.cloudview.R;
import com.example.cloudview.base.BaseApplication;
import com.example.cloudview.base.BaseFragment;
import com.leon.lib.settingview.LSettingItem;

import butterknife.BindView;

public class MineFragment extends BaseFragment {


    @BindView(R.id.item_my_love)
    public LSettingItem mItemMyLove;

    @BindView(R.id.item_my_info)
    public LSettingItem mItemMyInfo;

    @BindView(R.id.item_my_setting)
    public LSettingItem mItemMySetting;

    @BindView(R.id.item_my_download)
    public LSettingItem mItemMyDownload;

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {

        switchUIByPageState(PageState.SUCCESS);
    }

    @Override
    protected void initListener() {
        mItemMyLove.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {

            }
        });
        mItemMyInfo.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                Intent intent=new Intent("com.cloudview.action.MY_INFO_ACTION");
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

            }
        });
    }
}
