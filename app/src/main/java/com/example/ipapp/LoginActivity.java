package com.example.ipapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.example.ipapp.utils.ApiUrls;
import com.example.ipapp.utils.UtilsSharedPreferences;

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

    private void onClickButtonLogin(View v) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("email", editTextEmail.getText().toString());
        requestParams.put("hashedPassword", editTextPassword.getText().toString());
        UtilsSharedPreferences.setString(getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_EMAIL, editTextEmail.getText().toString());
        UtilsSharedPreferences.setString(getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_PASSWORD, editTextPassword.getText().toString());

        makeHTTPLoginRequest(requestParams);
    }
    private void makeHTTPLoginRequest( final Map<String, String> bodyParameters) {
        StringRequest loginRequest = new StringRequest(Request.Method.POST, ApiUrls.ACCOUNT_LOGIN,
                response -> {
                    Log.d(LOG_TAG, "RESPONSE : " + response);
                    if (response.contains("SUCCESS")) {

                        Intent goToHome = new Intent(LoginActivity.this, HomeActivity.class);
                        Bundle bundle = new Bundle();

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

}
