package com.shoaib.marsapp2.model

import com.squareup.moshi.Json

data class MarsPhotos(

    val id: String,

    @Json(name = "img_src")
    val imgUrlSrc: String
)