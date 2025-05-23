package com.example.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.myapplication.databinding.ActivityGenKnowledgeQuizzesBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GenKnowledgeQuizzesActivity extends AppCompatActivity {


    ActivityGenKnowledgeQuizzesBinding binding;
    ListAdapter listAdapter;
    ArrayList<Quiz> dataArrayList = new ArrayList<>();
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //the reference gets the instance of the realtime DB with the exact URL:
        databaseReference = FirebaseDatabase.getInstance().getReference("Quizzes");

        binding = ActivityGenKnowledgeQuizzesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //a connection between the objects to the adapter:
        listAdapter = new ListAdapter(GenKnowledgeQuizzesActivity.this, dataArrayList);
        binding.inGenKnowledgeQuizzesListView.setAdapter(listAdapter);
        binding.inGenKnowledgeQuizzesListView.setClickable(true);

        fetchFirebaseItems();

        //attach listener to the list items to move to the detailed activity:
        binding.inGenKnowledgeQuizzesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(GenKnowledgeQuizzesActivity.this, QuizDetailedActivity.class);
                intent.putExtra("quizData", dataArrayList.get(i) );
                startActivity(intent);
            }
        });


    }


    private void fetchFirebaseItems() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    // Convert Firebase data to Item object
                    Quiz firebaseQuiz = itemSnapshot.getValue(Quiz.class);
                    if (firebaseQuiz != null) {

                        if("General Knowledge".equals(firebaseQuiz.getCategory())){
                            dataArrayList.add(firebaseQuiz);
                        }
                    }
                }

                // Notify adapter to refresh the list
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database errors
                Toast.makeText(GenKnowledgeQuizzesActivity.this, "Failed to fetch data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
