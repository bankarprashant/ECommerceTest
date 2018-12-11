package com.prashantb.ecommercetest.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.prashant.apilib.INetworkClient;
import com.prashant.apilib.models.ProductDetails;
import com.prashantb.ecommercetest.BaseActivity;
import com.prashantb.ecommercetest.R;
import com.prashantb.ecommercetest.common.AppConstants;
import com.prashantb.ecommercetest.common.IOnItemClicked;
import com.prashantb.ecommercetest.common.ProductTypeEnum;
import com.prashantb.ecommercetest.common.Utils;
import com.prashantb.ecommercetest.home.categories.CategoriesAdapter;
import com.prashantb.ecommercetest.home.rankings.RankingAdapter;
import com.prashantb.ecommercetest.productDetails.ProductDetailsActivity;
import com.prashantb.ecommercetest.product_list.ProductListActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity implements HomeContract.View
        , IOnItemClicked {
    private static final String TAG = "HomeActivity";
    private HomeContract.Presenter presenter;
    private CategoriesAdapter categoriesAdapter;
    private RankingAdapter mostOrderedAdapter, mostViewedAdapter, mostSharedAdapter;

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
//        categoriesRecyclerView.setLayoutManager(new GridLayoutManager(HomeActivity.this
//                , 2
//                , LinearLayoutManager.HORIZONTAL
//                , false));
//
//        categoriesAdapter = new CategoriesAdapter();
//        categoriesRecyclerView.setAdapter(categoriesAdapter);

        mostOrderedRecyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this
                , LinearLayoutManager.HORIZONTAL
                , false));
        mostOrderedAdapter = new RankingAdapter(this, this, ProductTypeEnum.MOST_ORDERED);
        mostOrderedRecyclerView.setAdapter(mostOrderedAdapter);

        mostSharedRecyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this
                , LinearLayoutManager.HORIZONTAL
                , false));
        mostSharedAdapter = new RankingAdapter(this, this, ProductTypeEnum.MOST_SHARED);
        mostSharedRecyclerView.setAdapter(mostSharedAdapter);

        mostViewedRecyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this
                , LinearLayoutManager.HORIZONTAL
                , false));
        mostViewedAdapter = new RankingAdapter(this, this, ProductTypeEnum.MOST_VIEWED);
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

    @OnClick(R.id.viewedAllTextView)
    public void viewAllClicked() {
        startActivity(new Intent(HomeActivity.this, ProductListActivity.class));
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
                break;

        }
    }

    private void openProductDetailsActivity(ProductDetails productDetails) {
        Intent intent = new Intent(HomeActivity.this, ProductDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstants.KEY_MODEL, productDetails);
        intent.putExtras(bundle);
        startActivity(intent);
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
}
