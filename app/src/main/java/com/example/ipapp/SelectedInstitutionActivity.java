package com.example.ipapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

public class SelectedInstitutionActivity extends AppCompatActivity {

    private static final String INTENT_KEY_INSTITUTION_NAME = "institutionName";
    private String institutionName;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_institution);

        this.institutionName = getIntent().getStringExtra(INTENT_KEY_INSTITUTION_NAME);

        if (null != getSupportActionBar())
            getSupportActionBar().setTitle(institutionName);

        Toast
                .makeText(this.getApplicationContext(), "From Selected Inst : " + institutionName, Toast.LENGTH_LONG)
                .show();
    }
}
