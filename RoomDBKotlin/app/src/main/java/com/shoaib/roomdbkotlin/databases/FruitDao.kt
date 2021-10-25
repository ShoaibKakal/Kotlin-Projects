package com.shoaib.roomdbkotlin.databases

import androidx.room.*
import com.shoaib.roomdbkotlin.data.Fruit
import kotlinx.coroutines.flow.Flow

@Dao
interface FruitDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFruit(fruit: Fruit)

    @Delete
    suspend fun deleteFruit(fruit: Fruit)

    @Update
    suspend fun updateFruit(fruit: Fruit)

    @Query("SELECT * FROM fruit WHERE fruit_id = :id")
    fun getFruit(id: Int): Flow<Fruit>

    @Query("SELECT * FROM fruit ORDER BY fruit_id DESC")
    fun getAllFruits(): Flow<List<Fruit>>
}