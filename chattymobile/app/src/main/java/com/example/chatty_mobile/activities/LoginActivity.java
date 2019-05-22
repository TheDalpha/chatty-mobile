package com.example.chatty_mobile.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatty_mobile.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LOGGED IN";

    private Button btnCreate;
    private Button btnLogin;
    private TextView txtEmail;
    private TextView txtPassword;

    private FirebaseAuth mAuth;

    private Boolean isLoggedIn;

    /**
     * Runs when the instance is created
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        final Intent chat = new Intent(this, ChatActivity.class);

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Buttons
        btnLogin = findViewById(R.id.btnLogin);
        btnCreate = findViewById(R.id.btnCreate);

        // Views
        txtEmail = findViewById(R.id.fieldEmail);
        txtPassword = findViewById(R.id.fieldPassword);

        // Boolean
        isLoggedIn = false;

        /**
         * When button Login is clicked
         *
         */
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(txtEmail.getText().toString(), txtPassword.getText().toString());
            }

        });
    }

    /**
     * Sign in with Firebase email and password
     *
     * @param email
     * @param password
     */
    private void signIn(String email, String password) {
        if (!validateForm()) {
            return;
        }
        // ChatIntent
        final Intent chat = new Intent(this, ChatActivity.class);

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, opens the chat
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(chat);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Login failed: \nInvalid username or password.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            return;
                        }
                        // [END_EXCLUDE]
                    }

                });
        // [END sign_in_with_email]
    }

    /**
     * Validates the Email and Password
     *
     * @return
     */
    private boolean validateForm() {
        boolean valid = true;

        String email = txtEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            txtEmail.setError("Required.");
            valid = false;
        } else {
            txtEmail.setError(null);
        }

        String password = txtPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            txtPassword.setError("Required.");
            valid = false;
        } else {
            txtPassword.setError(null);
        }

        return valid;
    }


}

