package com.shoaib.roomdbkotlin.databases

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shoaib.roomdbkotlin.data.Fruit

@Database(entities = [Fruit::class], version = 1, exportSchema = false)
abstract class FruitDatabase: RoomDatabase() {

    abstract fun fruitDao(): FruitDao
    companion object{
        @Volatile
        private var INSTANCE: FruitDatabase? = null
        fun getDatabase(context: Application): FruitDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FruitDatabase::class.java,
                    "fruits_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}