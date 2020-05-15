package com.example.ipapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InstitutionsAdapter<T> extends RecyclerView.Adapter<InstitutionsAdapter.ViewHolder> {
    private final int recyclerViewID;

    private List<T> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    public InstitutionsAdapter(Context context, int recyclerViewID, List<T> mData) {
        this.recyclerViewID = recyclerViewID;
        this.mData = mData;
        this.mInflater = LayoutInflater.from(context);
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(this.recyclerViewID, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        T item = mData.get(position);
        holder.textViewInstitutionToString.setText(item.toString());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewInstitutionToString;

        ViewHolder(View itemView) {
            super(itemView);
            textViewInstitutionToString = itemView.findViewById(R.id.textInstitution);
        }

        @Override
        public void onClick(View view) {

        }
    }

    // convenience method for getting data at click position
    T getItem(int id) {
        return mData.get(id);
    }

}