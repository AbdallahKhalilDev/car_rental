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
import com.example.android_project.helpers.AuthErrors;
import com.example.android_project.helpers.SessionManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";

    private FirebaseAuth mAuth;
    private SessionManager sessionManager;

    private EditText emailField, passwordField;
    private Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        sessionManager = new SessionManager(this);

        emailField = findViewById(R.id.email);
        passwordField = findViewById(R.id.password);

        TextView signup = findViewById(R.id.signup);
        start = findViewById(R.id.get_started);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(i);
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    private void signIn() {
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            toast(R.string.err_fill_all);
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            toast(R.string.err_email_invalid);
            return;
        }

        start.setEnabled(false);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    start.setEnabled(true);
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithEmail:success");
                        onSignedIn(mAuth.getCurrentUser(), email);
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        toast(AuthErrors.messageFor(task.getException()));
                    }
                });
    }

    private void onSignedIn(FirebaseUser user, String email) {
        if (user == null) {
            toast(R.string.auth_failed);
            return;
        }
        sessionManager.saveLoginSession(user.getUid(), email);
        toast(R.string.login_success);

        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        finish();
    }

    // the activity context, never the application one, so the saved language applies
    private void toast(int messageId) {
        Toast.makeText(LoginActivity.this, messageId, Toast.LENGTH_SHORT).show();
    }
}
