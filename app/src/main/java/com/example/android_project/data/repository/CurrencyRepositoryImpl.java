package com.example.android_project.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.android_project.data.remote.CurrencyApi;
import com.example.android_project.helpers.AppExecutors;
import com.example.android_project.helpers.CurrencyManager;

public class CurrencyRepositoryImpl implements CurrencyRepository {

    private static final String TAG = "CurrencyRepository";

    // the API refreshes once a day, so a cached rate younger than this needs no network call
    private static final long MAX_AGE_MS = 24L * 60 * 60 * 1000;

    private final AppExecutors executors;
    private final CurrencyManager currencyManager;
    private final MutableLiveData<Double> rate = new MutableLiveData<>();

    public CurrencyRepositoryImpl(Context context) {
        this.executors = AppExecutors.getInstance();
        this.currencyManager = new CurrencyManager(context);
    }

    @Override
    public LiveData<Double> getRate() {
        loadRate();
        return rate;
    }

    private void loadRate() {
        executors.networkIO().execute(() -> {
            double cached = currencyManager.getCachedRate();
            if (cached > 0) {
                rate.postValue(cached);
            }
            if (isStale()) {
                try {
                    double fresh = CurrencyApi.fetchUsdToIls();
                    currencyManager.saveRate(fresh);
                    rate.postValue(fresh);
                } catch (Exception e) {
                    // offline or API down: the cached rate (or the USD fallback) already stands
                    Log.w(TAG, "USD to ILS fetch failed, keeping cached rate", e);
                }
            }
        });
    }

    private boolean isStale() {
        long ts = currencyManager.getRateTimestamp();
        return ts == 0 || System.currentTimeMillis() - ts > MAX_AGE_MS;
    }
}
