package com.example.android_project.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_project.R;
import com.example.android_project.helpers.CurrencyFormatter;
import com.example.android_project.model.Booking;
import com.example.android_project.model.BookingWithCar;

import java.util.ArrayList;
import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    public interface OnBookingLongClickListener {
        void onBookingLongClick(Booking booking);
    }

    private final Context context;
    private final OnBookingLongClickListener listener;
    private final List<BookingWithCar> bookings = new ArrayList<>();
    private String currency = "usd";
    private double rate = 0;

    public BookingAdapter(Context context, OnBookingLongClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setBookings(List<BookingWithCar> newBookings) {
        bookings.clear();
        if (newBookings != null) {
            bookings.addAll(newBookings);
        }
        notifyDataSetChanged();
    }

    public void setCurrency(String currency, double rate) {
        this.currency = currency;
        this.rate = rate;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(context).inflate(R.layout.viewholder_booking, parent, false);
        return new BookingViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        BookingWithCar item = bookings.get(position);
        Booking booking = item.getBooking();

        holder.name.setText(item.getCarName());
        holder.dates.setText(context.getString(R.string.booking_dates,
                booking.getStartDate(), booking.getEndDate()));
        // the stored total_price is USD; convert it for display at the current rate
        holder.total.setText(context.getString(R.string.booking_total,
                CurrencyFormatter.format(context, booking.getTotalPrice(), currency, rate)));

        holder.itemView.setOnLongClickListener(v -> {
            if (listener != null) {
                listener.onBookingLongClick(booking);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    static class BookingViewHolder extends RecyclerView.ViewHolder {

        final TextView name;
        final TextView dates;
        final TextView total;

        BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.booking_car_name);
            dates = itemView.findViewById(R.id.booking_dates);
            total = itemView.findViewById(R.id.booking_total);
        }
    }
}
