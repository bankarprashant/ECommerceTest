package com.prashantb.ecommercetest;

import android.support.annotation.StringRes;

import com.prashant.apilib.INetworkClient;

public interface BaseView {

    void showProgressBar();

    void hideProgressBar();

    INetworkClient getNetworkClient();

    void apiSuccess();

    void apiError(String msg);

    void apiError(@StringRes int msg);
}
