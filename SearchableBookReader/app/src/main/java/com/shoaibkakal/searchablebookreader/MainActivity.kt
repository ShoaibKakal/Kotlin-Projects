package com.shoaibkakal.searchablebookreader

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var btn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn = findViewById(R.id.btn);

        btn.setOnClickListener {
            val mIntent =  Intent(this, PdfActivity::class.java);
            startActivity(mIntent)
        }

    }
}