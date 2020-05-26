package com.example.ipapp.ui.institutions;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ipapp.R;
import com.example.ipapp.object.institution.Address;
import com.example.ipapp.object.institution.Institution;
import com.example.ipapp.utils.ApiUrls;
import com.example.ipapp.utils.UtilsSharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendDocumentActivity extends AppCompatActivity {

    private final static String LOG_TAG = "SEND_DOCUMENT";

    private RequestQueue requestQueue;

    private Button btnSendDocument;
    private Spinner spinnerInstitutionReceiverName;
    private Spinner spinnerInstitutionReceiverAddressID;
    private Spinner spinnerUserReceiver;
    private String selectedInstitutionReceiver;
    private String institutionName;
    private int selectedInstitutionAddressID;
    private Institution institution;

    private List<Institution> institutionList;
    private List<Address> addressesList;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_document);

        requestQueue = Volley.newRequestQueue(this);

        spinnerInstitutionReceiverName = findViewById(R.id.spinnerInstitutionReceiverName);
        spinnerInstitutionReceiverAddressID = findViewById(R.id.spinnerInstitutionReceiverID);
        spinnerUserReceiver = findViewById(R.id.spinnerUserReceiver);
        btnSendDocument = findViewById(R.id.buttonSendDocument);


        getInstitutionReceiverName();
    }

    private void populateInstitutionAddressesSpinner() {
        String[] addressesArray = new String[addressesList.size()];
        int index = 0;

        for (Address a : addressesList) {
            addressesArray[index++] = a.toString();
        }

        ArrayAdapter spinnerInstitutionAddressesAdapter = new ArrayAdapter<String>(this,  android.R.layout.simple_spinner_dropdown_item, addressesArray);
        spinnerInstitutionAddressesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerInstitutionReceiverAddressID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedInstitutionAddressID = Integer.parseInt(addressesArray[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getInstitutionAddressID() {
        Map<String, String> requestBody = new HashMap<>();

        requestBody.put("email", UtilsSharedPreferences.getString(this, UtilsSharedPreferences.KEY_LOGGED_EMAIL, ""));
        requestBody.put("hashedPassword", UtilsSharedPreferences.getString(this, UtilsSharedPreferences.KEY_LOGGED_PASSWORD, ""));
        requestBody.put("institutionName", selectedInstitutionReceiver);

        makeHTTPRetrieveInstitutionAddressID(requestBody);

    }

    private void callbackInstitutionAddressID(String JSONEncodedResponse) {

        try {
            JSONObject jsonObject = new JSONObject(JSONEncodedResponse);
            JSONObject returnObject = jsonObject.getJSONObject("returnedObject");

            JSONArray jsonArray = returnObject.getJSONArray("Addresses");

            for (int i = 0, length = jsonArray.length(); i < length; i++) {
                JSONObject currentInstitutionJSON = jsonArray.getJSONObject(i);

                addressesList.add(new Address().setID(currentInstitutionJSON.getInt("ID")));
            }


            populateInstitutionAddressesSpinner();
//            for(Address a : addressesList) {
//                if (a.getID() == selectedInstitutionAddressID) {
//                    //
//                }
//            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void makeHTTPRetrieveInstitutionAddressID(Map<String, String> params) {
        StringRequest getInstitutionAdressesID = new StringRequest(Request.Method.GET, ApiUrls.encodeGetURLParams(ApiUrls.INSTITUTION_RETRIEVE_ADDRESSES, params),
                response -> {
                    Log.d(LOG_TAG, "RESPONSE RETRIEVE INST ADRESSES ID " + response );

                    if (response.contains("SUCCESS")) {
                        callbackInstitutionAddressID(response);
                    }
                },
                error -> {
                    Log.d(LOG_TAG, error.toString());
                    error.printStackTrace();
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
    }

    private void populateInstitutionReceiverSpinner() {
        String[] institutionReceiverArray = new String[institutionList.size()];
        int index = 0;

        for (Institution i : institutionList) {
            institutionReceiverArray[index++] = i.getName();
        }

        ArrayAdapter spinnerInstitutionReceiverNameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, institutionReceiverArray);
        spinnerInstitutionReceiverNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerInstitutionReceiverName.setAdapter(spinnerInstitutionReceiverNameAdapter);
        spinnerInstitutionReceiverName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedInstitutionReceiver = institutionList.get(i).toString();


                getInstitutionAddressID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getInstitutionReceiverName() {

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", UtilsSharedPreferences.getString(this, UtilsSharedPreferences.KEY_LOGGED_EMAIL, ""));
        requestBody.put("hashedPassword", UtilsSharedPreferences.getString(this, UtilsSharedPreferences.KEY_LOGGED_PASSWORD, ""));
        requestBody.put("institutionsPerPage", String.valueOf(100));
        requestBody.put("pageNumber", String.valueOf(1));
        requestBody.put("orderByAsc", String.valueOf(1));

        makeHTTPRetrieveInstitutionList(requestBody);

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void makeHTTPRetrieveInstitutionList(Map<String, String> params) {
        StringRequest getRequest = new StringRequest(Request.Method.GET, ApiUrls.encodeGetURLParams(ApiUrls.INSTITUTION_RETRIEVE_ALL, params),
                response -> {
                    Log.d(LOG_TAG, "RESPONSE RETRIEVE INST LIST:" + response);

                    if (response.contains("SUCCESS")) {
                        callbbackGetInstitutionList(response);
                    }
                },
                error -> {
                    Log.d(LOG_TAG, error.toString());
                    error.printStackTrace();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };
        requestQueue.add(getRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void callbbackGetInstitutionList(String JSONEncodedString) {

        try {
            JSONObject jsonObject = new JSONObject(JSONEncodedString);
            JSONObject returnObject = jsonObject.getJSONObject("returnedObject");

            JSONArray jsonArray = returnObject.getJSONArray("institutions");

            for (int i = 0, length = jsonArray.length(); i < length; i++) {
                JSONObject currentInstitutionJSON = jsonArray.getJSONObject(i);

                institutionList.add(new Institution().setName(currentInstitutionJSON.getString("name")).setID(currentInstitutionJSON.getInt("ID")));
            }


            populateInstitutionReceiverSpinner();
//
//            for(Institution i : institutionList) {
//                if (i.getName().equals(selectedInstitutionReceiver)) {
//                    institution = i;
//                }
//            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
