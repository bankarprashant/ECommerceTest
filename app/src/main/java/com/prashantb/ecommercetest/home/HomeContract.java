package com.prashantb.ecommercetest.home;

import com.prashant.apilib.models.Category;
import com.prashant.apilib.models.ProductDetails;
import com.prashantb.ecommercetest.BaseView;

import java.util.ArrayList;
import java.util.Map;

interface HomeContract {
    interface View extends BaseView {
        void setMostViewedList(ArrayList<ProductDetails> productList);

        void setMostOrderedList(ArrayList<ProductDetails> productList);

        void setMostSharedList(ArrayList<ProductDetails> productList);

        void setCategoriesList(ArrayList<Category> categoryList);

        void setCategoriesMap(Map<Integer, Category> categoryMap);

    }

    interface Presenter {
        void getProductsApiCall();
    }
}
