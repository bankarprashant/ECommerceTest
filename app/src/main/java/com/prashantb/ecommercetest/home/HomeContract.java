package com.prashantb.ecommercetest.home;

import com.prashantb.ecommercetest.BaseView;

interface HomeContract {
    interface View extends BaseView {
    }

    interface Presenter {
        void getProductsApiCall();
    }
}
