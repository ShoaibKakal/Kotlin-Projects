package com.shoaib.firebasechatapp.network

import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface ApiService {

    @POST("send")
    fun sendMessage(@HeaderMap header: HashMap<String, String>, @Body messageBody: String):String
}