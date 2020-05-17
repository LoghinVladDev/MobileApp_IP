package com.example.ipapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ipapp.object.institution.Institution;
import com.example.ipapp.utils.ApiUrls;
import com.example.ipapp.utils.UtilsSharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG = "LOGIN ACTIVITY";
    private static RequestQueue httpRequestQueue;

    public static RequestQueue getRequestQueue(){
        return LoginActivity.httpRequestQueue;
    }

    //<editor-fold desc="UI ELEMENTS">
    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin, buttonRegister;
    //</editor-fold>


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginActivity.httpRequestQueue = Volley.newRequestQueue(this);

        initializeUI();
    }

    @SuppressLint("SetTextI18n")
    private void initializeUI() {
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                onClickButtonLogin(v);
            }
        });

        buttonRegister = findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButtonRegister(v);
            }
        });

        editTextEmail.setText("vlad.loghin00@gmail.com");
        editTextPassword.setText("parola");
    }

    private void onClickButtonRegister(View v) {
        Intent goToRegisterActivity = new Intent(this, RegisterActivity.class);
        startActivity(goToRegisterActivity);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void onClickButtonLogin(View v) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("email", editTextEmail.getText().toString());
        requestParams.put("hashedPassword", editTextPassword.getText().toString());
        UtilsSharedPreferences.setString(getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_EMAIL, editTextEmail.getText().toString());
        UtilsSharedPreferences.setString(getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_PASSWORD, editTextPassword.getText().toString());

        makeHTTPLoginRequest(requestParams);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void makeHTTPLoginRequest(final Map<String, String> bodyParameters) {
        StringRequest loginRequest = new StringRequest(Request.Method.POST, ApiUrls.ACCOUNT_LOGIN,
                response -> {
                    Log.d(LOG_TAG, "RESPONSE : " + response);
                    if (response.contains("SUCCESS")) {

                        requestRetrieveAccount(bodyParameters);

                        Intent goToHome = new Intent(LoginActivity.this, HomeActivity.class);

                        startActivity(goToHome);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                response,
                                Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.d(LOG_TAG, "VOLLEY ERROR : " + error.toString());
                    Toast.makeText(getApplicationContext(),
                            "Error : " + error,
                            Toast.LENGTH_SHORT).show();
                }
        )
        {
            @Override
            protected Map<String, String> getParams()
            {
                return bodyParameters;
            }
        };
        httpRequestQueue.add(loginRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void requestRetrieveAccount(Map<String, String> requestParams)
    {
        this.makeHTTPRetrieveAccountRequest(requestParams);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void makeHTTPRetrieveAccountRequest( final Map<String, String> bodyParameters)
    {
        //Toast.makeText(getApplicationContext(), bodyParameters.get("email"), Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(), bodyParameters.get("hashedPassword"), Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(), bodyParameters.get("apiKey"), Toast.LENGTH_SHORT).show();

        StringRequest retrieveAccountRequest = new StringRequest(Request.Method.GET, ApiUrls.encodeGetURLParams(ApiUrls.ACCOUNT_RETRIEVE_INFORMATION, bodyParameters),
                response ->
                {
                    Log.d(LOG_TAG, "RESPONSE : " + response);
                    if (response.contains("SUCCESS"))
                    {
                        callbackRetrieveAccountInformation(response);
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),
                                response,
                                Toast.LENGTH_SHORT).show();
                    }
                },
                error ->
                {
                    Log.d(LOG_TAG, "VOLLEY ERROR : " + error.toString());
                    Toast.makeText(getApplicationContext(),
                            "Error : " + error,
                            Toast.LENGTH_SHORT).show();
                }
        )
        {
            @Override
            protected Map<String, String> getParams()
            {
                return bodyParameters;
            }
        };
        httpRequestQueue.add(retrieveAccountRequest);
    }

    private void callbackRetrieveAccountInformation(String JSONEncodedResponse)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(JSONEncodedResponse);
            JSONObject responseObject = (JSONObject) jsonObject.get("returnedObject");

            UtilsSharedPreferences.setString(getApplicationContext(), UtilsSharedPreferences.KEY_FIRST_NAME, responseObject.getString("First_Name"));
            UtilsSharedPreferences.setString(getApplicationContext(), UtilsSharedPreferences.KEY_LAST_NAME, responseObject.getString("Last_Name"));

            Log.d(LOG_TAG, "LIST : ");
        }
        catch (JSONException e)
        {
            Log.e(LOG_TAG, "ERROR : " + e.toString());
        }
    }

}
