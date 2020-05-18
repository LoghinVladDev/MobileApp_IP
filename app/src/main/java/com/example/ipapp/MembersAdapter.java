package com.example.ipapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ipapp.object.institution.Member;

import java.util.List;

public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.ViewHolder> {
    private List<Member> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    MembersAdapter(Context context, List<Member> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public MembersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.rv_member_row, parent, false);
        return new MembersAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(MembersAdapter.ViewHolder holder, int position) {
        Member member = mData.get(position);
        holder.textView.setText(member.getUsername());
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
        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.memberRow);
        }

        @Override
        public void onClick(View view) {

        }
    }

    // convenience method for getting data at click position
    Member getItem(int id) {
        return mData.get(id);
    }

}