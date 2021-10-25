package com.shoaib.coroutinepractice1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.shoaib.coroutinepractice1.model.CommentModel
import com.shoaib.coroutinepractice1.network.ApiObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    val TAG = "textTag"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn).setOnClickListener {

            lifecycleScope.launch(Dispatchers.IO ) {

//                val posts = ApiObject.retrofitService.getPosts()
                val comments = ApiObject.retrofitService.getComments("2")

                Intent(this@MainActivity, SecondActivity::class.java).also {
                    it.putExtra("data", arrayListOf(comments))

                    startActivity(it)
                }
                for (comment in comments)
                {
                    Log.d(TAG, "onCreate: ${comment.email}")
                }
            }

        }
    }




}