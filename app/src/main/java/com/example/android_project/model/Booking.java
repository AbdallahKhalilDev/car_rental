package com.example.android_project.model;

import java.io.Serializable;

public class Booking implements Serializable {

    public static final long NO_ID = 0;

    private final long id;
    private final String userId;
    private final int carId;
    private final String startDate;
    private final String endDate;
    private final double totalPrice;
    private final long createdAt;

    public Booking(String userId, int carId, String startDate, String endDate, double totalPrice) {
        this(NO_ID, userId, carId, startDate, endDate, totalPrice, System.currentTimeMillis());
    }

    public Booking(long id, String userId, int carId, String startDate, String endDate,
                   double totalPrice, long createdAt) {
        this.id = id;
        this.userId = userId;
        this.carId = carId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public int getCarId() {
        return carId;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public long getCreatedAt() {
        return createdAt;
    }
}
