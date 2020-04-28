package com.example.cloudview.ui.fragment;

import com.example.cloudview.R;
import com.example.cloudview.base.BaseFragment;

public class MineFragment extends BaseFragment {
    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        switchUIByPageState(PageState.SUCCESS);
    }
}
