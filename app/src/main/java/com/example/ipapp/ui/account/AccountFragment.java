package com.example.ipapp.ui.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.ipapp.LoginActivity;
import com.example.ipapp.ModifyAccountActivity;
import com.example.ipapp.R;
import com.example.ipapp.RegisterActivity;
import com.example.ipapp.utils.UtilsSharedPreferences;

public class AccountFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_account, container, false);

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
