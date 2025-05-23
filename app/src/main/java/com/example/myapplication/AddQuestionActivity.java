package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddQuestionActivity extends AppCompatActivity {

    // variables for question text, choices and correct answer
    private String questText, ch1, ch2, ch3, ch4;
    private String corrAnswer;

    private Button saveQuest; // button to save question
    private String[] choices = {"1","2","3","4"}; // options for correct answer

    private Spinner corrAnswerSpinner; // spinner to select correct answer

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_question);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // initialize EditText fields and get their current text
        questText = ((EditText)findViewById(R.id.questText)).getText().toString();
        ch1 = ((EditText)findViewById(R.id.choice1)).getText().toString();
        ch2 = ((EditText)findViewById(R.id.choice2)).getText().toString();
        ch3 = ((EditText)findViewById(R.id.choice3)).getText().toString();
        ch4 = ((EditText)findViewById(R.id.choice4)).getText().toString();

        saveQuest = findViewById(R.id.btnSaveQuest); // button to save the new question

        corrAnswerSpinner = findViewById(R.id.corrAnsSpinner); // spinner to select correct answer

        // set up spinner adapter with the choices array
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, choices);
        corrAnswerSpinner.setAdapter(adapter);

        // spinner item selection listener
        corrAnswerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                corrAnswer = adapterView.getItemAtPosition(i).toString(); // save selected correct answer
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                corrAnswer = "1"; // default correct answer if nothing selected
            }
        });

        // save button click listener
        saveQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // update values from EditTexts
                questText = ((EditText)findViewById(R.id.questText)).getText().toString();
                ch1 = ((EditText)findViewById(R.id.choice1)).getText().toString();
                ch2 = ((EditText)findViewById(R.id.choice2)).getText().toString();
                ch3 = ((EditText)findViewById(R.id.choice3)).getText().toString();
                ch4 = ((EditText)findViewById(R.id.choice4)).getText().toString();

                // check if any field is empty
                if (ch1.isEmpty() || ch2.isEmpty() || ch3.isEmpty() || ch4.isEmpty() || questText.isEmpty()) {
                    Toast.makeText(AddQuestionActivity.this, "Fill the question text and choices!", Toast.LENGTH_LONG).show();
                } else {
                    // prepare result to send back to previous activity
                    Intent back = new Intent();
                    back.putExtra("q_text", questText);
                    back.putExtra("ch1", ch1);
                    back.putExtra("ch2", ch2);
                    back.putExtra("ch3", ch3);
                    back.putExtra("ch4", ch4);
                    back.putExtra("corrAnswer", corrAnswer);
                    setResult(RESULT_OK, back); // set result with data
                    finish(); // close this activity
                }
            }
        });
    }
}
