package com.example.ipapp.ui.institutions;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class RemoveMemberActivity extends AppCompatActivity {

    private static final String INTENT_KEY_INSTITUTION_JSON = "institution";
    private final static String LOG_TAG = "MEMBER_DELETE";

    Spinner spinnerMemberToDelete;
    Button buttonDeleteMember;
    private RequestQueue requestQueue;

    private String institutionName, selectedMemberToDelete;
    private String[] institutionMembers;

    private Institution institution;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_member);

        this.requestQueue = Volley.newRequestQueue(this);

        try {
            JSONObject parameters = new JSONObject(getIntent().getStringExtra(INTENT_KEY_INSTITUTION_JSON));
            this.institutionName = parameters.getString("name");

            this.institution = new Institution().setID(parameters.getInt("ID")).setName(parameters.getString("name"));

            institutionMembers = parameters.getString("members").split(",");

            Log.v(LOG_TAG, "Roles string: " + institutionMembers);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.initialiseUI();

    }

    private void initialiseUI() {
        spinnerMemberToDelete = findViewById(R.id.spinnerSelectMemberToRemove);
        buttonDeleteMember = findViewById(R.id.btnConfirmRemoveSelectedMember);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, institutionMembers);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerMemberToDelete.setAdapter(spinnerAdapter);
        spinnerMemberToDelete.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedMemberToDelete = institutionMembers[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        buttonDeleteMember.setOnClickListener(v -> deleteMemberRequest());
    }

    private void deleteMemberRequest() {
        Map<String, String> requestBody = new HashMap<>();

        requestBody.put("email", UtilsSharedPreferences.getString(getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_EMAIL, ""));
        requestBody.put("hashedPassword", UtilsSharedPreferences.getString(getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_PASSWORD, ""));
        requestBody.put("institutionName", institutionName);
        requestBody.put("memberEmail", selectedMemberToDelete);

        makeHTTPPostDeleteMemberRequesT(requestBody);
    }

    private void makeHTTPPostDeleteMemberRequesT(Map<String, String> params) {
        StringRequest request = new StringRequest(Request.Method.POST, ApiUrls.INSTITUTION_MEMBER_REMOVE,
                response -> {
                    Log.d(LOG_TAG, "RESPONSE REMOVE MEMBER: " + response);

                    if (response.contains("SUCCESS")) {
                        Toast.makeText(getApplicationContext(), "Member removed!", Toast.LENGTH_SHORT).show();
                        Intent goToSelectedInstitution = new Intent(getApplicationContext(), SelectedInstitutionActivity.class);

                        JSONObject parameters = new JSONObject();
                        try {
                            parameters.put("ID", institution.getID());
                            parameters.put("name", institution.getName());

                            goToSelectedInstitution.putExtra(INTENT_KEY_INSTITUTION_JSON, parameters.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        startActivity(goToSelectedInstitution);
                    }
                },
                error -> {

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
