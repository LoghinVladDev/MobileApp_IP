package com.example.ipapp.ui.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ipapp.R;
import com.example.ipapp.utils.ApiUrls;
import com.example.ipapp.utils.UtilsSharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class ModifyAccountActivity extends AppCompatActivity {
    private final static String LOG_TAG = "MODIFY ACC ACTIVITY";

    //<editor-fold desc="UI ELEMENTS">
    private EditText editTextNewPassword, editTextNewFirstName, editTextNewLastName;
    private CheckBox checkBoxPassword, checkBoxFirstName, checkBoxLastName;
    private Button buttonModifyAccount;
    //</editor-fold>

    private RequestQueue httpRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_account);

        initialiseUI();

        httpRequestQueue = Volley.newRequestQueue(this);
    }

    private void initialiseUI() {
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextNewFirstName = findViewById(R.id.editTextNewFirstName);
        editTextNewLastName = findViewById(R.id.editTextNewLastName);
        buttonModifyAccount = findViewById(R.id.buttonModifyAccount);
        checkBoxPassword = findViewById(R.id.newPasswordCheckBox);
        checkBoxFirstName = findViewById(R.id.newFirstNameCheckBox);
        checkBoxLastName = findViewById(R.id.newLastNameCheckBox);


        checkBoxPassword.setChecked(false);
        checkBoxFirstName.setChecked(false);
        checkBoxLastName.setChecked(false);

        editTextNewPassword.setVisibility(View.GONE);
        editTextNewLastName.setVisibility(View.GONE);
        editTextNewFirstName.setVisibility(View.GONE);

        checkBoxPassword.setOnCheckedChangeListener((buttonView, isChecked) -> editTextNewPassword.setVisibility(isChecked ? View.VISIBLE : View.GONE));
        checkBoxLastName.setOnCheckedChangeListener(((buttonView, isChecked) -> editTextNewLastName.setVisibility(isChecked ? View.VISIBLE : View.GONE)));
        checkBoxFirstName.setOnCheckedChangeListener(((buttonView, isChecked) -> editTextNewFirstName.setVisibility(isChecked ? View.VISIBLE : View.GONE)));

        buttonModifyAccount.setOnClickListener(v -> onClickButtonModifyAccount());
    }

    private void onClickButtonModifyAccount() {
        final String PARAM_EMAIL = "email";
        final String PARAM_PASSWORD = "currentHashedPassword";
        final String PARAM_NEW_PASSWORD = "newHashedPassword";
        final String PARAM_NEW_FIRST_NAME = "newFirstName";
        final String PARAM_NEW_LAST_NAME = "newLastName";

        if (!checkBoxFirstName.isChecked() && !checkBoxLastName.isChecked() && !checkBoxPassword.isChecked()) {
            Toast.makeText(getApplicationContext(), getString(R.string.toastModifyAccountNoBoxChecked), Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(getApplicationContext(), UtilsSharedPreferences.getString(getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_EMAIL, ""), Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), UtilsSharedPreferences.getString(getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_PASSWORD, ""), Toast.LENGTH_SHORT).show();

        Map<String, String> params = new HashMap<>();
        params.put(PARAM_EMAIL, UtilsSharedPreferences.getString(getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_EMAIL, ""));
        params.put(PARAM_PASSWORD, UtilsSharedPreferences.getString(getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_PASSWORD, ""));
        params.put(PARAM_NEW_PASSWORD, checkBoxPassword.isChecked() ? editTextNewPassword.getText().toString() : "");
        params.put(PARAM_NEW_FIRST_NAME, checkBoxFirstName.isChecked() ? editTextNewFirstName.getText().toString() : "");
        params.put(PARAM_NEW_LAST_NAME, checkBoxLastName.isChecked() ? editTextNewLastName.getText().toString() : "");

        Log.v(LOG_TAG, "PARAMS MAP MODIFY ACC REQ:" + params);
        makeHTTPModifyAccountRequest(params);
    }

    private void makeHTTPModifyAccountRequest(final Map<String, String> requestParameters) {
        StringRequest modifyAccountRequest = new StringRequest(Request.Method.POST, ApiUrls.ACCOUNT_MODIFY,
                response -> {
                    // TODO IMPLEMENT PROPER USER RESPONSE (ON MODIFYING ACC)
                    Log.d(LOG_TAG, "RESPONSE : " + response);
                    if (response.contains("SUCCESS")) {
                        Toast.makeText(getApplicationContext(), getString(R.string.toastModifyAccountRequestSuccess), Toast.LENGTH_SHORT).show();

                        Intent goBackToAccountFragment = new Intent(getApplicationContext(), AccountFragment.class);
                        startActivity(goBackToAccountFragment);
                        finish();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Error : " + response, Toast.LENGTH_LONG).show();
                },
                error -> {
                    Log.d(LOG_TAG, "VOLLEY ERROR : " + error.toString());
                    Toast.makeText(getApplicationContext(),
                            "Networking Error :" + error,
                            Toast.LENGTH_SHORT).show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                return requestParameters;
            }
        };
        httpRequestQueue.add(modifyAccountRequest);
    }
}
