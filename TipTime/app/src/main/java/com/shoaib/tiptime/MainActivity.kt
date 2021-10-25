package com.shoaib.tiptime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.shoaib.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCalculate.setOnClickListener { calculateTip() }

    }

    private fun calculateTip() {
        val inputCost = binding.costInputEditText.text.toString()
        val cost = inputCost.toDouble()
        val selectedId = binding.tipOptions.checkedRadioButtonId
        val tipPercentage = when(selectedId)
        {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighten_percent ->0.18
            else -> 0.10
        }

        var tip = cost * tipPercentage
        val roundUP = binding.switchRoudUp.isChecked

        if (roundUP)
        {
            tip = ceil(tip)
        }

        val formatedTip = NumberFormat.getCurrencyInstance().format(tip)
        Toast.makeText(this, tip.toString(), Toast.LENGTH_LONG).show()

        binding.textTipAmount.text = getString(R.string.tip_amount, formatedTip)

    }
}