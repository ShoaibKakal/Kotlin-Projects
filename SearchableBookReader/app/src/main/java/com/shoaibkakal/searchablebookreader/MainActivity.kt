package com.shoaibkakal.searchablebookreader

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var book1: ImageView
    private lateinit var book2: ImageView
    private lateinit var book3: ImageView
    private lateinit var book4: ImageView
    private lateinit var book5: ImageView
    private lateinit var book6: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        book1 = findViewById(R.id.book1)
        book2 = findViewById(R.id.book2)
        book3 = findViewById(R.id.book3)
        book4 = findViewById(R.id.book4)
        book5 = findViewById(R.id.book5)
        book6 = findViewById(R.id.book6)

        book1.setOnClickListener {
            naivgate()
        }

        book2.setOnClickListener {
            naivgate()
        }

        book3.setOnClickListener {
            naivgate()
        }

        book4.setOnClickListener {
            naivgate()
        }

        book5.setOnClickListener {
            naivgate()
        }

        book6.setOnClickListener {
            naivgate()
        }


    }

    private fun naivgate() {
        val mIntent = Intent(this, ChapterActivity::class.java)
        startActivity(mIntent)

    }
}