package com.example.ipapp.object.document;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class Invoice extends Document {
    private List<Pair<Item, Integer>> items;

    public String getType() { return "Invoice"; }

    public String toViewString(){
        return "Invoice, id = " + this.ID;
    }

    public String toString(){
        return
                "{documentHeader=" + super.toString() + ",type=Invoice}";
    }

    public Invoice(){
        super();
        this.items = new ArrayList<>();
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
