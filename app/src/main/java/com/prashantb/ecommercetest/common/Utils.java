package com.prashantb.ecommercetest.common;

import android.content.Context;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.Toast;

public class Utils {
    public static void showToast(Context context, @StringRes int msgID) {
        showToast(context, context.getString(msgID));
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void showProgressBar(View progressBar) {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    public static void hideProgressBar(View progressBar) {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }
}
