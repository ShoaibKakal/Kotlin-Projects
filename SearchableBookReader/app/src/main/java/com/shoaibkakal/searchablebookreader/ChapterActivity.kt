package com.shoaibkakal.searchablebookreader

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

class ChapterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapter)


        findViewById<CardView>(R.id.chapter1).setOnClickListener { navigate("book1") }
        findViewById<CardView>(R.id.chapter2).setOnClickListener {  navigate("book2")}
        findViewById<CardView>(R.id.chapter3).setOnClickListener {  navigate("book3")}
        findViewById<CardView>(R.id.chapter4).setOnClickListener {  navigate("book4")}
        findViewById<CardView>(R.id.chapter5).setOnClickListener {  navigate("book5")}
        findViewById<CardView>(R.id.chapter6).setOnClickListener {  navigate("book2")}
        findViewById<CardView>(R.id.chapter7).setOnClickListener {  navigate("book3")}
        findViewById<CardView>(R.id.chapter8).setOnClickListener {  navigate("book4")}
        findViewById<CardView>(R.id.chapter10).setOnClickListener {  navigate("book5")}
    }

    private fun navigate(str:String)
    {
        val mIntent = Intent(this, PdfActivity::class.java);
        mIntent.putExtra("book",str)
        startActivity(mIntent)

    }
}