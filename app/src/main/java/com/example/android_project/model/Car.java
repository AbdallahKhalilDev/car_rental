package com.example.android_project.model;

import java.io.Serializable;

public class Car implements Serializable {

    private final int id;
    private final String name;
    private final double pricePerDay;
    private final int imageResId;
    private final String engine;
    private final String horsepower;
    private final String transmission;
    private final int seats;
    private final String features;

    public Car(int id, String name, double pricePerDay, int imageResId, String engine,
               String horsepower, String transmission, int seats, String features) {
        this.id = id;
        this.name = name;
        this.pricePerDay = pricePerDay;
        this.imageResId = imageResId;
        this.engine = engine;
        this.horsepower = horsepower;
        this.transmission = transmission;
        this.seats = seats;
        this.features = features;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getEngine() {
        return engine;
    }

    public String getHorsepower() {
        return horsepower;
    }

    public String getTransmission() {
        return transmission;
    }

    public int getSeats() {
        return seats;
    }

    public String getFeatures() {
        return features;
    }
}
