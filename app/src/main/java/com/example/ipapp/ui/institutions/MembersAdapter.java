package com.example.ipapp.ui.institutions;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ipapp.R;
import com.example.ipapp.object.institution.Institution;
import com.example.ipapp.object.institution.Member;
import com.example.ipapp.object.institution.Role;

import java.util.ArrayList;
import java.util.List;

public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.ViewHolder> {
    private static final String TAG = "M_ADAPT";
    private List<Member> mData;
    private LayoutInflater mInflater;
    private Institution memberInstitution;

    // data is passed into the constructor
    MembersAdapter(Context context, Institution institution) {
        this.mInflater = LayoutInflater.from(context);
        this.memberInstitution = institution;
        this.mData = this.memberInstitution.getMemberList();
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public MembersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.rv_member_row, parent, false);
        view.setOnClickListener(v -> {
            TextView memberTextViewName = v.findViewById(R.id.textViewMemberRow);

//            Toast.makeText(v.getContext(), memberTextViewName.getText().toString(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onCreateViewHolder: " + memberTextViewName.getText().toString());
        });
        return new MembersAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(MembersAdapter.ViewHolder holder, int position) {
        Member member = mData.get(position);
        holder.textViewMemberName.setText(member.getUsername());
        holder.textViewMemberRole.setText(member.getRole().getName());

        Log.e(TAG, "MEMEBER NO BRO : " + member.getUsername());

        List<String> memberInstitutionRolesNames = new ArrayList<>();

        memberInstitutionRolesNames.add(member.getRole().getName());

        for (Role r : memberInstitution.getRoleList()) {
            if (!r.getName().equals(member.getRole().getName())) {
                memberInstitutionRolesNames.add(r.getName());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(holder.itemView.getContext(), android.R.layout.simple_spinner_item, memberInstitutionRolesNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        holder.spinnerRoles.setAdapter(adapter);

        //<editor-fold desc="FOR DEBUG PURPOSES">
        //        holder.spinnerRoles.setVisibility(View.GONE);
//        holder.textViewMemberRole.setVisibility(View.GONE);
        //</editor-fold>

        if (!member.getRole().isAllowed(Role.CAN_MODIFY_ROLES)) {
            holder.spinnerRoles.setVisibility(View.GONE);
        } else {
            holder.textViewMemberRole.setVisibility(View.GONE);
        }
    }


    // total number of rows
    @Override
    public int getItemCount() {
        return null != mData ? mData.size() : 0;
    }


    // stores and recycles views as they are scrolled off screen
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewMemberName;
        TextView textViewMemberRole;
        Spinner spinnerRoles;

        ViewHolder(View itemView) {
            super(itemView);
            textViewMemberName = itemView.findViewById(R.id.textViewMemberRow);
            textViewMemberRole = itemView.findViewById(R.id.textViewMemberRole);
            spinnerRoles = itemView.findViewById(R.id.spinnerMemberRole);
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