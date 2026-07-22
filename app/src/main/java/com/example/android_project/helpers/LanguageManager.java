package com.example.android_project.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import java.util.Locale;

public class LanguageManager {

    public static final String PREFS_NAME = "UserPrefs";

    private static final String KEY_LANGUAGE = "language";
    private static final String DEFAULT_LANGUAGE = "en";

    private final SharedPreferences sharedPreferences;

    public LanguageManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public String getLanguage() {
        return sharedPreferences.getString(KEY_LANGUAGE, DEFAULT_LANGUAGE);
    }

    public void setLanguage(String language) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_LANGUAGE, language);
        editor.apply();
    }

    public static Context wrap(Context base, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration config = new Configuration(base.getResources().getConfiguration());
        config.setLocale(locale);
        // without this Arabic renders in Arabic but the layout stays left-to-right
        config.setLayoutDirection(locale);

        return base.createConfigurationContext(config);
    }
}
