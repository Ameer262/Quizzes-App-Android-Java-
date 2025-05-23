package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FinishedQuizActivity extends AppCompatActivity {

    // arrays for random quotes and button labels
    private String[] goodQuotes = {"Awesome!", "Congratulations!", "Wow!", "Super!", "Phenomenal!", "OMG!"};
    private String[] badQuotes = {"Hmmm...!", "Listen...!", "Not That Bad...", "Fifty Fifty!"};
    private String[] Buttons = {"Continue!", "Good!", "Proceed!", "Nice!"};

    // UI elements
    TextView quizNameV, quizCreatorV, topQuoteV, markV;
    ImageView imageView;
    Button done;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_finished_quiz);

        Random random = new Random();
        String buttonStr = Buttons[random.nextInt(Buttons.length)]; // random button label

        // find all views by ID
        done = findViewById(R.id.doneQuizButton);
        quizNameV = findViewById(R.id.doneQuizName);
        topQuoteV = findViewById(R.id.topWord);
        markV = findViewById(R.id.quizMark);
        quizCreatorV = findViewById(R.id.doneQuizCreatorName);
        imageView = findViewById(R.id.doneImage);

        // get data from intent
        Intent parent = getIntent();
        Quiz doneQuiz = (Quiz) parent.getSerializableExtra("finishedQuiz");
        int corr = parent.getIntExtra("howManyCorr", 0);

        String quoteStr;
        // select quote and image based on score
        if (corr >= 0.6 * doneQuiz.getNumOfQuestions()) {
            quoteStr = goodQuotes[random.nextInt(goodQuotes.length)];
            imageView.setImageResource(R.drawable.greenface);
        } else if (corr >= 0.3 * doneQuiz.getNumOfQuestions()) {
            quoteStr = goodQuotes[random.nextInt(goodQuotes.length)];
            imageView.setImageResource(R.drawable.averageface);
        } else {
            quoteStr = badQuotes[random.nextInt(badQuotes.length)];
            imageView.setImageResource(R.drawable.redface);
        }

        // update UI with quiz results
        topQuoteV.setText(quoteStr);
        done.setText(buttonStr);
        markV.setText("With a mark of " + corr + "/" + doneQuiz.getNumOfQuestions());
        quizNameV.setText(doneQuiz.getName());
        quizCreatorV.setText("By: " + doneQuiz.getCreator());

        // button click to go back to home page
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stPeriod = "3".toString();
                int t = Integer.parseInt(stPeriod);
                long afterT = System.currentTimeMillis() + (1000 * t); //if we want minutes -> 1000*60*t

                //send a notif.
                Intent intent = new Intent(FinishedQuizActivity.this, NotificationService.class);
                PendingIntent afterIntent = PendingIntent.getForegroundService(FinishedQuizActivity.this, 2, intent, PendingIntent.FLAG_IMMUTABLE);

                // Schedule the AlarmManager to trigger the PendingIntent at the specified time:
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, afterT, afterIntent);
                startActivity(new Intent(FinishedQuizActivity.this, HomePageActivity.class));
            }
        });
    }
}
