package com.example.ipapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


public class SelectedDocumentActivity extends AppCompatActivity {

    private static final String INTENT_KEY_DOCUMENT_JSON = "document";
    private String documentInformation;

    protected void onCreate(Bundle savedDocumentInformation) {
        super.onCreate(savedDocumentInformation);
        setContentView(R.layout.activity_selected_document);

        try {
            JSONObject parameters = new JSONObject(getIntent().getStringExtra(INTENT_KEY_DOCUMENT_JSON));
            this.documentInformation = parameters.getString("SelectedDocument");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Toast
                .makeText(this.getApplicationContext(), "From Selected Document : " + this.documentInformation, Toast.LENGTH_LONG)
                .show();

    }
}

