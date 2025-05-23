package com.example.myapplication;

import java.io.Serializable;

public class Question implements Serializable {

    private String text, ch1, ch2, ch3, ch4, corrAns;

    public Question(String text, String ch1, String ch2, String ch3, String ch4, String corrAns) {
        this.text = text;
        this.ch1 = ch1;
        this.ch2 = ch2;
        this.ch3 = ch3;
        this.ch4 = ch4;
        this.corrAns = corrAns;
    }

    public Question() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCh1() {
        return ch1;
    }

    public void setCh1(String ch1) {
        this.ch1 = ch1;
    }

    public String getCh2() {
        return ch2;
    }

    public void setCh2(String ch2) {
        this.ch2 = ch2;
    }

    public String getCh3() {
        return ch3;
    }

    public void setCh3(String ch3) {
        this.ch3 = ch3;
    }

    public String getCh4() {
        return ch4;
    }

    public void setCh4(String ch4) {
        this.ch4 = ch4;
    }

    public String getCorrAns() {
        return corrAns;
    }

    public void setCorrAns(String corrAns) {
        this.corrAns = corrAns;
    }

}
