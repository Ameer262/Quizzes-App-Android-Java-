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

public class AnimlasDetailedActivity extends AppCompatActivity {

    private Button toQuizzes; // button to navigate to quizzes screen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_animlas_detailed);

        toQuizzes = findViewById(R.id.toQuizzesButtonAnimals); // find button by ID

        toQuizzes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // when clicked, open AnimalsQuizzesActivity
                startActivity(new Intent(AnimlasDetailedActivity.this, AnimalsQuizzesActivity.class));
            }
        });
    }
}
