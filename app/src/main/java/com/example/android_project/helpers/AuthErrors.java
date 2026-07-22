package com.example.android_project.helpers;

import com.example.android_project.R;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public final class AuthErrors {

    private AuthErrors() {
    }

    // Firebase exception messages are English-only and phrased for developers, so they are
    // mapped to string resources instead of being shown to the user
    public static int messageFor(Exception exception) {
        // subclass of FirebaseAuthInvalidCredentialsException, so it has to be tested first
        if (exception instanceof FirebaseAuthWeakPasswordException) {
            return R.string.err_password_short;
        }
        if (exception instanceof FirebaseAuthUserCollisionException) {
            return R.string.err_email_in_use;
        }
        if (exception instanceof FirebaseAuthInvalidUserException) {
            return R.string.err_no_account;
        }
        if (exception instanceof FirebaseAuthInvalidCredentialsException) {
            return R.string.err_wrong_credentials;
        }
        return R.string.auth_failed;
    }
}
