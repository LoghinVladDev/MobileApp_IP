package com.example.ipapp.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ipapp.R;
import com.example.ipapp.utils.UtilsSharedPreferences;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AccountFragment extends Fragment {

    private static final String CLASS_TAG = "ACCOUNT_FRAGMENT";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_account, container, false);

        FloatingActionButton floatingActionButton = getActivity().findViewById(R.id.fabCreateInstitution);
        floatingActionButton.setVisibility(View.GONE);

        EditText emailEditText = root.findViewById(R.id.editTextCurrentEmail);
        emailEditText.setText(UtilsSharedPreferences.getString(getContext(),"EMAIL", ""));

        EditText firstNameEditText = root.findViewById(R.id.editTextCurrentFirstName);
        firstNameEditText.setText(UtilsSharedPreferences.getString(getContext(), "FIRST_NAME", ""));

        EditText lastNameEditText = root.findViewById(R.id.editTextCurrentLastName);
        lastNameEditText.setText(UtilsSharedPreferences.getString(getContext(), "LAST_NAME", ""));

        Button buttonLogout = root.findViewById(R.id.buttonLogout);

        Button buttonModifyAccount = root.findViewById(R.id.buttonModifyAccount);

        buttonLogout.setOnClickListener(v -> {
            Intent goToLoginActivity = new Intent();

            goToLoginActivity.setClass(getActivity(), LoginActivity.class);

            UtilsSharedPreferences.removeCredentials(getContext());

            startActivity(goToLoginActivity);

            getActivity().finish();
        });

        buttonModifyAccount.setOnClickListener(v -> {
            Intent goToModifyAccountActivity = new Intent();

            goToModifyAccountActivity.setClass(getActivity(), ModifyAccountActivity.class);

            startActivity(goToModifyAccountActivity);
        });

        return root;
    }

}
