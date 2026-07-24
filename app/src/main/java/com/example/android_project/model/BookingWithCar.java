package com.example.android_project.model;

// pairs a booking with its car's name, resolved by the bookings-cars JOIN so the history row
// can show the name without a second lookup
public class BookingWithCar {

    private final Booking booking;
    private final String carName;

    public BookingWithCar(Booking booking, String carName) {
        this.booking = booking;
        this.carName = carName;
    }

    public Booking getBooking() {
        return booking;
    }

    public String getCarName() {
        return carName;
    }
}
