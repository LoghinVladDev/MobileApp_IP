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
import com.example.ipapp.utils.UtilsSharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ModifyAccountActivity extends AppCompatActivity {
    private final static String LOG_TAG = "MODIFY ACC ACTIVITY";
    private final static String KEY_NEW_PASSWORD = "newHashedPassword";
    private final static String KEY_NEW_FIRST_NAME = "newFirstName";
    private final static String KEY_NEW_LAST_NAME = "newLastName";

    private static Map<String, String> params = new HashMap<>();

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

        getInitParams();

        httpRequestQueue = Volley.newRequestQueue(this);
    }

    private void initialiseUI() {
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextNewFirstName = findViewById(R.id.editTextNewFirstName);
        editTextNewLastName = findViewById(R.id.editTextNewLastName);
        buttonModifyAccount = findViewById(R.id.buttonModifyAccount);
        passwordCheckBox = findViewById(R.id.newPasswordCheckBox);
        firstNameCheckBox = findViewById(R.id.newFirstNameCheckBox);
        lastNameCheckBox = findViewById(R.id.newLastNameCheckBox);

        passwordCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickPasswordCheckBox(passwordCheckBox);
            }
        });

        firstNameCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickFirstNameCheckBox(firstNameCheckBox);
            }
        });

        lastNameCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLastNameCheckBox(lastNameCheckBox);
            }
        });


        buttonModifyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButtonModifyAccount();
            }
        });
    }

    private void onClickPasswordCheckBox(CheckBox c)
    {
        if (c.isChecked() && !editTextNewPassword.getText().toString().equals(""))
        {
            params.put(KEY_NEW_PASSWORD, editTextNewPassword.getText().toString());
        }
        else
        {
            params.put(KEY_NEW_PASSWORD, "");
        }
    }

    private void onClickFirstNameCheckBox(CheckBox c)
    {
        if (c.isChecked() && !editTextNewFirstName.getText().toString().equals(""))
        {
            params.put(KEY_NEW_FIRST_NAME, editTextNewFirstName.getText().toString());
        }
        else
        {
            params.put(KEY_NEW_FIRST_NAME, "");
        }
    }

    private void onClickLastNameCheckBox(CheckBox c)
    {
        if (c.isChecked() && !editTextNewLastName.getText().toString().equals(""))
        {
            params.put(KEY_NEW_LAST_NAME, editTextNewLastName.getText().toString());
        }
        else
        {
            params.put(KEY_NEW_LAST_NAME, "");
        }
    }

    private void onClickButtonModifyAccount() {
        if (!checkFormInput())
            return;
        Toast.makeText(getApplicationContext(), UtilsSharedPreferences.getString(getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_EMAIL, ""), Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), UtilsSharedPreferences.getString(getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_PASSWORD, ""), Toast.LENGTH_SHORT).show();
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

    private void getInitParams()
    {
        params.put("email", UtilsSharedPreferences.getString(getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_EMAIL, ""));
        params.put("currentHashedPassword", UtilsSharedPreferences.getString(getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_PASSWORD, ""));
        params.put(KEY_NEW_PASSWORD, "");
        params.put(KEY_NEW_FIRST_NAME, "");
        params.put(KEY_NEW_LAST_NAME, "");
    }



    private boolean checkFormInput() {
        // TOOD CHECK FORM INPUT - IF INPUT IS BAD SHOW A TOAST TO ALERT THE USER ACCORDINGLY
        return true;
    }
}
