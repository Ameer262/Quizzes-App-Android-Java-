package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityQuizDetailedBinding;

public class QuizDetailedActivity extends AppCompatActivity {

    ActivityQuizDetailedBinding binding; // view binding for layout
    private Quiz quiz; // the current quiz object

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizDetailedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // get the intent which got the user to this activity with all the extras
        Intent intent = this.getIntent();

        if (intent != null) {
            quiz = (Quiz) intent.getSerializableExtra("quizData"); // get quiz object
            Toast.makeText(QuizDetailedActivity.this, "Size: " + String.valueOf(quiz.getQuestions().size()), Toast.LENGTH_LONG).show();

            if (quiz != null) {
                // update details on screen
                binding.QuizName.setText(quiz.getName());
                binding.QuizDescription.setText(quiz.getDescription());
                binding.QuizCreatorName.setText("By: " + "Ameer"); // (static name for now)
                // binding.QuizImage.setImageResource(quiz.getImage()); // image optional
            }

            // set play button to start playing the quiz
            binding.playQuizButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent go = new Intent(QuizDetailedActivity.this, PlayQuizActivity.class);
                    go.putExtra("question", quiz.getQuestionAt(0)); // Start with the first question
                    go.putExtra("quizName", quiz.getName());
                    go.putExtra("questNum", 0);
                    go.putExtra("QsNum", quiz.getNumOfQuestions());
                    go.putExtra("corrSoFar", 0);
                    startActivityForResult(go, 1); // request_code = 1
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            int nextQuestionIndex = data.getIntExtra("nextQuestionIndex", -1);

            // if there are more questions, continue the quiz
            if (nextQuestionIndex != -1 && nextQuestionIndex < quiz.getNumOfQuestions()) {
                Intent go = new Intent(QuizDetailedActivity.this, PlayQuizActivity.class);
                go.putExtra("question", quiz.getQuestionAt(nextQuestionIndex));
                go.putExtra("quizName", quiz.getName());
                go.putExtra("questNum", nextQuestionIndex);
                go.putExtra("QsNum", quiz.getNumOfQuestions());
                go.putExtra("corrSoFar", data.getIntExtra("newProg", -1));
                startActivityForResult(go, 1);
            }

            // if finished all questions, move to the finished screen
            if (nextQuestionIndex == quiz.getNumOfQuestions()) {
                Intent done = new Intent(QuizDetailedActivity.this, FinishedQuizActivity.class);
                done.putExtra("howManyCorr", data.getIntExtra("newProg", 0));
                done.putExtra("finishedQuiz", quiz);
                startActivity(done);
            }
        }
    }
}
