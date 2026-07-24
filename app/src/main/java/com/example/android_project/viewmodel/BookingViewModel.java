package com.example.android_project.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android_project.data.repository.BookingRepository;
import com.example.android_project.data.repository.BookingRepositoryImpl;
import com.example.android_project.model.BookingWithCar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class BookingViewModel extends AndroidViewModel {

    private final BookingRepository bookingRepo;
    private final String userId;
    private final LiveData<List<BookingWithCar>> bookings;

    public BookingViewModel(@NonNull Application application) {
        super(application);
        // history is only reachable while signed in; an empty uid simply matches no rows
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user != null ? user.getUid() : "";
        bookingRepo = new BookingRepositoryImpl(application);
        // query in the constructor so rotation reuses the result instead of re-running it
        bookings = bookingRepo.getBookings(userId);
    }

    public LiveData<List<BookingWithCar>> getBookings() {
        return bookings;
    }

    public void delete(long bookingId) {
        bookingRepo.delete(bookingId, userId);
    }
}
