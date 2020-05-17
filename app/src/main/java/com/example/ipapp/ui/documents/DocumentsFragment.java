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

public class DocumentsFragment extends Fragment{

    private RecyclerView recyclerViewDocuments;
    private DocumentsAdapter adapter;
    private List<String> documents;
    private static final String LOG_TAG = "DOCUMENTS_FRAGMENT";

    private RequestQueue requestQueue;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        this.documents = new ArrayList<>();

        View root = inflater.inflate(R.layout.fragment_documents, container, false);

        this.recyclerViewDocuments = root.findViewById(R.id.recyclerViewDocuments);
        this.recyclerViewDocuments.setLayoutManager(new LinearLayoutManager(root.getContext()));


        this.adapter = new DocumentsAdapter<String>(this.getContext(), this.documents);

        this.requestQueue = LoginActivity.getRequestQueue();
        this.requestRetrieveUserCreatedDocuments();

        this.recyclerViewDocuments.setAdapter(adapter);

        this.recyclerViewDocuments.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));


//        ActionBar actionBar = ((HomeActivity)getActivity()).getSupportActionBar();
//        actionBar.setCustomView(R.layout.action_bar_documents);
//
//        View actionBarRoot = actionBar.getCustomView();
//        Spinner spinnerSortDocuments = actionBarRoot.findViewById(R.id.spinnerSortDocuments);
//        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this.getContext(), R.array.spinnerSortDocuments, android.R.layout.simple_spinner_item);
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        spinnerSortDocuments.setAdapter(spinnerAdapter);

        return root;
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
        requestParams.put("apiKey", "");

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

    private void callbackGetUserCreatedDocuments(String JSONEncodedResponse)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(JSONEncodedResponse);
            JSONObject responseObject = (JSONObject) jsonObject.get("returnedObject");

            JSONArray documentsListJSON = (JSONArray) responseObject.get("documents");

            for(int i = 0, length = documentsListJSON.length(); i < length; i++)
            {
                JSONObject currentDocumentJSON = documentsListJSON.getJSONObject(i);
                this.documents.add(currentDocumentJSON.getString("documentType"));
                Toast.makeText(getContext(), "HELLO", Toast.LENGTH_SHORT).show();
            }
            Log.d(LOG_TAG, "LIST : " + this.documents.toString());
        }
        catch (JSONException e)
        {
            Log.e(LOG_TAG, "ERROR : " + e.toString());
        }
    }
}
