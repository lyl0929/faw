package com.nua.faw.ui.activity;

import android.os.Bundle;

import com.nua.faw.R;
import com.nua.faw.base.BaseActivity;
import com.nua.faw.base.BasePresenter;

/**
 * 作者：yuhb on 2019/6/14 16:18.
 * 描述：
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected boolean isregisterEventBus() {
        return false;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
