package com.example.android_project.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.android_project.data.local.CarDao;
import com.example.android_project.helpers.AppExecutors;
import com.example.android_project.model.Car;

import java.util.List;

public class CarRepositoryImpl implements CarRepository {

    private static final String TAG = "CarRepository";

    private final CarDao carDao;
    private final AppExecutors executors;
    private final MutableLiveData<List<Car>> cars = new MutableLiveData<>();

    public CarRepositoryImpl(Context context) {
        this.carDao = new CarDao(context);
        this.executors = AppExecutors.getInstance();
    }

    @Override
    public LiveData<List<Car>> getAllCars() {
        loadCars();
        return cars;
    }

    private void loadCars() {
        executors.diskIO().execute(() -> {
            Log.d(TAG, "loading cars on " + Thread.currentThread().getName());
            cars.postValue(carDao.getAllCars());
        });
    }
}
