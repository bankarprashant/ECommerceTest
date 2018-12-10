package com.prashantb.ecommercetest;

import android.app.Application;

import com.prashant.apilib.APILib;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Initialize APILib
        APILib.newInstance(getApplicationContext());
    }
}
