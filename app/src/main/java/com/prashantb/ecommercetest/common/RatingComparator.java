package com.prashantb.ecommercetest.common;

import com.prashant.apilib.models.ProductDetails;

import java.util.Comparator;

public class RatingComparator implements Comparator<ProductDetails> {
    private ProductTypeEnum productTypeEnum;

    public RatingComparator(ProductTypeEnum productTypeEnum) {
        this.productTypeEnum = productTypeEnum;
    }

    @Override
    public int compare(ProductDetails productDetails, ProductDetails t1) {
        if (productTypeEnum.equals(ProductTypeEnum.MOST_SHARED)) {
            return t1.getShares().compareTo(productDetails.getShares());
        } else if (productTypeEnum.equals(ProductTypeEnum.MOST_ORDERED)) {
            return t1.getOrderCount().compareTo(productDetails.getOrderCount());
        } else if (productTypeEnum.equals(ProductTypeEnum.MOST_VIEWED)) {
            return t1.getViewCount().compareTo(productDetails.getViewCount());
        } else
            return 0;
    }
}
