package com.prashantb.ecommercetest.common;

import com.prashant.apilib.models.ProductDetails;

import java.util.Comparator;

public class RatingComparator implements Comparator<ProductDetails> {
    private RatingEnum ratingEnum;

    public RatingComparator(RatingEnum ratingEnum) {
        this.ratingEnum = ratingEnum;
    }

    @Override
    public int compare(ProductDetails productDetails, ProductDetails t1) {
        if (ratingEnum.equals(RatingEnum.MOST_SHARED)) {
            return t1.getShares().compareTo(productDetails.getShares());
        } else if (ratingEnum.equals(RatingEnum.MOST_ORDERED)) {
            return t1.getOrderCount().compareTo(productDetails.getOrderCount());
        } else if (ratingEnum.equals(RatingEnum.MOST_VIEWED)) {
            return t1.getViewCount().compareTo(productDetails.getViewCount());
        } else
            return 0;
    }
}
