package com.example.ipapp.ui.institutions;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ipapp.CustomAdapter;
import com.example.ipapp.HomeActivity;
import com.example.ipapp.LoginActivity;
import com.example.ipapp.R;
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

public class InstitutionsFragment extends Fragment {

    CustomAdapter adapter;

    private static final String CLASS_TAG = "INSTITUTIONS_FRAGMENT";

    private RequestQueue requestQueue;

    private List<Institution> institutions;
    private List<String> institutionNames;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // TODO IMPLEMENT GET MEMBERS INST REQ HERE

        this.requestQueue = LoginActivity.getRequestQueue();
        this.requestPopulateInstitutions();

        View root = inflater.inflate(R.layout.fragment_institutions, container, false);

        this.institutions = new ArrayList<>();
        this.institutionNames = new ArrayList<>();

        /*ArrayList<String> documentNames = new ArrayList<>();
        documentNames.add("BRD grup soșiete jeneral");
        documentNames.add("RDS");
        documentNames.add("Varu BC");
        documentNames.add("Judecatoria Iasi");
         */

        this.institutions.add(new Institution().setID(1).setName("plm"));

        RecyclerView recyclerView = root.findViewById(R.id.rvInstitutions);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        adapter = new CustomAdapter(root.getContext(), this.institutions, R.layout.recyclerview_institution);
        recyclerView.setAdapter(adapter);

        return root;
    }

    private void callbackPopulateInstitutionsList(String JSONEncodedResponse) {
        try {
            JSONObject jsonObject = new JSONObject(JSONEncodedResponse);
            JSONObject responseObject = (JSONObject) jsonObject.get("returnedObject");

            //Log.d(CLASS_TAG, "RESPONSE : " + responseObject.toString());

            JSONArray institutionListJSON = (JSONArray) responseObject.get("institution");

            for(int i = 0, length = institutionListJSON.length(); i < length; i++){
                JSONObject currentInstitutionJSON = (JSONObject) institutionListJSON.getJSONObject(i);
                this.institutions.add(
                        new Institution()
                                .setName(currentInstitutionJSON.getString("institutionName"))
                                .setID(currentInstitutionJSON.getInt("ID"))
                );
                adapter.notifyDataSetChanged();
                //Log.d(CLASS_TAG, "STATUS_UPD : " + currentInstitutionJSON.toString());
            }
            Log.d(CLASS_TAG, "LIST : " + this.institutions.toString());
        } catch (JSONException e){
            Log.e(CLASS_TAG, "ERROR : " + e.toString());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void requestPopulateInstitutions(){
        Map<String, String> requestParams = new HashMap<>();

        requestParams.put("email", UtilsSharedPreferences.getString(this.getContext().getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_EMAIL,""));
        requestParams.put("hashedPassword", UtilsSharedPreferences.getString(this.getContext().getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_PASSWORD, ""));

        //Log.d("INSTITUTION_FRAGMENT", "EMAIL PARAM TEST : " +  UtilsSharedPreferences.getString(this.getContext().getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_EMAIL,""));

        this.makeHTTPGetInstitutionsForMemberRequest(requestParams);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void makeHTTPGetInstitutionsForMemberRequest(final Map<String, String> bodyParameters){
              StringBuilder sb = new StringBuilder();

        //String URL = ApiUrls.INSTITUTION_MEMBER_RETRIEVE_INSTITUTIONS_FOR_MEMBER + "?";
        //bodyParameters.entrySet().forEach((k) -> {sb.append(k.getKey()).append("=").append(k.getValue()).append("&");});

        //URL = (URL + sb.toString());
        //URL = URL.substring(0, URL.length() - 1);

        //Log.d("DEBUG_INST_FRAG", "test : " + URL);

        StringRequest getRequest = new StringRequest(
                Request.Method.GET,
                ApiUrls.encodeGetURLParams(ApiUrls.INSTITUTION_MEMBER_RETRIEVE_INSTITUTIONS_FOR_MEMBER, bodyParameters),
                response -> {
                    if(response.contains("SUCCESS"))
                        callbackPopulateInstitutionsList(response);
                    //Log.d("INSTITUTIONS_FRAGMENT", "RESPONSE : " + response);
                    Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                },
                error -> {
                    Log.d(CLASS_TAG, "VOLLEY ERROR : " + error.toString());
                    Toast.makeText(getContext(), "Error : " + error, Toast.LENGTH_SHORT).show();
                }
        ) {
            protected Map<String, String> getParams() { return bodyParameters; }
        };
        this.requestQueue.add(getRequest);
    }

}

