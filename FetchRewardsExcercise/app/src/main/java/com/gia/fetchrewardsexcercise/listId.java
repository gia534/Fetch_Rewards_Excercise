package com.gia.fetchrewardsexcercise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

public class listId extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ItemsModelAdapter itemsModelAdapter;
    private ArrayList<ItemsModel> itemsModelList;
    private ProgressBar progressBar;
    private Button sortIdButton;
    private Button sortListButton;
    String website = "https://fetch-hiring.s3.amazonaws.com/hiring.json";
    boolean clicked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_id);

        recyclerView = findViewById(R.id.recyclerview2);
        progressBar = findViewById(R.id.progressbar2);
        sortIdButton = findViewById(R.id.idButton2);
        sortListButton = findViewById(R.id.listButton2);
        itemsModelList = new ArrayList<>();

        sortIdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(listId.this, MainActivity.class);
                startActivity(intent);
            }
        });

        getData();
        buildRecyclerView();
    }

    private void getData() {

        RequestQueue requestQueue = Volley.newRequestQueue(listId.this);

        // Making Json array request and retrieving date from json objects
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, website, null, response -> {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject jsonObject = response.getJSONObject(i);
                    int itemId = jsonObject.getInt("id");
                    int listId = jsonObject.getInt("listId");
                    String name = jsonObject.getString("name");

                    if(!jsonObject.isNull("name")&& !jsonObject.getString("name").isEmpty()){
                        itemsModelList.add(new ItemsModel(itemId, listId, name));
                        buildRecyclerView();
                    }
                    MainActivity.Sort.sortByList(itemsModelList);
                    MainActivity.Sort.sortByName(itemsModelList);


                } catch (Exception e) {
                    Log.e("Main Activity getData", e.getMessage());
                }
            }
        }, error -> Toast.makeText(listId.this, "Unable to retrieve data at this time", Toast.LENGTH_SHORT).show());
        requestQueue.add(jsonArrayRequest);
    }


    private void buildRecyclerView() {

        itemsModelAdapter = new ItemsModelAdapter(itemsModelList, listId.this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(itemsModelAdapter);
    }

}