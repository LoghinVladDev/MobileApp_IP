package com.example.ipapp.ui.institutions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.ipapp.R;

public class InstitutionsFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // TODO IMPLEMENT GET MEMBERS INST REQ HERE
        View root = inflater.inflate(R.layout.fragment_institutions, container, false);

        TextView textView = root.findViewById(R.id.text_home);

        return root;
    }
}
