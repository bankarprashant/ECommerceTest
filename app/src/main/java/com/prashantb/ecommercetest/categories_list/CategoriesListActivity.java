package com.prashantb.ecommercetest.categories_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.prashant.apilib.models.Category;
import com.prashant.apilib.models.ProductDetails;
import com.prashantb.ecommercetest.BaseActivity;
import com.prashantb.ecommercetest.R;
import com.prashantb.ecommercetest.common.AppConstants;
import com.prashantb.ecommercetest.common.IOnItemClicked;
import com.prashantb.ecommercetest.common.ProductTypeEnum;
import com.prashantb.ecommercetest.home.categories.CategoriesAdapter;
import com.prashantb.ecommercetest.product_list.ProductListActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class CategoriesListActivity extends BaseActivity implements IOnItemClicked
        , CategoriesListContract.View {
    private static final String TAG = "CategoriesListActivity";
    private ArrayList<Category> categoryList;
    private CategoriesAdapter adapter;
    private Map<Integer, Category> categoryMap;
    private CategoriesListContract.Presenter presenter;

    @BindView(R.id.toolbarTitleTextView)
    TextView toolbarTitleTextView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.categoriesRecyclerView)
    RecyclerView categoriesRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setMyContentView(R.layout.activity_categories_list);

        initialize();
    }

    private void initialize() {
        getBundleData();
        setUPToolbar();
        initPresenter();
        setUpViews();
    }

    private void getBundleData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            toolbarTitleTextView.setText(bundle.getString(AppConstants.KEY_TITLE, ""));
            categoryMap = (Map<Integer, Category>) bundle.getSerializable(AppConstants.KEY_MAP);
            categoryList = (ArrayList<Category>) bundle.getSerializable(AppConstants.KEY_MODEL_LIST);
        }
    }

    private void setUPToolbar() {
        setToolbar(toolbar, R.drawable.ic_arrow_back);
    }

    private void initPresenter() {
        presenter = new CategoriesPresenter(this);
    }

    private void setUpViews() {
        if (categoryList != null) {
            categoriesRecyclerView.setLayoutManager(new GridLayoutManager(CategoriesListActivity.this, 2));
            adapter = new CategoriesAdapter(this);
            adapter.setCategoriesList(categoryList);
            categoriesRecyclerView.setAdapter(adapter);
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
        ArrayList<Category> categoriesList = adapter.getCategoriesList();
        Category category = categoriesList.get(position);

        List<ProductDetails> productDetails = category.getProductDetails();
        List<Integer> childCategories = category.getChildCategories();

        if (productDetails != null && productDetails.size() > 0) {
            showProductListActivity(category.getName(), ProductTypeEnum.CATEGORIES, category);
        } else if (childCategories != null && childCategories.size() > 0) {
            showCategoriesListActivity(category.getName(), presenter.getSubCategories(categoryMap, childCategories));
        }
    }

    private void showProductListActivity(String title, ProductTypeEnum productTypeEnum, Category category) {
        Intent intent = new Intent(CategoriesListActivity.this, ProductListActivity.class);

        intent.putExtra(AppConstants.KEY_TITLE, title);
        intent.putExtra(AppConstants.TYPE_RATING, productTypeEnum);
        intent.putExtra(AppConstants.KEY_MODEL_LIST, new ArrayList<>(category.getProductDetails()));

        startActivity(intent);
    }

    private void showCategoriesListActivity(String title, ArrayList<Category> categoryList) {
        Intent intent = new Intent(CategoriesListActivity.this, CategoriesListActivity.class);

        intent.putExtra(AppConstants.KEY_TITLE, title);
        intent.putExtra(AppConstants.KEY_MAP, (Serializable) categoryMap);
        intent.putExtra(AppConstants.KEY_MODEL_LIST, categoryList);

        startActivity(intent);
    }
}
