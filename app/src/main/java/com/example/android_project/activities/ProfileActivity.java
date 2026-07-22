package com.example.android_project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android_project.R;
import com.example.android_project.data.local.UserDao;
import com.example.android_project.helpers.AppExecutors;
import com.example.android_project.helpers.SessionManager;
import com.example.android_project.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends BaseActivity {

    private FirebaseAuth mAuth;
    private SessionManager sessionManager;
    private UserDao userDao;
    private String uid, email;

    private TextView emailValue;
    private EditText nameField, phoneField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        sessionManager = new SessionManager(this);
        userDao = new UserDao(this);

        emailValue = findViewById(R.id.email_value);
        nameField = findViewById(R.id.name);
        phoneField = findViewById(R.id.phone);
        Button save = findViewById(R.id.btn_save);
        Button logout = findViewById(R.id.btn_logout);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            signOut();
            return;
        }
        uid = currentUser.getUid();
        email = currentUser.getEmail();
        emailValue.setText(email);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileActivity.this, R.string.logged_out, Toast.LENGTH_SHORT).show();
                signOut();
            }
        });

        // on rotation the fields are restored from the saved instance state, and re-reading the
        // database here would overwrite an edit the user had not saved yet
        if (savedInstanceState == null) {
            loadProfile();
        }
    }

    private void loadProfile() {
        AppExecutors executors = AppExecutors.getInstance();
        executors.diskIO().execute(() -> {
            User user = userDao.getByUid(uid);
            executors.mainThread().execute(() -> bind(user));
        });
    }

    // null when the account was created on another device: the fields stay empty and the first
    // save inserts the missing row
    private void bind(User user) {
        if (user == null) {
            return;
        }
        nameField.setText(user.getName());
        phoneField.setText(user.getPhone());
    }

    private void saveProfile() {
        String name = nameField.getText().toString().trim();
        String phone = phoneField.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone)) {
            Toast.makeText(this, R.string.err_fill_all, Toast.LENGTH_SHORT).show();
            return;
        }

        User profile = new User(uid, name, email, phone);
        AppExecutors executors = AppExecutors.getInstance();
        executors.diskIO().execute(() -> {
            userDao.insertOrUpdate(profile);
            executors.mainThread().execute(() ->
                    Toast.makeText(ProfileActivity.this, R.string.profile_saved,
                            Toast.LENGTH_SHORT).show());
        });
    }

    private void signOut() {
        mAuth.signOut();
        sessionManager.clearSession();

        // the whole task goes, so back cannot land on a screen belonging to the signed-out user
        Intent i = new Intent(ProfileActivity.this, WelcomeActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }
}
