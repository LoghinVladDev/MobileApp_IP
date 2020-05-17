package com.example.ipapp.object.document;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class Receipt extends Document {
    // TODO PAYMENT METHOD TOTAL INVOICE LINKAGE
    private List<Pair<Item, Integer>> items;

    public String toString(){
        return "Receipt, id = " + this.ID;
    }

    public Receipt(){
        super();

        this.items = new ArrayList<>();
    }

    public List<Pair<Item, Integer>> getItems() {
        return items;
    }

    public Receipt setItems(List<Pair<Item, Integer>> items) {
        this.items = items;
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Receipt addItem(Item item, int quantity){
        Pair<Item, Integer> pair = null;
        if(this.items.stream().anyMatch(e->e.getFirst().equals(item)))
           pair =  this.items.stream().filter(e->e.getFirst().equals(item)).findFirst().get();

        if(pair != null)
            pair.setSecond(pair.getSecond() + quantity);

        this.items.add(new Pair<>(item, quantity));
        return this;
    }
}
