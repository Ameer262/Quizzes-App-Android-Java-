package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    // definitions:
    private EditText emailEdit, passwordEdit;
    private Button btnLogin, gotoSignup, btnForgotPass;
    private FirebaseAuth mAuth;

    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "my_pref";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASS = "password";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // get firebase connection:
        mAuth = FirebaseAuth.getInstance();

        // connect the reference with the object in the layout:
        emailEdit = findViewById(R.id.getEmailEditText);
        passwordEdit = findViewById(R.id.getPassEditText);
        btnLogin = findViewById(R.id.loginButton);
        gotoSignup = findViewById(R.id.newquizwizzer);
        btnForgotPass = findViewById(R.id.forgotPasswordBTN);

        // get SharedPreferences and load saved email/password if available
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String email_sp = sharedPreferences.getString(KEY_EMAIL, null);
        String pass_sp = sharedPreferences.getString(KEY_PASS, null);

        if (email_sp != null && pass_sp != null) {
            emailEdit.setText(email_sp);
            passwordEdit.setText(pass_sp);
        }

        // login button clicked
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                login(email, password);
            }
        });

        // move to signup page if user clicks "new quiz wizzer"
        gotoSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(go);
            }
        });

        // handle forgot password button
        btnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // build a new default dialog in the current activity:
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

                // get the wanted layout (.xml file):
                View dialogView = getLayoutInflater().inflate(R.layout.forgot_pass_dialog, null);

                EditText emailBox = dialogView.findViewById(R.id.emailBox);

                // create the dialog and show it with the wanted design:
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();
                dialog.setCancelable(false);

                // Reset button clicked inside the dialog
                dialogView.findViewById(R.id.btnReset).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String userEmail = emailBox.getText().toString();

                        // validate email format
                        if (TextUtils.isEmpty(userEmail) && !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                            Toast.makeText(LoginActivity.this, "Enter your registered email id", Toast.LENGTH_LONG).show();
                            return;
                        }

                        // send password reset email
                        mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Check your email", Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Unable to send, failed", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        if (mAuth.getCurrentUser() != null) {
                            mAuth.signOut();
                        }
                    }
                });

                // Cancel button inside the dialog
                dialogView.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                // set dialog background to transparent
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }

                // show the dialog
                dialog.show();
            }
        });
    }

    // login method to authenticate with Firebase
    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // if login successful, go to home page

                            Toast.makeText(LoginActivity.this, "Logged in successfully!", Toast.LENGTH_LONG).show();
                            Intent go = new Intent(LoginActivity.this, HomePageActivity.class);
                            startActivity(go);
                        } else {
                            // if login fails
                            Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
