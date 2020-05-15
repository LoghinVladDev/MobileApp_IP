package com.example.ipapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ipapp.object.institution.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class InstitutionsAdapter extends RecyclerView.Adapter<InstitutionsAdapter.ViewHolder> {

    private List<Institution> mData;
    private LayoutInflater mInflater;
    private Context context;

    // data is passed into the constructor
    public InstitutionsAdapter(Context context, List<Institution> mData) {
        this.context = context;
        this.mData = mData;
        this.mInflater = LayoutInflater.from(context);
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.rv_institution_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Institution item = mData.get(position);
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
            textViewInstitutionToString = itemView.findViewById(R.id.institutionRow);
        }

        @Override
        public void onClick(View view) {

        }
    }

    // convenience method for getting data at click position
    Institution getItem(int id) {
        return mData.get(id);
    }

}