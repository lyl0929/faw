package com.nua.faw.utils;

/**
 * Created by yuhb on 17/2/24.
 */

/**
 * This is reflect exception
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class ReflectException extends RuntimeException {

    private static final long serialVersionUID = 312038727504126519L;

    public ReflectException(String message) {
        super(message);
    }

    public ReflectException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReflectException() {
        super();
    }

    public ReflectException(Throwable cause) {
        super(cause);
    }
}
