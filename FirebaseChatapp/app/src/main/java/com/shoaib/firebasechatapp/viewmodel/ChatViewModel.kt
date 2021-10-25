package com.shoaib.firebasechatapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoaib.firebasechatapp.fragment.ChatFragment
import com.shoaib.firebasechatapp.network.FCMApi
import com.shoaib.firebasechatapp.utilities.Constants
import kotlinx.coroutines.launch

const val TAG = "testTag"

class ChatViewModel : ViewModel() {


    val response = MutableLiveData<String>()


    fun getResponse(messageBody: String) {
        viewModelScope.launch {
            try {
                FCMApi.retrofitService.sendMessage(Constants.getRemoteMsgHeaders(), messageBody)
                    .also {
                        response.value = it
                        Log.d(ChatFragment.TAG, "ViewModel: ${response.value}")

                    }
            } catch (e: Exception) {
                Log.d(TAG, "getResponse: ${e.localizedMessage}")
            }
        }
    }
}