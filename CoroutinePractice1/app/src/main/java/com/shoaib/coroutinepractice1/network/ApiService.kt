package com.shoaib.coroutinepractice1.network

import com.shoaib.coroutinepractice1.model.CommentModel
import com.shoaib.coroutinepractice1.model.PostModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://jsonplaceholder.typicode.com/"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ApiService {

    @GET("posts")
    suspend fun getPosts(): List<PostModel>

    @GET("posts/{post_id}/comments")
    suspend fun getComments(@Path(value = "post_id", encoded = true) post_id: String): List<CommentModel>
}

// singleton Pattern
object ApiObject {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}