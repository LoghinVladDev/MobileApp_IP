package com.example.ipapp.ui.institutions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.ipapp.R;
import com.example.ipapp.object.institution.Institution;
import com.example.ipapp.object.institution.Role;
import com.example.ipapp.ui.MotoItemDecoration;

import java.util.List;

public class ModifyInstitutionRolesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewRoles;
    private ModifyRolesAdapter adapter;

    private String institutionName;

    private static String LOG_TAG = "MODIFY_INSTITUTION_ROLES_ACTIVITY";

    private List<Role> roles;
    private Institution institution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_roles);

        this.institutionName = getIntent().getStringExtra("KEY_INSTITUTION_NAME");

        for(Institution i : InstitutionsFragment.getInstitutions())
            if(i.getName().equals(this.institutionName))
                this.institution = i;

        this.roles = this.institution.getRoleList();

        InstitutionsFragment.debugInstitutionList();

        Log.d(LOG_TAG, "Roles : " + roles.toString());

        initialiseUI(this.institution.getRoleList());
    }

    private void initialiseUI(List<Role> roles) {

        Log.d(LOG_TAG, "Roles : " + roles.toString());

        this.recyclerViewRoles = findViewById(R.id.recyclerViewRoles);
        this.recyclerViewRoles.setLayoutManager(new LinearLayoutManager(this));
        // TODO FINISH ADAPTER
        this.adapter = new ModifyRolesAdapter(this, roles, this.institution);
        this.recyclerViewRoles.addItemDecoration(new MotoItemDecoration());
        recyclerViewRoles.setAdapter(adapter);
    }

}
