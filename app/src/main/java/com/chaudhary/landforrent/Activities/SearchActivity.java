package com.chaudhary.landforrent.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.chaudhary.landforrent.R;
import com.chaudhary.landforrent.DataModels.RequestDataModel;
import com.chaudhary.landforrent.Utils.Endpoints;
import com.chaudhary.landforrent.Utils.VolleySingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final EditText et_land_group, et_city;
        et_land_group = findViewById(R.id.et_land);
        et_city = findViewById(R.id.et_city);
        Button submit_button = findViewById(R.id.submit_button);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String land_group = et_land_group.getText().toString();
                String city = et_city.getText().toString();
                if(isValid(land_group, city)){
                    get_search_results(land_group, city);
                }
            }
        });
    }


    private void get_search_results(final String land_group, final String city) {
        StringRequest stringRequest = new StringRequest(
                Method.POST, Endpoints.search_owners, new Listener<String>() {
            @Override
            public void onResponse(String response) {
                //json response
                Intent intent = new Intent(SearchActivity.this, SearchResults.class);
                intent.putExtra("city", city);
                intent.putExtra("land_group", land_group);
                intent.putExtra("json", response);
                startActivity(intent);
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SearchActivity.this, "Something went wrong:(", Toast.LENGTH_SHORT).show();
                Log.d("VOLLEY", Objects.requireNonNull(error.getMessage()));
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("city", city);
                params.put("land_group", land_group);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    private boolean isValid(String land_group, String city){
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
        if(!valid_land_groups.contains(land_group)){
            showMsg("Land group invalid choose from " + valid_land_groups);
            return false;
        }else if(city.isEmpty()){
            showMsg("Enter city");
            return false;
        }
        return true;
    }


    private void showMsg(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


}