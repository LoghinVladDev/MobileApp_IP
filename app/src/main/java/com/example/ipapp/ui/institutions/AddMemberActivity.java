package com.example.ipapp.ui.institutions;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ipapp.R;
import com.example.ipapp.object.institution.Institution;
import com.example.ipapp.utils.ApiUrls;
import com.example.ipapp.utils.UtilsSharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddMemberActivity extends AppCompatActivity {

    private static final String INTENT_KEY_INSTITUTION_JSON = "institution";
    private final static String LOG_TAG = "MEMBER_ADD";
    private RequestQueue requestQueue;

    private EditText editTextUserEmail;
    private Spinner spinnerRole;
    private Button btnAddNewMember;
    private String institutionName, selectedRoleForUser;
    private String[] institutionsRolesName;

    private Institution institution;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        this.requestQueue = Volley.newRequestQueue(this);

        try {
            JSONObject parameters = new JSONObject(getIntent().getStringExtra(INTENT_KEY_INSTITUTION_JSON));
            this.institutionName = parameters.getString("name");

            this.institution = new Institution().setID(parameters.getInt("ID")).setName(parameters.getString("name"));

            institutionsRolesName = parameters.getString("roles").split(",");

            Log.v(LOG_TAG, "Roles string: " + institutionsRolesName);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.initialiseUI();
    }

    private void initialiseUI() {
        spinnerRole = findViewById(R.id.spinnerSelectRoleForMember);
        btnAddNewMember = findViewById(R.id.buttonAddMember);
        editTextUserEmail = findViewById(R.id.editTextUserEmailToAdd);


        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, institutionsRolesName);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerRole.setAdapter(spinnerAdapter);
        spinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedRoleForUser = institutionsRolesName[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnAddNewMember.setOnClickListener(v -> { addMemberRequest(); });

    }

    private void addMemberRequest() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", UtilsSharedPreferences.getString(getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_EMAIL, ""));
        requestBody.put("hashedPassword", UtilsSharedPreferences.getString(getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_PASSWORD, ""));
        requestBody.put("institutionName", institutionName);
        requestBody.put("userIdentifier", editTextUserEmail.getText().toString().trim());
        requestBody.put("roleName", selectedRoleForUser);

        Log.v(LOG_TAG, "M_ADD_REQUEST" + requestBody.toString() );

        makeHTTPPostAddMemberRequest(requestBody);
    }

    private void makeHTTPPostAddMemberRequest(Map<String, String> params) {
        StringRequest request = new StringRequest(Request.Method.POST, ApiUrls.INSTITUTION_MEMBER_ADD,
                response -> {
                    Log.d(LOG_TAG, "RESPONSE ON MEMBER ADD" + response);

                    if (response.contains("SUCCESS")) {
                        Toast.makeText(getApplicationContext(), "Member added!", Toast.LENGTH_SHORT).show();
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
                error ->  {
                    error.printStackTrace();
                    Log.d(LOG_TAG, "RESPONSE" + error.toString());
                })
        {
            @Override
            protected Map<String, String> getParams(){
                return params;
            }
        };
        requestQueue.add(request);
    }
}
