package com.example.ipapp.ui.documents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ipapp.HomeActivity;
import com.example.ipapp.R;
import com.example.ipapp.object.document.Document;

import java.util.ArrayList;
import java.util.List;

public class DocumentsFragment extends Fragment{

    private RecyclerView recyclerViewDocuments;
    private DocumentsAdapter adapter;
    private List<Document> documents;
    private Toolbar toolbar;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_documents, container, false);

        this.recyclerViewDocuments = root.findViewById(R.id.recyclerViewDocuments);
        this.recyclerViewDocuments.setLayoutManager(new LinearLayoutManager(root.getContext()));

        this.documents = new ArrayList<>();

        this.adapter = new DocumentsAdapter<>(root.getContext(), R.id.recyclerViewDocuments, this.documents);

        toolbar = root.findViewById(R.id.toolbar);
        toolbar.setTitle("Document");
        ((HomeActivity)getActivity()).setActionBar(toolbar);
        return root;
    }

    public void removeProgressBar()
    {
        ProgressBar progressBar = this.getActivity().findViewById(R.id.progressBarLoadDocuments);
        progressBar.setVisibility(View.GONE);
    }

}
