package com.nua.faw.base;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * BasePresenter,子类继承后，在实现对应具体页面的Presenter
 */
public class BasePresenter<V> {


    private CompositeDisposable mCompositeSubscription;
    protected V mView;

    public BasePresenter(V view) {
        this.mView = view;
    }

    public void onDestory() {
        this.mView = null;
        onUnsubscribe();
    }


    /**
     * 所有rx订阅后，需要调用此方法，用于在detachView时取消订阅
     */
    public void addSubscription(Disposable subscribe) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeDisposable();
        }
        mCompositeSubscription.add(subscribe);
    }

    /**
     * 移除某个subscribe
     */
    public void removeSubscription(Disposable subscribe) {
        if (mCompositeSubscription == null || subscribe == null) {
            return;
        }
        mCompositeSubscription.remove(subscribe);
    }

    /**
     * 取消本页面所有订阅
     */
    protected void onUnsubscribe() {
        if (mCompositeSubscription != null ) {
            mCompositeSubscription.clear();
        }
    }


}

