package com.example.cloudview.ui.fragment;

import com.example.cloudview.R;
import com.example.cloudview.base.BaseFragment;

public class SearchFragment extends BaseFragment {
    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initView() {
        switchUIByPageState(PageState.SUCCESS);
    }
}
