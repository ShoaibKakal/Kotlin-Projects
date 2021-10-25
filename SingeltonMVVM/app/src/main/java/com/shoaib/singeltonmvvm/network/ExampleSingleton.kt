package com.shoaib.singeltonmvvm.network

import com.shoaib.singeltonmvvm.models.User

object ExampleSingleton {

    val singletonUser: User by lazy {
        User("Shoaibkakil@gmail.com", "Shoaib", "image.png")
    }
}