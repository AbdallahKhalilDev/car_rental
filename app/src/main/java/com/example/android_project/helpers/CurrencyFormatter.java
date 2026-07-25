package com.example.android_project.helpers;

import android.content.Context;

import com.example.android_project.R;

import java.util.Locale;

public final class CurrencyFormatter {

    private CurrencyFormatter() {
    }

    // prices are stored in USD; NIS is a display-only conversion. A missing rate (rate <= 0)
    // falls back to USD, so an offline first launch shows dollars instead of a blank or a zero.
    public static String format(Context context, double usdAmount, String currency, double rate) {
        if ("nis".equals(currency) && rate > 0) {
            return context.getString(R.string.price_nis,
                    String.format(Locale.US, "%.0f", usdAmount * rate));
        }
        return context.getString(R.string.price_usd,
                String.format(Locale.US, "%.0f", usdAmount));
    }
}
