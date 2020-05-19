package com.example.ipapp.ui.documents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ipapp.R;
import com.example.ipapp.object.document.Item;
import com.example.ipapp.object.document.Pair;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {
    private List<Pair<Item, Integer>> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    ItemsAdapter(Context context, List<Pair<Item, Integer>> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.rv_item_row, parent, false);
        return new ItemsAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ItemsAdapter.ViewHolder holder, int position) {
        Pair<Item, Integer> item = mData.get(position);

        String itemCount = item.getFirst().getName() + " x " + item.getSecond().toString();

        holder.textViewItem.setText(itemCount);
        holder.textViewItemCurrency.setText(item.getFirst().getCurrency());
        holder.textViewItemValueWithTax.setText(String.valueOf(item.getFirst().getValue()));
        holder.textViewItemsTotalValueWithTax.setText(String.valueOf(item.getFirst().getValue() * item.getSecond()));
    }


    // total number of rows
    @Override
    public int getItemCount() {
        if (mData != null)
            return mData.size();
        else
            return 0;
    }


    // stores and recycles views as they are scrolled off screen
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewItem;
        TextView textViewItemCurrency;
        TextView textViewItemValueWithTax;
        TextView textViewItemsTotalValueWithTax;

        ViewHolder(View itemView) {
            super(itemView);
            textViewItem = itemView.findViewById(R.id.textViewItemDescription);
            textViewItemCurrency = itemView.findViewById(R.id.textViewItemCurrency);
            textViewItemValueWithTax = itemView.findViewById(R.id.textViewItemValue);
            textViewItemsTotalValueWithTax = itemView.findViewById(R.id.textViewItemsTotalValue);
        }

        @Override
        public void onClick(View view) {

        }
    }

    // convenience method for getting data at click position
    Pair<Item, Integer> getItem(int id) {
        return mData.get(id);
    }
}
