package com.example.cloudview.ui.fragment;

import com.example.cloudview.R;
import com.example.cloudview.base.BaseFragment;

public class SortFragment extends BaseFragment {
    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_sort;
    }

    @Override
    protected void initView() {
        switchUIByPageState(PageState.SUCCESS);
    }
}
