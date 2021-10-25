package com.shoaib.firebasechatapp.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST


private const val BASE_URL = "https://fcm.googleapis.com/fcm/"


private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface FCMApiService {

    @POST("send")
    suspend fun sendMessage(@HeaderMap header: HashMap<String, String>, @Body messageBody: String):String
}

// Singleton Pattern
object FCMApi{

    val retrofitService: FCMApiService by lazy {
            retrofit.create(FCMApiService::class.java)
    }
}