package com.prashantb.ecommercetest.productDetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.prashant.apilib.models.ProductDetails;
import com.prashant.apilib.models.Tax;
import com.prashant.apilib.models.Variant;
import com.prashantb.ecommercetest.BaseActivity;
import com.prashantb.ecommercetest.R;
import com.prashantb.ecommercetest.common.AppConstants;
import com.prashantb.ecommercetest.common.Utils;
import com.prashantb.ecommercetest.productDetails.adapter.VariantAdapter;

import java.util.List;

import butterknife.BindView;

public class ProductDetailsActivity extends BaseActivity {
    private static final String TAG = "ProductDetailsActivity";
    private ProductDetails productDetails;

    @BindView(R.id.toolbarTitleTextView)
    TextView toolbarTitleTextView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.productIdTextView)
    TextView productIdTextView;
    @BindView(R.id.dateAddedTextView)
    TextView dateAddedTextView;
    @BindView(R.id.taxTextView)
    TextView taxTextView;
    @BindView(R.id.variantsRecyclerView)
    RecyclerView variantsRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setMyContentView(R.layout.activity_product_details);

        initialize();
    }

    private void initialize() {
        getBundleData();
        setUPToolbar();
        setUpViews();
    }

    private void setUPToolbar() {
        toolbarTitleTextView.setText(productDetails != null ? productDetails.getName() : "");
        setToolbar(toolbar, R.drawable.ic_arrow_back);
    }

    private void getBundleData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            productDetails = (ProductDetails) extras.getSerializable(AppConstants.KEY_MODEL);
        }
    }

    private void setUpViews() {
        if (productDetails != null) {
            productIdTextView.setText(getString(R.string.product_id, productDetails.getId()));

            dateAddedTextView.setText(getString(R.string.date_added
                    , Utils.getDateInRequiredFormat(productDetails.getDateAdded(), AppConstants.API_DATE_FORMAT, AppConstants.DISPLAY_DATE_FORMAT)));

            Tax tax = productDetails.getTax();
            if (tax != null) {
                String taxStr = getString(R.string.tax, tax.getName(), Utils.roundTwoDecimals(tax.getValue()));
                taxTextView.setText(taxStr);
            } else {
                taxTextView.setText(R.string.na);
            }

            List<Variant> variants = productDetails.getVariants();
            if (variants != null && variants.size() > 0) {
                variantsRecyclerView.setLayoutManager(new LinearLayoutManager(ProductDetailsActivity.this));
                VariantAdapter adapter = new VariantAdapter(ProductDetailsActivity.this, variants);
                variantsRecyclerView.setAdapter(adapter);
            }
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
}
