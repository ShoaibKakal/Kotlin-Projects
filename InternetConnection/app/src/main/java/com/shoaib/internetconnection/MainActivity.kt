package com.shoaib.internetconnection

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkConnection()
    }


    override fun onStart() {
        super.onStart()
        Toast.makeText(this, if (isConnected()) "You are connected to the internet" else "You are NOT connected to the internet", Toast.LENGTH_SHORT
        ).show()
    }

    private fun checkConnection() {
        val connectivity = CheckConnectivity(application)
        connectivity.observe(this, { isConnected ->
            Log.d("testTag", "checkConnection: Observer Called")
            if (isConnected) {
                Toast.makeText(this, "You are connected to the internet", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "You are NOT connected to the internet", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun isConnected(): Boolean {
        var connected = false
        try {
            val cm =
                applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val nInfo = cm.activeNetworkInfo
            connected = nInfo != null && nInfo.isAvailable && nInfo.isConnected

            return connected
        } catch (e: Exception) {
            Log.e("Connectivity Exception", e.message!!)
        }
        return connected
    }
}