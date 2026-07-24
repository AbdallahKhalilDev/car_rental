package com.example.android_project.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.android_project.data.local.BookingDao;
import com.example.android_project.helpers.AppExecutors;
import com.example.android_project.model.BookingWithCar;

import java.util.List;

public class BookingRepositoryImpl implements BookingRepository {

    private final BookingDao bookingDao;
    private final AppExecutors executors;
    private final MutableLiveData<List<BookingWithCar>> bookings = new MutableLiveData<>();

    public BookingRepositoryImpl(Context context) {
        this.bookingDao = new BookingDao(context);
        this.executors = AppExecutors.getInstance();
    }

    @Override
    public LiveData<List<BookingWithCar>> getBookings(String userId) {
        load(userId);
        return bookings;
    }

    @Override
    public void delete(long bookingId, String userId) {
        // diskIO is a single thread, so the delete commits before the re-query that reposts the list
        executors.diskIO().execute(() -> {
            bookingDao.delete(bookingId);
            bookings.postValue(bookingDao.getByUser(userId));
        });
    }

    private void load(String userId) {
        executors.diskIO().execute(() -> bookings.postValue(bookingDao.getByUser(userId)));
    }
}
