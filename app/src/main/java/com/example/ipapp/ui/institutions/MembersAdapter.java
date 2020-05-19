package com.example.ipapp.ui.institutions;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ShareActionProvider;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ipapp.R;
import com.example.ipapp.object.institution.Institution;
import com.example.ipapp.object.institution.Member;
import com.example.ipapp.object.institution.Role;
import com.example.ipapp.utils.ApiUrls;
import com.example.ipapp.utils.UtilsSharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.ViewHolder> {
    private static final String TAG = "M_ADAPT";
    private List<Member> mData;
    private LayoutInflater mInflater;
    private Institution memberInstitution;
    private Context context;

    private RequestQueue requestQueue;

    // data is passed into the constructor
    MembersAdapter(Context context, Institution institution) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.memberInstitution = institution;
        this.mData = this.memberInstitution.getMemberList();

        this.requestQueue = Volley.newRequestQueue(this.context);
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
        holder.spinnerRoles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Role selectedRole = null;

                for(Role role : memberInstitution.getRoleList())
                    if(role.getName().equals(parent.getSelectedItem()))
                        selectedRole = role;

//                    Log.d(TAG, "ROLE SELECTION : " + selectedRole + ", m name " + holder.textViewMemberName.getText().toString());

                requestMemberRoleChanged(selectedRole, holder.textViewMemberName.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //<editor-fold desc="FOR DEBUG PURPOSES">
        //        holder.spinnerRoles.setVisibility(View.GONE);
//        holder.textViewMemberRole.setVisibility(View.GONE);
        //</editor-fold>

        for(Member m : this.memberInstitution.getMemberList())
            if(m.getUsername().equals(UtilsSharedPreferences.getString(this.context, UtilsSharedPreferences.KEY_LOGGED_EMAIL, "")))
                member = m;

        if (!member.getRole().isAllowed(Role.CAN_MODIFY_ROLES)) {
            holder.spinnerRoles.setVisibility(View.GONE);
        } else {
            holder.textViewMemberRole.setVisibility(View.GONE);
        }
    }

    private void requestMemberRoleChanged(Role role, String memberEmail){
        Map<String, String> params = new HashMap<>();

        params.put("email", UtilsSharedPreferences.getString(this.context, UtilsSharedPreferences.KEY_LOGGED_EMAIL, ""));
        params.put("hashedPassword", UtilsSharedPreferences.getString(this.context, UtilsSharedPreferences.KEY_LOGGED_PASSWORD, ""));
        params.put("institutionName", this.memberInstitution.getName());
        params.put("newRole", role.getName());
        params.put("memberEmail", memberEmail);

        this.makeHTTPRequestMemberChangeRole(params, role);
    }

    private void responseCallbackUpdateMemberRole(Role newRole, String memberEmail){
        for(Member m : this.memberInstitution.getMemberList())
            if(m.getUsername().equals(memberEmail))
                m.setRole(newRole);

        Log.d(TAG, "responseCallbackUpdateMemberRole: Update Check : " + this.memberInstitution.getMemberList());
    }

    private void makeHTTPRequestMemberChangeRole(Map<String, String> params, Role role){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                ApiUrls.INSTITUTION_MEMBER_MODIFY_ROLE,
                response -> {
                    Log.d(TAG, "RESPONSE ON CHANGE ROLE : " + response);

                    if(response.contains("SUCCESS")){
                        this.responseCallbackUpdateMemberRole(role, params.get("memberEmail"));
                    }
//                    else{
                        //TODO :  implement de-select on failure
//                    }
                },
                error -> {
                    Log.d(TAG, "RESPONSE ON ERROR CHANGE ROLE, VOLLEY ERROR : " + error.toString());
                }
        ) {
            protected Map<String, String> getParams() {
                return params;
            }
        };
        this.requestQueue.add(request);
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