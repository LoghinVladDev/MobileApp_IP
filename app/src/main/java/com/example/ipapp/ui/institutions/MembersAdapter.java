package com.example.ipapp.ui.institutions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private List<Member> mData;
    private LayoutInflater mInflater;
    private Institution memberInstitution;

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
        holder.memberName.setText(member.getUsername());
        holder.memberRole.setText(member.getRole().getName());

//        List<String> memberInstitutionRolesNames = new ArrayList<>();
//
//        for (Role r : memberInstitution.getRoleList()) {
//            if (!r.getName().equals(member.getRole().getName())) {
//                memberInstitutionRolesNames.add(r.getName());
//            }
//        }
//
//        if (!member.getRole().isAllowed(Role.CAN_MODIFY_ROLES)) {
//            holder.allRoles.setVisibility(View.GONE);
//        } else {
//            holder.memberRole.setVisibility(View.GONE);
//        }
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
        TextView memberName;
        TextView memberRole;
//        Spinner allRoles;

        ViewHolder(View itemView) {
            super(itemView);
            memberName = itemView.findViewById(R.id.memberRow);
            memberRole = itemView.findViewById(R.id.memberRole);
//            allRoles = itemView.findViewById(R.id.memberRoleSpinner);
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