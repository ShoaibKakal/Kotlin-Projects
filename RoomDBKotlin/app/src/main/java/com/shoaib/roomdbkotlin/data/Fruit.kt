package com.shoaib.roomdbkotlin.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Fruit(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "fruit_id")
    val id: Int = 0,

    @ColumnInfo(name = "fruit_name")
    val fruitName: String,

    @ColumnInfo(name = "fruit_quantity")
    val stockQuantity: Int,

    @ColumnInfo(name = "fruit_price")
    val fruitPrice: Double,
)