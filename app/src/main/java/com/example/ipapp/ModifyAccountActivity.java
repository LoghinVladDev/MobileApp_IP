package com.example.ipapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ipapp.utils.ApiUrls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ModifyAccountActivity extends AppCompatActivity {
    private final static String LOG_TAG = "MODIFY ACC ACTIVITY";

    private EditText editTextNewPassword, editTextNewFirstName, editTextNewLastName;
    private Button buttonModifyAccount;
    private RequestQueue httpRequestQueue;
    private CheckBox passwordCheckBox;
    private CheckBox firstNameCheckBox;
    private CheckBox lastNameCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_account);

        initialiseUI();
        httpRequestQueue = Volley.newRequestQueue(this);
    }

    private void initialiseUI() {
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextNewFirstName = findViewById(R.id.editTextLastName);
        editTextNewLastName = findViewById(R.id.editTextNewLastName);
        buttonModifyAccount = findViewById(R.id.buttonModifyAccount);
        passwordCheckBox = findViewById(R.id.newPasswordCheckBox);
        firstNameCheckBox = findViewById(R.id.newFirstNameCheckBox);
        lastNameCheckBox = findViewById(R.id.newLastNameCheckBox);

        buttonModifyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButtonModifyAccount();
            }
        });
    }

    private void onClickButtonModifyAccount() {
        if (!checkFormInput())
            return;
        Map<String, String> params = getModifyAccountParams();
        makeHTTPModifyAccountRequest(params);
    }

    private void makeHTTPModifyAccountRequest(final Map<String, String> requestParameters) {
        StringRequest modifyAccountRequest = new StringRequest(Request.Method.POST, ApiUrls.ACCOUNT_MODIFY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // TODO IMPLEMENT PROPER USER RESPONSE (ON MODIFYING ACC)
                        Log.d(LOG_TAG, "RESPONSE : " + response);
                        Toast.makeText(getApplicationContext(),
                                response,
                                Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(LOG_TAG, "VOLLEY ERROR : " + error.toString());
                        Toast.makeText(getApplicationContext(),
                                "Error : " + error,
                                Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                return requestParameters;
            }
        };
        httpRequestQueue.add(modifyAccountRequest);
    }

    private Map<String, String> getModifyAccountParams() {
        Map<String, String> params = new HashMap<>();
        // TODO IMPLEMENT METHOD
        /*Modify Account (POST) :
         * param : email=nihiwo9844@mailboxt.com&currentHashedPassword=test&newHashedPassword=TEST&newFirstName=Oldghin&newLastName=Oldgout
         */
        return params;
    }

    private boolean checkFormInput() {
        // TOOD CHECK FORM INPUT - IF INPUT IS BAD SHOW A TOAST TO ALERT THE USER ACCORDINGLY
        return true;
    }
}
