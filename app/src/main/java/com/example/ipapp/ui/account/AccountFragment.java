package com.example.ipapp.ui.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.ipapp.HomeActivity;
import com.example.ipapp.LoginActivity;
import com.example.ipapp.ModifyAccountActivity;
import com.example.ipapp.R;
import com.example.ipapp.RegisterActivity;
import com.example.ipapp.object.institution.Institution;
import com.example.ipapp.utils.ApiUrls;
import com.example.ipapp.utils.UtilsSharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class AccountFragment extends Fragment {

    private static final String CLASS_TAG = "ACCOUNT_FRAGMENT";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_account, container, false);

        EditText emailEditText = root.findViewById(R.id.editTextCurrentEmail);
        emailEditText.setText(UtilsSharedPreferences.getString(getContext(),"EMAIL", ""));

        EditText firstNameEditText = root.findViewById(R.id.editTextCurrentFirstName);
        firstNameEditText.setText(UtilsSharedPreferences.getString(getContext(), "FIRST_NAME", ""));

        EditText lastNameEditText = root.findViewById(R.id.editTextCurrentLastName);
        lastNameEditText.setText(UtilsSharedPreferences.getString(getContext(), "LAST_NAME", ""));

        Button buttonLogout = root.findViewById(R.id.buttonLogout);

        Button buttonModifyAccount = root.findViewById(R.id.buttonModifyAccount);

        buttonLogout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent goToLoginActivity = new Intent();

                goToLoginActivity.setClass(getActivity(), LoginActivity.class);

                UtilsSharedPreferences.removeCredentials(getContext());

                startActivity(goToLoginActivity);

                getActivity().finish();
            }
        });

        buttonModifyAccount.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent goToModifyAccountActivity = new Intent();

                goToModifyAccountActivity.setClass(getActivity(), ModifyAccountActivity.class);

                startActivity(goToModifyAccountActivity);
            }
        });

        return root;
    }

}
