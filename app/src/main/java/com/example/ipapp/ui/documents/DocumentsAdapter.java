package com.example.ipapp.ui.documents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ipapp.R;

import java.util.List;

class DocumentsAdapter<T> extends RecyclerView.Adapter<DocumentsAdapter.ViewHolder> {
    private final int recyclerViewID;
    private List<T> mData;
    private LayoutInflater mInflater;

    DocumentsAdapter(Context context, int recyclerViewID, List<T> mData) {
        this.recyclerViewID = recyclerViewID;
        this.mData = mData;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(this.recyclerViewID, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        T item = mData.get(position);
        holder.textViewDocumentToString.setText(item.toString());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewDocumentToString;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDocumentToString = itemView.findViewById(R.id.textDocument);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
