package com.example.ipapp.ui.institutions;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.ipapp.R;
import com.example.ipapp.object.institution.Institution;

public class SendDocumentActivity extends AppCompatActivity {

    private final static String LOG_TAG = "SEND_DOCUMENT";

    private RequestQueue requestQueue;

    private Button btnSendDocument;
    private Spinner spinnerInstitutionReceiverName;
    private Spinner spinnerInstitutionReceiverID;
    private Spinner spinnerUserReceiver;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_document);

        requestQueue = Volley.newRequestQueue(this);

        spinnerInstitutionReceiverName = findViewById(R.id.spinnerInstitutionReceiverName);
        spinnerInstitutionReceiverID = findViewById(R.id.spinnerInstitutionReceiverID);
        spinnerUserReceiver = findViewById(R.id.spinnerUserReceiver);
        btnSendDocument = findViewById(R.id.buttonSendDocument);

    }
}
