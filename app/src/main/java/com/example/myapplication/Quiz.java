package com.example.myapplication;

import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class Quiz implements Serializable {

    //attributes:
    private String name;

    private String category;
    private int image;
    private String Description;
    private int numOfQuestions;
    private String creator;

    private ArrayList<Question> questions = new ArrayList<Question>();


    /* *** methods *** */

    // constructor:
    public Quiz(String name, String category, int image, String description, int numOfQuestions) {
        this.category = category;
        this.name = name;
        this.image = image;
        this.Description = description;
        this.numOfQuestions = numOfQuestions;
    }


    // default c'tor:
    public Quiz() { }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getNumOfQuestions() {
        return numOfQuestions;
    }

    public void setNumOfQuestions(int numOfQuestions) {
        this.numOfQuestions = numOfQuestions;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "name='" + name + '\'' +
                ", image=" + image +
                ", Description='" + Description + '\'' +
                ", numOfQuestions=" + numOfQuestions +
                '}';
    }

    public void addQuestion (Question q){
        this.questions.add(q);
    }

    public Question getQuestionAt(int i){
        return this.questions.get(i);
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCategory() {
        return category;
    }
}
