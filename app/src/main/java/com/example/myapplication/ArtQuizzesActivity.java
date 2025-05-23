package com.example.myapplication;

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
import com.example.myapplication.databinding.ActivityArtQuizzesBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ArtQuizzesActivity extends AppCompatActivity {

    DatabaseReference databaseReference; // reference to Firebase Database
    ActivityArtQuizzesBinding binding; // view binding
    ArrayList<Quiz> dataArrayList = new ArrayList<>(); // list to hold quizzes
    ListAdapter listAdapter; // adapter for displaying quizzes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // the reference gets the instance of the realtime DB with the exact URL:
        databaseReference = FirebaseDatabase.getInstance().getReference("Quizzes");

        binding = ActivityArtQuizzesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // a connection between the objects to the adapter:
        listAdapter = new ListAdapter(ArtQuizzesActivity.this, dataArrayList);
        binding.inArtQuizzesListView.setAdapter(listAdapter);
        binding.inArtQuizzesListView.setClickable(true);

        fetchFirebaseItems(); // fetch quizzes from Firebase

        // attach listener to the list items to move to the detailed activity:
        binding.inArtQuizzesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // open QuizDetailedActivity and pass clicked quiz
                Intent intent = new Intent(ArtQuizzesActivity.this, QuizDetailedActivity.class);
                intent.putExtra("quizData", dataArrayList.get(i));
                startActivity(intent);
            }
        });

    }

    // method to fetch quizzes from Firebase
    private void fetchFirebaseItems() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    // Convert Firebase data to Quiz object
                    Quiz firebaseQuiz = itemSnapshot.getValue(Quiz.class);
                    if (firebaseQuiz != null) {
                        // Add only quizzes from Art category
                        if ("Art".equals(firebaseQuiz.getCategory())) {
                            dataArrayList.add(firebaseQuiz);
                        }
                    }
                }
                // Notify adapter that data changed
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database errors
                Toast.makeText(ArtQuizzesActivity.this, "Failed to fetch data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
