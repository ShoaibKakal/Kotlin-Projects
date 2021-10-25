package com.shoaib.learnfragments

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var imageBanana: ImageView
    private lateinit var imageKiwi: ImageView
    val TAG = "hello"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imageBanana = findViewById(R.id.imageBanana)
        imageKiwi = findViewById(R.id.imageKiwi)

        val fragmentManager = supportFragmentManager

//        fragmentManager.commit {
////            setCustomAnimations()
//            setReorderingAllowed(true)
//            replace(R.id.fragment_container_view, TestFragment())
//            addToBackStack("testFragment")
//
//        }


        imageBanana.setOnClickListener {
            fragmentManager.commit {
                setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
                )

//                 var bitmap: Bitmap? = null
//                var bs: ByteArrayOutputStream = ByteArrayOutputStream()
//                bitmap?.compress(Bitmap.CompressFormat.PNG, 50, bs)

                replace<TestFragment>(R.id.descriptive_fragment_container, args = bundleOf("image" to  R.drawable.banana))
                setReorderingAllowed(true)
                addToBackStack("test1Fragment")
            }
        }




    }
}