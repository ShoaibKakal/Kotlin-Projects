package com.shoaibkakal.searchablebookreader


import android.os.Bundle
import android.text.Html
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class PdfActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf)

        val textView = findViewById<TextView>(R.id.pdfView)
        var text = " ";

        try {
            val stream = assets.open("sample1.txt");
            val reader = BufferedReader(InputStreamReader(stream))
            text = reader.readText()
        } catch (e: IOException) {
            e.printStackTrace();
        }

        textView.text = text;


        val searchText = findViewById<EditText>(R.id.inputSearch)
        findViewById<ImageView>(R.id.imageSearch)
            .setOnClickListener {
                val textToHighlight = searchText.text.toString()

                val replaceWith =
                    "<span style='background-color:yellow'>" + textToHighlight + "</span>"

                val modifiedText = text.replace(textToHighlight, replaceWith)
                textView.text = Html.fromHtml(modifiedText)
            }
    }
}