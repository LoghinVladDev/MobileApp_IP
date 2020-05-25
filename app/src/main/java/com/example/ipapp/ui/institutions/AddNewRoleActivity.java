package com.example.ipapp.ui.institutions;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ipapp.R;
import com.example.ipapp.utils.ApiUrls;
import com.example.ipapp.utils.UtilsSharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class AddNewRoleActivity extends AppCompatActivity {

    private static final String LOG_TAG = "ADD_NEW_ROLE";
    //<editor-fold desc="UI COMPONENTS">
    EditText editTextNewRoleName;
    Button buttonAddNewRole;
    //</editor-fold>
    RequestQueue httpRequestQueue;
    String institutionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_role);


        initUI();
        httpRequestQueue = Volley.newRequestQueue(this);
        institutionName = getIntent().getStringExtra("KEY_INSTITUTION_NAME");
    }

    private void initUI() {
        //<editor-fold desc="FIND VIEW ID FOR UI COMPONENTS">
        editTextNewRoleName = findViewById(R.id.editTextNewRoleName);
        buttonAddNewRole = findViewById(R.id.buttonAddNewRole);
        //</editor-fold>

        buttonAddNewRole.setOnClickListener(this::onClickButtonAddNewRole);

    }

    private void onClickButtonAddNewRole(View v) {
        if (!validateForm())
            return;
        Map<String, String> bodyParams = getParams();
        bodyParams.put("roleName", editTextNewRoleName.getText().toString());

        StringRequest requestCreateRole = new StringRequest(Request.Method.POST, ApiUrls.INSTITUTION_ROLE_CREATE, response -> {
            Log.d(LOG_TAG, "RESPONSE : " + response);
            if (response.contains("SUCCESS")) {
                Toast.makeText(this, "Institution added successfully!", Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(this, getString(R.string.toastErrorVolley) + response, Toast.LENGTH_LONG).show();
        }, error -> {
            Log.d(LOG_TAG, "VOLLEY ERROR : " + error.toString());
            Toast.makeText(this,
                    getString(R.string.toastErrorVolley) + error.toString(),
                    Toast.LENGTH_SHORT).show();
        }) {
            @Override
            protected Map<String, String> getParams() {
                return bodyParams;
            }
        };

        httpRequestQueue.add(requestCreateRole);
    }

    private boolean validateForm() {
        if (editTextNewRoleName.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.toastNewRoleName), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put("roleName", editTextNewRoleName.getText().toString().trim());
        params.put("institutionName", institutionName);
        params.put("email", UtilsSharedPreferences.getString(this, UtilsSharedPreferences.KEY_LOGGED_EMAIL, ""));
        params.put("hashedPassword", UtilsSharedPreferences.getString(this, UtilsSharedPreferences.KEY_LOGGED_PASSWORD, ""));
        return params;
    }
}
