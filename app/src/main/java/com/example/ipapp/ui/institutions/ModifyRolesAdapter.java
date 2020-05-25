package com.example.ipapp.ui.institutions;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
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
        // TODO FINISH THIS

        this.requestQueue = Volley.newRequestQueue(context);

        this.institution = institution;
        this.mData = mData;
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
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

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewRoleName;

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

        private Button finishRoleModifyButton;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRoleName = itemView.findViewById(R.id.textViewRoleName);

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

            /// TODO : this.finishRoleModifyButton = findViewByID(...)

            this.finishRoleModifyButton.setOnClickListener(e->this.onClickModifyRole());

        }

        private void onClickModifyRole(){
            String roleName = ""; // TODO : actual role name ,before modify

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
                finishModifyRole(roleName, newRole);
            } catch (JSONException exception){
                Log.d(LOG_TAG, "HOPA : " + exception.toString());
            }
        }

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

        this.makeHTTPRequestModifyRole(params);
    }

    private void callbackRoleModify(){
        Log.d(LOG_TAG, "ROLE MODIFY SUCCESS");
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
