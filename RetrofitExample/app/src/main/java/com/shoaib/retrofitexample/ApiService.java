package com.shoaib.retrofitexample;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {


    @GET("OrderGet")
    Call<ArrayList<ItemData>> getAllOrders(@Query("TailorShopId") int TailorShopId);
}
