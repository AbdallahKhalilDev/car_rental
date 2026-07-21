package com.example.android_project.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "car_rental.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_CARS = "cars";
    public static final String CAR_ID = "id";
    public static final String CAR_NAME = "name";
    public static final String CAR_PRICE_PER_DAY = "price_per_day";
    public static final String CAR_IMAGE_NAME = "image_name";
    public static final String CAR_ENGINE = "engine";
    public static final String CAR_HORSEPOWER = "horsepower";
    public static final String CAR_TRANSMISSION = "transmission";
    public static final String CAR_SEATS = "seats";
    public static final String CAR_FEATURES = "features";

    public static final String TABLE_BOOKINGS = "bookings";
    public static final String BOOKING_ID = "id";
    public static final String BOOKING_USER_ID = "user_id";
    public static final String BOOKING_CAR_ID = "car_id";
    public static final String BOOKING_START_DATE = "start_date";
    public static final String BOOKING_END_DATE = "end_date";
    public static final String BOOKING_TOTAL_PRICE = "total_price";
    public static final String BOOKING_CREATED_AT = "created_at";

    public static final String TABLE_USERS = "users";
    public static final String USER_UID = "uid";
    public static final String USER_NAME = "name";
    public static final String USER_EMAIL = "email";
    public static final String USER_PHONE = "phone";

    private static final String CREATE_TABLE_CARS =
            "CREATE TABLE " + TABLE_CARS + " ("
                    + CAR_ID + " INTEGER PRIMARY KEY, "
                    + CAR_NAME + " TEXT NOT NULL, "
                    + CAR_PRICE_PER_DAY + " REAL NOT NULL, "
                    + CAR_IMAGE_NAME + " TEXT NOT NULL, "
                    + CAR_ENGINE + " TEXT, "
                    + CAR_HORSEPOWER + " TEXT, "
                    + CAR_TRANSMISSION + " TEXT, "
                    + CAR_SEATS + " INTEGER, "
                    + CAR_FEATURES + " TEXT)";

    private static final String CREATE_TABLE_BOOKINGS =
            "CREATE TABLE " + TABLE_BOOKINGS + " ("
                    + BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + BOOKING_USER_ID + " TEXT NOT NULL, "
                    + BOOKING_CAR_ID + " INTEGER NOT NULL, "
                    + BOOKING_START_DATE + " TEXT NOT NULL, "
                    + BOOKING_END_DATE + " TEXT NOT NULL, "
                    + BOOKING_TOTAL_PRICE + " REAL NOT NULL, "
                    + BOOKING_CREATED_AT + " INTEGER NOT NULL, "
                    + "FOREIGN KEY (" + BOOKING_CAR_ID + ") REFERENCES "
                    + TABLE_CARS + "(" + CAR_ID + "))";

    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + " ("
                    + USER_UID + " TEXT PRIMARY KEY, "
                    + USER_NAME + " TEXT, "
                    + USER_EMAIL + " TEXT, "
                    + USER_PHONE + " TEXT)";

    private static volatile DatabaseHelper instance;

    public static DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (DatabaseHelper.class) {
                if (instance == null) {
                    // application context — an Activity context would leak the singleton
                    instance = new DatabaseHelper(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(@NonNull SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CARS);
        db.execSQL(CREATE_TABLE_BOOKINGS);
        db.execSQL(CREATE_TABLE_USERS);
        seedCars(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARS);
        onCreate(db);
    }

    private void seedCars(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            insertCar(db, 1, "Mercedes‑Benz E450", 300, "e450",
                    "3.0L Inline-6 Turbo", "402 hp", "9-speed automatic", 5,
                    "Leather, GPS, 360 Camera, Air Suspension");
            insertCar(db, 2, "Audi Q5", 230, "q5",
                    "2.0L Inline-4 Turbo", "201 hp", "7-speed automatic", 5,
                    "Park Assist, GPS, 360 Camera, Adaptive Cruise Control");
            insertCar(db, 3, "Porsche 911 turbo S", 550, "turbo_s",
                    "3.7L Flat-6 Twin-Turbo", "650 hp", "8-speed PDK", 4,
                    "Leather, Park Assist, rear-axle steering, 360 Camera, "
                            + "Adaptive Cruise Control, Front Axle Lift System");
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void insertCar(SQLiteDatabase db, int id, String name, double pricePerDay,
                           String imageName, String engine, String horsepower,
                           String transmission, int seats, String features) {
        ContentValues values = new ContentValues();
        values.put(CAR_ID, id);
        values.put(CAR_NAME, name);
        values.put(CAR_PRICE_PER_DAY, pricePerDay);
        values.put(CAR_IMAGE_NAME, imageName);
        values.put(CAR_ENGINE, engine);
        values.put(CAR_HORSEPOWER, horsepower);
        values.put(CAR_TRANSMISSION, transmission);
        values.put(CAR_SEATS, seats);
        values.put(CAR_FEATURES, features);
        db.insert(TABLE_CARS, null, values);
    }
}
