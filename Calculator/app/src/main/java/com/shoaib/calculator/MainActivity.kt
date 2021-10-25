package com.shoaib.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var num0Btn: Button
    lateinit var num1Btn: Button
    lateinit var num2Btn: Button
    lateinit var num3Btn: Button
    lateinit var num4Btn: Button
    lateinit var num5Btn: Button
    lateinit var num6Btn: Button
    lateinit var num7Btn: Button
    lateinit var num8Btn: Button
    lateinit var num9Btn: Button
    lateinit var addBtn: Button
    lateinit var subtractBtn: Button
    lateinit var multiplyBtn: Button
    lateinit var divideBtn: Button
    lateinit var moduleBtn: Button
    lateinit var acBtn: Button
    lateinit var dotBtn: Button
    lateinit var equalBtn: Button
    lateinit var funBtn: Button
    lateinit var textResult: TextView
    var num1: Int = 0
    var num2: Int = 0
    var operator: Char = ' '


    var stack: Stack<Int> = Stack<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

        textResult.text = " "
        num0Btn.setOnClickListener {
            textResult.append(num0Btn.text.toString())
        }

        num1Btn.setOnClickListener {
            textResult.append(num1Btn.text.toString())
        }

        num2Btn.setOnClickListener {
            textResult.append(num2Btn.text.toString())
        }

        num3Btn.setOnClickListener {
            textResult.append(num3Btn.text.toString())
        }

        num4Btn.setOnClickListener {
            textResult.append(num4Btn.text.toString())
        }

        num5Btn.setOnClickListener {
            textResult.append(num5Btn.text.toString())
        }

        num6Btn.setOnClickListener {
            textResult.append(num6Btn.text.toString())
        }
        num7Btn.setOnClickListener {
            textResult.append(num7Btn.text.toString())
        }

        num8Btn.setOnClickListener {
            textResult.append(num8Btn.text.toString())
        }

        num9Btn.setOnClickListener {
            textResult.append(num9Btn.text.toString())
        }

        addBtn.setOnClickListener {
            textResult.append(addBtn.text.toString())
        }

        subtractBtn.setOnClickListener {
            textResult.append(subtractBtn.text.toString())
        }

        divideBtn.setOnClickListener {
            textResult.append(divideBtn.text.toString())
        }

        multiplyBtn.setOnClickListener {
            textResult.append(multiplyBtn.text.toString())
        }

        dotBtn.setOnClickListener {
            textResult.append(dotBtn.text.toString())
        }

        acBtn.setOnClickListener {
            textResult.text = " "
        }

        equalBtn.setOnClickListener {
            showResult(textResult.text.toString())
        }
    }

    private fun infixToPostFix(str: String): String {
        var result: String = ""

        val stack: Stack<Char> = Stack()

        for (i in 0 until str.length - 1) {
            val ch: Char = str[i]

            if (Character.isLetterOrDigit(ch)) {
                result += ch
            } else if (ch == '(') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result += stack.pop()
                }
            } else {
                while (!stack.isEmpty() && precedence(ch) <= precedence(stack.peek())) {
                    result += stack.pop()
                }

                stack.push(ch);
            }
        }

        while (!stack.isEmpty()) {
            if (stack.peek() == '(') {
                return "Invalid Expression"

            }
            result += stack.pop();
        }
        return result;

    }

    private fun precedence(ch: Char): Int {

        when (ch) {
            '+' -> return 1;
            '-' -> return 1;

            '*' -> return 2
            '/' -> return 2

        }
        return 0;
    }

    private fun showResult(str: String) {
//        Toast.makeText(this, infixToPostFix("a+b*(c^d-e)^(f+g*h)-i"), Toast.LENGTH_LONG).show()

        var flag = 0;
        for (i in 0 until str.length - 1) {
            if (flag == 0) {
                if (!str[i].isLetterOrDigit())
                {
                    flag = 1
                }

            }
        }
    }

    private fun init() {
        textResult = findViewById(R.id.textViewResult)
        num0Btn = findViewById(R.id.num0Btn)
        num1Btn = findViewById(R.id.num1Btn)
        num2Btn = findViewById(R.id.num2Btn)
        num3Btn = findViewById(R.id.num3Btn)
        num4Btn = findViewById(R.id.num4Btn)
        num5Btn = findViewById(R.id.num5Btn)
        num6Btn = findViewById(R.id.num6Btn)
        num7Btn = findViewById(R.id.num7Btn)
        num8Btn = findViewById(R.id.num8Btn)
        num9Btn = findViewById(R.id.num9Btn)
        addBtn = findViewById(R.id.addBtn)
        subtractBtn = findViewById(R.id.subBtn)
        multiplyBtn = findViewById(R.id.multiplyBtn)
        divideBtn = findViewById(R.id.divideBtn)
        moduleBtn = findViewById(R.id.moduleBtn)
        acBtn = findViewById(R.id.acBtn)
        dotBtn = findViewById(R.id.dotBtn)
        equalBtn = findViewById(R.id.equalBtn)
        funBtn = findViewById(R.id.funBtn)
    }
}