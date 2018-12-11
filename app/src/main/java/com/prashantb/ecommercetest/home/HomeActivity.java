package com.prashantb.ecommercetest.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.prashant.apilib.INetworkClient;
import com.prashant.apilib.models.Category;
import com.prashant.apilib.models.ProductDetails;
import com.prashantb.ecommercetest.BaseActivity;
import com.prashantb.ecommercetest.CategoriesListActivity;
import com.prashantb.ecommercetest.R;
import com.prashantb.ecommercetest.common.AppConstants;
import com.prashantb.ecommercetest.common.IOnItemClicked;
import com.prashantb.ecommercetest.common.ProductTypeEnum;
import com.prashantb.ecommercetest.common.Utils;
import com.prashantb.ecommercetest.home.categories.CategoriesAdapter;
import com.prashantb.ecommercetest.home.rankings.ProductListAdapter;
import com.prashantb.ecommercetest.productDetails.ProductDetailsActivity;
import com.prashantb.ecommercetest.product_list.ProductListActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity implements HomeContract.View
        , IOnItemClicked {
    private static final String TAG = "HomeActivity";
    private HomeContract.Presenter presenter;
    private CategoriesAdapter categoriesAdapter;
    private Map<Integer, Category> categoryMap;
    private ProductListAdapter mostOrderedAdapter, mostViewedAdapter, mostSharedAdapter;

    @BindView(R.id.categoriesRecyclerView)
    RecyclerView categoriesRecyclerView;
    @BindView(R.id.mostOrderedRecyclerView)
    RecyclerView mostOrderedRecyclerView;
    @BindView(R.id.mostSharedRecyclerView)
    RecyclerView mostSharedRecyclerView;
    @BindView(R.id.mostViewedRecyclerView)
    RecyclerView mostViewedRecyclerView;
    @BindView(R.id.orderedAllTextView)
    TextView orderedAllTextView;
    @BindView(R.id.sharedAllTextView)
    TextView sharedAllTextView;
    @BindView(R.id.viewedAllTextView)
    TextView viewedAllTextView;
    @BindView(R.id.toolbarTitleTextView)
    TextView toolbarTitleTextView;
    @BindView(R.id.progressContainer)
    RelativeLayout progressContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMyContentView(R.layout.activity_home);

        initialize();
    }

    private void initialize() {
        setToolbarTitle();
        initPresenter();
        setAdapter();
        getProductsApiCall();
    }

    private void setAdapter() {
        categoriesRecyclerView.setLayoutManager(new GridLayoutManager(HomeActivity.this
                , 2
                , LinearLayoutManager.HORIZONTAL
                , false));

        categoriesAdapter = new CategoriesAdapter(this);
        categoriesRecyclerView.setAdapter(categoriesAdapter);

        mostOrderedRecyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this
                , LinearLayoutManager.HORIZONTAL
                , false));
        mostOrderedAdapter = new ProductListAdapter(this, this, ProductTypeEnum.MOST_ORDERED);
        mostOrderedRecyclerView.setAdapter(mostOrderedAdapter);

        mostSharedRecyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this
                , LinearLayoutManager.HORIZONTAL
                , false));
        mostSharedAdapter = new ProductListAdapter(this, this, ProductTypeEnum.MOST_SHARED);
        mostSharedRecyclerView.setAdapter(mostSharedAdapter);

        mostViewedRecyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this
                , LinearLayoutManager.HORIZONTAL
                , false));
        mostViewedAdapter = new ProductListAdapter(this, this, ProductTypeEnum.MOST_VIEWED);
        mostViewedRecyclerView.setAdapter(mostViewedAdapter);
    }

    private void setToolbarTitle() {
        toolbarTitleTextView.setText(R.string.home);
    }

    private void initPresenter() {
        presenter = new HomePresenter(this);
    }

    private void getProductsApiCall() {
        presenter.getProductsApiCall();
    }

    @Override
    public void showProgressBar() {
        Utils.showProgressBar(progressContainer);
    }

    @Override
    public void hideProgressBar() {
        Utils.hideProgressBar(progressContainer);
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
        Utils.showToast(getApplicationContext(), msg);
    }

    @Override
    public void apiError(int msg) {
        Utils.showToast(getApplicationContext(), msg);
    }

    @Override
    public void itemClicked(ProductTypeEnum tag, int position) {
        switch (tag) {
            case MOST_ORDERED:
                List<ProductDetails> rankingList = mostOrderedAdapter.getRankingList();
                openProductDetailsActivity(rankingList.get(position));
                break;

            case MOST_SHARED:
                List<ProductDetails> rankingListShared = mostSharedAdapter.getRankingList();
                openProductDetailsActivity(rankingListShared.get(position));
                break;

            case MOST_VIEWED:
                List<ProductDetails> rankingListViewed = mostViewedAdapter.getRankingList();
                openProductDetailsActivity(rankingListViewed.get(position));
                break;

            case CATEGORIES:
                ArrayList<Category> categoriesList = categoriesAdapter.getCategoriesList();
                Category category = categoriesList.get(position);

                List<ProductDetails> productDetails = category.getProductDetails();
                List<Integer> childCategories = category.getChildCategories();

                if (productDetails != null && productDetails.size() > 0) {
                    showProductListActivity(category.getName(), ProductTypeEnum.CATEGORIES);
                } else if (childCategories != null && childCategories.size() > 0) {
                    if (categoryMap != null) {

                        ArrayList<Category> categoryList = new ArrayList<>();

                        for (int i = 0; i < childCategories.size(); i++) {
                            int id = childCategories.get(i);

                            if (categoryMap.containsKey(id)) {
                                categoryList.add(categoryMap.get(id));
                            }
                        }
                        showCategoriesListActivity(category.getName(), categoryList);
                    }
                }
                break;
        }
    }

    @Override
    public void setMostViewedList(ArrayList<ProductDetails> productList) {
        mostViewedAdapter.setRankingList(productList);
    }

    @Override
    public void setMostOrderedList(ArrayList<ProductDetails> productList) {
        mostOrderedAdapter.setRankingList(productList);
    }

    @Override
    public void setMostSharedList(ArrayList<ProductDetails> productList) {
        mostSharedAdapter.setRankingList(productList);
    }

    @Override
    public void setCategoriesList(ArrayList<Category> categoryList) {
        categoriesAdapter.setCategoriesList(categoryList);
    }

    @OnClick(R.id.categoryAllTextView)
    public void categoryAllClicked() {
        showCategoriesListActivity("Product Categories", categoriesAdapter.getCategoriesList());
    }

    @OnClick(R.id.viewedAllTextView)
    public void viewAllClicked() {
        showProductListActivity("Most Viewed Products", ProductTypeEnum.MOST_VIEWED);
    }

    @OnClick(R.id.orderedAllTextView)
    public void orderedAllClicked() {
        showProductListActivity("Most Ordered Products", ProductTypeEnum.MOST_ORDERED);
    }

    @OnClick(R.id.sharedAllTextView)
    public void sharedAllClicked() {
        showProductListActivity("Most Shared Products", ProductTypeEnum.MOST_SHARED);
    }

    @Override
    public void setCategoriesMap(Map<Integer, Category> categoryMap) {
        this.categoryMap = categoryMap;
    }

    private void showProductListActivity(String title, ProductTypeEnum productTypeEnum) {
        Intent intent = new Intent(HomeActivity.this, ProductListActivity.class);

        intent.putExtra(AppConstants.KEY_TITLE, title);
        intent.putExtra(AppConstants.TYPE_RATING, productTypeEnum);
        intent.putExtra(AppConstants.KEY_MODEL_LIST, new ArrayList<>(mostSharedAdapter.getRankingList()));

        startActivity(intent);
    }

    private void showCategoriesListActivity(String title, ArrayList<Category> categoryList) {
        Intent intent = new Intent(HomeActivity.this, CategoriesListActivity.class);

        intent.putExtra(AppConstants.KEY_TITLE, title);
        intent.putExtra(AppConstants.KEY_MAP, (Serializable) categoryMap);
        intent.putExtra(AppConstants.KEY_MODEL_LIST, categoryList);

        startActivity(intent);
    }

    private void openProductDetailsActivity(ProductDetails productDetails) {
        Intent intent = new Intent(HomeActivity.this, ProductDetailsActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstants.KEY_MODEL, productDetails);
        intent.putExtras(bundle);

        startActivity(intent);
    }
}
