package com.nua.faw.utils;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.bumptech.glide.load.model.GlideUrl;
import com.nua.faw.MyApplication;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 选择图片库相关工具类
 */
public class Util {


    private static Handler mHandler;
    private static ExecutorService EXECUTORS_INSTANCE;

    public static Executor getExecutor() {
        if (EXECUTORS_INSTANCE == null) {
            synchronized (Util.class) {
                if (EXECUTORS_INSTANCE == null) {
                    EXECUTORS_INSTANCE = Executors.newFixedThreadPool(6);
                }
            }
        }
        return EXECUTORS_INSTANCE;
    }


    public static void runOnMainThread(Runnable runnable) {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }
        mHandler.post(runnable);
    }

    public static void runOnThread(Runnable runnable) {
        getExecutor().execute(runnable);
    }

    public static GlideUrl getGlideUrlByUser(String url) {
//        if (AccountHelper.isLogin()) {
//            return new GlideUrl(url,
//                    new LazyHeaders
//                            .Builder()
//                            .addHeader("Cookie", AccountHelper.getCookie())
//                            .build());
//        } else {
        return new GlideUrl(url);
//        }
    }


    public static boolean hasSDCard() {
        String status = Environment.getExternalStorageState();
        if (!status.equals(Environment.MEDIA_MOUNTED)) {
            return false;
        }
        return true;
    }

    public static String getCameraPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera/";// filePath:/sdcard/
    }

    public static String getSaveImageFullName() {
        return "IMG_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpg";// 照片命名
    }

//    public static ArrayList<String> toArrayList(List<Image> images) {
//        ArrayList<String> strings = new ArrayList<>();
//        for (Image i : images) {
//            strings.add(i.getPath());
//        }
//        return strings;
//    }
//
//    public static String[] toArray(List<Image> images) {
//        if (images == null) {
//            return null;
//        }
//        int len = images.size();
//        if (len == 0) {
//            return null;
//        }
//
//        String[] strings = new String[len];
//        int i = 0;
//        for (Image image : images) {
//            strings[i] = image.getPath();
//            i++;
//        }
//        return strings;
//    }


    public static String getVersionName() {
        try {
            return MyApplication
                    .getContext()
                    .getPackageManager()
                    .getPackageInfo(MyApplication.getContext().getPackageName(), 0)
                    .versionName;
        } catch (PackageManager.NameNotFoundException ex) {
            return "undefined version name";
        }
    }


    public static File getSaveFile(Context context) {
        File file = new File(context.getFilesDir(), "pic.jpg");
        return file;
    }


    public static int getColorWithAlpha(float alpha, int baseColor) {
        int a = Math.min(255, Math.max(0, (int) (alpha * 255))) << 24;
        int rgb = 0x00ffffff & baseColor;
        return a + rgb;
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean hasSoftKeys(WindowManager windowManager) {

        Display d = windowManager.getDefaultDisplay();

        DisplayMetrics realDisplayMetrics = new DisplayMetrics();

        d.getRealMetrics(realDisplayMetrics);

        int realHeight = realDisplayMetrics.heightPixels;

        int realWidth = realDisplayMetrics.widthPixels;

        DisplayMetrics displayMetrics = new DisplayMetrics();

        d.getMetrics(displayMetrics);

        int displayHeight = displayMetrics.heightPixels;

        int displayWidth = displayMetrics.widthPixels;

        return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;

    }


    /**
     * 判断app是否安装
     *
     * @param packageName
     * @return
     */
    public static boolean appIsAvilible(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }


//    /**
//     * 开始导航
//     */
//    public static void launchAmapApp(Context context, String latitude, String longitude) {
//        if (!Util.appIsAvilible(context, "com.autonavi.minimap")) {
//            ToastUtils.showLong("您手机没有安装高德APP,无法为您提供导航");
//            return;
//        }
//
//        com.javadocmd.simplelatlng.LatLng src = new com.javadocmd.simplelatlng.LatLng(
//                Double.parseDouble(latitude), Double.parseDouble(longitude));
//        com.javadocmd.simplelatlng.LatLng marsSrc = LatLngChinaTool.Baidu2Mars(src);
//        double lat = (float) marsSrc.getLatitude();
//        double lon = (float) marsSrc.getLongitude();
//        String poiName = "default";
//        poiName = "到这里";
//        // 以下是调高德地图导航的方法
//        Intent intent = new Intent();
//        intent.setData(android.net.Uri
//                .parse("androidamap://navi?sourceApplication=yongche&poiname=" + poiName + "&lat="
//                        + lat
//                        + "&lon="
//                        + lon + "&dev=0&style=2"));
//        intent.addCategory("android.intent.category.DEFAULT");
//        intent.setPackage("com.autonavi.minimap");
//        context.startActivity(intent);
//    }


    public static void launchBaiduApp(Context context, String latitude, String longitude) {
        if (!Util.appIsAvilible(context, "com.baidu.BaiduMap")) {
            ToastUtils.showLong("您手机没有安装百度APP,无法为您提供导航");
            return;
        }

        double lat = Double.parseDouble(latitude);
        double lon = Double.parseDouble(longitude);

        try {
            Intent intent = Intent.getIntent("intent://map/marker?location="
                    + lat
                    + ","
                    + lon
                    + "&title="
                    + "位置"
                    + "&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
//            intent.setData(Uri.parse());
            context.startActivity(intent);
        } catch (Exception e) {
//            Toast.makeText(this, R.string.install_baiduapk, Toast.LENGTH_LONG)
//                    .show();
//
//            Intent intent = new Intent();
//            intent.setAction("android.intent.action.VIEW");
//            Uri content_url = Uri
//                    .parse("http://as.baidu.com/a/item?docid=2947238&f=web_alad_6");
//            intent.setData(content_url);
//            startActivity(intent);
//            e.printStackTrace();
        }

    }

    public static boolean isLetterDigit(String str) {
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            } else if (Character.isLetter(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }
        String regex = "^[a-zA-Z0-9]+$";
        boolean isRight = isDigit && isLetter && str.matches(regex);
        return isRight;
    }

    /**
     * 判断服务是否开启
     *
     * @return
     */
    public static boolean isServiceRunning(Context context, String ServiceName) {
        if (("").equals(ServiceName) || ServiceName == null) {
            return false;
        }
        ActivityManager myManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager
                .getRunningServices(30);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().toString()
                    .equals(ServiceName)) {
                return true;
            }
        }
        return false;
    }


    public static Drawable getAppIcon(Context context) {
        try {
            return context.getPackageManager().getApplicationIcon(context.getPackageName());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        //canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;

    }


//    public static LatLng changToLatLng(YongcheLocation location) {
//        if (location != null) {
//            return new LatLng(location.getLatitude(), location.getLongitude());
//        } else {
//            return null;
//        }
//    }

    //查找有序数组中是否包含某个值的用法
    public static boolean useArraysBinarySearch(String[] arr, String targetValue) {
        return Arrays.asList(arr).contains(targetValue);
//        int a = Arrays.binarySearch(arr, targetValue);
//        if (a > 0) {
//            return true;
//        } else {
//            return false;
//        }
    }


    public static String getHour(String time) {
        String[] times = time.split(":");
        String hoursTemp = times[0];
        String hour = "0";
        if (hoursTemp.substring(0).equals("0")) {
            hour = hoursTemp.substring(1);
        } else {
            hour = hoursTemp;
        }

        return hour;


    }

    public static String getMinute(String time) {
        String[] times = time.split(":");
        String minuteTemp = times[1];
        String minute = "0";
        //十位数为0
        if (minuteTemp.substring(0).equals("0")) {
            minute = minuteTemp.substring(1);
        } else {
            minute = minuteTemp;
        }

        return minute;


    }

}







