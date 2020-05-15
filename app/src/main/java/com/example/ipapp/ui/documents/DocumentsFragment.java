package com.example.ipapp.ui.documents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ipapp.InstitutionsAdapter;
import com.example.ipapp.R;
import com.example.ipapp.object.document.Document;

import java.util.List;

public class DocumentsFragment extends Fragment {

    private RecyclerView recyclerViewDocuments;
    private InstitutionsAdapter adapter;
    private List<Document> documents;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_documents, container, false);

        //TextView textView = root.findViewById(R.id.text_notifications);
        this.recyclerViewDocuments = root.findViewById(R.id.recyclerViewDocuments);
        this.recyclerViewDocuments.setLayoutManager(new LinearLayoutManager(root.getContext()));
        // this.adapter = new InstitutionsAdapter(root.getContext(), this., R.)

        this.adapter = new DocumentsAdapter(root.getContext(), this.documents, R.layout.recyclerview_documents);
        return root;
    }
}
