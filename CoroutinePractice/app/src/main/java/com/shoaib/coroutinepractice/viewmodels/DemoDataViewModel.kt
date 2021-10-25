package com.shoaib.coroutinepractice.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoaib.coroutinepractice.network.DemoApi
import com.shoaib.coroutinepractice.network.DemoClass
import kotlinx.coroutines.launch

enum class MarsApiStatus { LOADING, ERROR, DONE }

private const val TAG = "demoData"

class DemoDataViewModel : ViewModel() {


    fun getData(): List<DemoClass>? {
        var outputData: List<DemoClass>? = null
        viewModelScope.launch {
            Log.d(TAG, "getData: ${MarsApiStatus.LOADING}")
            try {
                outputData = DemoApi.retrofitService.getUserData()


            } catch (e: Exception) {
                Log.d(TAG, "getData: ${MarsApiStatus.ERROR}")
            }

        }
        return outputData
    }
}