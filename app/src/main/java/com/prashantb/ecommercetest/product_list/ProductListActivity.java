package com.prashantb.ecommercetest.product_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.prashant.apilib.models.ProductDetails;
import com.prashantb.ecommercetest.BaseActivity;
import com.prashantb.ecommercetest.R;
import com.prashantb.ecommercetest.common.AppConstants;
import com.prashantb.ecommercetest.common.IOnItemClicked;
import com.prashantb.ecommercetest.common.ProductTypeEnum;
import com.prashantb.ecommercetest.home.HomeActivity;
import com.prashantb.ecommercetest.home.rankings.ProductListAdapter;
import com.prashantb.ecommercetest.productDetails.ProductDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ProductListActivity extends BaseActivity implements IOnItemClicked {
    private static final String TAG = "ProductListActivity";
    private ArrayList<ProductDetails> productLsit;
    private ProductTypeEnum productTypeEnum;
    private ProductListAdapter adapter;

    @BindView(R.id.toolbarTitleTextView)
    TextView toolbarTitleTextView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.productRecyclerView)
    RecyclerView productRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setMyContentView(R.layout.activity_product_list);

        initialize();
    }

    private void initialize() {
        getBundleData();
        setUPToolbar();
        setUpViews();
    }

    private void getBundleData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            toolbarTitleTextView.setText(bundle.getString(AppConstants.KEY_TITLE, ""));
            productTypeEnum = (ProductTypeEnum) bundle.getSerializable(AppConstants.TYPE_RATING);
            productLsit = (ArrayList<ProductDetails>) bundle.getSerializable(AppConstants.KEY_MODEL_LIST);
        }
    }

    private void setUPToolbar() {
        setToolbar(toolbar, R.drawable.ic_arrow_back);
    }

    private void setUpViews() {
        if (productLsit != null) {
            productRecyclerView.setLayoutManager(new GridLayoutManager(ProductListActivity.this, 2));
            adapter = new ProductListAdapter(this, this, productTypeEnum);
            adapter.setRankingList(productLsit);
            productRecyclerView.setAdapter(adapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void itemClicked(ProductTypeEnum tag, int position) {
        List<ProductDetails> rankingList = adapter.getRankingList();
        openProductDetailsActivity(rankingList.get(position));
    }

    private void openProductDetailsActivity(ProductDetails productDetails) {
        Intent intent = new Intent(ProductListActivity.this, ProductDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstants.KEY_MODEL, productDetails);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
