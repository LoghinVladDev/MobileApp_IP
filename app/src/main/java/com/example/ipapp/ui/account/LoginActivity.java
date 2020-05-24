package com.example.ipapp.ui.account;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ipapp.HomeActivity;
import com.example.ipapp.R;
import com.example.ipapp.utils.ApiUrls;
import com.example.ipapp.utils.UtilsSharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG = "LOGIN ACTIVITY";
    private static RequestQueue httpRequestQueue;

    public static RequestQueue getRequestQueue(){
        return LoginActivity.httpRequestQueue;
    }

    //<editor-fold desc="UI ELEMENTS">
    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin, buttonRegister;
    //</editor-fold>


    //dots slider
    private ViewPager viewPager;
    private LinearLayout layout_dots;
    private MyAdapter adapter;
    private static String[] array_welcome_title = {"login", "slide1", "slide2", "slide3"};
    private List<Integer> layouts = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginActivity.httpRequestQueue = Volley.newRequestQueue(this);


        layout_dots = findViewById(R.id.layout_dots);
        viewPager =  findViewById(R.id.viewpager);


        layout_dots = (LinearLayout) findViewById(R.id.layout_dots);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        layouts.add(R.layout.vp_login);
        layouts.add(R.layout.vp_customers);
        layouts.add(R.layout.vp_features);
        layouts.add(R.layout.vp_questions);

        adapter = new MyAdapter(this, new ArrayList<ViewPage>());
        final List<ViewPage> items = new ArrayList<>();
        for (int i = 0; i < array_welcome_title.length; i++) {
            ViewPage obj = new ViewPage();
            items.add(obj);
        }
        adapter.setItems(items);
        viewPager.setAdapter(adapter);

        // displaying selected image first
        viewPager.setCurrentItem(0);
        addBottomDots(layout_dots, adapter.getCount(), 0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int pos, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int pos) {
                //  ((TextView) findViewById(R.id.title)).setText(items.get(pos).name);
                addBottomDots(layout_dots, adapter.getCount(), pos);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }


    private void addBottomDots(LinearLayout layout_dots, int size, int current) {

        ImageView[] dots = new ImageView[size];
        layout_dots.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);
            int width_height = 15;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(10, 10, 10, 10);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.shape_circle_outline);
            layout_dots.addView(dots[i]);
        }
        if (dots.length > 0) {
            dots[current].setImageResource(R.drawable.shape_circle);
        }
    }


    private class MyAdapter extends PagerAdapter {

        private Activity act;
        private List<ViewPage> items;

        // constructor
        public MyAdapter(Activity activity, List<ViewPage> items) {
            this.act = activity;
            this.items = items;
        }

        @Override
        public int getCount() {
            return this.items.size();
        }

        public ViewPage getItem(int pos) {
            return items.get(pos);
        }

        public void setItems(List<ViewPage> items) {
            this.items = items;
            notifyDataSetChanged();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final ViewPage o = items.get(position);
            LayoutInflater inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(layouts.get(position), container, false);

            if (position == 0 ) {
                editTextEmail = view.findViewById(R.id.editTextEmail);
                editTextPassword = view.findViewById(R.id.editTextPassword);
                buttonLogin = view.findViewById(R.id.buttonLogin);
                buttonRegister = view.findViewById(R.id.buttonRegister);

                editTextEmail.setText("lau.lau95@yahoo.com");
                editTextPassword.setText("pass");

                buttonLogin.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View v) {
                        onClickButtonLogin(v);
                    }
                });
                buttonRegister.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickButtonRegister(v);
                    }
                });
            }

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((RelativeLayout) object);
        }

    }






    private void onClickButtonRegister(View v) {
        Intent goToRegisterActivity = new Intent(this, RegisterActivity.class);
        startActivity(goToRegisterActivity);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void onClickButtonLogin(View v) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("email", editTextEmail.getText().toString());
        requestParams.put("hashedPassword", editTextPassword.getText().toString());
        UtilsSharedPreferences.setString(getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_EMAIL, editTextEmail.getText().toString());
        UtilsSharedPreferences.setString(getApplicationContext(), UtilsSharedPreferences.KEY_LOGGED_PASSWORD, editTextPassword.getText().toString());

        makeHTTPLoginRequest(requestParams);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void makeHTTPLoginRequest(final Map<String, String> bodyParameters) {
        StringRequest loginRequest = new StringRequest(Request.Method.POST, ApiUrls.ACCOUNT_LOGIN,
                response -> {
                    Log.d(LOG_TAG, "RESPONSE : " + response);
                    if (response.contains("SUCCESS")) {

                        requestRetrieveAccount(bodyParameters);

                        Intent goToHome = new Intent(LoginActivity.this, HomeActivity.class);

                        startActivity(goToHome);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                response,
                                Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.d(LOG_TAG, "VOLLEY ERROR : " + error.toString());
                    Toast.makeText(getApplicationContext(),
                            "Error : " + error,
                            Toast.LENGTH_SHORT).show();
                }
        )
        {
            @Override
            protected Map<String, String> getParams()
            {
                return bodyParameters;
            }
        };
        httpRequestQueue.add(loginRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void requestRetrieveAccount(Map<String, String> requestParams)
    {
        this.makeHTTPRetrieveAccountRequest(requestParams);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void makeHTTPRetrieveAccountRequest( final Map<String, String> bodyParameters)
    {
        StringRequest retrieveAccountRequest = new StringRequest(Request.Method.GET, ApiUrls.encodeGetURLParams(ApiUrls.ACCOUNT_RETRIEVE_INFORMATION, bodyParameters),
                response ->
                {
                    Log.d(LOG_TAG, "RESPONSE : " + response);
                    if (response.contains("SUCCESS"))
                    {
                        callbackRetrieveAccountInformation(response);
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),
                                response,
                                Toast.LENGTH_SHORT).show();
                    }
                },
                error ->
                {
                    Log.d(LOG_TAG, "VOLLEY ERROR : " + error.toString());
                    Toast.makeText(getApplicationContext(),
                            "Error : " + error,
                            Toast.LENGTH_SHORT).show();
                }
        )
        {
            @Override
            protected Map<String, String> getParams()
            {
                return bodyParameters;
            }
        };
        httpRequestQueue.add(retrieveAccountRequest);
    }

    private void callbackRetrieveAccountInformation(String JSONEncodedResponse)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(JSONEncodedResponse);
            JSONObject responseObject = (JSONObject) jsonObject.get("returnedObject");

            UtilsSharedPreferences.setString(getApplicationContext(), UtilsSharedPreferences.KEY_FIRST_NAME, responseObject.getString("First_Name"));
            UtilsSharedPreferences.setString(getApplicationContext(), UtilsSharedPreferences.KEY_LAST_NAME, responseObject.getString("Last_Name"));

            Log.d(LOG_TAG, "LIST : ");
        }
        catch (JSONException e)
        {
            Log.e(LOG_TAG, "ERROR : " + e.toString());
        }
    }

}
