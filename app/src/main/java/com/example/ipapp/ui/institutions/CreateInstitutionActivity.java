package com.example.ipapp.ui.institutions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ipapp.HomeActivity;
import com.example.ipapp.R;
import com.example.ipapp.utils.ApiUrls;
import com.example.ipapp.utils.UtilsSharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateInstitutionActivity extends AppCompatActivity {
    public static final String LOG_TAG = "CREATE INSTITUTION";
    private RequestQueue httpRequestQueue;

    //<editor-fold desc="UI ELEMENTS">
    private EditText editTextName, editTextCIF;

    private EditText editTextCountry, editTextRegion;
    private EditText editTextCity, editTextStreetName;
    private EditText editTextStreetNumber, editTextBuilding;
    private EditText editTextApartment;

    private Button buttonCreateInstitution;
    //</editor-fold>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_institution);

        httpRequestQueue = Volley.newRequestQueue(this);
        initialiseUI();
    }

    private void initialiseUI() {
        editTextApartment = findViewById(R.id.editTextCreateInstitutionsApartment);
        editTextBuilding = findViewById(R.id.editTextCreateInstitutionsBuilding);
        editTextCIF = findViewById(R.id.editTextCreateInstitutionsCIF);
        editTextCity = findViewById(R.id.editTextCreateInstitutionCity);
        editTextCountry = findViewById(R.id.editTextCreateInstitutionCountry);
        editTextName = findViewById(R.id.editTextCreateInstitutionName);
        editTextRegion = findViewById(R.id.editTextCreateInstitutionRegion);
        editTextStreetName = findViewById(R.id.editTextCreateInstitutionStreetName);
        editTextStreetNumber = findViewById(R.id.editTextCreateInstitutionStreetNumber);


        buttonCreateInstitution = findViewById(R.id.buttonCreateInstitution);
        buttonCreateInstitution.setOnClickListener(v -> {
            if (!formHasProperValues())
                return;

            makeHTTPRequestCreateInstitution(getHTTPRequestParams());
        });
    }

    private void makeHTTPRequestCreateInstitution(final Map<String, String> bodyParams) {
        StringRequest requestCreateInstitution = new StringRequest(Request.Method.POST, ApiUrls.INSTITUTION_CREATE,
                response -> {
                    Log.d(LOG_TAG, "RESPONSE : " + response);
                    if (response.contains("SUCCESS")) {
                        Toast.makeText(this, getString(R.string.toastSuccessCreateInstitution), Toast.LENGTH_LONG).show();
                        Intent goToHomeActivity = new Intent(this, HomeActivity.class);
                        startActivity(goToHomeActivity);
                        finish(); // cant go back from home to this activity
                    } else
                        Toast.makeText(this, getString(R.string.toastErrorRequestInput) + response, Toast.LENGTH_SHORT).show();
                },
                error -> {
                    Log.e(LOG_TAG, "Volley error: " + error);
                    Toast.makeText(this, getString(R.string.toastErrorVolley), Toast.LENGTH_SHORT).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                return bodyParams;
            }
        };

        this.httpRequestQueue.add(requestCreateInstitution);
    }

    private Map<String, String> getHTTPRequestParams() {
        JSONObject jsonAddress = new JSONObject();
        try {
            jsonAddress.put("Country", editTextCountry.getText().toString().trim());
            jsonAddress.put("Region", editTextRegion.getText().toString().trim());
            jsonAddress.put("City", editTextCity.getText().toString().trim());
            jsonAddress.put("Street", editTextStreetName.getText().toString().trim());
            jsonAddress.put("Number", Integer.parseInt(editTextStreetNumber.getText().toString().trim()));
            jsonAddress.put("Building", editTextBuilding.getText().toString().trim());
            jsonAddress.put("Apartment", Integer.parseInt(editTextApartment.getText().toString().trim()));
        } catch (JSONException ex) {
            Log.e(LOG_TAG, ex.toString());
        }

        Map<String, String> params = new HashMap<>();

        params.put("email", UtilsSharedPreferences.getString(this, UtilsSharedPreferences.KEY_LOGGED_EMAIL, ""));
        params.put("hashedPassword", UtilsSharedPreferences.getString(this, UtilsSharedPreferences.KEY_LOGGED_PASSWORD, ""));
        params.put("institutionName", editTextName.getText().toString());
        params.put("institutionCIF", editTextCIF.getText().toString());
        params.put("institutionAddress", jsonAddress.toString());

        Log.v(LOG_TAG, "Inst Address : " + jsonAddress.toString());
        Log.v(LOG_TAG, params.toString());

        return params;
    }

    private boolean formHasProperValues() {
        String country = editTextCountry.getText().toString().trim();
        String region = editTextRegion.getText().toString().trim();
        String city = editTextCity.getText().toString().trim();
        String streetName = editTextStreetName.getText().toString().trim();
        String streetNo = editTextStreetNumber.getText().toString().trim();
        String building = editTextBuilding.getText().toString().trim();
        String apartment = editTextApartment.getText().toString().trim();
        String cif = editTextCIF.getText().toString().trim();

        if (country.isEmpty() || region.isEmpty() || city.isEmpty() ||
                streetName.isEmpty() || streetNo.isEmpty() || building.isEmpty() || apartment.isEmpty()) {
            Toast.makeText(this, getString(R.string.toastFillAllFields), Toast.LENGTH_LONG).show();
            return false;
        }
        if (!cif.startsWith("RO") || !cif.endsWith("C")) {
            Toast.makeText(this, getString(R.string.toastBadCIF), Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
