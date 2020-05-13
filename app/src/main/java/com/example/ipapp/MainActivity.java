package com.example.ipapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ipapp.utils.UtilsSharedPreferences;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if ("empty".equals(
                UtilsSharedPreferences.getString(getApplicationContext(),
                        UtilsSharedPreferences.KEY_LOGGED_EMAIL,
                        "empty"))) {

        }
    }

}
