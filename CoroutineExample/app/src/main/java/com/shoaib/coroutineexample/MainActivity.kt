package com.shoaib.coroutineexample

import android.graphics.BitmapFactory
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.shoaib.coroutineexample.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var btn_loadImage: Button
    lateinit var imageView : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        btn_loadImage = binding.btnLoadImage
        imageView = binding.image

        btn_loadImage.setOnClickListener { btnLoadImage(it) }
    }

    private fun btnLoadImage(it: View?) {


        CoroutineScope(Dispatchers.IO).launch {
            Log.d("myTag", "btnLoadImage: ThreadName: ${Thread.currentThread().name}" )
            val url = URL("https://s3.amazonaws.com/user-content.stoplight.io/8987/1541019969018")
            val bitmap = BitmapFactory.decodeStream(url.openStream())

            withContext(Dispatchers.Main){
                Log.d("myTag", "btnLoadImage: withContext ThreadName: ${Thread.currentThread().name}" )
                imageView.setImageBitmap(bitmap)
            }

        }


    }
}