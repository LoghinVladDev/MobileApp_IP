package com.example.ipapp;

import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.example.ipapp.ui.account.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class HomeActivity extends AppCompatActivity {

    private RequestQueue requestQueue;

    private BottomNavigationView navView;

    public BottomNavigationView getNavView() {
        return navView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.requestQueue = LoginActivity.getRequestQueue();

        initialiseBottomNavigation();
    }

    private void initialiseBottomNavigation() {
        navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        /*
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.bottom_navigation_institutions, R.id.bottom_navigation_account, R.id.bottom_navigation_documents)
                .build();
        */
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

}
