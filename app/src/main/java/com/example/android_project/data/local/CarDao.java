package com.example.android_project.data.local;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android_project.model.Car;

import java.util.ArrayList;
import java.util.List;

public class CarDao {

    private final Context context;
    private final DatabaseHelper helper;

    public CarDao(Context context) {
        this.context = context.getApplicationContext();
        this.helper = DatabaseHelper.getInstance(context);
    }

    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        try (Cursor cursor = db.query(DatabaseHelper.TABLE_CARS, null, null, null, null, null,
                DatabaseHelper.CAR_ID + " ASC")) {
            while (cursor.moveToNext()) {
                cars.add(fromCursor(cursor));
            }
        }
        return cars;
    }

    public Car getCarById(int id) {
        SQLiteDatabase db = helper.getReadableDatabase();
        try (Cursor cursor = db.query(DatabaseHelper.TABLE_CARS, null,
                DatabaseHelper.CAR_ID + " = ?", new String[]{String.valueOf(id)},
                null, null, null)) {
            return cursor.moveToFirst() ? fromCursor(cursor) : null;
        }
    }

    private Car fromCursor(Cursor cursor) {
        return new Car(
                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.CAR_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.CAR_NAME)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.CAR_PRICE_PER_DAY)),
                resolveDrawable(cursor.getString(
                        cursor.getColumnIndexOrThrow(DatabaseHelper.CAR_IMAGE_NAME))),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.CAR_ENGINE)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.CAR_HORSEPOWER)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.CAR_TRANSMISSION)),
                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.CAR_SEATS)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.CAR_FEATURES)));
    }

    // the table stores a drawable name, because R ids are regenerated on every build
    @SuppressLint("DiscouragedApi")
    private int resolveDrawable(String imageName) {
        return context.getResources()
                .getIdentifier(imageName, "drawable", context.getPackageName());
    }
}
