package com.example.ipapp.object.document;

public class Pair <K,U>  {
    private K first;
    private U second;

    public Pair(K first, U second){
        this.first = first;
        this.second = second;
    }

    public K getFirst() {
        return first;
    }

    public U getSecond() {
        return second;
    }

    public void setFirst(K first) {
        this.first = first;
    }

    public void setSecond(U second) {
        this.second = second;
    }
}
