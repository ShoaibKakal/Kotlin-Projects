package com.shoaibkakal.mycanvas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
import android.widget.LinearLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        myCanvasTest.systemUiVisibility = SYSTEM_UI_FLAG_FULLSCREEN
//        setContentView(MyCanvasTest(this))


        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNavigationView.addView(MyCanvasTest(this))

    }
}