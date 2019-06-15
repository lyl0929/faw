package com.nua.faw.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.text.TextUtils;
import android.view.View;

import com.nua.faw.bean.LoginBean;

/**
 * 作者：yuhb on 2018/4/19 13:23.
 * 描述：
 */

public final class AccountHelper {

    private LoginBean user;
    private Application application;
    @SuppressLint("StaticFieldLeak")
    private static AccountHelper instances;

    private AccountHelper(Application application) {
        this.application = application;
    }

    public static void init(Application application) {
        instances = new AccountHelper(application);
    }

    public static boolean isLogin() {
        return !TextUtils.isEmpty(getUserId()) && !TextUtils.isEmpty(getToken());
    }

    public static String getToken() {
        String cookie = getUser().getToken();
        return cookie == null ? "" : cookie;
    }

    public static String getUserId() {
        return getUser().getUserId();
    }

    public synchronized static LoginBean getUser() {
        if (instances == null) {
            return new LoginBean();
        }
        if (instances.user == null) {
            instances.user = SharedPreferencesHelper.loadFormSource(instances.application, LoginBean.class);
        }
        if (instances.user == null) {
            instances.user = new LoginBean();
        }
        return instances.user;
    }

    public static void updateUserCache(LoginBean user) {
        if (user == null) {
            return;
        }
//        // 保留Token信息
        if (TextUtils.isEmpty(user.getToken()) && instances.user != user) {
            user.setToken(instances.user.getToken());
        }

        if (TextUtils.isEmpty(user.getUserId()) && instances.user != user) {
            user.setUserId(instances.user.getUserId());
        }

//        if (TextUtils.isEmpty(user.getHeadImgURL()) && instances.user != user)
//            user.setHeadImgURL(instances.user.getHeadImgURL());
//
        if (TextUtils.isEmpty(user.getMobile()) && instances.user != user) {
            user.setMobile(instances.user.getMobile());
        }

        if (TextUtils.isEmpty(user.getRealName()) && instances.user != user) {
            user.setRealName(instances.user.getRealName());
        }

        if (TextUtils.isEmpty(user.getNickName()) && instances.user != user) {
            user.setNickName(instances.user.getNickName());
        }

        instances.user = user;
        SharedPreferencesHelper.save(instances.application, user);
    }

    public static void clearUserCache() {
        instances.user = null;
        SharedPreferencesHelper.remove(instances.application, LoginBean.class);
    }

    public static void login(LoginBean user) {
        // 保存缓存
        updateUserCache(user);

    }

    /**
     * 退出登陆操作需要传递一个View协助完成延迟检测操作
     *
     * @param view     View
     * @param runnable 当清理完成后回调方法
     */
    public static void logout(final View view, final Runnable runnable) {
        // 清除用户缓存
        clearUserCache();
        // 等待缓存清理完成
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.removeCallbacks(this);
                LoginBean user = SharedPreferencesHelper.load(instances.application, LoginBean.class);
                // 判断当前用户信息是否清理成功
                if (user == null || TextUtils.isEmpty(user.getUserId())) {
                    clearAndPostBroadcast(instances.application);
                    runnable.run();
                } else {
                    view.postDelayed(this, 200);
                }
            }
        }, 200);

    }

    /**
     * 当前用户信息清理完成后调用方法清理服务等信息
     *
     * @param application Application
     */
    private static void clearAndPostBroadcast(Application application) {
//        EventBus.getDefault().post(new RefreshLoginDataEvent());
    }

}
