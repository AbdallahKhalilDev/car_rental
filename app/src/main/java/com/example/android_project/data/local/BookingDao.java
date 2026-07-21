package com.example.android_project.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android_project.model.Booking;

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

    public List<Booking> getByUser(String userId) {
        List<Booking> bookings = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        try (Cursor cursor = db.query(DatabaseHelper.TABLE_BOOKINGS, null,
                DatabaseHelper.BOOKING_USER_ID + " = ?", new String[]{userId},
                null, null, DatabaseHelper.BOOKING_CREATED_AT + " DESC")) {
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

    private Booking fromCursor(Cursor cursor) {
        return new Booking(
                cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.BOOKING_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.BOOKING_USER_ID)),
                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.BOOKING_CAR_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.BOOKING_START_DATE)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.BOOKING_END_DATE)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.BOOKING_TOTAL_PRICE)),
                cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.BOOKING_CREATED_AT)));
    }
}
