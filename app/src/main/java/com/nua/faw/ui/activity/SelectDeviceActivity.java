package com.nua.faw.ui.activity;

import android.os.Bundle;

import com.nua.faw.base.BaseActivity;
import com.nua.faw.base.BasePresenter;

/**
 * 作者：yuhb on 2019/6/14 16:28.
 * 描述：
 */
public class SelectDeviceActivity extends BaseActivity {
    @Override
    protected int getContentViewLayoutID() {
        return 0;
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
