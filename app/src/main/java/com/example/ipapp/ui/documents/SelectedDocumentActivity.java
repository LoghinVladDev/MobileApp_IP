package com.example.ipapp.ui.documents;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.INotificationSideChannel;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ipapp.R;
import com.example.ipapp.object.document.Document;
import com.example.ipapp.object.document.Invoice;
import com.example.ipapp.object.document.Item;
import com.example.ipapp.object.document.Pair;
import com.example.ipapp.object.document.Receipt;
import com.example.ipapp.object.institution.Institution;
import com.example.ipapp.ui.institutions.InstitutionsFragment;
import com.example.ipapp.utils.ApiUrls;
import com.example.ipapp.utils.UtilsSharedPreferences;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SelectedDocumentActivity extends AppCompatActivity {

    private final static String LOG_TAG = "SELECTED DOC ACTIVITY";

    private static final String INTENT_KEY_DOCUMENT_JSON = "document";
    private RequestQueue httpRequestQueue;

    private ItemsAdapter adapter;
    private List<Item> itemsList;

    private String documentType;
    private int documentID;

    private Document document;

    private Institution senderInstitution;

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedDocumentInformation) {
        super.onCreate(savedDocumentInformation);
        setContentView(R.layout.activity_selected_document);

        this.httpRequestQueue = Volley.newRequestQueue(this);

        try {
            JSONObject parameters = new JSONObject(getIntent().getStringExtra(INTENT_KEY_DOCUMENT_JSON));
            int senderInstitutionID = parameters.getInt("SenderInstitution");
            this.documentID = parameters.getInt("DocumentID");
            this.documentType = parameters.getString("DocumentType");

            for (Document d : DocumentsFragment.getDocuments()) {
                if (d.getID() == this.documentID)
                {
                    this.document = d;
                }
            }

            for(Institution i : InstitutionsFragment.getInstitutions())
                if(i.getID() == senderInstitutionID)
                    this.senderInstitution = i;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(LOG_TAG, "Params for req : " + this.senderInstitution.getName() + ", " + this.documentID);

        TextView textViewDocumentType = findViewById(R.id.textViewDocumentType);
        textViewDocumentType.setText(this.documentType);

        TextView textViewSenderInstitutionName = findViewById(R.id.textViewSenderInstitutionName);
        textViewSenderInstitutionName.setText(this.senderInstitution.getName());

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

        requestRetrieveDocument();
    }

    private void initialiseRecyclerView(List<Pair<Item, Integer>> itemsList) {
        RecyclerView recyclerView = findViewById(R.id.recyclerViewItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ItemsAdapter(this, itemsList);

        recyclerView.setAdapter(adapter);
    }

//    private void setTotalPrices()
//    {
//        TextView textViewSubTotalPrice = findViewById(R.id.textViewSubTotalPrice);
//        textViewSubTotalPrice.setText(String.valueOf(subTotalPrice));
//
//        TextView textViewTotalPrice = findViewById(R.id.textViewTotalPrice);
//        textViewTotalPrice.setText(String.valueOf(totalPrice));
//
//        TextView textViewTaxesPrice = findViewById(R.id.textViewTaxesPrice);
//        textViewTaxesPrice.setText(String.valueOf(taxes));
//    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void requestRetrieveDocument() {
        Map<String, String> requestParams = new HashMap<>();

        requestParams.put("email", UtilsSharedPreferences.getString(this.getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_EMAIL, ""));
        requestParams.put("hashedPassword", UtilsSharedPreferences.getString(this.getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_PASSWORD, ""));
        requestParams.put("institutionName", this.senderInstitution.getName());
        requestParams.put("documentID", Integer.toString(this.documentID));

        this.makeHTTPRetrieveDocumentRequest(requestParams);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void makeHTTPRetrieveDocumentRequest(final Map<String, String> bodyParameters) {
        StringRequest retrieveDocumentRequest = new StringRequest(Request.Method.GET, ApiUrls.encodeGetURLParams(ApiUrls.DOCUMENT_RETRIEVE_INDIVIDUAL, bodyParameters),
                response ->
                {
                    Log.d(LOG_TAG, "RESPONSE : " + response);
                    if (response.contains("SUCCESS")) {
                        Log.d(LOG_TAG, "makeHTTPRetrieveDocumentRequest: " + response.toString());
                        callbackPopulateDocumentItems(response);
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
        this.httpRequestQueue.add(retrieveDocumentRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void callbackPopulateDocumentItems(String JSONEncodedResponse) {
        itemsList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(JSONEncodedResponse);
            JSONObject responseObject = (JSONObject) jsonObject.get("returnedObject");

            JSONObject documentJSON = (JSONObject) responseObject.get("document");

            JSONArray itemsListJSON = (JSONArray) documentJSON.getJSONArray("items");

            Invoice castedInvoice = null;
            Receipt castedReceipt = null;

            try {
                castedInvoice = (Invoice) this.document;
            }
            catch (ClassCastException ignored) {
            }

            try {
                castedReceipt = (Receipt) this.document;
            }
            catch (ClassCastException ignored) {
            }



            for (int i = 0, length = itemsListJSON.length(); i < length; i++) {
                JSONObject currentItemJSON = (JSONObject) itemsListJSON.getJSONObject(i);

                JSONObject currentItemDetailsJSON = (JSONObject) currentItemJSON.getJSONObject("item");

                Log.d(LOG_TAG, "LIST : " + currentItemDetailsJSON.toString());

                if (castedInvoice != null)
                {
                    castedInvoice.addItem(new Item()
                            .setID(currentItemDetailsJSON.getInt("ID"))
                            .setProductNumber(currentItemDetailsJSON.getInt("productNumber"))
                            .setName(currentItemDetailsJSON.getString("description"))
                            .setValue(currentItemDetailsJSON.getDouble("unitPrice"))
                            .setTax(currentItemDetailsJSON.getDouble("itemTax"))
                            .setValueWithTax(currentItemDetailsJSON.getDouble("unitPriceWithTax"))
                            .setCurrency(currentItemDetailsJSON.getString("currencyTitle")), currentItemJSON.getInt("quantity"));
                }
                else if (castedReceipt != null)
                {
                    castedReceipt.addItem(new Item()
                                .setID(currentItemDetailsJSON.getInt("ID"))
                                .setProductNumber(currentItemDetailsJSON.getInt("productNumber"))
                                .setName(currentItemDetailsJSON.getString("description"))
                                .setValue(currentItemDetailsJSON.getDouble("unitPrice"))
                                .setTax(currentItemDetailsJSON.getDouble("itemTax"))
                                .setValueWithTax(currentItemDetailsJSON.getDouble("unitPriceWithTax"))
                                .setCurrency(currentItemDetailsJSON.getString("currencyTitle")), currentItemJSON.getInt("quantity"));

                }

            }

            if (castedInvoice != null)
            {
                initialiseRecyclerView(castedInvoice.getItems());
            }
            else if (castedReceipt != null)
            {
                initialiseRecyclerView(castedReceipt.getItems());
            }

//            setTotalPrices();

            Log.d(LOG_TAG, "LIST : " + this.itemsList.toString());
        } catch (JSONException e) {
            Log.e(LOG_TAG, "ERROR : " + e.toString());
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void requestDeleteDocument() {
        Map<String, String> requestParams = new HashMap<>();

        requestParams.put("email", UtilsSharedPreferences.getString(this.getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_EMAIL, ""));
        requestParams.put("hashedPassword", UtilsSharedPreferences.getString(this.getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_PASSWORD, ""));
        requestParams.put("institutionName", "InstitutieTestare");                  ///// testat pentru invoice cu id 9
        requestParams.put("documentID", Integer.toString(this.documentID));

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

