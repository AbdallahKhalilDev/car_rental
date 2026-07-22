package com.example.android_project.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android_project.data.repository.CarRepository;
import com.example.android_project.data.repository.CarRepositoryImpl;
import com.example.android_project.model.Car;

import java.util.List;

public class CarListViewModel extends AndroidViewModel {

    private final CarRepository carRepo;
    private final LiveData<List<Car>> cars;

    public CarListViewModel(@NonNull Application application) {
        super(application);
        carRepo = new CarRepositoryImpl(application);
        cars = carRepo.getAllCars();
    }

    public LiveData<List<Car>> getCars() {
        return cars;
    }
}
