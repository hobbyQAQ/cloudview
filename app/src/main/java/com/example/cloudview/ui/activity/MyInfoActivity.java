package com.example.cloudview.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RelativeLayout;

import com.example.cloudview.R;
import com.leon.lib.settingview.LSettingItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyInfoActivity extends AppCompatActivity {

    private Unbinder mBind;

    @BindView(R.id.item_my_icon)
    public RelativeLayout mItemMyIcon;

    @BindView(R.id.item_my_nickname)
    public RelativeLayout mItemMyNickName;

    @BindView(R.id.item_my_gender)
    public RelativeLayout mItemMyGender;

    @BindView(R.id.item_my_birthday)
    public RelativeLayout mItemMyBirthday;

    @BindView(R.id.item_my_position)
    public RelativeLayout mItemMyPosition;

    @BindView(R.id.item_my_motto)
    public RelativeLayout mItemMyMotto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        mBind = ButterKnife.bind(this);//记得解绑
        initView();
    }

    private void initView() {
    }
}
