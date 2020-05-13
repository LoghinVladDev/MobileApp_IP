package com.example.ipapp;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private static final String LOG_TAG = "Register Activity";
    //<editor-fold desc="UI ELEMENTS">
    private EditText editTextEmail, editTextFirstName, editTextLastName,editTextPassword;
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

        buttonRegister = findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButtonRegister(v);
            }
        });
    }

    private void onClickButtonRegister(View v) {
        Map<String, String> bodyParameters = new HashMap<>();
        bodyParameters.put("email", editTextEmail.getText().toString());
        bodyParameters.put("firstName", editTextFirstName.getText().toString());
        bodyParameters.put("lastName", editTextLastName.getText().toString());
        bodyParameters.put("hashedPassword", editTextPassword.getText().toString());

        makeHTTPRegisterRequest(bodyParameters);
    }
    private void makeHTTPRegisterRequest(final Map<String, String> bodyParameters) {
        StringRequest loginRequest = new StringRequest(Request.Method.POST, ApiUrls.ACCOUNT_CREATE,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
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
