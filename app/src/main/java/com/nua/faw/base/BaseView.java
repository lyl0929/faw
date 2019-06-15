package com.nua.faw.base;

import com.uber.autodispose.AutoDisposeConverter;

public interface BaseView {
    void onError(int type);

    void toast(String msg);

    /**
     * 显示加载动画
     */
    void showLoading(int type);

    /**
     * 隐藏加载
     */
    void dismissLoading(int type);

    /**
     * 绑定生命周期
     */
    <X> AutoDisposeConverter<X> bindAutoDispose();

}