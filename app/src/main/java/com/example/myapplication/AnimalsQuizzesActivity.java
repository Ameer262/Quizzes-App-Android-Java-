package com.example.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.databinding.ActivityAnimalsQuizzesBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AnimalsQuizzesActivity extends AppCompatActivity {

    DatabaseReference databaseReference; // reference to Firebase Database
    ActivityAnimalsQuizzesBinding binding; // view binding
    ArrayList<Quiz> dataArrayList = new ArrayList<>(); // list to store quizzes
    ListAdapter listAdapter; // adapter to show quizzes in list view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // the reference gets the instance of the realtime DB with the exact URL:
        databaseReference = FirebaseDatabase.getInstance().getReference("Quizzes");

        binding = ActivityAnimalsQuizzesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // a connection between the objects to the adapter:
        listAdapter = new ListAdapter(AnimalsQuizzesActivity.this, dataArrayList);
        binding.inAnimalsQuizzesListView.setAdapter(listAdapter);
        binding.inAnimalsQuizzesListView.setClickable(true);

        fetchFirebaseItems(); // fetch quizzes from Firebase

        // attach listener to the list items to move to the detailed activity:
        binding.inAnimalsQuizzesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // open QuizDetailedActivity when a quiz is clicked
                Intent intent = new Intent(AnimalsQuizzesActivity.this, QuizDetailedActivity.class);
                intent.putExtra("quizData", dataArrayList.get(i)); // send clicked quiz object
                startActivity(intent);
            }
        });

    }

    // method to fetch quizzes from Firebase Realtime Database
    private void fetchFirebaseItems() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    // Convert Firebase data to Quiz object
                    Quiz firebaseQuiz = itemSnapshot.getValue(Quiz.class);
                    if (firebaseQuiz != null) {
                        // Add only quizzes from Animals category
                        if ("Animals".equals(firebaseQuiz.getCategory())) {
                            dataArrayList.add(firebaseQuiz);
                        }
                    }
                }
                // Notify adapter to refresh the list view
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database errors
                Toast.makeText(AnimalsQuizzesActivity.this, "Failed to fetch data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
