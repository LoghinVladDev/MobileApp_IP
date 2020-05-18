package com.example.ipapp.ui.institutions;

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
import com.example.ipapp.R;
import com.example.ipapp.object.institution.Address;
import com.example.ipapp.object.institution.Institution;
import com.example.ipapp.object.institution.Member;
import com.example.ipapp.object.institution.Role;
import com.example.ipapp.utils.ApiUrls;
import com.example.ipapp.utils.UtilsSharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.file.attribute.UserPrincipalLookupService;
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

        Toast
                .makeText(this.getApplicationContext(), "From Selected Inst : " + institutionName, Toast.LENGTH_LONG)
                .show();

        this.rolesRequest();
        this.addressesRequest();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void rolesRequest(){
        Map<String, String> parameters = new HashMap<>();

        parameters.put("email", UtilsSharedPreferences.getString(getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_EMAIL, ""));
        parameters.put("hashedPassword", UtilsSharedPreferences.getString(getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_PASSWORD, ""));
        parameters.put("institutionName", this.institution.getName());

        this.makeHTTPRetrieveInstitutionRolesRequest(parameters);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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
                        .setID(role.getInt("ID"))
                        .setRight(Role.CAN_MODIFY_INSTITUTION,                  rights.getInt(Role.CAN_MODIFY_INSTITUTION)                  ==1)
                        .setRight(Role.CAN_DELETE_INSTITUTION,                  rights.getInt(Role.CAN_MODIFY_INSTITUTION)                  ==1)
                        .setRight(Role.CAN_ADD_MEMBERS,                         rights.getInt(Role.CAN_ADD_MEMBERS)                         ==1)
                        .setRight(Role.CAN_REMOVE_MEMBERS,                      rights.getInt(Role.CAN_REMOVE_MEMBERS)                      ==1)
                        .setRight(Role.CAN_UPLOAD_DOCUMENTS,                    rights.getInt(Role.CAN_UPLOAD_DOCUMENTS)                    ==1)
                        .setRight(Role.CAN_PREVIEW_UPLOADED_DOCUMENTS,          rights.getInt(Role.CAN_PREVIEW_UPLOADED_DOCUMENTS)          ==1)
                        .setRight(Role.CAN_REMOVE_UPLOADED_DOCUMENT,            rights.getInt(Role.CAN_REMOVE_UPLOADED_DOCUMENT)            ==1)
                        .setRight(Role.CAN_SEND_DOCUMENTS,                      rights.getInt(Role.CAN_SEND_DOCUMENTS)                      ==1)
                        .setRight(Role.CAN_PREVIEW_RECEIVED_DOCUMENTS,          rights.getInt(Role.CAN_PREVIEW_RECEIVED_DOCUMENTS)          ==1)
                        .setRight(Role.CAN_PREVIEW_SPECIFIC_RECEIVED_DOCUMENT,  rights.getInt(Role.CAN_PREVIEW_SPECIFIC_RECEIVED_DOCUMENT)  ==1)
                        .setRight(Role.CAN_REMOVE_RECEIVED_DOCUMENT,            rights.getInt(Role.CAN_REMOVE_RECEIVED_DOCUMENT)            ==1)
                        .setRight(Role.CAN_DOWNLOAD_DOCUMENTS,                  rights.getInt(Role.CAN_DOWNLOAD_DOCUMENTS)                  ==1)
                        .setRight(Role.CAN_ADD_ROLES,                           rights.getInt(Role.CAN_ADD_ROLES)                           ==1)
                        .setRight(Role.CAN_REMOVE_ROLES,                        rights.getInt(Role.CAN_REMOVE_ROLES)                        ==1)
                        .setRight(Role.CAN_MODIFY_ROLES,                        rights.getInt(Role.CAN_MODIFY_ROLES)                        ==1)
                        .setRight(Role.CAN_ASSIGN_ROLES,                        rights.getInt(Role.CAN_ASSIGN_ROLES)                        ==1)
                        .setRight(Role.CAN_DE_ASSIGN_ROLES,                     rights.getInt(Role.CAN_DE_ASSIGN_ROLES)                     ==1)
                );
            }

            this.institution.addRoles(roleList);
        } catch (JSONException e){
            e.printStackTrace();
        }

        this.membersRequest();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void membersRequest(){
        Map<String, String> parameters = new HashMap<>();

        parameters.put("email", UtilsSharedPreferences.getString(getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_EMAIL, ""));
        parameters.put("hashedPassword", UtilsSharedPreferences.getString(getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_PASSWORD, ""));
        parameters.put("institutionName", this.institution.getName());

        this.makeHTTPRetrieveInstitutionMembersRequest(parameters);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void membersRequestCallback(JSONObject responseObject){
        Log.d(LOG_TAG, "INSTITUTION MEMBERS : " + responseObject.toString() );

        try {
            JSONArray membersArray = responseObject.getJSONArray("members");

            for(int i = 0, length = membersArray.length(); i < length; i++){
                JSONObject memberJSON = (JSONObject) membersArray.get(i);

                List<Member> members = new ArrayList<>();

                Role role = null;

                for (Role r : this.institution.getRoleList()) {
                    if(r.getID() == memberJSON.getInt("id"))
                        role = r;
                }

                members.add(
                        new Member()
                            .setUsername(memberJSON.getString("email"))
                            .setUserID(memberJSON.getInt("userID"))
                            .setRole(role)
                );

                this.institution.addMembers(members);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }

        Log.d(LOG_TAG, "INSTITUTION : " + this.institution.debugToString());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void addressesRequest(){
        Map<String, String> params = new HashMap<>();

        params.put("email", UtilsSharedPreferences.getString(getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_EMAIL, ""));
        params.put("hashedPassword", UtilsSharedPreferences.getString(getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_PASSWORD, ""));
        params.put("institutionName", this.institution.getName());

        this.makeHTTPRetrieveAddressesRequest(params);
    }

    private void addressesRequestCallback(JSONObject responseObject){
        try{
            JSONArray addressesArray = responseObject.getJSONArray("Addresses");

            List<Address> addresses = new ArrayList<>();
            for(int i = 0, length = addressesArray.length(); i < length; i++){
                JSONObject addressJSON = (JSONObject) addressesArray.get(i);

                addresses.add(
                        new Address()
                            .setID(addressJSON.getInt("ID"))
                            .setCountry(addressJSON.getString("Country"))
                            .setRegion(addressJSON.getString("Region"))
                            .setCity(addressJSON.getString("City"))
                            .setStreet(addressJSON.getString("Street"))
                            .setNumber(addressJSON.getInt("Number"))
                            .setBuilding(addressJSON.getString("Building"))
                            .setFloor(addressJSON.getInt("Floor"))
                            .setApartment(addressJSON.getInt("Apartment"))
                );
            }

            this.institution.addAddresses(addresses);
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void makeHTTPRetrieveAddressesRequest(final Map<String, String> bodyParameters){
        StringRequest request = new StringRequest(
            Request.Method.GET,
            ApiUrls.encodeGetURLParams(ApiUrls.INSTITUTION_RETRIEVE_ADDRESSES, bodyParameters),
            response -> {
                Log.d(LOG_TAG, "RESPONSE : " + response);
                if( response.contains("SUCCESS") ) {
                    try {
                        this.addressesRequestCallback(new JSONObject(response).getJSONObject("returnedObject"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                }
            },
            error -> {
                Log.d(LOG_TAG, "VOLLEY ERROR : " + error.toString());
                Toast.makeText(getApplicationContext(), "Error : " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        ) {
            protected Map<String, String> getParams() { return bodyParameters; }
        };
        this.requestQueue.add(request);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void makeHTTPRetrieveInstitutionMembersRequest(final Map<String, String> bodyParameters){
        StringRequest request = new StringRequest(
                Request.Method.GET,
                ApiUrls.encodeGetURLParams(ApiUrls.INSTITUTION_MEMBER_RETRIEVE_MEMBERS, bodyParameters),
                response -> {
                    Log.d(LOG_TAG, "RESPONSE : " + response);
                    if(response.contains("SUCCESS")){
                        try{
                            this.membersRequestCallback(new JSONObject(response).getJSONObject("returnedObject"));
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), response , Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.d(LOG_TAG, "VOLLEY_ERROR : " + error.toString());
                    Toast.makeText(getApplicationContext(), "Error : " + error.toString(), Toast.LENGTH_SHORT).show();
                }
        ) {
            protected Map<String, String> getParams() { return bodyParameters; }
        };
        this.requestQueue.add(request);
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
