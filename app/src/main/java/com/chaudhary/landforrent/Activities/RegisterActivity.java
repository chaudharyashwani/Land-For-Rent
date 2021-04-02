package com.chaudhary.landforrent.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.chaudhary.landforrent.R;
import com.chaudhary.landforrent.Utils.Endpoints;
import com.chaudhary.landforrent.Utils.VolleySingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.android.volley.Request.Method;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameEt, cityEt, landGroupEt, passwordEt, mobileEt;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nameEt = findViewById(R.id.name);
        cityEt = findViewById(R.id.city);
        landGroupEt = findViewById(R.id.land_group);
        passwordEt = findViewById(R.id.password);
        mobileEt = findViewById(R.id.number);
        submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, city, land_group, password, mobile;
                name = nameEt.getText().toString();
                city = cityEt.getText().toString();
                land_group = landGroupEt.getText().toString();
                password = passwordEt.getText().toString();
                mobile = mobileEt.getText().toString();
                if (isValid(name, city, land_group, password, mobile)) {
                    register(name, city, land_group, password, mobile);
                }
            }
        });

    }





    private void register(final String name, final String city, final String land_group, final String password,
                          final String mobile) {
        StringRequest stringRequest = new StringRequest(Method.POST, Endpoints.register_url, new Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Success"))
                {
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("city",city).apply();
                    Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    RegisterActivity.this.finish();
                }else{
                    Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(RegisterActivity.this, "Something went wrong:(", Toast.LENGTH_SHORT).show();
                Log.d("VOLLEY", error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("city", city);
                params.put("land_group",land_group);
                params.put("password", password);
                params.put("number", mobile);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }



    private boolean isValid(String name, String city, String land_group, String password,
                            String mobile)
    {
        List<String> valid_land_groups = new ArrayList<>();
        valid_land_groups.add("5");
        valid_land_groups.add("10");
        valid_land_groups.add("20");
        valid_land_groups.add("30");
        valid_land_groups.add("40");
        valid_land_groups.add("50");
        valid_land_groups.add("100");
        valid_land_groups.add("500");
        valid_land_groups.add("1000");

        if (name.isEmpty()) {
            showMessage("Name is empty");
            return false;
        } else if (city.isEmpty()) {
            showMessage("City name is required");
            return false;

        }else if (!valid_land_groups.contains(land_group))
        {
            showMessage("Land group invalid choose your nearest value from " + valid_land_groups);
            return false;
        }

        else if (mobile.length() != 10) {
            showMessage("Invalid mobile number, number should be of 10 digits");
            return false;
        }
        return true;
    }


    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


}