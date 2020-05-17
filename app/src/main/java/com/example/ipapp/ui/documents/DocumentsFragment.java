package com.example.ipapp.ui.documents;

import androidx.appcompat.app.ActionBar;
import android.net.IpSecManager;
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
import androidx.appcompat.widget.Toolbar;

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
    private static final String LOG_TAG = "DOCUMENTS_FRAGMENT";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_documents, container, false);

        this.recyclerViewDocuments = root.findViewById(R.id.recyclerViewDocuments);
        this.recyclerViewDocuments.setLayoutManager(new LinearLayoutManager(root.getContext()));

        this.documents = new ArrayList<>();

        this.adapter = new DocumentsAdapter<Document>(root.getContext(), R.id.recyclerViewDocuments, this.documents);

       // Log.v(LOG_TAG, null == getActivity().getActionBar() ? "am belit pl :(" : "merge, :(");

        ActionBar actionBar = ((HomeActivity)getActivity()).getSupportActionBar();
        actionBar.setCustomView(R.layout.action_bar_documents);

        View actionBarRoot = actionBar.getCustomView();
        Spinner spinnerSortDocuments = actionBarRoot.findViewById(R.id.spinnerSortDocuments);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this.getContext(), R.array.spinnerSortDocuments, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerSortDocuments.setAdapter(spinnerAdapter);

        return root;
    }

    public void removeProgressBar()
    {
        ProgressBar progressBar = this.getActivity().findViewById(R.id.progressBarLoadDocuments);
        progressBar.setVisibility(View.INVISIBLE);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getActivity().getMenuInflater().inflate(R.menu.document_spinner, menu);
//        return true;
//    }
}
