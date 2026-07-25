package com.example.android_project.data.repository;

import androidx.lifecycle.LiveData;

public interface CurrencyRepository {

    LiveData<Double> getRate();
}
