package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText editEmail, editPassword, editUsername; // input fields
    private Button btnSignUp, btnGoToLogin; // buttons
    private FirebaseAuth mAuth; // firebase auth instance
    private SharedPreferences sharedPreferences; // shared preferences for local storage

    private static final String SHARED_PREF_NAME = "my_pref";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASS = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        // link views
        editEmail = findViewById(R.id.editTextTextEmailAddress);
        editPassword = findViewById(R.id.editTextTextPassword);
        editUsername = findViewById(R.id.editTextTextUsername);
        btnSignUp = findViewById(R.id.buttonSignUp);
        btnGoToLogin = findViewById(R.id.alreadyHaveAccountBtn);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        // signup button clicked
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmail.getText().toString();
                String password = editPassword.getText().toString();
                String username = editUsername.getText().toString();

                if (!email.isEmpty() && !password.isEmpty() && !username.isEmpty()) {
                    // add user to Firebase
                    signUp(email, password, username);
                } else {
                    // show error if fields are empty
                    Toast.makeText(SignUpActivity.this, "Empty E-mail or Password!", Toast.LENGTH_LONG).show();
                }
            }
        });

        // go to login screen if already have account
        btnGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(go);
            }
        });
    }

    // function to sign up user to Firebase
    private void signUp(String email, String password, String username) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    // user created successfully
                    Toast.makeText(SignUpActivity.this, "Signed up successfully!", Toast.LENGTH_LONG).show();

                    // save user details locally
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_NAME, username);
                    editor.putString(KEY_EMAIL, email);
                    editor.putString(KEY_PASS, password);
                    editor.apply();

                    // move to Home Page
                    Intent go = new Intent(SignUpActivity.this, HomePageActivity.class);
                    startActivity(go);
                } else {
                    // signup failed
                    Toast.makeText(SignUpActivity.this, "Couldn't Sign You Up!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
