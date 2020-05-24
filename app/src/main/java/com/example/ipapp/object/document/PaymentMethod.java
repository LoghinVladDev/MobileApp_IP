package com.example.ipapp.object.document;

import androidx.annotation.NonNull;

public class PaymentMethod {
    private String title;
    private int ID;

    public PaymentMethod(){

    }

    @NonNull
    @Override
    public String toString() {
        return "{ID=" + this.ID + ",title=" + this.title + "}";
    }

    public String viewString(){
        return this.title;
    }

    public PaymentMethod setID(int ID) {
        this.ID = ID;
        return this;
    }

    public PaymentMethod setTitle(String title) {
        this.title = title;
        return this;
    }

    public int getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }
}
