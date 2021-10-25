package com.shoaib.retrofitexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private ArrayList<ItemData> itemDataArrayList;
    private OrderAdapter orderAdapter;
    private int id = 49;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.itemRecyclerView);
        itemDataArrayList = new ArrayList<>();


        getAllOrders(id);
    }

    private void getAllOrders(int id) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tailordesk.azurewebsites.net/TailorGetApi/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<ArrayList<ItemData>> call = apiService.getAllOrders(id);

        call.enqueue(new Callback<ArrayList<ItemData>>() {
            @Override
            public void onResponse(Call<ArrayList<ItemData>> call, Response<ArrayList<ItemData>> response) {

                if(response.isSuccessful())
                {
                    itemDataArrayList = response.body();

                    for (int i=0; i< itemDataArrayList.size(); i++)
                    {
                        orderAdapter = new OrderAdapter(itemDataArrayList, MainActivity.this);
                        recyclerView.setAdapter(orderAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ItemData>> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Fail to get data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}