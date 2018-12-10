package com.prashantb.ecommercetest.home;

import android.util.Log;

import com.prashant.apilib.Callback;
import com.prashant.apilib.common.ErrorCodeEnum;
import com.prashant.apilib.models.GetProductsResponse;

class HomePresenter implements HomeContract.Presenter {
    private static final String TAG = "HomePresenter";
    private HomeContract.View view;

    HomePresenter(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void getProductsApiCall() {

        view.getNetworkClient().getProductsData("yes", new Callback<GetProductsResponse>() {
            @Override
            public void onSuccess(GetProductsResponse response) {
                Log.d(TAG, "onSuccess() called with: matchesResponse = [" + response + "]");
            }

            @Override
            public void onError(String error, ErrorCodeEnum errorCode) {
                super.onError(error, errorCode);
                Log.d(TAG, "onError() called with: error = [" + error + "], errorCode = [" + errorCode + "]");
            }
        });
    }
}
