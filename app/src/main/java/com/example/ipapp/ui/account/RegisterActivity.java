package com.example.ipapp.ui.account;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.ipapp.R;
import com.example.ipapp.utils.ApiUrls;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private static final String LOG_TAG = "Register Activity";
    //<editor-fold desc="UI ELEMENTS">
    private EditText editTextEmail, editTextFirstName, editTextLastName, editTextPassword, editTextConfirmPassword;
    private Button buttonRegister;
    //</editor-fold>

    private RequestQueue httpRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initialiseUI();

        httpRequestQueue = Volley.newRequestQueue(this);
    }

    private void initialiseUI() {
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);

        buttonRegister = findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(v -> onClickButtonRegister(v));
    }

    private void onClickButtonRegister(View v) {

        if (! (editTextEmail.getText().toString().isEmpty() && editTextPassword.getText().toString().isEmpty() && editTextFirstName.getText().toString().isEmpty() &&
                editTextLastName.getText().toString().isEmpty() && editTextConfirmPassword.getText().toString().isEmpty()) ) {

            if (editTextPassword.getText().toString().equals(editTextConfirmPassword.getText().toString())) {

                Map<String, String> bodyParameters = new HashMap<>();
                bodyParameters.put("email", editTextEmail.getText().toString());
                bodyParameters.put("firstName", editTextFirstName.getText().toString());
                bodyParameters.put("lastName", editTextLastName.getText().toString());
                bodyParameters.put("hashedPassword", editTextPassword.getText().toString());

                makeHTTPRegisterRequest(bodyParameters);
            }
            else {
                editTextConfirmPassword.setError("Passwords doesn't match!");
            }
        }
        else {
            if (editTextPassword.getText().toString().isEmpty()) editTextPassword.setError("Insert a password!");
            if (editTextEmail.getText().toString().isEmpty()) editTextEmail.setError("Insert an email!");
            if (editTextFirstName.getText().toString().isEmpty()) editTextFirstName.setError("Insert your first name!");
            if (editTextLastName.getText().toString().isEmpty()) editTextLastName.setError("Insert your last name!");
            if (editTextConfirmPassword.getText().toString().isEmpty()) editTextConfirmPassword.setError("Confirm your password!");
        }

    }

    private void makeHTTPRegisterRequest(final Map<String, String> bodyParameters) {
        StringRequest loginRequest = new StringRequest(Request.Method.POST, ApiUrls.ACCOUNT_CREATE,
                response -> {
                    Log.d(LOG_TAG, "RESPONSE : " + response);
                    Toast.makeText(getApplicationContext(),
                            response,
                            Toast.LENGTH_SHORT).show();
                    if (response.contains("SUCCESS")) {
                        Intent goBackToLoginPage = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(goBackToLoginPage);
                        finish();
                    }
                },
                error -> {
                    Log.d(LOG_TAG, "VOLLEY ERROR : " + error.toString());
                    Toast.makeText(getApplicationContext(),
                            "Error : " + error,
                            Toast.LENGTH_SHORT).show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                return bodyParameters;
            }
        };
        httpRequestQueue.add(loginRequest);
    }
}
