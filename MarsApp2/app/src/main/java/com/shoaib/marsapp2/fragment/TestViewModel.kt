package com.shoaib.marsapp2.fragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoaib.marsapp2.model.MarsPhotos
import com.shoaib.marsapp2.network.MarsApi
import kotlinx.coroutines.launch

private const val TAG = "testTag"

class TestViewModel : ViewModel() {

    val photos = MutableLiveData<List<MarsPhotos>>()

    init {
        getResponse()
    }

    private fun getResponse() {

        viewModelScope.launch {
            try {
                photos.value = MarsApi.retrofitService.getPhotos()
            } catch (e: Exception) {
                Log.d(TAG, "getResponse: Exception: ${e.localizedMessage}")
            }
        }
    }
}