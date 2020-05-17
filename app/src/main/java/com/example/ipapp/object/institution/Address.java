package com.example.ipapp.object.institution;

import androidx.annotation.NonNull;

public class Address {
    private int ID;
    private String country;
    private String region;
    private String city;
    private String street;
    private int number;
    private String building;
    private int floor;
    private int apartment;

    @Override
    public String toString() {
        return "{id=" + this.ID + ",country=" + this.country + ",region=" + this.region + ",city=" + this.city +
            ",street=" + this.street + ",number" + this.number + ",building=" + this.building + ",floor=" + this.floor +
            "apartment=" + this.apartment + "}";
    }

    public Address(){

    }

    public Address setID(int ID) {
        this.ID = ID;
        return this;
    }

    public Address setBuilding(String building) {
        this.building = building;
        return this;
    }

    public Address setApartment(int apartment) {
        this.apartment = apartment;
        return this;
    }

    public Address setCity(String city) {
        this.city = city;
        return this;
    }

    public Address setCountry(String country) {
        this.country = country;
        return this;
    }

    public Address setFloor(int floor) {
        this.floor = floor;
        return this;
    }

    public Address setNumber(int number) {
        this.number = number;
        return this;
    }

    public Address setRegion(String region) {
        this.region = region;
        return this;
    }

    public Address setStreet(String street) {
        this.street = street;
        return this;
    }

    public int getID() {
        return ID;
    }

    public int getApartment() {
        return apartment;
    }

    public int getFloor() {
        return floor;
    }

    public int getNumber() {
        return number;
    }

    public String getBuilding() {
        return building;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }

    public String getStreet() {
        return street;
    }
}
