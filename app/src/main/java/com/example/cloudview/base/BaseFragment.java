package com.example.cloudview.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cloudview.R;
import com.example.cloudview.utils.LogUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 *  控制界面的加载
 */
public abstract class BaseFragment extends Fragment {




    public enum PageState{NONE,ERROR,EMPTY,SUCCESS,LOADING};
    private Unbinder mBind;
    private FrameLayout mBaseContainer;
    private View mErrorView;
    private View mSuccessView;
    private View mLoadingView;
    private View mEmptyView;
    private PageState mCurrentState;

    @OnClick(R.id.error_retry_button)
    public void retry(){
        LogUtil.d(this,"retry......");
        onRetryClick();
    }

    protected void onRetryClick() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView =findRootView(inflater,container);
        //成功时返回的视图
        mBaseContainer = rootView.findViewById(R.id.base_container);
        loadStateView(inflater,container);

        mBind = ButterKnife.bind(this, rootView);
        initView();
        initPresenter();
        loadData();
        initListener();
        return rootView;
    }

    protected  void initListener(){

    }

    /**
     *
     * @param inflater
     * @param container
     * @return
     */
    protected View findRootView(LayoutInflater inflater,ViewGroup container){
        return  inflater.inflate(R.layout.base_fragment_layout,container,false);
    }


    /**
     * 加载各种状态的view
     * @param inflater
     * @param container
     */
    private void loadStateView(LayoutInflater inflater, ViewGroup container) {
        mSuccessView = loadSuccessView(inflater,container);
        mBaseContainer.addView(mSuccessView);
        mLoadingView = loadLoadingView(inflater,container);
        mBaseContainer.addView(mLoadingView);
        mErrorView = loadErrorView(inflater,container);
        mBaseContainer.addView(mErrorView);
        mEmptyView = loadEmptyView(inflater,container);
        mBaseContainer.addView(mEmptyView);
        switchUIByPageState(PageState.NONE);
    }

    /**
     * 子类通过这个方法来切换界面
     * @param state
     */
    protected void switchUIByPageState(PageState state){
        this.mCurrentState = state;
        mSuccessView.setVisibility(mCurrentState == PageState.SUCCESS ? View.VISIBLE:View.GONE);
        mLoadingView.setVisibility(mCurrentState == PageState.LOADING ? View.VISIBLE:View.GONE);
        mEmptyView.setVisibility(mCurrentState == PageState.EMPTY ? View.VISIBLE:View.GONE);
        mErrorView.setVisibility(mCurrentState == PageState.ERROR ? View.VISIBLE:View.GONE);
        if(mCurrentState == PageState.NONE){
            mSuccessView.setVisibility(View.GONE);
            mLoadingView.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.GONE);
            mErrorView.setVisibility(View.GONE);
        }
    }

    private View loadLoadingView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_loading,container,false);
    }
    private View loadErrorView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_error,container,false);
    }
    private View loadEmptyView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_empty,container,false);
    }

    protected View loadSuccessView(LayoutInflater inflater, ViewGroup container) {
        int resId = getRootViewResId();
        return inflater.inflate(resId, container, false);
    }

    protected void initView() {
        //初始化界面元素
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mBind != null) {
            mBind.unbind();
        }
    }

    protected void release(){
        //释放资源
    }

    protected void initPresenter() {
        //创建presnter
    }

    protected void loadData() {
        //加载数据
    }

    protected abstract int getRootViewResId();
}
