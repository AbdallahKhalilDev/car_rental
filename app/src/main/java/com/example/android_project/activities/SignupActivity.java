package com.example.android_project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
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
import com.example.android_project.helpers.AuthErrors;
import com.example.android_project.helpers.SessionManager;
import com.example.android_project.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends BaseActivity {

    private static final String TAG = "SignupActivity";
    private static final int MIN_PASSWORD_LENGTH = 6;

    private FirebaseAuth mAuth;
    private SessionManager sessionManager;
    private UserDao userDao;

    private EditText nameField, emailField, passwordField, confirmField, phoneField;
    private Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        sessionManager = new SessionManager(this);
        userDao = new UserDao(this);

        nameField = findViewById(R.id.name);
        emailField = findViewById(R.id.email);
        passwordField = findViewById(R.id.password);
        confirmField = findViewById(R.id.cnf_pass);
        phoneField = findViewById(R.id.phone);

        TextView login = findViewById(R.id.login);
        start = findViewById(R.id.get_started);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }

    private void createAccount() {
        String name = nameField.getText().toString().trim();
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString();
        String confirm = confirmField.getText().toString();
        String phone = phoneField.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone)
                || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirm)) {
            toast(R.string.err_fill_all);
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            toast(R.string.err_email_invalid);
            return;
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            toast(R.string.err_password_short);
            return;
        }
        if (!password.equals(confirm)) {
            toast(R.string.err_password_mismatch);
            return;
        }

        start.setEnabled(false);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    start.setEnabled(true);
                    if (task.isSuccessful()) {
                        Log.d(TAG, "createUserWithEmail:success");
                        onSignedUp(mAuth.getCurrentUser(), name, email, phone);
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        toast(AuthErrors.messageFor(task.getException()));
                    }
                });
    }

    private void onSignedUp(FirebaseUser user, String name, String email, String phone) {
        if (user == null) {
            toast(R.string.auth_failed);
            return;
        }
        User profile = new User(user.getUid(), name, email, phone);
        AppExecutors.getInstance().diskIO().execute(() -> userDao.insertOrUpdate(profile));

        sessionManager.saveLoginSession(user.getUid(), email);
        toast(R.string.signup_success);

        startActivity(new Intent(SignupActivity.this, HomeActivity.class));
        finish();
    }

    // the activity context, never the application one, so the saved language applies
    private void toast(int messageId) {
        Toast.makeText(SignupActivity.this, messageId, Toast.LENGTH_SHORT).show();
    }
}
