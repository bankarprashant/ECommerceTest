package com.prashantb.ecommercetest.home;

import com.prashant.apilib.models.ProductDetails;
import com.prashantb.ecommercetest.BaseView;

import java.util.ArrayList;

interface HomeContract {
    interface View extends BaseView {
        void setMostViewedList(ArrayList<ProductDetails> productList);

        void setMostOrderedList(ArrayList<ProductDetails> productList);

        void setMostSharedList(ArrayList<ProductDetails> productList);
    }

    interface Presenter {
        void getProductsApiCall();
    }
}
