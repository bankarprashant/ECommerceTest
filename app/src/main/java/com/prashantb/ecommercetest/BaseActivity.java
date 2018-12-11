package com.prashantb.ecommercetest;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.prashant.apilib.APILib;
import com.prashant.apilib.INetworkClient;

import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {
    public INetworkClient iNetworkClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        iNetworkClient = APILib.newInstance(getApplicationContext()).getNetworkClient();
    }

    public void setMyContentView(int layoutResID) {
        FrameLayout container = findViewById(R.id.container);

        if (container != null) {
            LayoutInflater.from(this).inflate(layoutResID, container, true);
        }

        ButterKnife.bind(this);
    }

    public void setToolbar(Toolbar toolbar, @DrawableRes int resId) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeAsUpIndicator(resId);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
