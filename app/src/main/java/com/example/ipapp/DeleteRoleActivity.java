package com.example.ipapp;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ipapp.utils.ApiUrls;
import com.example.ipapp.utils.UtilsSharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class DeleteRoleActivity extends AppCompatActivity {

    private static final String LOG_TAG = "DELETE_ROLE";
    EditText editTextRoleName;
    Button buttonDeleteRole;

    String institutionName;
    String roleName;
    private RequestQueue httpRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_role);

        httpRequestQueue = Volley.newRequestQueue(this);

        institutionName = getIntent().getExtras().getString("KEY_INSTITUTION_NAME");
        roleName = getIntent().getExtras().getString("KEY_ROLE_NAME");

        initUI();
    }

    private void initUI() {
        editTextRoleName = findViewById(R.id.editTextDeleteRoleName);
        buttonDeleteRole = findViewById(R.id.buttonDeleteRole);

        buttonDeleteRole.setOnClickListener(this::onClickButtonDeleteRole);
        editTextRoleName.setText(roleName);
        editTextRoleName.setKeyListener(null); // readonly field

    }

    private void onClickButtonDeleteRole(View view) {
        Map<String, String> bodyParams = getParams();
        StringRequest request = new StringRequest(
                Request.Method.POST,
                ApiUrls.INSTITUTION_ROLE_DELETE,
                response -> {
                    Log.v(LOG_TAG, "Req Response: " + response);
                    if (response.contains("SUCCESS")) {
                        Toast.makeText(this, "Role deleted successfully!", Toast.LENGTH_LONG).show();
                        Intent goToHomeActivity = new Intent(this, HomeActivity.class);
                        startActivity(goToHomeActivity);
                    } else
                        Toast.makeText(this, getString(R.string.toastErrorVolley) + response, Toast.LENGTH_LONG).show();
                },
                error -> {
                    Log.v(LOG_TAG, "Volley err : " + error.toString());
                    Toast.makeText(this, getString(R.string.toastErrorVolley) + error, Toast.LENGTH_LONG).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                return bodyParams;
            }
        };
        httpRequestQueue.add(request);
    }

    private Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put("email", UtilsSharedPreferences.getString(this, UtilsSharedPreferences.KEY_LOGGED_EMAIL, ""));
        params.put("hashedPassword", UtilsSharedPreferences.getString(this, UtilsSharedPreferences.KEY_LOGGED_PASSWORD, ""));
        params.put("institutionName", this.institutionName);
        params.put("roleName", editTextRoleName.getText().toString());
        return params;
    }
}
