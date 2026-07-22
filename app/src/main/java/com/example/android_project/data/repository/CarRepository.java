package com.example.android_project.data.repository;

import android.content.Context;
import android.util.Log;

import com.example.android_project.data.local.CarDao;
import com.example.android_project.helpers.AppExecutors;
import com.example.android_project.model.Car;

import java.util.List;

public class CarRepository {

    private static final String TAG = "CarRepository";

    public interface CarsCallback {
        void onCarsLoaded(List<Car> cars);
    }

    private final CarDao carDao;
    private final AppExecutors executors;

    public CarRepository(Context context) {
        this.carDao = new CarDao(context);
        this.executors = AppExecutors.getInstance();
    }

    public void getAllCars(CarsCallback callback) {
        executors.diskIO().execute(() -> {
            Log.d(TAG, "loading cars on " + Thread.currentThread().getName());
            List<Car> cars = carDao.getAllCars();
            executors.mainThread().execute(() -> callback.onCarsLoaded(cars));
        });
    }
}
