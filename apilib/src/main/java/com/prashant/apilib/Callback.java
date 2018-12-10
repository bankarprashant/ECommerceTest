package com.prashant.apilib;

import com.prashant.apilib.common.ErrorCodeEnum;

public abstract class Callback<T> {
    public abstract void onSuccess(T t);

    public void onError(String error, ErrorCodeEnum errorCode) {
    }
}