package com.example.android_project.helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class CurrencyManager {

    private static final String KEY_CURRENCY = "currency";
    private static final String KEY_RATE = "rate_ils";
    private static final String KEY_RATE_TS = "rate_ils_ts";
    private static final String DEFAULT_CURRENCY = "usd";

    private final SharedPreferences prefs;

    public CurrencyManager(Context context) {
        // the same file the language / theme / currency choices already live in
        prefs = context.getSharedPreferences(LanguageManager.PREFS_NAME, Context.MODE_PRIVATE);
    }

    public String getCurrency() {
        return prefs.getString(KEY_CURRENCY, DEFAULT_CURRENCY);
    }

    // 0 means no rate has ever been fetched — callers treat that as "show USD"
    public double getCachedRate() {
        return Double.longBitsToDouble(prefs.getLong(KEY_RATE, Double.doubleToLongBits(0)));
    }

    public long getRateTimestamp() {
        return prefs.getLong(KEY_RATE_TS, 0);
    }

    public void saveRate(double rate) {
        // SharedPreferences has no putDouble, so the rate rides in as its raw long bits
        prefs.edit()
                .putLong(KEY_RATE, Double.doubleToLongBits(rate))
                .putLong(KEY_RATE_TS, System.currentTimeMillis())
                .apply();
    }
}
