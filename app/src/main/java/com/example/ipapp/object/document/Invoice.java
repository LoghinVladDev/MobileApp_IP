package com.example.ipapp.object.document;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.List;

public class Invoice extends Document {
    private List<Pair<Item, Integer>> items;

    public Invoice(){
        super();
    }

    public List<Pair<Item, Integer>> getItems() {
        return items;
    }

    public Invoice setItems(List<Pair<Item, Integer>> items) {
        this.items = items;
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Invoice addItem(Item item, int quantity){
        Pair<Item, Integer> pair = null;
        if(this.items.stream().anyMatch(e->e.getFirst().equals(item)))
            pair =  this.items.stream().filter(e->e.getFirst().equals(item)).findFirst().get();

        if(pair != null)
            pair.setSecond(pair.getSecond() + quantity);

        this.items.add(new Pair<>(item, quantity));
        return this;
    }
}