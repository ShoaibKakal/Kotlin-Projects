package com.shoaib.roomdbkotlin.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.shoaib.roomdbkotlin.data.Fruit
import com.shoaib.roomdbkotlin.databases.FruitDao
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class FruitViewModel(private val fruitDao: FruitDao): ViewModel() {


    fun addFruit(fruit: Fruit)
    {
        viewModelScope.launch {
            fruitDao.insertFruit(fruit)
        }
    }




    class FruitViewModelFactory(private val fruitDao: FruitDao) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FruitViewModel::class.java))
            {
                @Suppress("UNCHECKED_CAST")
                return FruitViewModel(fruitDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class")
        }

    }
}