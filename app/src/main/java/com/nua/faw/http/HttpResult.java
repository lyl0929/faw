package com.nua.faw.http;

import java.io.Serializable;

/**
 * @author yu
 */
public class HttpResult<T> implements Serializable {
    private String resultNote;
    private int result = 0;
    private T detail;

    public String getErrorMsg() {
        return resultNote;
    }

    public void setErrorMsg(String resultNote) {
        this.resultNote = resultNote;
    }

    public int getCode() {
        return result;
    }

    public void setCode(int result) {
        this.result = result;
    }

    public T getResponse() {
        return detail;
    }

    public void setResponse(T detail) {
        this.detail = detail;
    }
}
