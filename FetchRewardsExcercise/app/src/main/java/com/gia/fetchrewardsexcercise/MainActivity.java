package com.gia.fetchrewardsexcercise;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*Todo: Sort them*/
public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ItemsModelAdapter itemsModelAdapter;
    private ArrayList<ItemsModel> itemsModelList;
    private ProgressBar progressBar;
    private Button sortIdButton;
    private Button sortListButton;
    String website = "https://fetch-hiring.s3.amazonaws.com/hiring.json";
    boolean clicked = false;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        progressBar = findViewById(R.id.progressbar);
        sortIdButton = findViewById(R.id.idButton);
        sortListButton = findViewById(R.id.listButton);
        itemsModelList = new ArrayList<>();


        getData();
        buildRecyclerView();

        sortListButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, listId.class);
            startActivity(intent);
        });


    }

    private void getData() {

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

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
                    Sort.sortByList(itemsModelList);


                } catch (Exception e) {
                    Log.e("Main Activity getData", e.getMessage());
                }
            }
        }, error -> Toast.makeText(MainActivity.this, "Unable to retrieve data at this time", Toast.LENGTH_SHORT).show());
        requestQueue.add(jsonArrayRequest);
    }


    private void buildRecyclerView() {

        itemsModelAdapter = new ItemsModelAdapter(itemsModelList, MainActivity.this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(itemsModelAdapter);
    }


  public static class Sort {


      public static void sortByList (ArrayList<ItemsModel> itemsModels){
          Collections.sort(itemsModels, new Comparator<ItemsModel>() {
              @Override
              public int compare(ItemsModel o1, ItemsModel o2) {
                  return Integer.compare(o1.getListId(), o2.getListId());
              }
          });
      }

      public static void sortByName(ArrayList<ItemsModel> itemsModelArrayList){
          Collections.sort(itemsModelArrayList, new Comparator<ItemsModel>() {
              @Override
              public int compare(ItemsModel o1, ItemsModel o2) {
                  return o1.getName().compareTo(o2.getName());
              }
          });
      }
  }

}