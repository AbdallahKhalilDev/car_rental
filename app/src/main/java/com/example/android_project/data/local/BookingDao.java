package com.example.android_project.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android_project.model.Booking;
import com.example.android_project.model.BookingWithCar;

import java.util.ArrayList;
import java.util.List;

public class BookingDao {

    private final DatabaseHelper helper;

    public BookingDao(Context context) {
        this.helper = DatabaseHelper.getInstance(context);
    }

    public long insert(Booking booking) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.BOOKING_USER_ID, booking.getUserId());
        values.put(DatabaseHelper.BOOKING_CAR_ID, booking.getCarId());
        values.put(DatabaseHelper.BOOKING_START_DATE, booking.getStartDate());
        values.put(DatabaseHelper.BOOKING_END_DATE, booking.getEndDate());
        values.put(DatabaseHelper.BOOKING_TOTAL_PRICE, booking.getTotalPrice());
        values.put(DatabaseHelper.BOOKING_CREATED_AT, booking.getCreatedAt());
        return helper.getWritableDatabase().insert(DatabaseHelper.TABLE_BOOKINGS, null, values);
    }

    // JOIN so each row carries the car name, avoiding a per-row lookup in the adapter
    public List<BookingWithCar> getByUser(String userId) {
        List<BookingWithCar> bookings = new ArrayList<>();
        String sql = "SELECT b." + DatabaseHelper.BOOKING_ID
                + ", b." + DatabaseHelper.BOOKING_USER_ID
                + ", b." + DatabaseHelper.BOOKING_CAR_ID
                + ", b." + DatabaseHelper.BOOKING_START_DATE
                + ", b." + DatabaseHelper.BOOKING_END_DATE
                + ", b." + DatabaseHelper.BOOKING_TOTAL_PRICE
                + ", b." + DatabaseHelper.BOOKING_CREATED_AT
                + ", c." + DatabaseHelper.CAR_NAME
                + " FROM " + DatabaseHelper.TABLE_BOOKINGS + " b"
                + " JOIN " + DatabaseHelper.TABLE_CARS + " c"
                + " ON b." + DatabaseHelper.BOOKING_CAR_ID + " = c." + DatabaseHelper.CAR_ID
                + " WHERE b." + DatabaseHelper.BOOKING_USER_ID + " = ?"
                + " ORDER BY b." + DatabaseHelper.BOOKING_CREATED_AT + " DESC";
        SQLiteDatabase db = helper.getReadableDatabase();
        try (Cursor cursor = db.rawQuery(sql, new String[]{userId})) {
            while (cursor.moveToNext()) {
                bookings.add(fromCursor(cursor));
            }
        }
        return bookings;
    }

    public int delete(long bookingId) {
        return helper.getWritableDatabase().delete(DatabaseHelper.TABLE_BOOKINGS,
                DatabaseHelper.BOOKING_ID + " = ?", new String[]{String.valueOf(bookingId)});
    }

    private BookingWithCar fromCursor(Cursor cursor) {
        Booking booking = new Booking(
                cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.BOOKING_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.BOOKING_USER_ID)),
                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.BOOKING_CAR_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.BOOKING_START_DATE)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.BOOKING_END_DATE)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.BOOKING_TOTAL_PRICE)),
                cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.BOOKING_CREATED_AT)));
        String carName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.CAR_NAME));
        return new BookingWithCar(booking, carName);
    }
}
