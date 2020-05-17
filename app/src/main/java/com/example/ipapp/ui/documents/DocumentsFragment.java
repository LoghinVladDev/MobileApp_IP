package com.example.ipapp.ui.documents;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import android.net.IpSecManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.example.ipapp.HomeActivity;
import com.example.ipapp.LoginActivity;
import com.example.ipapp.R;
import com.example.ipapp.object.document.Document;
import com.example.ipapp.object.document.Invoice;
import com.example.ipapp.object.document.Receipt;
import com.example.ipapp.object.institution.Institution;
import com.example.ipapp.utils.ApiUrls;
import com.example.ipapp.utils.UtilsSharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocumentsFragment extends Fragment {

    private RecyclerView recyclerViewDocuments;
    private DocumentsAdapter adapter;
    private List<Document> documents;
    private static final String LOG_TAG = "DOCUMENTS_FRAGMENT";

    private RequestQueue requestQueue;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        this.documents = new ArrayList<>();

        this.requestQueue = LoginActivity.getRequestQueue();

        View root = inflater.inflate(R.layout.fragment_documents, container, false);

        this.initRv(root);

//        Toast.makeText(getContext(), String.valueOf(adapter.getItemCount()), Toast.LENGTH_SHORT).show();
//        ActionBar actionBar = ((HomeActivity)getActivity()).getSupportActionBar();
//        actionBar.setCustomView(R.layout.action_bar_documents);
//
//        View actionBarRoot = actionBar.getCustomView();

        List<String> categories = new ArrayList<>();
        categories.add("");
        categories.add("Created");
        categories.add("Received");
        categories.add("Sent");

        Spinner spinnerSortDocuments = root.findViewById(R.id.documentSpinner);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSortDocuments.setAdapter(spinnerAdapter);

        spinnerSortDocuments.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

                if (item.equals("Created"))
                {
                    documents = new ArrayList<>();
                    requestRetrieveUserCreatedDocuments();
                    initRv(root);
                }

                if (item.equals("Received"))
                {
                    documents = new ArrayList<>();
                    requestRetrieveUserReceivedDocuments();
                    initRv(root);
                }

                if (item.equals("Sent"))
                {
                    documents = new ArrayList<>();
                    requestRetrieveUserSentDocuments();
                    initRv(root);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        spinnerSortDocuments.setAdapter(spinnerAdapter);

        return root;
    }

    private void initRv(View root)
    {
        RecyclerView recyclerView = root.findViewById(R.id.recyclerViewDocuments);
        recyclerView.setLayoutManager( new LinearLayoutManager(root.getContext()));

        adapter = new DocumentsAdapter(root.getContext(), this.documents);

        recyclerView.setAdapter(adapter);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getActivity().getMenuInflater().inflate(R.menu.document_spinner, menu);
//        return true;
//    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void requestRetrieveUserCreatedDocuments()
    {
        Map<String, String> requestParams = new HashMap<>();

        requestParams.put("email", UtilsSharedPreferences.getString(getActivity().getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_EMAIL, ""));
        requestParams.put("hashedPassword", UtilsSharedPreferences.getString(getActivity().getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_PASSWORD, ""));
        requestParams.put("institutionName", "");

        this.makeHTTPGetUserCreatedDocuments(requestParams);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void makeHTTPGetUserCreatedDocuments(final Map<String, String> bodyParameters)
    {
        StringRequest getRequest = new StringRequest(Request.Method.GET,
                ApiUrls.encodeGetURLParams(ApiUrls.DOCUMENT_RETRIEVE_FILTER_USER_CREATED, bodyParameters),
                response ->
                {
                    if(response.contains("SUCCESS"))
                    {
                        Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                        callbackGetUserCreatedDocuments(response);
                    }
                },
                error ->
                {
                    Log.d(LOG_TAG, "VOLLEY ERROR : " + error.toString());
                }
        ) {
            protected Map<String, String> getParams() { return bodyParameters; }
        };
        this.requestQueue.add(getRequest);
    }

    private void callbackGetUserCreatedDocuments(String JSONEncodedResponse) {
        try {
            JSONObject jsonObject = new JSONObject(JSONEncodedResponse);
            JSONObject responseObject = (JSONObject) jsonObject.get("returnedObject");

            JSONArray documentsListJSON = (JSONArray) responseObject.get("documents");

            for(int i = 0, length = documentsListJSON.length(); i < length; i++) {
                JSONObject currentDocumentJSON = documentsListJSON.getJSONObject(i);
                if (currentDocumentJSON.getString("documentType").equals("Receipt")) {
                    this.documents.add(new Receipt().setID(currentDocumentJSON.getInt("ID")));
                }
                else if(currentDocumentJSON.getString("documentType").equals("Invoice")) {
                    this.documents.add(new Invoice().setID(currentDocumentJSON.getInt("ID")));
                }
            }
            Log.d(LOG_TAG, "LIST : " + this.documents.toString());
        }
        catch (JSONException e) {
            Log.e(LOG_TAG, "ERROR : " + e.toString());
        }
    }

    //////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void requestRetrieveUserSentDocuments()
    {
        Map<String, String> requestParams = new HashMap<>();

        requestParams.put("email", UtilsSharedPreferences.getString(getActivity().getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_EMAIL, ""));
        requestParams.put("hashedPassword", UtilsSharedPreferences.getString(getActivity().getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_PASSWORD, ""));
        requestParams.put("institutionName", "");

        this.makeHTTPGetUserSentDocuments(requestParams);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void makeHTTPGetUserSentDocuments(final Map<String, String> bodyParameters)
    {

        StringRequest getRequest = new StringRequest(Request.Method.GET,
                ApiUrls.encodeGetURLParams(ApiUrls.DOCUMENT_RETRIEVE_FILTER_USER_SENT, bodyParameters),
                response ->
                {
                    if(response.contains("SUCCESS"))
                    {
                        Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                        callbackGetUserSentDocuments(response);
                    }
                },
                error ->
                {
                    Log.d(LOG_TAG, "VOLLEY ERROR : " + error.toString());
                }
        ) {
            protected Map<String, String> getParams() { return bodyParameters; }
        };
        this.requestQueue.add(getRequest);
    }

    private void callbackGetUserSentDocuments(String JSONEncodedResponse)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(JSONEncodedResponse);
            JSONObject responseObject = (JSONObject) jsonObject.get("returnedObject");

            JSONArray documentsListJSON = (JSONArray) responseObject.get("documents");

            for(int i = 0, length = documentsListJSON.length(); i < length; i++)
            {
                JSONObject currentDocumentJSON = documentsListJSON.getJSONObject(i);
                if (currentDocumentJSON.getString("documentType").equals("Receipt"))
                {
                    this.documents.add(new Receipt().setID(currentDocumentJSON.getInt("ID")));
                }
                else
                {
                    this.documents.add(new Invoice().setID(currentDocumentJSON.getInt("ID")));
                }
                adapter.notifyDataSetChanged();
            }
            Log.d(LOG_TAG, "LIST : " + this.documents.toString());
        }
        catch (JSONException e)
        {
            Log.e(LOG_TAG, "ERROR : " + e.toString());
        }
    }

    /////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void requestRetrieveUserReceivedDocuments()
    {
        Map<String, String> requestParams = new HashMap<>();

        requestParams.put("email", UtilsSharedPreferences.getString(getActivity().getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_EMAIL, ""));
        requestParams.put("hashedPassword", UtilsSharedPreferences.getString(getActivity().getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_PASSWORD, ""));
        requestParams.put("institutionName", "");

        this.makeHTTPGetUserReceivedDocuments(requestParams);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void makeHTTPGetUserReceivedDocuments(final Map<String, String> bodyParameters)
    {
        StringRequest getRequest = new StringRequest(Request.Method.GET,
        ApiUrls.encodeGetURLParams(ApiUrls.DOCUMENT_RETRIEVE_FILTER_USER_RECEIVED, bodyParameters),
        response ->
        {
            Log.d(LOG_TAG, "RESPONSE : " + response);
            if(response.contains("SUCCESS"))
            {
                Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                callbackGetUserReceivedDocuments(response);
            }
        },
        error ->
        {
            Log.d(LOG_TAG, "VOLLEY ERROR : " + error.toString());
        }
        ) {
            protected Map<String, String> getParams() { return bodyParameters; }
        };
        this.requestQueue.add(getRequest);
    }

    private void callbackGetUserReceivedDocuments(String JSONEncodedResponse)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(JSONEncodedResponse);
            JSONObject responseObject = (JSONObject) jsonObject.get("returnedObject");

            JSONArray documentsListJSON = (JSONArray) responseObject.get("documents");

            for(int i = 0, length = documentsListJSON.length(); i < length; i++)
            {
                JSONObject currentDocumentJSON = documentsListJSON.getJSONObject(i);
                if (currentDocumentJSON.getString("documentType").equals("Receipt"))
                {
                    this.documents.add(new Receipt().setID(currentDocumentJSON.getInt("ID")));
                }
                else
                {
                    this.documents.add(new Invoice().setID(currentDocumentJSON.getInt("ID")));
                }
                adapter.notifyDataSetChanged();
            }
            Log.d(LOG_TAG, "LIST : " + this.documents.toString());
        }
        catch (JSONException e)
        {
            Log.e(LOG_TAG, "ERROR : " + e.toString());
        }
    }
}
