package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Quiz> {

    // Constructor for ListAdapter
    public ListAdapter(@NonNull Context context, ArrayList<Quiz> dataArrayList) {
        super(context, R.layout.list_item, dataArrayList);
    }

    /**
     * this func takes the list and the position, and applies the "list_item" view/design on the list's items
     * @param position - position of each item
     * @param convertView - the converted view
     * @param parent
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Quiz quiz = getItem(position); // gets the quiz object at the given position

        if (convertView == null) {
            // if view is null, inflate a new list_item layout
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // locate the views in "list_item.xml" and update them
        if (quiz != null) {
            ImageView imageView = convertView.findViewById(R.id.quizImageInItem); // quiz image (currently not used)
            // imageView.setImageResource(quiz.getImage()); // (commented out line for setting image)

            TextView quizName = convertView.findViewById(R.id.quizNameInItem); // quiz name text
            quizName.setText(quiz.getName());

            TextView numOfQuestions = convertView.findViewById(R.id.quizNumOfQuestionsInItem); // number of questions text
            numOfQuestions.setText(String.format("        %d Questions", quiz.getNumOfQuestions()));
        }

        return convertView; // return the updated view for the list item
    }
}
