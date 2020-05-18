package com.example.ipapp.ui.documents;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.ipapp.object.document.Item;
import com.example.ipapp.utils.UtilsSharedPreferences;

import java.util.HashMap;
import java.util.Map;

import com.example.ipapp.R;


public class ModifyDocumentActivity extends AppCompatActivity {

    private EditText editTextNewItemID;
    private EditText editTextNewItemName;
    private EditText editTextNewItemCurrency;
    private EditText editTextNewItemProductNumber;
    private EditText editTextNewItemTax;
    private EditText editTextNewItemValue;
    private EditText editTextNewItemValueWithTax;
    private CheckBox checkBoxAddItem;
    private Button buttonModifyDocument;
    private Item addedItem;

    protected void onCreate(Bundle savedDocumentInformation) {
        super.onCreate(savedDocumentInformation);
        setContentView(R.layout.activity_modify_document);
        initialiseUI();
    }

    private void initialiseUI() {
        editTextNewItemID = findViewById(R.id.editTextNewItemID);
        editTextNewItemName = findViewById(R.id.editTextNewItemName);
        editTextNewItemCurrency = findViewById(R.id.editTextNewItemCurrency);
        editTextNewItemProductNumber = findViewById(R.id.editTextNewItemProductNumber);
        editTextNewItemTax = findViewById(R.id.editTextNewItemTax);
        editTextNewItemValue = findViewById(R.id.editTextNewItemValue);
        editTextNewItemValueWithTax = findViewById(R.id.editTextNewItemValueWithTax);

        checkBoxAddItem = findViewById(R.id.newItemCheckBox);

        buttonModifyDocument = findViewById(R.id.buttonModifyDocument);

        checkBoxAddItem.setChecked(false);

        editTextNewItemID.setVisibility(View.GONE);
        editTextNewItemName.setVisibility(View.GONE);
        editTextNewItemCurrency.setVisibility(View.GONE);
        editTextNewItemProductNumber.setVisibility(View.GONE);
        editTextNewItemTax.setVisibility(View.GONE);
        editTextNewItemValue.setVisibility(View.GONE);
        editTextNewItemValueWithTax.setVisibility(View.GONE);

        checkBoxAddItem.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editTextNewItemID.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            editTextNewItemName.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            editTextNewItemCurrency.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            editTextNewItemProductNumber.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            editTextNewItemTax.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            editTextNewItemValue.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            editTextNewItemValueWithTax.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });

        buttonModifyDocument.setOnClickListener(v -> onClickButtonModifyDocument());
    }

    private void onClickButtonModifyDocument() {
        Map<String, String> params = new HashMap<>();

        addedItem = new Item()
                .setID(Integer.parseInt(editTextNewItemID.getText().toString()))
                .setName(editTextNewItemName.getText().toString())
                .setCurrency(editTextNewItemCurrency.getText().toString())
                .setProductNumber(Integer.parseInt(editTextNewItemProductNumber.getText().toString()))
                .setTax(Double.parseDouble(editTextNewItemTax.getText().toString()))
                .setValue(Double.parseDouble(editTextNewItemValue.getText().toString()))
                .setValueWithTax(Double.parseDouble(editTextNewItemValueWithTax.getText().toString()));

        params.put("email", UtilsSharedPreferences.getString(getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_EMAIL, ""));
        params.put("hashedPassword", UtilsSharedPreferences.getString(getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_PASSWORD, ""));
        params.put("institutionName", "InstitutieTestare");
    }

}