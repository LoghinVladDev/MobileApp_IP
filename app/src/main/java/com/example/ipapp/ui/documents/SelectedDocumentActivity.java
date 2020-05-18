package com.example.ipapp.ui.documents;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ipapp.ModifyDocumentActivity;
import com.example.ipapp.R;
import com.example.ipapp.utils.ApiUrls;
import com.example.ipapp.utils.UtilsSharedPreferences;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SelectedDocumentActivity extends AppCompatActivity {

    private final static String LOG_TAG = "SELECTED DOC ACTIVITY";

    private static final String INTENT_KEY_DOCUMENT_JSON = "document";
    private RequestQueue httpRequestQueue;
    private String documentInformation;
    private String institutionSender;
    private String documentType;
    private String documentID;

    protected void onCreate(Bundle savedDocumentInformation) {
        super.onCreate(savedDocumentInformation);
        setContentView(R.layout.activity_selected_document);

        this.httpRequestQueue = Volley.newRequestQueue(this);

        try {
            JSONObject parameters = new JSONObject(getIntent().getStringExtra(INTENT_KEY_DOCUMENT_JSON));
            this.documentInformation = parameters.getString("SelectedDocument");
            this.institutionSender = parameters.getString("SenderInstitution");
            this.documentType = parameters.getString("DocumentType");
            this.documentID = parameters.getString("DocumentID");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Toast
                .makeText(this.getApplicationContext(), "From Selected Document : " + this.documentInformation, Toast.LENGTH_LONG)
                .show();

        Toast
                .makeText(this.getApplicationContext(), "From Institution : " + this.institutionSender, Toast.LENGTH_LONG)
                .show();

        Toast
                .makeText(this.getApplicationContext(), "Document is : " + this.documentType, Toast.LENGTH_LONG)
                .show();

        Toast
                .makeText(this.getApplicationContext(), "With ID : " + this.documentID, Toast.LENGTH_LONG)
                .show();

        FloatingActionButton buttonModifyAccount = findViewById(R.id.buttonModifyDocument);
        buttonModifyAccount.setOnClickListener(v -> {
            Intent goToModifyDocument = new Intent(SelectedDocumentActivity.this, ModifyDocumentActivity.class);
            startActivity(goToModifyDocument);
        });


        FloatingActionButton buttonDeleteAccount = findViewById(R.id.buttonDeleteDocument);
        buttonDeleteAccount.setOnClickListener(v -> {
            AlertDialog.Builder deleteAlertBuilder = new AlertDialog.Builder(v.getContext());
            deleteAlertBuilder.setMessage("Are you sure?");
            deleteAlertBuilder.setCancelable(true);

            deleteAlertBuilder.setPositiveButton(
                    "Yes",
                    (dialog, id) -> {
                        dialog.cancel();
//                                requestDeleteDocument();
                        onBackPressed();
                    });

            deleteAlertBuilder.setNegativeButton(
                    "No",
                    (dialog, id) -> dialog.cancel());

            AlertDialog deleteAlert = deleteAlertBuilder.create();
            deleteAlert.show();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void requestDeleteDocument() {
        Map<String, String> requestParams = new HashMap<>();

        requestParams.put("email", UtilsSharedPreferences.getString(this.getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_EMAIL, ""));
        requestParams.put("hashedPassword", UtilsSharedPreferences.getString(this.getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_PASSWORD, ""));
        requestParams.put("institutionName", "InstitutieTestare");                  ///// testat pentru invoice cu id 9
        requestParams.put("documentID", this.documentID);

        this.makeHTTPDeleteDocumentRequest(requestParams);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void makeHTTPDeleteDocumentRequest(final Map<String, String> bodyParameters) {
        String deleteService = "";

        if (this.documentType.equals("Receipt")) {
            deleteService = ApiUrls.DOCUMENT_DELETE_RECEIPT;
        } else if (this.documentType.equals("Invoice")) {
            deleteService = ApiUrls.DOCUMENT_DELETE_INVOICE;
        }

        StringRequest deleteDocumentRequest = new StringRequest(Request.Method.POST, ApiUrls.encodeGetURLParams(deleteService, bodyParameters),
                response ->
                {
                    Log.d(LOG_TAG, "RESPONSE : " + response);
                    if (response.contains("SUCCESS")) {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                response,
                                Toast.LENGTH_SHORT).show();
                    }
                },
                error ->
                {
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
        this.httpRequestQueue.add(deleteDocumentRequest);
    }

}

