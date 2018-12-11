package com.prashantb.ecommercetest.product_list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.prashantb.ecommercetest.BaseActivity;
import com.prashantb.ecommercetest.R;

import butterknife.BindView;

public class ProductListActivity extends BaseActivity {
    private static final String TAG = "ProductListActivity";

    @BindView(R.id.toolbarTitleTextView)
    TextView toolbarTitleTextView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setMyContentView(R.layout.activity_product_list);

        initialize();
    }

    private void initialize() {
        setUPToolbar();
    }

    private void setUPToolbar() {
        toolbarTitleTextView.setText("");
        setToolbar(toolbar, R.drawable.ic_arrow_back);
    }
}
