package com.example.android_project.data.repository;

import androidx.lifecycle.LiveData;

import com.example.android_project.model.Car;

import java.util.List;

public interface CarRepository {

    LiveData<List<Car>> getAllCars();
}
