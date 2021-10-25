 package com.shoaib.agecalculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*


 class MainActivity : AppCompatActivity() {


     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_main)
         val ageButton = findViewById<Button>(R.id.buttonAge)
         val userDOB = findViewById<EditText>(R.id.inputAge)
         val output = findViewById<TextView>(R.id.textViewAge)
         
         ageButton.setOnClickListener {

             val userInput = Integer.parseInt(userDOB.text.toString())
             val currentAge = Calendar.getInstance().get(Calendar.YEAR) - userInput
             output.text = "Your Age is: $currentAge Years"

         }
     }
 }

