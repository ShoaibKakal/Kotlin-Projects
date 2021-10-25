package com.shoaib.tictactoeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import android.widget.Toast.makeText
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var remainingBoxes: ArrayList<Int> = ArrayList()
    var player1: ArrayList<Int> = ArrayList()
    var player2: ArrayList<Int> = ArrayList()
    var lastClick = 1
    val TAG: String = "Hello";
    private lateinit var btn1: Button
    lateinit var btn2: Button
    lateinit var btn3: Button
    lateinit var btn4: Button
    lateinit var btn5: Button
    lateinit var btn6: Button
    lateinit var btn7: Button
    lateinit var btn8: Button
    lateinit var btn9: Button

    lateinit var replayBtn:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        remainingBoxes.addAll(1..9)

        btn1 = findViewById(R.id.btn1)
        btn2 = findViewById<Button>(R.id.btn2)
        btn3 = findViewById<Button>(R.id.btn3)
        btn4 = findViewById<Button>(R.id.btn4)
        btn5 = findViewById<Button>(R.id.btn5)
        btn6 = findViewById<Button>(R.id.btn6)
        btn7 = findViewById<Button>(R.id.btn7)
        btn8 = findViewById<Button>(R.id.btn8)
        btn9 = findViewById<Button>(R.id.btn9)
        replayBtn = findViewById<Button>(R.id.replyBtn)

        btn1.setOnClickListener {
            check(btn1, 1)
        }
        btn2.setOnClickListener {
            check(btn2, 2)
        }
        btn3.setOnClickListener {
            check(btn3, 3)
        }
        btn4.setOnClickListener {
            check(btn4, 4)
        }
        btn5.setOnClickListener {
            check(btn5, 5)
        }
        btn6.setOnClickListener {
            check(btn6, 6)
        }
        btn7.setOnClickListener {
            check(btn7, 7)
        }
        btn8.setOnClickListener {
            check(btn8, 8)
        }
        btn9.setOnClickListener {
            check(btn9, 9)
        }

        replayBtn.setOnClickListener {
            val intent = getIntent()
            finish()
            startActivity(intent)
        }


    }

    private fun check(it: Button, x: Int): Int {

        var check = 0
        remainingBoxes.forEach { box ->
            if (box == x) {
                if (lastClick == 1) {
                    it.setBackgroundColor(resources.getColor(R.color.black))
                    player1.add(x)
                    remainingBoxes.remove(x)
                    lastClick = 2
                    check = runChecker()
                    if (check == 1)
                        remainingBoxes.clear()
                    autoPlay()
                    return 1
                } else {
                    it.setBackgroundColor(resources.getColor(R.color.purple_500))
                    player2.add(x)
                    remainingBoxes.remove(x)
                    lastClick = 1
                    check = runChecker()
                    if (check == 1)
                        remainingBoxes.clear()
                    return 2
                }
            }

        }



        return 0

    }

    private fun autoPlay() {

        var x: Button? = null

        val randomIndex = java.util.Random().nextInt(remainingBoxes.size)
        val cellId = remainingBoxes[randomIndex]
        x = when (cellId) {
            1 -> btn1
            2 -> btn2
            3 -> btn3
            4 -> btn4
            5 -> btn5
            6 -> btn6
            7 -> btn7
            8 -> btn8
            9 -> btn9
            else -> btn1
        }

        check(x, cellId)
    }

    private fun runChecker(): Int {

        if (player1.size == 3 && player2.size == 3) {
            return checkWinner()
        }

        return 0
    }

    private fun checkWinner(): Int {


        player1.forEach { p ->
            Log.d(TAG, "checkWinner: $p")
        }

        if (checkRows(player1) || checkColums(player1) || checkDiagnols(player1)) {
            makeText(this, "Player 1 Won", Toast.LENGTH_LONG).show()
            return 1
        } else if (checkRows(player2) || checkColums(player2) || checkDiagnols(player2)) {
            makeText(this, "Player 2 Won", Toast.LENGTH_LONG).show()
            return 1
        } else {
            makeText(this, "Tie", Toast.LENGTH_LONG).show()
            return 1
        }


        return 0;
    }

    private fun checkDiagnols(ar: ArrayList<Int>): Boolean {
        //Diagnol 1
        if (ar.contains(1) && ar.contains(5) && ar.contains(9)) {
            //player win
            return true
        }
        //Diagnol 2
        else if (ar.contains(3) && ar.contains(5) && ar.contains(7)) {
            //player win
            return true
        }
        return false
    }

    private fun checkRows(ar: ArrayList<Int>): Boolean {
        //Row 1
        if (ar.contains(1) && ar.contains(2) && ar.contains(3)) {
            //player win
            return true
        }
        //Row 2
        else if (ar.contains(4) && ar.contains(5) && ar.contains(6)) {
            //player win
            return true
        }
        //Row 3
        else if (ar.contains(7) && ar.contains(8) && ar.contains(9)) {
            //player win
            return true
        }
        return false
    }

    private fun checkColums(ar: ArrayList<Int>): Boolean {

        //column 1
        if (ar.contains(1) && ar.contains(4) && ar.contains(7)) {
            //player win
            return true
        }
        //column 2
        else if (ar.contains(2) && ar.contains(5) && ar.contains(8)) {
            //player win
            return true
        }
        //column 3
        else if (ar.contains(3) && ar.contains(6) && ar.contains(9)) {
            //player win
            return true
        }

        return false
    }

    private fun sortArray(arrayList: ArrayList<Int>) {

        var temp = 0;

        var i = 0;
        while (i < arrayList.size) {
            if (arrayList[i] > temp) {
                temp = arrayList[i]
            } else {
                arrayList[i - 1] = arrayList[i]
                arrayList[i] = temp
                i = -1
                temp = 0
            }
            i++
        }
    }
}