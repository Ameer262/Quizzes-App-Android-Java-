package com.example.myapplication;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import static androidx.core.content.ContextCompat.startForegroundService;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Fragment that allows the user to create a new quiz and save it to Firebase.
 */
public class CreateQuizFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    // SharedPreferences for getting username
    private static final String KEY_NAME = "name";
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "my_pref";

    // UI elements
    private Button saveToFirebase, goToAddingQ, restart;
    private EditText quizName, quizDesc;
    private String quizCategory;
    private Spinner quizCategorySpinner;

    private int numQs; // number of questions added
    private String[] choices = {"General Knowledge", "Sports", "Art", "Geography", "Animals", "Science"};

    // Firebase Realtime Database Reference
    private DatabaseReference databaseReference;

    private ArrayList<Question> addedQs = null; // list of questions user adds
    public Quiz currentQuiz; // currently created quiz object

    public CreateQuizFragment() {
        // Required empty public constructor
    }

    public static CreateQuizFragment newInstance(String param1, String param2) {
        CreateQuizFragment fragment = new CreateQuizFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_quiz, container, false);

        // Set up category spinner
        Spinner choiceSpinner = view.findViewById(R.id.choiceSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                choices
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choiceSpinner.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize buttons
        restart = view.findViewById(R.id.btnrestart);
        saveToFirebase = view.findViewById(R.id.btnSaveQuiz);
        goToAddingQ = view.findViewById(R.id.btnAddQ);

        // Initialize EditTexts
        quizName = view.findViewById(R.id.quizNameInCreate);
        quizDesc = view.findViewById(R.id.quizDescInCreate);

        // Initialize Spinner
        quizCategorySpinner = view.findViewById(R.id.choiceSpinner);

        numQs = 0; // initialize number of questions
        quizCategory = "General Knowledge"; // default category
        addedQs = new ArrayList<>(); // initialize list of added questions

        // Spinner listener to update selected quiz category
        quizCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                quizCategory = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                quizCategory = "General Knowledge";
            }
        });

        // reference to Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference("Quizzes");

        // Listener for Save Quiz button
        saveToFirebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numQs > 0) {
                    // Create a new Quiz object with entered data
                    currentQuiz = new Quiz(
                            quizName.getText().toString(),
                            quizCategory,
                            2,
                            quizDesc.getText().toString(),
                            numQs
                    );

                    // Add all added questions to the quiz
                    for (int i = 0; i < numQs; i++) {
                        currentQuiz.addQuestion(addedQs.get(i));
                    }

                    // Get creator name from SharedPreferences
                    sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                    String creator = sharedPreferences.getString(KEY_NAME, null);
                    currentQuiz.setCreator(creator);

                    saveQuizToFirebase(currentQuiz); // save quiz to Firebase
                } else {
                    Toast.makeText(getActivity(), "No questions added so far!", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Listener for Add Question button
        goToAddingQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numQs < 10) {
                    // Open AddQuestionActivity
                    Intent intent = new Intent(getActivity(), AddQuestionActivity.class);
                    startActivityForResult(intent, 1);
                } else {
                    Toast.makeText(getActivity(), "You've reached the maximum amount of questions", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Restart button (currently commented out)
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // recreate(); (currently commented)
            }
        });
    }

    // Save quiz object to Firebase database
    private void saveQuizToFirebase(Quiz newQuiz) {
        DatabaseReference newQuizRef = databaseReference.push(); // generate new node

        newQuizRef.setValue(newQuiz)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getActivity(), "Quiz added successfully", Toast.LENGTH_LONG).show();

                    //send a notif.
                    Intent intent = new Intent(getActivity(), NotificationService.class);
                    requireActivity().startForegroundService(intent);
                    getActivity().finish(); // finish fragment's activity
                })
                .addOnFailureListener(e ->
                        Toast.makeText(getActivity(), "Failed to add quiz: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );

        startActivity(new Intent(getActivity(), HomePageActivity.class)); // go back to homepage
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Handle result coming back from AddQuestionActivity
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String x1 = data.getStringExtra("q_text");
            String x2 = data.getStringExtra("ch1");
            String x3 = data.getStringExtra("ch2");
            String x4 = data.getStringExtra("ch3");
            String x5 = data.getStringExtra("ch4");
            String x6 = data.getStringExtra("corrAnswer");

            // Create new Question object and add it to list
            Question q = new Question(x1, x2, x3, x4, x5, x6);
            this.addedQs.add(q);
            this.numQs++;

            // Update the number of added questions
            TextView t = getView().findViewById(R.id.numQadded);
            t.setText("Added questions: " + this.numQs + "  (max: 10)");

            Toast.makeText(getActivity(), String.valueOf(this.addedQs.size()), Toast.LENGTH_LONG).show();
        }
    }
}
