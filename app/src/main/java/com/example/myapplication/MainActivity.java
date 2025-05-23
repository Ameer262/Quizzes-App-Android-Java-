package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button btnSignUp, btnLogin, ameerBtn; // buttons for navigation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // navigate to SignUpActivity when signup button clicked
        btnSignUp = findViewById(R.id.gotoSignup);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(go);
            }
        });

        // navigate to LoginActivity when login button clicked
        btnLogin = findViewById(R.id.gotoLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(go);
            }
        });

        // (optional) visitor button code (currently commented out)
        /*
        ameerBtn = findViewById(R.id.visitorBtn);
        ameerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(MainActivity.this, HomePageActivity.class);
                startActivity(go);
            }
        });
        */
    }
}
