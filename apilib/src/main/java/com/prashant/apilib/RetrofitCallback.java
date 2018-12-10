package com.prashant.apilib;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.prashant.apilib.common.ErrorCodeEnum;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

abstract class RetrofitCallback<T> implements retrofit2.Callback<T> {
    private static final String TAG = "RetrofitCallback";
    private final Callback callback;

    RetrofitCallback(Callback<T> callback) {
        this.callback = callback;
    }

    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        Log.d(TAG, "onResponse: BASE_URL : " + call.request().url().toString());

        if (!response.isSuccessful()) {

            switch (response.code()) {
                case 400:
                    parseErrorResponse(response);
                    break;
                case 401:
                    parseErrorResponse(response);
                    break;
                case 404:
                    callback.onError(response.message(), ErrorCodeEnum.RESOURCE_NOT_FOUND);
                    break;
                case 500:
                    callback.onError(response.message(), ErrorCodeEnum.INTERNAL_SERVER_ERROR);
                    break;
            }
        } else {
            callback.onSuccess(response.body());
        }
    }

    private void parseErrorResponse(Response<T> response) {
        try {
            if (response.errorBody() != null) {
                String errorString = response.errorBody().string();
                Log.d(TAG, "parseErrorResponse: ErrorString : " + errorString);

                callback.onError(errorString, ErrorCodeEnum.JSON_FORMAT_ERROR);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        t.printStackTrace();

        if (t instanceof IllegalStateException) {
            callback.onError(t.toString(), ErrorCodeEnum.JSON_FORMAT_ERROR);
            return;
        } else if (t instanceof JsonSyntaxException) {
            callback.onError(t.toString(), ErrorCodeEnum.JSON_FORMAT_ERROR);
            return;
        } else if (t instanceof IOException) {
            callback.onError("No Internet", ErrorCodeEnum.NO_NETWORK);
            return;
        }

        callback.onError("", ErrorCodeEnum.UNKNOWN_ERROR);
    }
}
