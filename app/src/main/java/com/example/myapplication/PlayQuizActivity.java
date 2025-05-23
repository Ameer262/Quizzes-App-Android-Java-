package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class PlayQuizActivity extends AppCompatActivity {

    // UI elements
    private TextView quizNameTV, questTextTV, questNumTV, progTV, timerView;
    private Button b1, b2, b3, b4;

    // quiz data
    private String quizNameStr;
    private int currQNum, numQs, corrAnsSoFar;
    private Question currQuestion;
    private Boolean isAnswered = false;
    private CountDownTimer timer;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_play_quiz);

        // connect UI to layout
        quizNameTV = findViewById(R.id.QuizNameInPlay);
        questTextTV = findViewById(R.id.QuestTextInPlay);
        questNumTV = findViewById(R.id.QuestNumInPlay);
        progTV = findViewById(R.id.progressTV);
        timerView = findViewById(R.id.countdown_timer_view);

        // get quiz data from Intent
        Intent parent = this.getIntent();
        currQuestion = (Question) parent.getSerializableExtra("question");
        quizNameStr = parent.getStringExtra("quizName");
        currQNum = parent.getIntExtra("questNum", 0);
        numQs = parent.getIntExtra("QsNum", 0);
        corrAnsSoFar = parent.getIntExtra("corrSoFar", 0);

        // set texts
        quizNameTV.setText(quizNameStr);
        questTextTV.setText(currQuestion.getText());
        questNumTV.setText("Question number " + currQNum + "/" + numQs);
        progTV.setText("(progress: " + corrAnsSoFar + "/" + currQNum + ")");

        // connect answer buttons
        b1 = findViewById(R.id.buttonInPlay);
        b2 = findViewById(R.id.button2InPlay);
        b3 = findViewById(R.id.button3InPlay);
        b4 = findViewById(R.id.button4InPlay);

        // set button texts
        b1.setText(currQuestion.getCh1());
        b2.setText(currQuestion.getCh2());
        b3.setText(currQuestion.getCh3());
        b4.setText(currQuestion.getCh4());

        // setup click listeners for each button
        setupButton(b1, "1");
        setupButton(b2, "2");
        setupButton(b3, "3");
        setupButton(b4, "4");

        // reset timer color to black and start countdown
        timerView.setTextColor(Color.parseColor("#2B2D30"));
        startCountDownTimer();
    }

    // setup click behavior for answer buttons
    private void setupButton(Button button, String buttonNumber) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAnswered = true;
                timer.cancel();
                timerView.setText("00:00:00");

                // disable all buttons
                b1.setClickable(false);
                b2.setClickable(false);
                b3.setClickable(false);
                b4.setClickable(false);

                // check if the answer is correct
                if (currQuestion.getCorrAns().equals(buttonNumber)) {
                    button.setBackgroundColor(Color.parseColor("#008000")); // green
                    delayNextQuestion(true);
                } else {
                    button.setBackgroundColor(Color.parseColor("#800000")); // red
                    highlightCorrectAnswer();
                }
            }
        });
    }

    // highlight the correct answer when user clicks wrong
    private void highlightCorrectAnswer() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currQuestion.getCorrAns().equals("1")) {
                    b1.setBackgroundColor(Color.parseColor("#008000"));
                } else if (currQuestion.getCorrAns().equals("2")) {
                    b2.setBackgroundColor(Color.parseColor("#008000"));
                } else if (currQuestion.getCorrAns().equals("3")) {
                    b3.setBackgroundColor(Color.parseColor("#008000"));
                } else {
                    b4.setBackgroundColor(Color.parseColor("#008000"));
                }

                delayNextQuestion(false);
            }
        }, 500); // wait 0.5 seconds before showing the correct one
    }

    // move to the next question after showing feedback
    private void delayNextQuestion(boolean answeredCorrectly) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent result = new Intent();
                result.putExtra("nextQuestionIndex", currQNum + 1);
                result.putExtra("newProg", answeredCorrectly ? corrAnsSoFar + 1 : corrAnsSoFar);
                setResult(RESULT_OK, result);
                finish();
            }
        }, 2000); // wait 2 seconds
    }

    // start the countdown timer
    private void startCountDownTimer() {
        timerView.setText("00:00:00");

        timer = new CountDownTimer(25000, 1000) { // 25 seconds countdown
            @Override
            public void onTick(long millisUntilFinished) {
                // format timer as HH:MM:SS
                long hours = (millisUntilFinished / 1000) / 3600;
                long minutes = ((millisUntilFinished / 1000) % 3600) / 60;
                long seconds = (millisUntilFinished / 1000) % 60;

                String formattedTime = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
                timerView.setText(formattedTime);
            }

            @Override
            public void onFinish() {
                // time is up
                timerView.setText("00:00:00");
                timerView.setTextColor(Color.parseColor("#D70040")); // red color
                Toast.makeText(PlayQuizActivity.this, "Time's Up", Toast.LENGTH_LONG).show();

                MediaPlayer alarm = MediaPlayer.create(PlayQuizActivity.this, R.raw.timeisupsound);
                alarm.start();

                // after 3 seconds show the correct answer and move on
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (currQuestion.getCorrAns().equals("1")) {
                            b1.setBackgroundColor(Color.parseColor("#008000"));
                        } else if (currQuestion.getCorrAns().equals("2")) {
                            b2.setBackgroundColor(Color.parseColor("#008000"));
                        } else if (currQuestion.getCorrAns().equals("3")) {
                            b3.setBackgroundColor(Color.parseColor("#008000"));
                        } else {
                            b4.setBackgroundColor(Color.parseColor("#008000"));
                        }

                        Intent result = new Intent();
                        result.putExtra("nextQuestionIndex", currQNum + 1);
                        result.putExtra("newProg", corrAnsSoFar);
                        setResult(RESULT_OK, result);
                        finish();
                    }
                }, 3000); // wait 3 seconds
            }
        }.start();
    }
}
