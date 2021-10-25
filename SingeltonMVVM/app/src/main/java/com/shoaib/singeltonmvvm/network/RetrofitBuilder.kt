package com.shoaib.singeltonmvvm.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL = "https://open-api.xzy/"
class RetrofitBuilder {

    val retrofitBuilder : Retrofit.Builder by lazy{
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
    }

    val apiService:ApiService by lazy {
        retrofitBuilder
            .build()
            .create(ApiService::class.java)
    }
}