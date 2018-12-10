package com.prashant.apilib;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prashant.apilib.common.APIConstants;
import com.prashant.apilib.common.Utils;
import com.prashant.apilib.models.GetProductsResponse;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class RestClient implements INetworkClient {

    private APIInterface apiInterface;

    RestClient() {
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();

        //set cache
        int cacheSize = 10 * 1024 * 1024; // 10 MB
        Cache cache = new Cache(APILib.getContext().getCacheDir(), cacheSize);

        //set logging
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .cache(cache)
                .addInterceptor(new Interceptor() {
                    @NonNull
                    @Override
                    public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
                        Request request = chain.request();

                        if ("yes".equals(request.header(APIConstants.CACHE_TAG))) {
                            if (Utils.isConnectedToInternet(APILib.getContext())) {
                                request = request.newBuilder().header("Cache-Control", "public, max-age=" + 0).build();
                            } else {
                                request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
                            }
                        }
                        return chain.proceed(request);
                    }
                })
                .addInterceptor(httpLoggingInterceptor)
                .build();

        String BASE_URL = APIConstants.BASE_URL;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        apiInterface = retrofit.create(APIInterface.class);
    }

    @Override
    public void getProductsData(String shouldCache, Callback<GetProductsResponse> callback) {
        Call<GetProductsResponse> call = apiInterface.getProductsData(shouldCache);
        call.enqueue(new RetrofitCallback<GetProductsResponse>(callback) {
        });
    }
}
