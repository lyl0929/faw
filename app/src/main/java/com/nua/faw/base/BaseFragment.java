package com.nua.faw.base;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nua.faw.widget.TitleToolbar;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：yuhb on 2018/2/27 09:38.
 * 邮箱：yuhb@njext.com
 * 描述：
 */


public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView {
    protected P mPresenter;
    protected FragmentActivity _mActivity;
    protected View rootView;
    private Unbinder butterKnife;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _mActivity = (FragmentActivity) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(getContentViewLayoutID(), container, false);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        butterKnife = ButterKnife.bind(this, view);
        mPresenter = createPresenter();
        initViews(savedInstanceState);

    }

    //    /**
//     * 初始化 Toolbar
//     */
    protected void initToolBar(TitleToolbar toolbar, boolean homeAsUpEnabled, String title) {
        ((BaseActivity) getActivity()).initToolBar(toolbar, title);
    }


//    protected void setTranslucentStatusBar() {
//        ((BaseActivity) getActivity()).setTranslucentStatusBar();
//    }
//
//
//    protected void setStatusBarLightMode() {
//        ((BaseActivity) getActivity()).setStatusBarLightMode();
//    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isregisterEventBus() && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (null != mPresenter) {
            mPresenter.onDestory();
        }
        if (null != butterKnife) {
            butterKnife.unbind();
        }
    }

    /**
     * bind layout resource file
     *
     * @return id of layout resource
     */
    protected abstract int getContentViewLayoutID();


    /**
     * init all views and add events
     */
    protected abstract void initViews(Bundle savedInstanceState);


    protected abstract P createPresenter();

    protected abstract boolean isregisterEventBus();


    @Override
    public void onError(int type) {

    }

    @Override
    public void toast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading(int type) {

    }

    @Override
    public void dismissLoading(int type) {

    }

    /**
     * 绑定生命周期
     */
    @Override
    public <X> AutoDisposeConverter<X> bindAutoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider
                .from(this, Lifecycle.Event.ON_DESTROY));
    }

}
