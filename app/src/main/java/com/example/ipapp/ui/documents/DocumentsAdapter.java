package com.example.ipapp.ui.documents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ipapp.R;
import com.example.ipapp.object.document.Document;

import java.util.List;

public class DocumentsAdapter extends RecyclerView.Adapter<DocumentsAdapter.ViewHolder>
{
    private final View.OnClickListener listRowOnClickListener;
    private List<Document> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    DocumentsAdapter(Context context, List<Document> data, View.OnClickListener rowOnClickListener)
    {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.listRowOnClickListener = rowOnClickListener;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.rv_document_row, parent, false);

        view.setOnClickListener(listRowOnClickListener);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Document document = mData.get(position);
        holder.myTextView.setText(document.toViewString());

        if (document.getType().equals("Invoice")) {
            holder.invoiceImage.setVisibility(View.VISIBLE);
            holder.receiptImage.setVisibility(View.GONE);
        }

        if (document.getType().equals("Receipt")) {
            holder.invoiceImage.setVisibility(View.GONE);
            holder.receiptImage.setVisibility(View.VISIBLE);
        }
    }


    // total number of rows
    @Override
    public int getItemCount()
    {
        if (mData != null)
           return mData.size();
        else
            return 0;
    }


    // stores and recycles views as they are scrolled off screen
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView myTextView;
        ImageView receiptImage;
        ImageView invoiceImage;

        ViewHolder(View itemView)
        {
            super(itemView);
            myTextView = itemView.findViewById(R.id.documentRow);
            receiptImage = itemView.findViewById(R.id.receiptImage);
            invoiceImage = itemView.findViewById(R.id.invoiceImage);
        }

        @Override
        public void onClick(View view)
        {

        }
    }

    // convenience method for getting data at click position
    Document getItem(int id)
    {
        return mData.get(id);
    }

}