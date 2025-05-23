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

public class ScienceDetailedActivity extends AppCompatActivity {

    private Button toQuizzes; // button to navigate to the quizzes list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_science_detailed2);

        // connect the button
        toQuizzes = findViewById(R.id.toQuizzesButtonScience);

        // set click listener for the button
        toQuizzes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to ScienceQuizzesActivity when clicked
                startActivity(new Intent(ScienceDetailedActivity.this, ScienceQuizzesActivity.class));
            }
        });
    }
}
