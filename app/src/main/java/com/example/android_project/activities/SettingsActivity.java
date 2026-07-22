package com.example.android_project.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android_project.R;
import com.example.android_project.helpers.LanguageManager;

public class SettingsActivity extends BaseActivity {

    RadioButton rb_eng, rb_arb, rb_dark, rb_light, rb_usd, rb_nis;
    Button btn_save;
    LanguageManager languageManager;
    SharedPreferences sharedPreferences;
    String language, theme, currency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rb_eng = findViewById(R.id.eng);
        rb_arb = findViewById(R.id.arb);
        rb_dark = findViewById(R.id.dark);
        rb_light = findViewById(R.id.light);
        rb_usd = findViewById(R.id.usd);
        rb_nis = findViewById(R.id.nis);
        btn_save = findViewById(R.id.btn_save);

        languageManager = new LanguageManager(this);
        sharedPreferences = getSharedPreferences(LanguageManager.PREFS_NAME, MODE_PRIVATE);

        language = languageManager.getLanguage();
        theme = sharedPreferences.getString("theme", "light");
        currency = sharedPreferences.getString("currency", "usd");

        if (language.equals("ar")) {
            rb_arb.setChecked(true);
        } else {
            rb_eng.setChecked(true);
        }

        if (theme.equals("dark")) {
            rb_dark.setChecked(true);
        } else {
            rb_light.setChecked(true);
        }

        if (currency.equals("nis")) {
            rb_nis.setChecked(true);
        } else {
            rb_usd.setChecked(true);
        }

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedLanguage = rb_arb.isChecked() ? "ar" : "en";
                String selectedTheme = rb_dark.isChecked() ? "dark" : "light";
                String selectedCurrency = rb_nis.isChecked() ? "nis" : "usd";

                languageManager.setLanguage(selectedLanguage);

                // theme and currency are stored but not applied yet; sessions 8 and later read them
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("theme", selectedTheme);
                editor.putString("currency", selectedCurrency);
                editor.apply();

                Toast.makeText(SettingsActivity.this, R.string.settings_saved, Toast.LENGTH_SHORT).show();

                // the locale is read in attachBaseContext, so screens already on the stack keep the
                // old one — clearing the task forces every screen to be rebuilt
                Intent i = new Intent(SettingsActivity.this, HomeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });
    }
}
