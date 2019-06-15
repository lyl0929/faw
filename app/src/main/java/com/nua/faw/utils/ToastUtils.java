package com.nua.faw.utils;

import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import com.nua.faw.MyApplication;


/**
 */
public class ToastUtils {

    public static void showShort(CharSequence message) {
        show(message, Toast.LENGTH_SHORT);
    }

    public static void showLong(CharSequence message) {
        show(message, Toast.LENGTH_LONG);
    }


    public static void show(CharSequence message, int duration) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        Toast toast = Toast.makeText(MyApplication.getContext(), message, duration);
        if (Looper.myLooper() == Looper.getMainLooper()) {
            toast.show();
        } else {
            HandlerUtils.runOnUI(() -> toast.show());
        }
    }

}
