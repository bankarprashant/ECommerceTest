package com.prashant.apilib;

import com.prashant.apilib.models.GetProductsResponse;

public interface INetworkClient {

    /**
     * @param shouldCache "yes" for caching api response "no" for otherwise
     * @param callback
     */
    void getProductsData(String shouldCache, Callback<GetProductsResponse> callback);
}
