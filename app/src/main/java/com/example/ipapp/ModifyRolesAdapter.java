package com.example.ipapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ipapp.object.institution.Role;

import java.util.List;

public class ModifyRolesAdapter extends RecyclerView.Adapter<ModifyRolesAdapter.ViewHolder> {
    private List<Role> mData;
    private LayoutInflater mInflater;

    public ModifyRolesAdapter(Context context, List<Role> mData) {
        // TODO FINISH THIS
        this.mData = mData;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.rv_institution_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // TODO FINISH THIS
        Role role = mData.get(position);
        holder.textViewRoleName.setText(role.toString());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // TODO FINISH THIS
        public TextView textViewRoleName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }

    }
}
