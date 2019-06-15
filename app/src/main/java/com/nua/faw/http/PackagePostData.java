package com.nua.faw.http;

import android.text.TextUtils;

import com.nua.faw.bean.LoginBean;
import com.nua.faw.utils.AccountHelper;
import com.nua.faw.utils.EncodeUtils;
import com.nua.faw.utils.Util;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;

/**
 * 作者：yuhb on 2017/8/8 17:18.
 * 描述：
 */

public class PackagePostData {

    private static String version = Util.getVersionName();

    private static String addToken(JSONObject root) {
        try {
            LoginBean login = AccountHelper.getUser();
            if (TextUtils.isEmpty(login.getUserId())) {
                root.put("token", "");
            } else {
                root.put("token", login.getToken());
            }
            root.put("version", version);
            return root.toString();

        } catch (JSONException e) {
            e.printStackTrace();
            return root.toString();
        }
    }


    /**
     * 获取验证码接口
     *
     * @param mobile
     * @return
     */
    public static String queryAuthCode(String mobile, String systemFlag, String type) {
        JSONObject root = new JSONObject();
        JSONObject params = new JSONObject();
        try {
            root.put("cmd", "queryAuthCode");
            addToken(root);
            root.put("params", params);
            params.put("mobile", mobile);
            params.put("appName", "zjy");
            params.put("systemFlag", systemFlag);
            params.put("type", type);
            return root.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 检查APP更新接口
     */
    public static String queryAppUpdate() {
        JSONObject root = new JSONObject();
        JSONObject params = new JSONObject();
        try {
            root.put("cmd", "queryAppUpdate");
            addToken(root);
            root.put("params", params);
            params.put("version", version);
            params.put("platform", "android");
            params.put("appName", "zjy");

            return root.toString();
        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    /**
     * 初始化接口
     *
     * @return
     */
    public static String init() {
        JSONObject root = new JSONObject();
        JSONObject params = new JSONObject();
        try {
            root.put("cmd", "queryInit");
            addToken(root);
            root.put("params", params);
            return root.toString();
        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }



    public static RequestBody queryCorpInfoForApp(String corpId) {
        JSONObject root = new JSONObject();
        JSONObject params = new JSONObject();
        try {
            root.put("cmd", "queryCorpInfoForApp");
            addToken(root);
            root.put("params", params);
            params.put("corpId", corpId);
            return createRequestBody(root.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static RequestBody createRequestBody(String params) {
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                EncodeUtils.urlEncode((params)));
        return body;
    }


}
