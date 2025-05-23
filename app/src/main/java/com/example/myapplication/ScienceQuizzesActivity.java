package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityScienceQuizzesBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ScienceQuizzesActivity extends AppCompatActivity {

    DatabaseReference databaseReference; // reference to Firebase DB
    ActivityScienceQuizzesBinding binding; // binding for the layout
    ArrayList<Quiz> dataArrayList = new ArrayList<>(); // list to store quizzes
    ListAdapter listAdapter; // adapter to bind quizzes to ListView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // the reference gets the instance of the realtime DB
        databaseReference = FirebaseDatabase.getInstance().getReference("Quizzes");

        binding = ActivityScienceQuizzesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // connect the ListView to the adapter
        listAdapter = new ListAdapter(ScienceQuizzesActivity.this, dataArrayList);
        binding.inScienceQuizzesListView.setAdapter(listAdapter);
        binding.inScienceQuizzesListView.setClickable(true);

        // fetch quizzes from database
        fetchFirebaseItems();

        // attach listener: when user clicks an item -> move to quiz details
        binding.inScienceQuizzesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ScienceQuizzesActivity.this, QuizDetailedActivity.class);
                intent.putExtra("quizData", dataArrayList.get(i)); // send the clicked quiz
                startActivity(intent);
            }
        });
    }

    // fetch quizzes from Firebase where category is "Science"
    private void fetchFirebaseItems() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    Quiz firebaseQuiz = itemSnapshot.getValue(Quiz.class);
                    if (firebaseQuiz != null) {
                        if ("Science".equals(firebaseQuiz.getCategory())) {
                            dataArrayList.add(firebaseQuiz);
                        }
                    }
                }

                // notify the adapter to refresh the list view
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // handle database errors
                Toast.makeText(ScienceQuizzesActivity.this, "Failed to fetch data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
