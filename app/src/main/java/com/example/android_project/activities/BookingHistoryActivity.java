package com.example.android_project.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_project.R;
import com.example.android_project.adapters.BookingAdapter;
import com.example.android_project.model.Booking;
import com.example.android_project.viewmodel.BookingViewModel;

public class BookingHistoryActivity extends BaseActivity
        implements BookingAdapter.OnBookingLongClickListener {

    private BookingViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booking_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView bookingList = findViewById(R.id.booking_list);
        bookingList.setLayoutManager(new LinearLayoutManager(this));

        BookingAdapter adapter = new BookingAdapter(this, this);
        bookingList.setAdapter(adapter);

        TextView msg1 = findViewById(R.id.msg1);
        TextView msg2 = findViewById(R.id.msg2);

        viewModel = new ViewModelProvider(this).get(BookingViewModel.class);
        viewModel.getBookings().observe(this, bookings -> {
            adapter.setBookings(bookings);
            boolean empty = bookings == null || bookings.isEmpty();
            bookingList.setVisibility(empty ? View.GONE : View.VISIBLE);
            msg1.setVisibility(empty ? View.VISIBLE : View.GONE);
            msg2.setVisibility(empty ? View.VISIBLE : View.GONE);
        });
    }

    @Override
    public void onBookingLongClick(Booking booking) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.delete_booking_title)
                .setMessage(R.string.delete_booking_msg)
                .setPositiveButton(R.string.delete, (d, w) -> {
                    viewModel.delete(booking.getId());
                    Toast.makeText(this, R.string.booking_deleted, Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }
}
