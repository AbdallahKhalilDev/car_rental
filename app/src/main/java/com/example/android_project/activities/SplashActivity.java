package com.example.android_project.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android_project.R;
import com.example.android_project.data.local.DatabaseHelper;
import com.example.android_project.helpers.AppExecutors;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // first launch creates and seeds the database; doing it here spends the splash delay on it
        Context appContext = getApplicationContext();
        AppExecutors.getInstance().diskIO().execute(
                () -> DatabaseHelper.getInstance(appContext).getReadableDatabase());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, WelcomeActivity.class);
                startActivity(i);
                finish();
            }
        }, 3000);
    }

}
