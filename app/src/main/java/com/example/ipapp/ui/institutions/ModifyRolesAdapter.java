package com.example.ipapp.ui.institutions;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ipapp.DeleteRoleActivity;
import com.example.ipapp.R;
import com.example.ipapp.object.institution.Institution;
import com.example.ipapp.object.institution.Member;
import com.example.ipapp.object.institution.Role;
import com.example.ipapp.utils.ApiUrls;
import com.example.ipapp.utils.UtilsSharedPreferences;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModifyRolesAdapter extends RecyclerView.Adapter<ModifyRolesAdapter.ViewHolder> {
    private List<Role> mData;
    private LayoutInflater mInflater;
    private Context context;
    private Institution institution;

    private RequestQueue requestQueue;

    private static String LOG_TAG = "MODIFY_ROLES_ADAPTER";

    ModifyRolesAdapter(Context context, List<Role> mData, Institution institution) {
        this.requestQueue = Volley.newRequestQueue(context);

        this.institution = institution;
        this.mData = mData;
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.rv_role_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // TODO FINISH THIS
        Role role = mData.get(position);
        Log.v(LOG_TAG, "Role: " + role + "Position: " + position);
        Log.v(LOG_TAG, "Holder " + holder.toString());
        Log.v(LOG_TAG, holder.textViewRoleName == null ? "TEXTVIEW NULL" : "TEXTVIEW OK");
        holder.textViewRoleName.setText(role.toViewString());
        holder.buttonSaveRoleChanges.setOnClickListener(e -> holder.onClickModifyRole());
        holder.buttonDeleteRole.setOnClickListener(e->this.onClickDeleteRole(holder.textViewRoleName.getText().toString()));

        for(Role r : mData){
            //Log.d(LOG_TAG,"ROLE CHECK : " + r.getName() + ", look for : " + textViewRoleName.getText().toString());
            if(r.getName().equals(holder.textViewRoleName.getText().toString())){
                holder.switchCanModifyInstitution.setChecked(r.isAllowed(Role.CAN_MODIFY_INSTITUTION));
                holder.switchCanDeassignRoles.setChecked(r.isAllowed(Role.CAN_DE_ASSIGN_ROLES));
                holder.switchCanAddMembers.setChecked(r.isAllowed(Role.CAN_ADD_MEMBERS));
                holder.switchCanDeleteInstitution.setChecked(r.isAllowed(Role.CAN_DELETE_INSTITUTION));
                holder.switchCanAssignRoles.setChecked(r.isAllowed(Role.CAN_ASSIGN_ROLES));
                holder.switchCanRemoveMembers.setChecked(r.isAllowed(Role.CAN_REMOVE_MEMBERS));
                holder.switchCanUploadMembers.setChecked(r.isAllowed(Role.CAN_UPLOAD_DOCUMENTS));
                holder.switchCanPreviewUploadedDocuments.setChecked(r.isAllowed(Role.CAN_PREVIEW_UPLOADED_DOCUMENTS));
                holder.switchCanRemoveUploadedDocuments.setChecked(r.isAllowed(Role.CAN_REMOVE_UPLOADED_DOCUMENT));
                holder.switchCanSendDocuments.setChecked(r.isAllowed(Role.CAN_SEND_DOCUMENTS));
                holder.switchCanPreviewReceivedDocuments.setChecked(r.isAllowed(Role.CAN_PREVIEW_RECEIVED_DOCUMENTS));
                holder.switchCanPreviewSpecificReceivedDocument.setChecked(r.isAllowed(Role.CAN_PREVIEW_SPECIFIC_RECEIVED_DOCUMENT));
                holder.switchCanRemoveReceivedDocument.setChecked(r.isAllowed(Role.CAN_REMOVE_RECEIVED_DOCUMENT));
                holder.switchCanDownloadDocuments.setChecked(r.isAllowed(Role.CAN_DOWNLOAD_DOCUMENTS));
                holder.switchCanAddRoles.setChecked(r.isAllowed(Role.CAN_ADD_ROLES));
                holder.switchCanRemoveRoles.setChecked(r.isAllowed(Role.CAN_REMOVE_ROLES));
                holder.switchCanModifyRoles.setChecked(r.isAllowed(Role.CAN_MODIFY_ROLES));
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewRoleName;
        Button buttonDeleteRole;
        Button buttonSaveRoleChanges;
        //<editor-fold desc="SWITCHES">
        Switch switchCanAddMembers;
        Switch switchCanAddRoles;
        Switch switchCanAssignRoles;
        Switch switchCanDeassignRoles;
        Switch switchCanDeleteInstitution;
        Switch switchCanModifyRoles;
        Switch switchCanModifyInstitution;
        Switch switchCanRemoveMembers;
        Switch switchCanUploadMembers;
        Switch switchCanPreviewUploadedDocuments;
        Switch switchCanRemoveUploadedDocuments;
        Switch switchCanSendDocuments;
        Switch switchCanPreviewReceivedDocuments;
        Switch switchCanPreviewSpecificReceivedDocument;
        Switch switchCanRemoveReceivedDocument;
        Switch switchCanDownloadDocuments;
        Switch switchCanRemoveRoles;

        //</editor-fold>



        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRoleName = itemView.findViewById(R.id.textViewRoleName);
            buttonDeleteRole = itemView.findViewById(R.id.buttonDeleteCurrentRole);
            buttonSaveRoleChanges = itemView.findViewById(R.id.buttonSaveRoleChanges);

            //<editor-fold desc="SWITCHES">
            switchCanModifyInstitution = itemView.findViewById(R.id.switchCanModifyInstitution);
            switchCanDeleteInstitution = itemView.findViewById(R.id.switchCanDeleteInstitution);
            switchCanAddMembers = itemView.findViewById(R.id.switchCanAddMembers);
            switchCanDeleteInstitution = itemView.findViewById(R.id.switchCanDeleteInstitution);
            switchCanAddMembers = itemView.findViewById(R.id.switchCanAddMembers);
            switchCanRemoveMembers = itemView.findViewById(R.id.switchCanRemoveMembers);
            switchCanUploadMembers = itemView.findViewById(R.id.switchCanUploadMembers);
            switchCanPreviewUploadedDocuments = itemView.findViewById(R.id.switchCanPreviewUploadedDocuments);
            switchCanRemoveUploadedDocuments = itemView.findViewById(R.id.switchCanRemoveUploadedDocuments);
            switchCanSendDocuments = itemView.findViewById(R.id.switchCanSendDocuments);
            switchCanPreviewReceivedDocuments = itemView.findViewById(R.id.switchCanPreviewReceivedDocuments);
            switchCanPreviewSpecificReceivedDocument = itemView.findViewById(R.id.switchCanPreviewSpecificReceivedDocument);

            switchCanRemoveReceivedDocument = itemView.findViewById(R.id.switchCanRemoveReceivedDocument);
            switchCanDownloadDocuments = itemView.findViewById(R.id.switchCanDownloadDocuments);
            switchCanAddRoles = itemView.findViewById(R.id.switchCanAddRoles);
            switchCanRemoveRoles = itemView.findViewById(R.id.switchCanRemoveRoles);
            switchCanModifyRoles = itemView.findViewById(R.id.switchCanModifyRoles);
            switchCanAssignRoles = itemView.findViewById(R.id.switchCanAssignRoles);
            switchCanDeassignRoles = itemView.findViewById(R.id.switchCanDeassignRoles);

            switchCanModifyRoles = itemView.findViewById(R.id.switchCanModifyRoles);
            //</editor-fold>



        }

        private void onClickDeleteRole(View v) {
            Intent goToDeleteRole = new Intent(itemView.getContext(), DeleteRoleActivity.class);
            goToDeleteRole.putExtra("KEY_INSTITUTION_NAME", getInstitutionName());
            goToDeleteRole.putExtra("KEY_ROLE_NAME", textViewRoleName.getText().toString());
            itemView.getContext().startActivity(goToDeleteRole);
        }

        private void onClickModifyRole(){
            String roleName = this.textViewRoleName.getText().toString(); // TODO : actual role name ,before modify

            Role newRole = new Role()
                    .setName(this.textViewRoleName.getText().toString()) // TODO : new role name
                    .setRight(Role.CAN_MODIFY_INSTITUTION, this.switchCanModifyInstitution.isChecked())
                    .setRight(Role.CAN_DELETE_INSTITUTION, this.switchCanDeleteInstitution.isChecked())
                    .setRight(Role.CAN_ADD_MEMBERS, this.switchCanAddMembers.isChecked())
                    .setRight(Role.CAN_REMOVE_MEMBERS, this.switchCanRemoveMembers.isChecked())
                    .setRight(Role.CAN_UPLOAD_DOCUMENTS, this.switchCanUploadMembers.isChecked())
                    .setRight(Role.CAN_PREVIEW_UPLOADED_DOCUMENTS, this.switchCanPreviewUploadedDocuments.isChecked())
                    .setRight(Role.CAN_REMOVE_UPLOADED_DOCUMENT, this.switchCanRemoveUploadedDocuments.isChecked())
                    .setRight(Role.CAN_SEND_DOCUMENTS, this.switchCanSendDocuments.isChecked())
                    .setRight(Role.CAN_PREVIEW_RECEIVED_DOCUMENTS, this.switchCanPreviewReceivedDocuments.isChecked())
                    .setRight(Role.CAN_PREVIEW_SPECIFIC_RECEIVED_DOCUMENT, this.switchCanPreviewSpecificReceivedDocument.isChecked())
                    .setRight(Role.CAN_REMOVE_RECEIVED_DOCUMENT, this.switchCanRemoveReceivedDocument.isChecked())
                    .setRight(Role.CAN_DOWNLOAD_DOCUMENTS, this.switchCanDownloadDocuments.isChecked())
                    .setRight(Role.CAN_ADD_ROLES, this.switchCanAddRoles.isChecked())
                    .setRight(Role.CAN_REMOVE_ROLES, this.switchCanRemoveRoles.isChecked())
                    .setRight(Role.CAN_MODIFY_ROLES, this.switchCanModifyRoles.isChecked())
                    .setRight(Role.CAN_ASSIGN_ROLES, this.switchCanAssignRoles.isChecked())
                    .setRight(Role.CAN_DE_ASSIGN_ROLES, this.switchCanDeassignRoles.isChecked());

            try {

//                Log.d(LOG_TAG, "test : " + roleName + ", " + newRole.toString());

                finishModifyRole(roleName, newRole);
            } catch (JSONException exception){
                Log.d(LOG_TAG, "HOPA : " + exception.toString());
            }
        }

    }

    private boolean isRoleAssigned(String roleName){
        for(Member m : this.institution.getMemberList())
            if(m.getRole().getName().equals(roleName))
                return true;

        return false;
    }

    private void onClickDeleteRole(String roleName){
        Log.d(LOG_TAG, "WANT TO DELETE : " + roleName);

        if(this.isRoleAssigned(roleName)) {
            Toast.makeText(context, "Cannot delete this role,there are members assigned with this role", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, String> params = new HashMap<>();

        params.put("email", UtilsSharedPreferences.getString(this.context, UtilsSharedPreferences.KEY_LOGGED_EMAIL, ""));
        params.put("hashedPassword", UtilsSharedPreferences.getString(this.context, UtilsSharedPreferences.KEY_LOGGED_PASSWORD, ""));
        params.put("institutionName", this.institution.getName());
        params.put("roleName", roleName);

        this.makeHTTPRequestDeleteRole(params);
    }

    private void makeHTTPRequestDeleteRole(Map<String ,String> params){
        StringRequest request = new StringRequest(
            Request.Method.POST,
            ApiUrls.INSTITUTION_ROLE_DELETE,
            response -> {
                Log.d(LOG_TAG, "DELETE ROLE : " + response);
                if(response.contains("SUCCESS")){
                    Toast.makeText(context, "Role Deletion Success", Toast.LENGTH_SHORT).show();
                    for(Role r : this.mData){
                        if(r.getName().equals(params.get("roleName"))) {
                            this.mData.remove(r);
                            this.notifyDataSetChanged();
                            break;
                        }
                    }
                }
            },
            error -> {
                Log.e(LOG_TAG, "VOLLEY ERROR : " + error.toString());
            }
        ) {
            @Override
            protected Map<String, String> getParams(){
                return params;
            }
        };
        this.requestQueue.add(request);
    }

    private String getInstitutionName() {
        return this.institution.getName();
    }

    /**
     *
     * @param oldRoleName  CAN BE ROLE or STRING containing old role
     * @param newRole  create a new role object for simplicity. has integrated hashmap for roles
     */
    private void finishModifyRole(String oldRoleName, Role newRole) throws JSONException {
        Map<String, String> params = new HashMap<>();

        params.put("email", UtilsSharedPreferences.getString(this.context, UtilsSharedPreferences.KEY_LOGGED_EMAIL, ""));
        params.put("hashedPassword", UtilsSharedPreferences.getString(this.context, UtilsSharedPreferences.KEY_LOGGED_PASSWORD, ""));
        params.put("institutionName", this.institution.getName());
        params.put("roleName", oldRoleName);
        params.put("newRoleName", newRole.getName() == null ? oldRoleName : (newRole.getName().isEmpty() ? oldRoleName : newRole.getName()));
        params.put("newRoleRights", newRole.getRightsDictionaryJSON().toString());

        Log.d(LOG_TAG, "Debug before save role : " + params.toString());

        this.makeHTTPRequestModifyRole(params);
    }

    private void callbackRoleModify(){
        Log.d(LOG_TAG, "ROLE MODIFY SUCCESS");
        Toast.makeText(this.context, "Role Update Success!", Toast.LENGTH_SHORT).show();
    }

    private void makeHTTPRequestModifyRole(Map<String, String> params){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                ApiUrls.INSTITUTION_ROLE_MODIFY,
                response -> {
                    Log.d(LOG_TAG, "MODIFY ROLE RESPONSE : " + response.toString());
                    if(response.contains("SUCCESS")){
                        this.callbackRoleModify();
                    }
                },
                error -> {
                    Log.d(LOG_TAG, "VOLLEY ERROR : " + error.toString());
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        this.requestQueue.add(request);
    }
}
