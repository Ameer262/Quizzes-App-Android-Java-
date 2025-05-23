package com.example.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GeneralKnowledgeDetailedActivity extends AppCompatActivity {

    private Button toQuizzesBtn, bNow, bLater; // buttons for navigation and future use
    private EditText editTextLater; // edit text for optional later reminder (not used yet)

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_general_knowledge_detailed);

        // navigate to GenKnowledgeQuizzesActivity when button is clicked
        toQuizzesBtn = findViewById(R.id.toQuizzesButtonGen);
        toQuizzesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(GeneralKnowledgeDetailedActivity.this, GenKnowledgeQuizzesActivity.class);
                startActivity(go);
            }
        });

        // request permissions for posting notifications and foreground service
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.POST_NOTIFICATIONS, Manifest.permission.FOREGROUND_SERVICE}, 100);

    }
}
