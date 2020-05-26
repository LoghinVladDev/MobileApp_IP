package com.example.ipapp.ui.documents;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ipapp.HomeActivity;
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

    private EditText itemProductNumberEditText;
    private EditText itemTitleEditText;
    private EditText itemDescriptionEditText;
    private EditText itemValueEditText;
    private EditText itemTaxEditText;
    private EditText itemQuantityText;

    private TextView itemProductNumberTextView;
    private TextView itemTitleTextView;
    private TextView itemDescriptionTextView;
    private TextView itemValueTextView;
    private TextView itemTaxTextView;
    private TextView itemQuantityTextView;

    private TextView itemCurrencyTextView;
    private TextView paymentTypeTextView;

    private CheckBox haveInvoiceCheckBox;
    private TextView haveInvoiceTextView;

    private TextView invoiceIDTextView;
    private EditText invoiceIDEditText;

    private Spinner spinnerDocumentType, spinnerPaymentMethod, spinnerCurrency, spinnerInstitution;

    private RequestQueue requestQueue;

    private String[] documentType = {"Invoice", "Receipt"};
    private String[] paymentMethodString;
    private String[] institutionArray;
    private String[] currencyArray;
    private String selectedDocumentType, selectedInstitution, selectedCurrency, selectedPaymentMethod;
    private List<PaymentMethod> paymentMethodList;
    private List<Currency> currencyList;
//    private List<Item> documentItemList;

    private List<Institution> institutionList;

    private Receipt newReceipt;

    private boolean flagCurrencySelected;
    private boolean flagPaymentMethodSelected;
    private boolean flagReceiptSelected;
    private boolean flagInstitutionSelected;
    private boolean flagDocumentTypeSelected;

    // TODO : paymentMethods
    // TODO : spinner receipt/invoice + visibility on fields

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_document);

        this.requestQueue = Volley.newRequestQueue(getApplicationContext());

        this.itemProductNumberEditText = findViewById(R.id.receipt_productNumber);
        this.itemTitleEditText = findViewById(R.id.receipt_productTitle);
        this.itemDescriptionEditText = findViewById(R.id.receipt_productDescription);
        this.itemValueEditText = findViewById(R.id.receipt_productValueBeforeTaxes);
        this.itemTaxEditText = findViewById(R.id.receipt_productTaxPercentage);
        this.itemQuantityText = findViewById(R.id.receipt_productQuantity);

        this.spinnerPaymentMethod = findViewById(R.id.spinnerReceiptPayment);
        this.spinnerCurrency = findViewById(R.id.spinnerReceiptCurrency);
        this.spinnerDocumentType = findViewById(R.id.spinnerDocumentType);
        this.spinnerInstitution = findViewById(R.id.spinnerInstitutionName);

        this.itemProductNumberTextView = findViewById(R.id.textviewProductNumber);
        this.itemTitleTextView = findViewById(R.id.textviewProductTitle);
        this.itemDescriptionTextView = findViewById(R.id.textviewProductDescription);
        this.itemValueTextView = findViewById(R.id.textviewProductValueBeforeTax);
        this.itemTaxTextView = findViewById(R.id.textviewProductTaxPercentage);
        this.itemQuantityTextView = findViewById(R.id.textviewProductQuantity);

        this.paymentTypeTextView = findViewById(R.id.textviewProductPayment);
        this.itemCurrencyTextView = findViewById(R.id.textviewCurrency);

        this.haveInvoiceCheckBox = findViewById(R.id.checkBoxDoYouHaveInvoiceID);
        this.invoiceIDEditText = findViewById(R.id.editTextInvoiceID);

        this.haveInvoiceCheckBox.setOnCheckedChangeListener(
                (e,v)-> {if(v) this.invoiceIDEditText.setVisibility(View.VISIBLE); else this.invoiceIDEditText.setVisibility(View.GONE); }
        );

        this.institutionList = InstitutionsFragment.getInstitutions(); // TODO : ADD IN SPINNER

        this.newReceipt = new Receipt();

        this.populateDocumentUtils();

        findViewById(R.id.receipt_nextItem).setOnClickListener(e->addNewItemToDocument());
        findViewById(R.id.receipt_endOfReceipt).setOnClickListener(e->finishDocumentCreation());

    }

    private void populateDocumentUtils(){

        this.populateSpinnerDocumentType(); //keeps in selectedDocumentType the document type
        this.populateSpinnerInstitution(); //keeps in selectedInstitution -.-
        this.makeHTTPRequestPaymentMethods();
        this.makeHTTPRequestCurrencies();
    }


    private void populateSpinnerInstitution() {

        InstitutionsFragment.debugInstitutionList();

        List<String> tempList = new ArrayList<>();
        for (Institution i : institutionList) {
            tempList.add(i.getName());
        }

        Log.d(LOG_TAG, "DEBUG INST LIST : " + tempList.toString());

        institutionArray = new String[tempList.size()];

        institutionArray = tempList.toArray(institutionArray);

        ArrayAdapter<String> spinnerInstitutionAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, institutionArray);
        spinnerInstitutionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerInstitution.setAdapter(spinnerInstitutionAdapter);
        spinnerInstitution.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedInstitution = institutionArray[i];
                flagInstitutionSelected = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                flagInstitutionSelected = false;
            }
        });
    }

    private void populateSpinnerDocumentType() {
        ArrayAdapter<String> spinnerDocumentTypeAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, documentType);
        spinnerDocumentTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerDocumentType.setAdapter(spinnerDocumentTypeAdapter);
        spinnerDocumentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedDocumentType = documentType[i];
                if(selectedDocumentType.equals("Invoice")) {
                    showInvoiceEdits();
                    flagReceiptSelected = false;
                }
                else if(selectedDocumentType.equals("Receipt")) {
                    showReceiptEdits();
                    flagReceiptSelected = true;
                }
                flagDocumentTypeSelected = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                flagDocumentTypeSelected = false;
                flagReceiptSelected = false;
            }
        });
    }

    private void showInvoiceEdits(){
        this.itemProductNumberEditText.setVisibility(View.VISIBLE);
        this.itemTitleEditText.setVisibility(View.VISIBLE);
        this.itemDescriptionEditText.setVisibility(View.VISIBLE);
        this.itemValueEditText.setVisibility(View.VISIBLE);
        this.itemTaxEditText.setVisibility(View.VISIBLE);
        this.itemQuantityText.setVisibility(View.VISIBLE);
        this.spinnerCurrency.setVisibility(View.VISIBLE);

        this.itemProductNumberTextView.setVisibility(View.VISIBLE);
        this.itemTitleTextView.setVisibility(View.VISIBLE);
        this.itemDescriptionTextView.setVisibility(View.VISIBLE);
        this.itemValueTextView.setVisibility(View.VISIBLE);
        this.itemTaxTextView.setVisibility(View.VISIBLE);
        this.itemQuantityTextView.setVisibility(View.VISIBLE);
        this.itemCurrencyTextView.setVisibility(View.VISIBLE);

        this.paymentTypeTextView.setVisibility(View.GONE);
        this.spinnerPaymentMethod.setVisibility(View.GONE);

        this.haveInvoiceCheckBox.setVisibility(View.GONE);
        this.invoiceIDEditText.setVisibility(View.GONE);
    }

    private void showReceiptEdits(){

        this.itemProductNumberEditText.setVisibility(View.VISIBLE);
        this.itemTitleEditText.setVisibility(View.VISIBLE);
        this.itemDescriptionEditText.setVisibility(View.VISIBLE);
        this.itemValueEditText.setVisibility(View.VISIBLE);
        this.itemTaxEditText.setVisibility(View.VISIBLE);
        this.itemQuantityText.setVisibility(View.VISIBLE);
        this.spinnerCurrency.setVisibility(View.VISIBLE);

        this.itemProductNumberTextView.setVisibility(View.VISIBLE);
        this.itemTitleTextView.setVisibility(View.VISIBLE);
        this.itemDescriptionTextView.setVisibility(View.VISIBLE);
        this.itemValueTextView.setVisibility(View.VISIBLE);
        this.itemTaxTextView.setVisibility(View.VISIBLE);
        this.itemQuantityTextView.setVisibility(View.VISIBLE);
        this.itemCurrencyTextView.setVisibility(View.VISIBLE);

        this.paymentTypeTextView.setVisibility(View.VISIBLE);
        this.spinnerPaymentMethod.setVisibility(View.VISIBLE);

        this.haveInvoiceCheckBox.setVisibility(View.VISIBLE);
        this.haveInvoiceCheckBox.setChecked(false);

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

        List<String> tempList = new ArrayList<>();
        for (PaymentMethod p : paymentMethodList) {
            tempList.add(p.getTitle());
        }

        paymentMethodString = new String[tempList.size()];
        paymentMethodString = tempList.toArray(paymentMethodString);

        Log.d(LOG_TAG, "PAYMENT METHODS FINAL : " + this.paymentMethodList.toString());

        ArrayAdapter<String> spinnerPaymentMethodAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, paymentMethodString);
        spinnerPaymentMethodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerPaymentMethod.setAdapter(spinnerPaymentMethodAdapter);
        spinnerPaymentMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPaymentMethod = paymentMethodString[i];

                flagPaymentMethodSelected = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                flagPaymentMethodSelected = false;
            }
        });

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

        this.currencyArray = new String[this.currencyList.size()];

        int uselessIncrementVariable = 0;

        for(Currency c : this.currencyList){
            this.currencyArray[uselessIncrementVariable++] = c.getTitle();
        }

        ArrayAdapter<String> spinnerCurrencyAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, this.currencyArray);
        spinnerCurrencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCurrency.setAdapter(spinnerCurrencyAdapter);
        spinnerCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(LOG_TAG, "WHAT : " + i);
                selectedCurrency = currencyList.get(i).getTitle();
                flagCurrencySelected = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                flagCurrencySelected = false;
            }
        });

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
        /*
        Map<String, String> params = new HashMap<>();

        params.put("email", UtilsSharedPreferences.getString(getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_EMAIL, ""));
        params.put("hashedPassword", UtilsSharedPreferences.getString(getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_PASSWORD, ""));

        params.put("institutionName", this.institutionList.get(0).getName()); // TODO : ADD FROM SPINNER ? OR SOMETHING

        params.put("documentItems", this.newReceipt.getDocumentItemsJSON().toString());

        this.makeHTTPRequestUploadReceipt(params);

         */

        if(!this.flagInstitutionSelected)
            return;

        Map<String, String> params = new HashMap<>();

        params.put("email", UtilsSharedPreferences.getString(getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_EMAIL, ""));
        params.put("hashedPassword", UtilsSharedPreferences.getString(getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_PASSWORD, ""));

        params.put("institutionName", this.selectedInstitution);

        if(selectedDocumentType.equals("Receipt")){
            for(PaymentMethod p : this.paymentMethodList)
                if(p.getTitle().equals(selectedPaymentMethod)) {
                    params.put("paymentMethodID", p.getID() +"");
                    break;
                }
            if(this.haveInvoiceCheckBox.isChecked()){
                params.put("invoiceID", this.invoiceIDEditText.getText().toString());
            } else {
                params.put("documentItems", newReceipt.getDocumentItemsJSON().toString());
            }

            this.makeHTTPRequestUploadReceipt(params);
        } else if(selectedDocumentType.equals("Invoice")) {
            params.put("documentItems", newReceipt.getDocumentItemsJSON().toString());

            this.makeHTTPRequestUploadInvoice(params);
        }

        Log.d(LOG_TAG, "Params to be sent : " + params.toString());
    }

    private void makeHTTPRequestUploadInvoice(Map<String, String> params){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                ApiUrls.DOCUMENT_UPLOAD_INVOICE,
                response -> {
                    Log.d(LOG_TAG, "RESPONSE : " + response.toString());

                    if(response.contains("SUCCESS")){
                        this.callbackUploadSuccess();
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



    private void makeHTTPRequestUploadReceipt(Map<String, String> params){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                ApiUrls.DOCUMENT_UPLOAD_RECEIPT,
                response -> {
                    Log.d(LOG_TAG, "RESPONSE : " + response.toString());

                    if(response.contains("SUCCESS")){
                        this.callbackUploadSuccess();
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

    private void callbackUploadSuccess(){
        Log.d(LOG_TAG, "Yay!");

        Intent goToHomeActivityIntent = new Intent(this, HomeActivity.class);
        startActivity(goToHomeActivityIntent);
    }

    public boolean checkEditTexts(){

        if(!this.flagDocumentTypeSelected || !this.flagCurrencySelected) {
            Toast.makeText(getApplicationContext(), "You must select a currency!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(this.flagReceiptSelected && !this.flagPaymentMethodSelected) {
            Toast.makeText(getApplicationContext(), "You must select a payment method!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(this.flagReceiptSelected && this.haveInvoiceCheckBox.isChecked() && this.invoiceIDEditText.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "You must type an invoice ID!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(
            this.itemProductNumberEditText.getText().toString().isEmpty() ||
            this.itemTitleEditText.getText().toString().isEmpty() ||
            this.itemDescriptionEditText.getText().toString().isEmpty() ||
            this.itemValueEditText.getText().toString().isEmpty() ||
            this.itemTaxEditText.getText().toString().isEmpty() ||
            this.itemQuantityText.getText().toString().isEmpty()
        ) {
            Toast.makeText(getApplicationContext(), "You must fill in all item fields!", Toast.LENGTH_SHORT).show();
            return false;
        }

        double taxValue = Double.parseDouble(this.itemTaxEditText.getText().toString());
        if(taxValue > 100){
            Toast.makeText(getApplicationContext(), "Tax cannot be higher than 100%", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(taxValue >= 1 && taxValue <= 100){
            this.itemTaxEditText.setText(Double.toString(taxValue/100));
        }

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
                .setCurrency(this.selectedCurrency)
                .setProductNumber(this.itemProductNumberEditText.getText().toString())
                .setTax(Double.parseDouble(this.itemTaxEditText.getText().toString()))
                .setDescription(this.itemDescriptionEditText.getText().toString())
            ,
            Integer.parseInt(this.itemQuantityText.getText().toString())
        );

        this.itemTitleEditText.setText("");
        this.itemValueEditText.setText("");
        this.itemProductNumberEditText.setText("");
        this.itemTaxEditText.setText("");
        this.itemDescriptionEditText.setText("");
        this.itemQuantityText.setText("");
    }
}
