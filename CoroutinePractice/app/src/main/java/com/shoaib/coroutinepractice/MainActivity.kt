package com.shoaib.coroutinepractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.shoaib.coroutinepractice.viewmodels.DemoDataViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: DemoDataViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.getData()?.forEach {
            Log.d("hellotest", "onCreate: ${it.title}")
        }


    }
}