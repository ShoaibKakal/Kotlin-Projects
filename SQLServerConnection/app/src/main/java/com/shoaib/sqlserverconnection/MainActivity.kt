package com.shoaib.sqlserverconnection

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.shoaib.sqlserverconnection.connection.db
import com.shoaib.sqlserverconnection.connection.pass
import com.shoaib.sqlserverconnection.connection.un
import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement

class MainActivity : AppCompatActivity() {

    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var registerBtn: Button
    private lateinit var status: TextView

    private  var TAG = "SQLConnection"
    private lateinit var stmt: PreparedStatement
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        name = findViewById(R.id.userName)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        registerBtn = findViewById(R.id.registerbtn)
        status = findViewById(R.id.status)


        registerBtn.setOnClickListener {

        }
    }

    inner class RegisterUser: AsyncTask<String, String, String>() {
        private var z = ""
        private var isSuccess = false

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun onPostExecute(result: String?) {
            //
        }

        override fun doInBackground(vararg p0: String?): String {

            return null
        }

    }

    fun connection( server: String): Connection?{

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        var connection: Connection? = null
        var connectionURL: String? = null

        try {

            Class.forName("net.sourceforge.jtds.jdbc.Driver")
            connectionURL = "jdbc:jtds:sqlserver://$server/$db;user=$un;password=$pass;"
            connection = DriverManager.getConnection(connectionURL)
        }catch (e: Exception)
        {
            Log.d(TAG, "connection: "+ e.message)
        }

        return connection
    }
}