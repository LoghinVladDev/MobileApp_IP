package com.example.ipapp.object.document;

import androidx.annotation.NonNull;

public class Currency {
    private int ID;
    private String title;

    public Currency(){

    }

    @NonNull
    @Override
    public String toString() {
        return "{ID=" + this.ID + ",title=" + this.title + "}";
    }

    public String viewString(){
        return this.title;
    }

    public String getTitle() {
        return title;
    }

    public int getID() {
        return ID;
    }

    public Currency setTitle(String title) {
        this.title = title;
        return this;
    }

    public Currency setID(int ID) {
        this.ID = ID;
        return this;
    }
}
