package com.example.ipapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ipapp.object.institution.Institution;
import com.example.ipapp.object.institution.Role;
import com.example.ipapp.utils.ApiUrls;
import com.example.ipapp.utils.UtilsSharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectedInstitutionActivity extends AppCompatActivity {

    private static final String INTENT_KEY_INSTITUTION_NAME = "institutionName";
    private static final String INTENT_KEY_INSTITUTION_JSON = "institution";
    private String institutionName;

    private RequestQueue requestQueue;

    private static final String LOG_TAG = "SELECTED_INSTITUTION";

    private Institution institution;

    Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_institution);

        this.requestQueue = Volley.newRequestQueue(this);

        //this.institutionName = getIntent().getStringExtra(INTENT_KEY_INSTITUTION_NAME);

        try {
            JSONObject parameters = new JSONObject(getIntent().getStringExtra(INTENT_KEY_INSTITUTION_JSON));
            this.institutionName = parameters.getString("name");

            this.institution = new Institution().setID(parameters.getInt("ID")).setName(parameters.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (null != getSupportActionBar())
            getSupportActionBar().setTitle(institutionName);

        Toast
                .makeText(this.getApplicationContext(), "From Selected Inst : " + institutionName, Toast.LENGTH_LONG)
                .show();

        this.rolesRequest();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void rolesRequest(){
        Map<String, String> parameters = new HashMap<>();

        parameters.put("email", UtilsSharedPreferences.getString(getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_EMAIL, ""));
        parameters.put("hashedPassword", UtilsSharedPreferences.getString(getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_PASSWORD, ""));
        parameters.put("institutionName", this.institution.getName());

        this.makeHTTPRetrieveInstitutionRolesRequest(parameters);
    }

    private void rolesRequestCallback(JSONObject responseData){
        Log.d(LOG_TAG, "RESPONSE OBJECT : " + responseData.toString() );

        try {
            JSONArray rolesArray = responseData.getJSONArray("roles");
            List<Role> roleList = new ArrayList<>();

            for(int i = 0, length = rolesArray.length(); i < length; i++){

                JSONObject role = (JSONObject) rolesArray.get(i);
                JSONObject rights = role.getJSONObject("rights");

                roleList.add(
                    new Role()
                        .setName(role.getString("name"))
                        .setID(rights.getInt("ID"))
                        .setRight(Role.CAN_MODIFY_INSTITUTION,rights.getInt(Role.CAN_MODIFY_INSTITUTION) == 1)
                        .setRight(Role.CAN_DELETE_INSTITUTION, rights.getInt(Role.CAN_MODIFY_INSTITUTION) == 1)
                        .setRight(Role.CAN_ADD_MEMBERS, rights.getInt(Role.CAN_ADD_MEMBERS)==1)
                        .setRight(Role.CAN_REMOVE_MEMBERS, rights.getInt(Role.CAN_REMOVE_MEMBERS)==1)
                        .setRight(Role.CAN_UPLOAD_DOCUMENTS, rights.getInt(Role.CAN_UPLOAD_DOCUMENTS)==1)
                        .setRight(Role.CAN_PREVIEW_UPLOADED_DOCUMENTS, rights.getInt(Role.CAN_PREVIEW_UPLOADED_DOCUMENTS)==1)
                        .setRight(Role.CAN_REMOVE_UPLOADED_DOCUMENT, rights.getInt(Role.CAN_REMOVE_UPLOADED_DOCUMENT)==1)
                        .setRight(Role.CAN_SEND_DOCUMENTS, rights.getInt(Role.CAN_SEND_DOCUMENTS)==1)
                        .setRight(Role.CAN_PREVIEW_RECEIVED_DOCUMENTS, rights.getInt(Role.CAN_PREVIEW_RECEIVED_DOCUMENTS)==1)
                        .setRight(Role.CAN_PREVIEW_SPECIFIC_RECEIVED_DOCUMENT, rights.getInt(Role.CAN_PREVIEW_SPECIFIC_RECEIVED_DOCUMENT)==1)
                        .setRight(Role.CAN_REMOVE_RECEIVED_DOCUMENT, rights.getInt(Role.CAN_REMOVE_RECEIVED_DOCUMENT)==1)
                        .setRight(Role.CAN_DOWNLOAD_DOCUMENTS, rights.getInt(Role.CAN_DOWNLOAD_DOCUMENTS)==1)
                        .setRight(Role.CAN_ADD_ROLES, rights.getInt(Role.CAN_ADD_ROLES)==1)
                        .setRight(Role.CAN_REMOVE_ROLES, rights.getInt(Role.CAN_REMOVE_ROLES)==1)
                        .setRight(Role.CAN_MODIFY_ROLES, rights.getInt(Role.CAN_MODIFY_ROLES)==1)
                        .setRight(Role.CAN_ASSIGN_ROLES, rights.getInt(Role.CAN_ASSIGN_ROLES)==1)
                        .setRight(Role.CAN_DE_ASSIGN_ROLES, rights.getInt(Role.CAN_DE_ASSIGN_ROLES)==1)
                );
            }

            this.institution.addRoles(roleList);
        } catch (JSONException e){
            e.printStackTrace();
        }

        Log.d(LOG_TAG, "INSTITUTION : " + this.institution.debugToString());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void makeHTTPRetrieveInstitutionRolesRequest(final Map<String, String> bodyParameters){
        StringRequest request = new StringRequest(
                Request.Method.GET,
                ApiUrls.encodeGetURLParams(ApiUrls.INSTITUTION_ROLE_RETRIEVE_ALL, bodyParameters),
                response -> {
                    Log.d(LOG_TAG, "RESPONSE : " + response);
                    if(response.contains("SUCCESS")){
                        try{
                            this.rolesRequestCallback(new JSONObject(response).getJSONObject("returnedObject"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.d(LOG_TAG, "VOLLEY ERROR : " + error.toString());
                    Toast.makeText(getApplicationContext(), "Error : " + error, Toast.LENGTH_SHORT).show();
                }
        ) {
            protected Map<String, String> getParams() { return bodyParameters; }
        };
        this.requestQueue.add(request);
    }

}
