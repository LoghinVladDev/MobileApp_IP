package com.example.ipapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.ipapp.object.institution.Role;
import com.example.ipapp.ui.MotoItemDecoration;

import java.util.List;

public class ModifyInstitutionRolesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewRoles;
    private ModifyRolesAdapter adapter;

    private List<Role> roles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_roles);


        initialiseUI(roles);
    }

    private void initialiseUI(List<Role> roles) {

        this.recyclerViewRoles = findViewById(R.id.recyclerViewRoles);
        this.recyclerViewRoles.setLayoutManager(new LinearLayoutManager(this));
        // TODO FINISH ADAPTER
        this.adapter = new ModifyRolesAdapter(this, roles);
        this.recyclerViewRoles.addItemDecoration(new MotoItemDecoration());
        recyclerViewRoles.setAdapter(adapter);
    }

}
