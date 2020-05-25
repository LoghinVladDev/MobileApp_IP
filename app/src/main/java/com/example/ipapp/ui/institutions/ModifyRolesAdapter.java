package com.example.ipapp.ui.institutions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ipapp.R;
import com.example.ipapp.object.institution.Role;

import java.util.List;

public class ModifyRolesAdapter extends RecyclerView.Adapter<ModifyRolesAdapter.ViewHolder> {
    private List<Role> mData;
    private LayoutInflater mInflater;

    ModifyRolesAdapter(Context context, List<Role> mData) {
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

        }

    }
}
