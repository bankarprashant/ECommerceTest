package com.prashant.apilib;

import android.content.Context;

import com.prashant.apilib.common.APIConstants;

public class APILib {
    private static APILib APILib;
    private static Context appContext;
    private INetworkClient iNetworkClient;

    private APILib(Context context) {
        appContext = context;
    }

    public static com.prashant.apilib.APILib newInstance(Context context) {
        if (APILib == null) {
            APILib = new APILib(context.getApplicationContext());
        }
        return APILib;
    }

    public INetworkClient getNetworkClient() {
        if (appContext == null) {
            return null;
        }
        if (iNetworkClient == null) {
            iNetworkClient = new RestClient();
        }
        return iNetworkClient;
    }

    public static Context getContext() {
        return appContext;
    }

    public String getAppUrl() {
        return APIConstants.DOMAIN_URL;
    }
}

