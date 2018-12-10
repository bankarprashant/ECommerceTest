package com.prashant.apilib;

import com.prashant.apilib.models.GetProductsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

interface APIInterface {

    @GET("json")
    Call<GetProductsResponse> getProductsData(@Header("Cache-Tag") String shouldCache);
}
