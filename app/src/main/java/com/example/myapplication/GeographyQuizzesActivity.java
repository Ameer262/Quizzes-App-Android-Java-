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
import com.example.myapplication.databinding.ActivityGeographyQuizzesBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GeographyQuizzesActivity extends AppCompatActivity {

    DatabaseReference databaseReference; // reference to Firebase Database
    ActivityGeographyQuizzesBinding binding; // view binding
    ArrayList<Quiz> dataArrayList = new ArrayList<>(); // list of quizzes
    ListAdapter listAdapter; // adapter to display quizzes in ListView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // the reference gets the instance of the realtime DB with the exact URL:
        databaseReference = FirebaseDatabase.getInstance().getReference("Quizzes");

        binding = ActivityGeographyQuizzesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // connect the list adapter to the ListView
        listAdapter = new ListAdapter(GeographyQuizzesActivity.this, dataArrayList);
        binding.inGeographyQuizzesListView.setAdapter(listAdapter);
        binding.inGeographyQuizzesListView.setClickable(true);

        fetchFirebaseItems(); // fetch quizzes from database

        // when a quiz item is clicked, open detailed quiz page
        binding.inGeographyQuizzesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(GeographyQuizzesActivity.this, QuizDetailedActivity.class);
                intent.putExtra("quizData", dataArrayList.get(i)); // pass the clicked quiz
                startActivity(intent);
            }
        });
    }

    // fetch quizzes from Firebase and filter only "Geography" category
    private void fetchFirebaseItems() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    // Convert each item from Firebase to a Quiz object
                    Quiz firebaseQuiz = itemSnapshot.getValue(Quiz.class);
                    if (firebaseQuiz != null) {
                        if ("Geography".equals(firebaseQuiz.getCategory())) {
                            dataArrayList.add(firebaseQuiz);
                        }
                    }
                }
                // Refresh the ListView after data changes
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // show error message if fetching failed
                Toast.makeText(GeographyQuizzesActivity.this, "Failed to fetch data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
