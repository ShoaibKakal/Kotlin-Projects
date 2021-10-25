package com.shoaib.firebasechatapp.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private var retrofit:Retrofit? = null

class ApiClient {

     companion object{
         fun getClient(): Retrofit
         {
             if (retrofit == null)
             {
                 retrofit = Retrofit.Builder()
                     .baseUrl("https://fcm.googleapis.com/fcm/")
                     .addConverterFactory(ScalarsConverterFactory.create())
                     .build()
             }
             return retrofit!!
         }
     }
}