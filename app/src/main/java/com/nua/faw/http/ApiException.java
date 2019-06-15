package com.nua.faw.http;

/**
 * 作者：yuhb on 2018/1/11 10:35.
 * 描述：
 */

public class ApiException extends RuntimeException {

    public String code;

    public ApiException(String code, String detailMessage) {
        super(detailMessage);
        this.code = code;
    }
}
