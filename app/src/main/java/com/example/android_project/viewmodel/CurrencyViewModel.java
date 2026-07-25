package com.example.android_project.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android_project.data.repository.CurrencyRepository;
import com.example.android_project.data.repository.CurrencyRepositoryImpl;

public class CurrencyViewModel extends AndroidViewModel {

    private final CurrencyRepository currencyRepo;
    private final LiveData<Double> rate;

    public CurrencyViewModel(@NonNull Application application) {
        super(application);
        currencyRepo = new CurrencyRepositoryImpl(application);
        // fetched once here in the constructor, so rotation reuses the same ViewModel and rate
        rate = currencyRepo.getRate();
    }

    public LiveData<Double> getRate() {
        return rate;
    }
}
