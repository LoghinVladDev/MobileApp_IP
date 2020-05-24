package com.example.ipapp.ui.documents;

import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ipapp.R;
import com.example.ipapp.object.document.Currency;
import com.example.ipapp.object.document.Item;
import com.example.ipapp.object.document.PaymentMethod;
import com.example.ipapp.object.document.Receipt;
import com.example.ipapp.object.institution.Institution;
import com.example.ipapp.ui.institutions.InstitutionsFragment;
import com.example.ipapp.utils.ApiUrls;
import com.example.ipapp.utils.UtilsSharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateDocumentActivity extends AppCompatActivity {

    private static String LOG_TAG = "CREATE_DOCUMENT_ACTIVITY";

    EditText itemProductNumberEditText;
    EditText itemTitleEditText;
    EditText itemDescriptionEditText;
    EditText itemValueEditText;
    EditText itemTaxEditText;
    EditText itemQuantityText;

    private RequestQueue requestQueue;

    private List<PaymentMethod> paymentMethodList;
    private List<Currency> currencyList;
//    private List<Item> documentItemList;

    private List<Institution> institutionList;

    private Receipt newReceipt;

    // TODO : paymentMethods

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_document);

        this.requestQueue = Volley.newRequestQueue(getApplicationContext());

        this.populateDocumentUtils();

        this.institutionList = InstitutionsFragment.getInstitutions(); // TODO : ADD IN SPINNER

        this.newReceipt = new Receipt();

        findViewById(R.id.receipt_nextItem).setOnClickListener(e->addNewItemToDocument());
        findViewById(R.id.receipt_endOfReceipt).setOnClickListener(e->finishDocumentCreation());

        this.itemProductNumberEditText = findViewById(R.id.receipt_productNumber);
        this.itemTitleEditText = findViewById(R.id.receipt_productTitle);
        this.itemDescriptionEditText = findViewById(R.id.receipt_productDescription);
        this.itemValueEditText = findViewById(R.id.receipt_productValueBeforeTaxes);
        this.itemTaxEditText = findViewById(R.id.receipt_productTaxPercentage);
        this.itemQuantityText = findViewById(R.id.receipt_productQuantity);
    }

    private void populateDocumentUtils(){
        this.makeHTTPRequestPaymentMethods();
        this.makeHTTPRequestCurrencies();
    }

    private void callbackPaymentMethods(JSONObject responseObject) throws JSONException {
        JSONArray paymentMethodsArray = responseObject.getJSONArray("paymentMethods");

        this.paymentMethodList = new ArrayList<>();

        for(int i = 0, length = paymentMethodsArray.length(); i < length; i++){
            JSONObject paymentMethodsJSON = (JSONObject)paymentMethodsArray.get(i);

            this.paymentMethodList.add(
                new PaymentMethod()
                    .setID(paymentMethodsJSON.getInt("ID"))
                    .setTitle(paymentMethodsJSON.getString("title"))
            );
        }

        Log.d(LOG_TAG, "PAYMENT METHODS FINAL : " + this.paymentMethodList.toString());
    }

    private void callbackCurrencies(JSONObject responseObject) throws JSONException {
        JSONArray currenciesJSONArray = responseObject.getJSONArray("currencies");

        this.currencyList = new ArrayList<>();

        for(int i = 0, length = currenciesJSONArray.length(); i < length; i++){
            JSONObject currencyJSON = (JSONObject)currenciesJSONArray.get(i);

            this.currencyList.add(
                    new Currency()
                            .setID(currencyJSON.getInt("ID"))
                            .setTitle(currencyJSON.getString("title"))
            );
        }

        Log.d(LOG_TAG, "CURRENCIES FINAL : " + this.currencyList.toString());
    }

    private void makeHTTPRequestCurrencies(){
        StringRequest request = new StringRequest(
                Request.Method.GET,
                ApiUrls.DOCUMENT_MIST_RETRIEVE_CURRENCIES,
                response -> {
                    Log.d(LOG_TAG, "RESPONSE : " + response.toString());

                    if(response.contains("SUCCESS")){
                        try {
                            this.callbackCurrencies(new JSONObject(response).getJSONObject("returnedObject"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                error -> {
                    Log.d(LOG_TAG, "Volley Error : " + error.toString());
                }
        );

        this.requestQueue.add(request);
    }

    private void makeHTTPRequestPaymentMethods(){
        StringRequest request = new StringRequest(
                Request.Method.GET,
                ApiUrls.DOCUMENT_MIST_RETRIEVE_PAYMENT_METHODS,
                response -> {
                    Log.d(LOG_TAG, "RESPONSE : " + response.toString());

                    if(response.contains("SUCCESS")){
                        try {
                            this.callbackPaymentMethods(new JSONObject(response).getJSONObject("returnedObject"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                error -> {
                    Log.d(LOG_TAG, "Volley Error : " + error.toString());
                }
        );

        this.requestQueue.add(request);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void finishDocumentCreation(){

        Map<String, String> params = new HashMap<>();

        params.put("email", UtilsSharedPreferences.getString(getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_EMAIL, ""));
        params.put("hashedPassword", UtilsSharedPreferences.getString(getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_PASSWORD, ""));

        params.put("institutionName", this.institutionList.get(0).getName()); // TODO : ADD FROM SPINNER ? OR SOMETHING

        params.put("documentItems", this.newReceipt.getDocumentItemsJSON().toString());

        this.makeHTTPRequestUploadReceipt(params);
    }

    private void makeHTTPRequestUploadReceipt(Map<String, String> params){
        StringRequest request = new StringRequest(
                Request.Method.GET,
                ApiUrls.DOCUMENT_UPLOAD_RECEIPT,
                response -> {
                    Log.d(LOG_TAG, "RESPONSE : " + response.toString());

                    if(response.contains("SUCCESS")){
                        this.callbackUploadReceiptSuccess();
                    }
                },
                error -> {
                    Log.d(LOG_TAG, "Volley Error : " + error.toString());
                }
        ) {
            @Override
            protected Map<String, String> getParams(){
                return params;
            }
        };

        this.requestQueue.add(request);
    }

    private void callbackUploadReceiptSuccess(){
        Log.d(LOG_TAG, "Yay!");
    }

    public boolean checkEditTexts(){
        //TODO : Add edit checking

        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void addNewItemToDocument(){
        if(!this.checkEditTexts())
            return;

        this.newReceipt.addItem(
            new Item()
                .setName(this.itemTitleEditText.getText().toString())
                .setValue(Integer.parseInt(this.itemValueEditText.getText().toString()))
                .setCurrency("TODO")
                .setProductNumber(this.itemProductNumberEditText.getText().toString())
                .setTax(Double.parseDouble(this.itemTaxEditText.getText().toString()))
                .setDescription(this.itemDescriptionEditText.getText().toString())
            ,
            Integer.parseInt(this.itemQuantityText.getText().toString())
        );
    }
}
