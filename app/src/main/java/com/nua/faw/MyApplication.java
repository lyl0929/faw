package com.nua.faw;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.support.multidex.MultiDex;

import com.nua.faw.utils.AccountHelper;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;


//import com.umeng.message.IUmengRegisterCallback;
//import com.umeng.message.MsgConstant;
//import com.umeng.message.PushAgent;
//import com.umeng.message.UTrack;
//import com.umeng.message.UmengMessageHandler;
//import com.umeng.message.UmengNotificationClickHandler;
//import com.umeng.message.entity.UMessage;
//
//import org.android.agoo.xiaomi.MiPushRegistar;

/**
 * 作者：yuhb on 2018/2/27 09:28.
 * 描述：
 */

public class MyApplication extends Application {
    private Handler handler;
    private static Context mContext;
    public static HashMap<String, Integer> locationMap = new HashMap<>();
    public static InputStream inputStream;

    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), "f189a5e716", false);
        try {
            inputStream = getAssets().open("client.crt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        mContext = this.getApplicationContext();
        // 初始化账户基础信息
        AccountHelper.init(this);
//        LocationHelper.init(this);
//        InitHelper.init(this);
//        startSystemService();
//        initUpush();

    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }


    public static Context getContext() {
        return mContext;
    }

    /**
     * 启动服务
     */
//    public void startSystemService() {
//        YongcheService.startService(this);
//    }




}
