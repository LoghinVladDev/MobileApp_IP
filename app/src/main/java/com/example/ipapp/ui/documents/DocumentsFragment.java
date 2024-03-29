package com.example.ipapp.ui.documents;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ipapp.HomeActivity;
import com.example.ipapp.ui.account.LoginActivity;
import com.example.ipapp.R;
import com.example.ipapp.object.document.Document;
import com.example.ipapp.object.document.Invoice;
import com.example.ipapp.object.document.Receipt;
import com.example.ipapp.object.institution.Institution;
import com.example.ipapp.ui.institutions.CreateInstitutionActivity;
import com.example.ipapp.ui.institutions.InstitutionsFragment;
import com.example.ipapp.utils.ApiUrls;
import com.example.ipapp.utils.UtilsSharedPreferences;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocumentsFragment extends Fragment {

    private DocumentsAdapter adapter;

    private static List<Document> documentsInstance;

    public static List<Document> getDocuments(){
        if(documentsInstance == null)
            documentsInstance = new ArrayList<>();
        return documentsInstance;
    }

    private List<Document> documents;


    private static final String LOG_TAG = "DOCUMENTS_FRAGMENT";
    private static final String INTENT_KEY_DOCUMENT_JSON = "document";

    private List<Institution> institutionsList;

    private RecyclerView recyclerView;

    private RequestQueue requestQueue;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        FloatingActionButton floatingActionButton = getActivity().findViewById(R.id.fabCreateInstitution);
        floatingActionButton.setVisibility(View.VISIBLE);

        floatingActionButton.setOnClickListener(v -> {
            Intent goToCreateDocument = new Intent(getActivity(), CreateDocumentActivity.class);
            startActivity(goToCreateDocument);
        });

        this.documents = getDocuments();
        this.institutionsList = new ArrayList<>();

//        this.requestQueue = LoginActivity.getRequestQueue();

        this.requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        View root = inflater.inflate(R.layout.fragment_documents, container, false);

        this.initialiseRecyclerViewDocuments(root);

        //Toast.makeText(getContext(), String.valueOf(adapter.getItemCount()), Toast.LENGTH_SHORT).show();
        Toolbar actionBar = root.findViewById(R.id.layoutSpinner); // TODO : investigate
        ((AppCompatActivity) getActivity()).setSupportActionBar(actionBar);

        Spinner spinnerSortDocuments = root.findViewById(R.id.documentSpinner);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this.getContext(), R.array.spinnerSortDocuments, android.R.layout.simple_list_item_1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSortDocuments.setAdapter(spinnerAdapter);

        ProgressBar progressBar = root.findViewById(R.id.progressBarLoadDocuments);
        progressBar.setVisibility(View.VISIBLE);

        final String[] textSpinner = getResources().getStringArray(R.array.spinnerSortDocuments);

        spinnerSortDocuments.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                progressBar.setVisibility(View.GONE);

                if (item.equals(textSpinner[0])) {
                    Log.d(LOG_TAG, "IN Sent");
                    documents.clear();
                    requestRetrieveUserSentDocuments();
                }

                if (item.equals(textSpinner[1])) {
                    Log.d(LOG_TAG, "IN Received");
                    documents.clear();
                    requestRetrieveUserReceivedDocuments();
                }

                if (item.equals(textSpinner[2])) {
                    Log.d(LOG_TAG, "IN Created");
                    documents.clear();
                    requestRetrieveUserCreatedDocuments();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerSortDocuments.setAdapter(spinnerAdapter);



        return root;
    }

    private void initialiseRecyclerViewDocuments(View root) {
        recyclerView = root.findViewById(R.id.recyclerViewDocuments);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY > oldScrollY){
                    ((HomeActivity)getActivity()).getNavView().setVisibility(View.GONE);
                } else {
                    ((HomeActivity)getActivity()).getNavView().setVisibility(View.VISIBLE);
                }
            }
        });

        adapter = new DocumentsAdapter(root.getContext(), this.documents, v -> {
            TextView textView = v.findViewById(R.id.documentRow);

            Intent goToSelectedDocument = new Intent(this.getActivity(), SelectedDocumentActivity.class);

            JSONObject param = new JSONObject();

            Document document = null;

            int docID = Integer.parseInt(textView.getText().toString().split("=")[1].trim());

            Log.d(LOG_TAG, "DOC ID : " + docID);

            for(Document d : this.documents)
                if(d.getID() == docID)
                    document = d;

            try {
                if (document != null) {
                    param.put("SelectedDocument", document.toViewString());
                    param.put("DocumentType", document.getType());
                    param.put("SenderInstitution", document.getSenderInstitutionID());
                    param.put("DocumentID", document.getID());

                    ((HomeActivity)getActivity()).getNavView().setVisibility(View.VISIBLE);

                    goToSelectedDocument.putExtra(INTENT_KEY_DOCUMENT_JSON, param.toString());
                } else {
                    Log.e(LOG_TAG, "INTENT PARAM ERROR : " + "document null?");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            startActivity(goToSelectedDocument);
        });

        recyclerView.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void requestRetrieveUserCreatedDocuments() {
        Map<String, String> requestParams = new HashMap<>();

        requestParams.put("email", UtilsSharedPreferences.getString(getActivity().getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_EMAIL, ""));
        requestParams.put("hashedPassword", UtilsSharedPreferences.getString(getActivity().getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_PASSWORD, ""));
        requestParams.put("institutionName", "");

        this.makeHTTPGetUserCreatedDocuments(requestParams);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void callbackGetDocuments(String JSONEncodedResponse) {
        this.documents.clear();
        Log.d(LOG_TAG, "DOCS ARRAY : " + JSONEncodedResponse);
        try {
            JSONObject jsonObject = new JSONObject(JSONEncodedResponse);
            JSONObject responseObject = (JSONObject) jsonObject.get("returnedObject");

            JSONArray documentsListJSON = (JSONArray) responseObject.get("documents");

            for (int i = 0, length = documentsListJSON.length(); i < length; i++) {
                JSONObject currentDocumentJSON = documentsListJSON.getJSONObject(i);
                Document document = null;
                if (currentDocumentJSON.getString("documentType").equals("Receipt")) {
                    document = new Receipt();
                    //this.documents.add(new Receipt().setID(currentDocumentJSON.getInt("ID")));
                } else if (currentDocumentJSON.getString("documentType").equals("Invoice")) {
                    document = new Invoice();
                    //this.documents.add(new Invoice().setID(currentDocumentJSON.getInt("ID")));
                }
                if (document != null) {
                    this.documents.add(document
                            .setID(Integer.parseInt(currentDocumentJSON.getString("ID").equals("null") ? "-1" : currentDocumentJSON.getString("ID")))
                            .setSenderID(Integer.parseInt(currentDocumentJSON.getString("senderID").equals("null") ? "-1" : currentDocumentJSON.getString("senderID")))
                            .setSenderInstitutionID(Integer.parseInt(currentDocumentJSON.getString("senderInstitutionID").equals("null") ? "-1" : currentDocumentJSON.getString("senderInstitutionID")))
                            .setSenderAddressID(Integer.parseInt(currentDocumentJSON.getString("senderAddressID").equals("null") ? "-1" : currentDocumentJSON.getString("senderAddressID")))
                            .setReceiverID(Integer.parseInt((currentDocumentJSON.getString("receiverID").equals("null")) ? "-1" : currentDocumentJSON.getString("receiverID")))
                            .setReceiverInstitutionID(Integer.parseInt(currentDocumentJSON.getString("receiverInstitutionID").equals("null") ? "-1" : currentDocumentJSON.getString("receiverInstitutionID")))
                            .setReceiverAddressID(Integer.parseInt(currentDocumentJSON.getString("receiverAddressID").equals("null") ? "-1" : currentDocumentJSON.getString("receiverAddressID")))
                            .setCreatorID(Integer.parseInt(currentDocumentJSON.getString("creatorID").equals("null") ? "-1" : currentDocumentJSON.getString("creatorID")))
                            .setDateCreated(currentDocumentJSON.getString("dateCreated"))
                            .setDateSent(currentDocumentJSON.getString("dateSent"))
                            .setSent(currentDocumentJSON.getInt("isSent") == 1)
                    );
                }
            }

            this.documents.forEach(e->Log.d(LOG_TAG, "DOCUMENT FROM LIST : " + e.toString()));

            //Log.d(LOG_TAG, "LIST : " + this.documents.toString());
        } catch (JSONException e) {
            Log.e(LOG_TAG, "ERROR : " + e.toString());
        }
        adapter.notifyDataSetChanged();
        Log.d(LOG_TAG, "RV ITEMS COUNT : " + adapter.getItemCount());
        Log.d(LOG_TAG, "PLM : " + this.recyclerView.getVisibility());
        this.recyclerView.setVisibility(View.VISIBLE);
        Log.d(LOG_TAG, "PLM : " + this.recyclerView.getVisibility());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void makeHTTPGetUserCreatedDocuments(final Map<String, String> bodyParameters) {
        StringRequest getRequest = new StringRequest(Request.Method.GET,
                ApiUrls.encodeGetURLParams(ApiUrls.DOCUMENT_RETRIEVE_FILTER_USER_CREATED, bodyParameters),
                response ->
                {
                    if (response.contains("SUCCESS")) {
                        callbackGetUserCreatedDocuments(response);
                    }
                },
                error ->
                {
                    Log.d(LOG_TAG, "VOLLEY ERROR : " + error.toString());
                }
        ) {
            protected Map<String, String> getParams() {
                return bodyParameters;
            }
        };
        this.requestQueue.add(getRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void callbackGetUserCreatedDocuments(String JSONEncodedResponse) {

        this.callbackGetDocuments(JSONEncodedResponse);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void requestRetrieveUserSentDocuments() {
        Map<String, String> requestParams = new HashMap<>();

        requestParams.put("email", UtilsSharedPreferences.getString(getActivity().getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_EMAIL, ""));
        requestParams.put("hashedPassword", UtilsSharedPreferences.getString(getActivity().getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_PASSWORD, ""));
        requestParams.put("institutionName", "");

        this.makeHTTPGetUserSentDocuments(requestParams);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void makeHTTPGetUserSentDocuments(final Map<String, String> bodyParameters) {

        StringRequest getRequest = new StringRequest(Request.Method.GET,
                ApiUrls.encodeGetURLParams(ApiUrls.DOCUMENT_RETRIEVE_FILTER_USER_SENT, bodyParameters),
                response ->
                {
                    if (response.contains("SUCCESS")) {
                        callbackGetUserSentDocuments(response);
                    }
                },
                error ->
                {
                    Log.d(LOG_TAG, "VOLLEY ERROR : " + error.toString());
                }
        ) {
            protected Map<String, String> getParams() {
                return bodyParameters;
            }
        };
        this.requestQueue.add(getRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void callbackGetUserSentDocuments(String JSONEncodedResponse) {
        this.callbackGetDocuments(JSONEncodedResponse);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void requestRetrieveUserReceivedDocuments() {
        Map<String, String> requestParams = new HashMap<>();

        requestParams.put("email", UtilsSharedPreferences.getString(getActivity().getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_EMAIL, ""));
        requestParams.put("hashedPassword", UtilsSharedPreferences.getString(getActivity().getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_PASSWORD, ""));
        requestParams.put("institutionName", "");

        this.makeHTTPGetUserReceivedDocuments(requestParams);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void makeHTTPGetUserReceivedDocuments(final Map<String, String> bodyParameters) {
        StringRequest getRequest = new StringRequest(Request.Method.GET,
                ApiUrls.encodeGetURLParams(ApiUrls.DOCUMENT_RETRIEVE_FILTER_USER_RECEIVED, bodyParameters),
                response ->
                {
                    Log.d(LOG_TAG, "RESPONSE : " + response);
                    if (response.contains("SUCCESS")) {
                        callbackGetUserReceivedDocuments(response);
                    }
                },
                error ->
                {
                    Log.d(LOG_TAG, "VOLLEY ERROR : " + error.toString());
                }
        ) {
            protected Map<String, String> getParams() {
                return bodyParameters;
            }
        };
        this.requestQueue.add(getRequest);
    }

    private void callbackGetUserReceivedDocuments(String JSONEncodedResponse) {
        this.callbackGetDocuments(JSONEncodedResponse);
    }
}
