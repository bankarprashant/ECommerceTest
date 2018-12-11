package com.prashantb.ecommercetest.home;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.prashant.apilib.Callback;
import com.prashant.apilib.common.ErrorCodeEnum;
import com.prashant.apilib.models.Category;
import com.prashant.apilib.models.GetProductsResponse;
import com.prashant.apilib.models.ProductDetails;
import com.prashant.apilib.models.Ranking;
import com.prashant.apilib.models.RankingProduct;
import com.prashantb.ecommercetest.R;
import com.prashantb.ecommercetest.common.RatingComparator;
import com.prashantb.ecommercetest.common.ProductTypeEnum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class HomePresenter implements HomeContract.Presenter {
    private static final String TAG = "HomePresenter";
    private HomeContract.View view;

    HomePresenter(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void getProductsApiCall() {
        view.showProgressBar();

        view.getNetworkClient().getProductsData("yes", new Callback<GetProductsResponse>() {
            @Override
            public void onSuccess(GetProductsResponse response) {
                Log.d(TAG, "onSuccess() called with: matchesResponse = [" + response + "]");
                if (response != null) {
                    new FormatResponseAsync(response).execute();
                } else {
                    view.hideProgressBar();
                    view.apiError(R.string.api_error_msg);
                }
            }

            @Override
            public void onError(String error, ErrorCodeEnum errorCode) {
                super.onError(error, errorCode);
                Log.d(TAG, "onError() called with: error = [" + error + "], errorCode = [" + errorCode + "]");

                view.apiError(error);
                view.hideProgressBar();
            }
        });
    }

    @SuppressLint("UseSparseArrays")
    private class FormatResponseAsync extends AsyncTask<Void, Void, Void> {
        private GetProductsResponse response;
        private Map<Integer, ProductDetails> productDetailsMap = new HashMap<>();
        private Map<Integer, Category> categoryMap = new HashMap<>();

        private ArrayList<ProductDetails> orderedList;
        private ArrayList<ProductDetails> viewedList;
        private ArrayList<ProductDetails> sharedList;

        FormatResponseAsync(GetProductsResponse response) {
            this.response = response;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            List<Category> categories = response.getCategories();

            if (categories != null && categories.size() > 0) {
                for (int i = 0; i < categories.size(); i++) {

                    Category category = categories.get(i);
                    categoryMap.put(category.getId(), category);

                    List<ProductDetails> productDetailsList = category.getProductDetails();

                    if (productDetailsList != null && productDetailsList.size() > 0) {
                        for (int j = 0; j < productDetailsList.size(); j++) {
                            ProductDetails productDetails = productDetailsList.get(j);
                            productDetailsMap.put(productDetails.getId(), productDetails);
                        }
                    }
                }
            }

            Map<Integer, ProductDetails> mostViewedMap = new HashMap<>();
            Map<Integer, ProductDetails> mostOrderedMap = new HashMap<>();
            Map<Integer, ProductDetails> mostSharedMap = new HashMap<>();

            List<Ranking> rankingsList = response.getRankings();
            if (rankingsList != null && rankingsList.size() > 0) {
                for (int i = 0; i < rankingsList.size(); i++) {

                    Ranking ranking = rankingsList.get(i);
                    String type = ranking.getRanking();

                    List<RankingProduct> productsList = ranking.getProducts();

                    if (productsList != null && productsList.size() > 0) {
                        for (int j = 0; j < productsList.size(); j++) {

                            RankingProduct rankingProduct = productsList.get(j);

                            if (productDetailsMap.containsKey(rankingProduct.getId())) {
                                ProductDetails productDetails = productDetailsMap.get(rankingProduct.getId());

                                if (rankingProduct.getOrderCount() != null) {
                                    productDetails.setOrderCount(rankingProduct.getOrderCount());
                                } else if (rankingProduct.getShares() != null) {
                                    productDetails.setShares(rankingProduct.getShares());
                                } else if (rankingProduct.getViewCount() != null) {
                                    productDetails.setViewCount(rankingProduct.getViewCount());
                                }


                                if (type.toLowerCase().contains("viewed")) {
                                    mostViewedMap.put(rankingProduct.getId(), productDetails);
                                } else if (type.toLowerCase().contains("ordered")) {
                                    mostOrderedMap.put(rankingProduct.getId(), productDetails);
                                } else if (type.toLowerCase().contains("shared")) {
                                    mostSharedMap.put(rankingProduct.getId(), productDetails);
                                }
                            }
                        }
                    }
                }
            }

            orderedList = new ArrayList<>(mostOrderedMap.values());
            sharedList = new ArrayList<>(mostSharedMap.values());
            viewedList = new ArrayList<>(mostViewedMap.values());

            Collections.sort(orderedList, new RatingComparator(ProductTypeEnum.MOST_ORDERED));
            Collections.sort(sharedList, new RatingComparator(ProductTypeEnum.MOST_SHARED));
            Collections.sort(viewedList, new RatingComparator(ProductTypeEnum.MOST_VIEWED));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            view.setCategoriesMap(categoryMap);
            view.setCategoriesList(new ArrayList<>(categoryMap.values()));
            view.setMostOrderedList(orderedList);
            view.setMostViewedList(viewedList);
            view.setMostSharedList(sharedList);

            view.hideProgressBar();
        }
    }

}
