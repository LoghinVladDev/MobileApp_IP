package com.example.ipapp.ui.institutions;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ipapp.R;
import com.example.ipapp.object.institution.Institution;
import com.example.ipapp.utils.ApiUrls;
import com.example.ipapp.utils.UtilsSharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DeleteInstitutionActivity extends AppCompatActivity {

    private static final String INTENT_KEY_INSTITUTION_JSON = "institution";
    private final static String LOG_TAG = "INST_DELETE";
    private String institutionName;
    private RequestQueue requestQueue;

    private Button btnConfirmDeletion, btnDenyDeletion;

    private Institution institution;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_institution);

        this.requestQueue = Volley.newRequestQueue(this);

        try {
            JSONObject parameters = new JSONObject(getIntent().getStringExtra(INTENT_KEY_INSTITUTION_JSON));
            this.institutionName = parameters.getString("name");

            this.institution = new Institution().setID(parameters.getInt("ID")).setName(parameters.getString("name"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.initialiseUI();
    }

    private void initialiseUI() {
        btnConfirmDeletion = findViewById(R.id.buttonConfirmDeleteInstitution);
        btnDenyDeletion = findViewById(R.id.buttonDenyDeleteInstitution);

        btnConfirmDeletion.setOnClickListener(v -> {onClickConfirmDelete();});
        btnDenyDeletion.setOnClickListener(v -> {onClickDenyDelete();});
    }

    private void onClickDenyDelete() {
        Intent goBackToSelectedInstitution = new Intent(this, SelectedInstitutionActivity.class);

        JSONObject parameters = new JSONObject();
        try {
            parameters.put("ID", institution.getID());
            parameters.put("name", institution.getName());

            goBackToSelectedInstitution.putExtra(INTENT_KEY_INSTITUTION_JSON, parameters.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        startActivity(goBackToSelectedInstitution);
    }


    private void onClickConfirmDelete() {
        Map<String, String> requestBody = new HashMap<>();

        requestBody.put("email", UtilsSharedPreferences.getString(getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_EMAIL, ""));
        requestBody.put("hashedPassword", UtilsSharedPreferences.getString(getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_PASSWORD, ""));
        requestBody.put("institutionName", institutionName);

        makeHTTPPostDeleteInstitutionRequest(requestBody);
    }

    private void makeHTTPPostDeleteInstitutionRequest(Map<String, String> params) {
        StringRequest request = new StringRequest(Request.Method.POST, ApiUrls.INSTITUTION_DELETE,
                response -> {
                    Log.v(LOG_TAG, "RESPONSE: " +  response);
                    if (response.contains("SUCCESS")) {
                        Toast.makeText(getApplicationContext(), "Institution deleted!", Toast.LENGTH_SHORT).show();

                        Intent goBackToSelectedInstitution = new Intent(this, InstitutionsFragment.class);

                        startActivity(goBackToSelectedInstitution);
                        finish();
                    }
                },
                error -> {
                    Log.d(LOG_TAG, error.toString());
                    error.printStackTrace();
                })
        {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };

        requestQueue.add(request);
    }
}
