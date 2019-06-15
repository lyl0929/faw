package com.nua.faw.base;

import android.os.Environment;

import com.nua.faw.BuildConfig;

import java.io.File;

/**
 */

public class Constant {

    private static final String BASE_URL = BuildConfig.BASE_URL;
    public static final String WEB_BASE_URL = BuildConfig.WEB_BASE_URL;
    public static final boolean DEBUG_MODE = BuildConfig.LOG_DEBUG;
    public static final int VERSION = BuildConfig.VERSION_CODE;
    private static final String IP = "119.23.51.14";//阿里云IP
    private static final String LOCAL_IP = "192.168.3.20";
    private static final String PORT = "8080";


    // 默认存放文件下载的路径
    public final static String DEFAULT_SAVE_FILE_PATH = Environment
            .getExternalStorageDirectory()
            + File.separator
            + "yq"
            + File.separator + "download" + File.separator;


    public static String getBaseUrl() {
        return BASE_URL;
    }


    public static final int REQ_CODE_SETTING =0x008;



}

