package com.nua.faw.http;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.nua.faw.utils.AccountHelper;
import com.nua.faw.utils.ToastUtils;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.adapter.rxjava2.HttpException;


/**
 *
 */
public final class ErrorHandler {

    private static final String ERROR_CODE_TIME_OUT = "1000";
    private static final String ERROR_CODE_HTTP_EX = "1001";
    private static final String ERROR_CODE_PARSE_EX = "1002";
    private static final String ERROR_CODE_BUSY_EX = "1003";
    private static final String ERROR_CODE_NET_EX = "1004";

    private static Map<String, String> ERR = new HashMap<>(8);

    static {
        ERR.put(ERROR_CODE_TIME_OUT, "网络不畅，请稍后再试！");
        ERR.put(ERROR_CODE_HTTP_EX, "服务器异常，请稍后再试！");
        ERR.put(ERROR_CODE_PARSE_EX, "数据解析异常！");
        ERR.put(ERROR_CODE_BUSY_EX, "服务器繁忙！");
        ERR.put(ERROR_CODE_NET_EX, "请检查网络");
    }

    public static ApiException handleException(Throwable e, boolean showToast) {

        ApiException exception = parseException(e);
        int code = Integer.parseInt(exception.code);
        if (code == 12) {
            AccountHelper.clearUserCache();
            // 跳转到登陆页面
//            context.startActivity(new Intent(context, LoginActivity.class));
            // 结束除LoginActivity之外的所有Activity
//            AppManager.finishAllActivity(LoginActivity.class);
        } else {
            if (showToast) {
                ToastUtils.showShort(exception.getMessage());
            }
        }

        return exception;
    }


    private static ApiException parseException(Throwable e) {
        if (e instanceof ApiException) {
            return (ApiException) e;
        } else if (e instanceof NetworkDisconnectException) {// 网络链接断开
            return new ApiException(ERROR_CODE_NET_EX, ERR.get(ERROR_CODE_NET_EX));
        } else if (e instanceof ConnectException
                || e instanceof SocketTimeoutException) {// 超时
            return new ApiException(ERROR_CODE_TIME_OUT, ERR.get(ERROR_CODE_TIME_OUT));
        } else if (e instanceof HttpException) {// server 异常
            return new ApiException(ERROR_CODE_HTTP_EX, ERR.get(ERROR_CODE_HTTP_EX));
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            return new ApiException(ERROR_CODE_PARSE_EX, ERR.get(ERROR_CODE_PARSE_EX));
        } else {
            return new ApiException(ERROR_CODE_BUSY_EX, ERR.get(ERROR_CODE_BUSY_EX));
        }
    }
}
