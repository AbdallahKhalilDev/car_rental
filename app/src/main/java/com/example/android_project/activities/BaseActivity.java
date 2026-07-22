package com.example.android_project.activities;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_project.helpers.LanguageManager;

public class BaseActivity extends AppCompatActivity {

    // the only hook that runs before the layout inflates, so the only place a locale override lands
    @Override
    protected void attachBaseContext(Context newBase) {
        String language = new LanguageManager(newBase).getLanguage();
        super.attachBaseContext(LanguageManager.wrap(newBase, language));
    }
}
