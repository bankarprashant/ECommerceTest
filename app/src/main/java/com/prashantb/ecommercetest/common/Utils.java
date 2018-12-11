package com.prashantb.ecommercetest.common;

import android.content.Context;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    public static String getDateInRequiredFormat(String mStringDate, String oldFormat, String newFormat) {
        try {
            String formattedDate = "";
            SimpleDateFormat dateFormat = new SimpleDateFormat(oldFormat, Locale.getDefault());
            Date myDate = null;
            try {
                myDate = dateFormat.parse(mStringDate);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }

            SimpleDateFormat timeFormat = new SimpleDateFormat(newFormat, Locale.getDefault());
            formattedDate = timeFormat.format(myDate);

            if (!TextUtils.isEmpty(formattedDate)) {
                return formattedDate;
            }

            return mStringDate;
        } catch (Exception e) {
            e.printStackTrace();
            return mStringDate;
        }
    }

    public static String roundTwoDecimals(Double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return twoDForm.format(d);
    }


}
