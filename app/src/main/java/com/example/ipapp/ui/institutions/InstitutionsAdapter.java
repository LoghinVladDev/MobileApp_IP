package com.example.ipapp.ui.institutions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ipapp.R;
import com.example.ipapp.object.institution.Institution;

import java.util.List;

public class InstitutionsAdapter extends RecyclerView.Adapter<InstitutionsAdapter.ViewHolder> {

    private final View.OnClickListener listRowOnClickListener;
    private List<Institution> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    public InstitutionsAdapter(Context context, List<Institution> mData, View.OnClickListener rowOnClickListener) {
        this.mData = mData;
        this.mInflater = LayoutInflater.from(context);
        this.listRowOnClickListener = rowOnClickListener;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.rv_institution_row, parent, false);
        view.setOnClickListener(listRowOnClickListener);
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
            textViewInstitutionToString = itemView.findViewById(R.id.textViewRVRowInstitutionName);
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