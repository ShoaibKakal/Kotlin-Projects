package com.shoaib.singeltonmvvm.repositories

import androidx.lifecycle.LiveData
import com.shoaib.singeltonmvvm.models.User
import kotlinx.coroutines.CompletableJob

object UserRepository {

    var job: CompletableJob? = null

    fun getUser(userId: String):LiveData<User>{
        return object :LiveData<User>(){
            override fun onActive() {
                super.onActive()
            }
        }
    }

    fun cancelJobs()
    {
        job?.cancel()
    }
}