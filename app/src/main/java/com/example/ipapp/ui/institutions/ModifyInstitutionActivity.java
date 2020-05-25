package com.example.ipapp.ui.institutions;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.ipapp.R;
import com.example.ipapp.object.institution.Institution;

import org.json.JSONException;
import org.json.JSONObject;

public class ModifyInstitutionActivity extends AppCompatActivity {

    private final static String LOG_TAG = "INSTITUTION_MODIFY";
    private static final String INTENT_KEY_INSTITUTION_JSON = "institution";

    private RequestQueue requestQueue;
    private Institution institution;
    private String institutionName;

    private CheckBox checkBoxNewChangeInstitutionName, checkBoxChangeInstitutionAddress;
    private EditText editTextNewInstitutionName, editTextNewInstitutionCountry, editTextNewInstitutionRegion, editTextNewInstitutionCity, editTextNewInstitutionStreet,
            editTextNewInstitutionBuilding, editTextNewInstitutionApartment;
    private Button btnModifyInstitution;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_institution);

        this.requestQueue = Volley.newRequestQueue(this);

        try {
            JSONObject parameters = new JSONObject(getIntent().getStringExtra(INTENT_KEY_INSTITUTION_JSON));
            this.institutionName = parameters.getString("name");

            this.institution = new Institution().setID(parameters.getInt("ID")).setName(parameters.getString("name"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.initialiseUI();
    }

    private void initialiseUI() {

        checkBoxNewChangeInstitutionName = findViewById(R.id.checkBoxNewInstitutionName);
        checkBoxChangeInstitutionAddress = findViewById(R.id.checkBoxNewInstitutionAddress);

        // TODO: modificare institutie, checkbox -> visibility=visible

    }
}
