package com.example.ipapp.ui.institutions;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.example.ipapp.LoginActivity;
import com.example.ipapp.R;
import com.example.ipapp.object.institution.Institution;
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

public class InstitutionsFragment extends Fragment {
    private static final String INTENT_KEY_INSTITUTION_NAME = "institutionName";
    private static final String INTENT_KEY_INSTITUTION_JSON = "institution";

    private InstitutionsAdapter adapter;

    private static final String CLASS_TAG = "INSTITUTIONS_FRAGMENT";

    private RequestQueue requestQueue;

    private List<Institution> institutions;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        FloatingActionButton floatingActionButton = getActivity().findViewById(R.id.fabCreateInstitution);
        floatingActionButton.setVisibility(View.VISIBLE);

        this.requestQueue = LoginActivity.getRequestQueue();
        this.requestPopulateInstitutions();



        this.institutions = new ArrayList<>();

        View root = inflater.inflate(R.layout.fragment_institutions, container, false);
        initialiseUI(root);
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initialiseUI(View root) {
        RecyclerView recyclerView = root.findViewById(R.id.rvInstitutions);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        adapter = new InstitutionsAdapter(root.getContext(), this.institutions, v -> {
            TextView textView = v.findViewById(R.id.textViewRVRowInstitutionName);

            Intent goToSelectedInstitution = new Intent(this.getActivity(), SelectedInstitutionActivity.class);

            JSONObject param = new JSONObject();

            Institution institution = null;

            for (Institution i : this.institutions)
                if(i.getName().equals(textView.getText().toString()))
                    institution = i;

            try {
                if (institution != null) {
                    param.put("ID", institution.getID());
                    param.put("name", institution.getName());

                    goToSelectedInstitution.putExtra(INTENT_KEY_INSTITUTION_JSON, param.toString());
                }
                else{
                    Log.e(CLASS_TAG, "INTENT PARAM ERROR : " + "institution null?");
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            //goToSelectedInstitution.putExtra(INTENT_KEY_INSTITUTION_NAME, textView.getText().toString());
            startActivity(goToSelectedInstitution);
        });
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
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

        requestParams.put("email", UtilsSharedPreferences.getString(getActivity().getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_EMAIL, ""));
        requestParams.put("hashedPassword", UtilsSharedPreferences.getString(getActivity().getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_PASSWORD, ""));

        this.makeHTTPGetInstitutionsForMemberRequest(requestParams);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void makeHTTPGetInstitutionsForMemberRequest(final Map<String, String> bodyParameters){
        StringRequest getRequest = new StringRequest(
                Request.Method.GET,
                ApiUrls.encodeGetURLParams(ApiUrls.INSTITUTION_MEMBER_RETRIEVE_INSTITUTIONS_FOR_MEMBER, bodyParameters),
                response -> {
                    if(response.contains("SUCCESS"))
                        callbackPopulateInstitutionsList(response);
                    //Log.d("INSTITUTIONS_FRAGMENT", "RESPONSE : " + response);
                //    Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                },
                error -> {
                    Log.d(CLASS_TAG, "VOLLEY ERROR : " + error.toString());
                //    Toast.makeText(getContext(), "Error : " + error, Toast.LENGTH_SHORT).show();
                }
        ) {
            protected Map<String, String> getParams() { return bodyParameters; }
        };
        this.requestQueue.add(getRequest);
    }
}

