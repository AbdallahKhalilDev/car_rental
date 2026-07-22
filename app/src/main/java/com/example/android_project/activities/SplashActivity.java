package com.example.android_project.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.android_project.R;
import com.example.android_project.data.local.DatabaseHelper;
import com.example.android_project.helpers.AppExecutors;
import com.example.android_project.helpers.SessionManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends BaseActivity {

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
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                boolean signedIn = currentUser != null
                        && new SessionManager(SplashActivity.this).isLoggedIn();
                Class<?> next = signedIn ? HomeActivity.class : WelcomeActivity.class;
                Intent i = new Intent(SplashActivity.this, next);
                startActivity(i);
                finish();
            }
        }, 3000);
    }

}
