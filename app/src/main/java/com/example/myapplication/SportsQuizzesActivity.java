package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivitySportsQuizzesBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SportsQuizzesActivity extends AppCompatActivity {

    DatabaseReference databaseReference; // reference to Firebase DB
    ActivitySportsQuizzesBinding binding; // view binding for the layout
    ArrayList<Quiz> dataArrayList = new ArrayList<>(); // list to hold fetched quizzes
    ListAdapter listAdapter; // adapter for binding the quizzes to ListView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get reference to the "Quizzes" path in Firebase database
        databaseReference = FirebaseDatabase.getInstance().getReference("Quizzes");

        binding = ActivitySportsQuizzesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // connect the list view with the adapter
        listAdapter = new ListAdapter(SportsQuizzesActivity.this, dataArrayList);
        binding.inSportsQuizzesListView.setAdapter(listAdapter);
        binding.inSportsQuizzesListView.setClickable(true);

        // fetch quizzes data from Firebase
        fetchFirebaseItems();

        // when user clicks on a quiz item, open the detailed quiz page
        binding.inSportsQuizzesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SportsQuizzesActivity.this, QuizDetailedActivity.class);
                intent.putExtra("quizData", dataArrayList.get(i)); // pass clicked quiz object
                startActivity(intent);
            }
        });
    }

    // fetch all quizzes from Firebase where category = "Sports"
    private void fetchFirebaseItems() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    Quiz firebaseQuiz = itemSnapshot.getValue(Quiz.class);
                    if (firebaseQuiz != null) {
                        if ("Sports".equals(firebaseQuiz.getCategory())) {
                            dataArrayList.add(firebaseQuiz);
                        }
                    }
                }
                // notify the adapter that data changed
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // handle database errors
                Toast.makeText(SportsQuizzesActivity.this,
                        "Failed to fetch data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
