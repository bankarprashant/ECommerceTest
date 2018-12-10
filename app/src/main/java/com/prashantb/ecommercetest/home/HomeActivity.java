package com.prashantb.ecommercetest.home;

import android.os.Bundle;

import com.prashant.apilib.INetworkClient;
import com.prashantb.ecommercetest.BaseActivity;
import com.prashantb.ecommercetest.R;

public class HomeActivity extends BaseActivity implements HomeContract.View {
    private static final String TAG = "HomeActivity";
    private HomeContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMyContentView(R.layout.activity_home);

        initialize();
    }

    private void initialize() {
        initPresenter();
        getProductsApiCall();
    }

    private void initPresenter() {
        presenter = new HomePresenter(this);
    }

    private void getProductsApiCall() {
        presenter.getProductsApiCall();
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public INetworkClient getNetworkClient() {
        return iNetworkClient;
    }

    @Override
    public void apiSuccess() {

    }

    @Override
    public void apiError(String msg) {

    }

    @Override
    public void apiError(int msg) {

    }
}
