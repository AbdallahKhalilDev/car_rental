package com.example.android_project.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android_project.model.User;

public class UserDao {

    private final DatabaseHelper helper;

    public UserDao(Context context) {
        this.helper = DatabaseHelper.getInstance(context);
    }

    public long insertOrUpdate(User user) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.USER_UID, user.getUid());
        values.put(DatabaseHelper.USER_NAME, user.getName());
        values.put(DatabaseHelper.USER_EMAIL, user.getEmail());
        values.put(DatabaseHelper.USER_PHONE, user.getPhone());
        // one write for three cases: signup inserts, the profile screen updates, and if the local
        // row is missing (example the users table was rebuilt) this re-inserts it
        return helper.getWritableDatabase().insertWithOnConflict(DatabaseHelper.TABLE_USERS, null,
                values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public User getByUid(String uid) {
        SQLiteDatabase db = helper.getReadableDatabase();
        try (Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, null,
                DatabaseHelper.USER_UID + " = ?", new String[]{uid},
                null, null, null)) {
            return cursor.moveToFirst() ? fromCursor(cursor) : null;
        }
    }

    private User fromCursor(Cursor cursor) {
        return new User(
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.USER_UID)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.USER_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.USER_EMAIL)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.USER_PHONE)));
    }
}
