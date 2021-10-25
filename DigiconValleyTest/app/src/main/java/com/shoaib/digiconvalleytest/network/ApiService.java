package com.shoaib.digiconvalleytest.network;

import com.shoaib.digiconvalleytest.Model.Item;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("OrderGet")
    Call<ArrayList<Item>> getAllOrders(@Query("TailorShopId") int TailorShopId);

}
