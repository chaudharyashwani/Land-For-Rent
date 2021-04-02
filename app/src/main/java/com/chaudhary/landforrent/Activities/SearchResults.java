package com.chaudhary.landforrent.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaudhary.landforrent.Adapters.SearchAdapter;
import com.chaudhary.landforrent.DataModels.Owner;
import com.chaudhary.landforrent.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SearchResults extends AppCompatActivity
{
    List<Owner> ownerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        ownerList= new ArrayList<>();
        String json;
        String city, land_group;
        Intent intent = getIntent();
        json = intent.getStringExtra("json");
        city = intent.getStringExtra("city");
        land_group = intent.getStringExtra("land_group");
        TextView heading = findViewById(R.id.heading);
        String str = "Landowners in " + city + " having " + land_group +" Acre of Land ";
        heading.setText(str);
        Gson gson = new Gson();
        Type type = new TypeToken<List<Owner>>() {
        }.getType();
        List<Owner> dataModels = gson.fromJson(json, type);
        if (dataModels != null && dataModels.isEmpty()) {
            heading.setText("No results");
        }else if(dataModels!=null){
            ownerList.addAll(dataModels);
        }

        RecyclerView recyclerView = findViewById(R.id.recycler_View);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        SearchAdapter adapter = new SearchAdapter(ownerList, SearchResults.this);
        recyclerView.setAdapter(adapter);

    }


}