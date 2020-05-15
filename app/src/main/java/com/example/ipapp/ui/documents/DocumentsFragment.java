package com.example.ipapp.ui.documents;

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
import com.example.ipapp.object.document.Document;

import java.util.List;

public class DocumentsFragment extends Fragment {

    private RecyclerView recyclerViewDocuments;
    private CustomAdapter adapter;
    private List<Document> documents;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_documents, container, false);

        //TextView textView = root.findViewById(R.id.text_notifications);
        this.recyclerViewDocuments = root.findViewById(R.id.recyclerViewDocuments);
        this.recyclerViewDocuments.setLayoutManager(new LinearLayoutManager(root.getContext()));
        // this.adapter = new CustomAdapter(root.getContext(), this., R.)
        return root;
    }
}
