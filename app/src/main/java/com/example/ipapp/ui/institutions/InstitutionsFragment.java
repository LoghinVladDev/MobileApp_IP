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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ipapp.CustomAdapter;
import com.example.ipapp.R;

import java.util.ArrayList;

public class InstitutionsFragment extends Fragment {

    CustomAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // TODO IMPLEMENT GET MEMBERS INST REQ HERE
        View root = inflater.inflate(R.layout.fragment_institutions, container, false);

        ArrayList<String> documentNames = new ArrayList<>();
        documentNames.add("BRD grup soșiete jeneral");
        documentNames.add("RDS");
        documentNames.add("Varu BC");
        documentNames.add("Judecatoria Iasi");

        RecyclerView recyclerView = root.findViewById(R.id.rvInstitutions);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        adapter = new CustomAdapter(root.getContext(), documentNames);
        recyclerView.setAdapter(adapter);

        return root;
    }
}
