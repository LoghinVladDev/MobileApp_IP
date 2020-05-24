package com.example.ipapp.object.document;

import org.json.JSONException;
import org.json.JSONObject;

public class Item {
    private String name;
    private String description;
    private int ID;
    private String productNumber;
    private double value;
    private double tax;
    private double valueWithTax;
    private String currency;

    public Item(){
        
    }

    public Item setID(int ID) {
        this.ID = ID;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Item setDescription(String description) {
        this.description = description;
        return this;
    }

    public Item setName(String name) {
        this.name = name;
        return this;
    }

    public Item setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public Item setProductNumber(String productNumber) {
        this.productNumber = productNumber;
        return this;
    }

    public Item setTax(double tax) {
        this.tax = tax;
        return this;
    }

    public Item setValue(double value) {
        this.value = value;
        return this;
    }

    public Item setValueWithTax(double valueWithTax) {
        this.valueWithTax = valueWithTax;
        return this;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public double getTax() {
        return tax;
    }

    public double getValue() {
        return value;
    }

    public double getValueWithTax() {
        return valueWithTax;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public String getCurrency() {
        return currency;
    }
}

