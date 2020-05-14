package com.example.ipapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MAIN ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v(LOG_TAG, "Main Activity -> LoginActivity");
        startActivity(new Intent(MainActivity.this, LoginActivity.class));

        // TODO Implement "boolean isNoInternetError(VolleyError error);" function should log the actual error with Log.e("Volley error:", error.toString());
        // for details regarding the task go here - https://stackoverflow.com/questions/21011279/android-volley-checking-internet-state

    }

}
