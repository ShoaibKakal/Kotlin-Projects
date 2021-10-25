 package com.shoaib.connectsqlserver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.shoaib.connectsqlserver.model.ConnectionHelper
import java.lang.Exception
import java.sql.Connection

 class MainActivity : AppCompatActivity() {

    lateinit var connection: Connection
    var connectionResult = "";
     val TAG = "SQL-TAG"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        findViewById<Button>(R.id.button).setOnClickListener {
            GetTextFromSQL(it)
        }
    }

     fun GetTextFromSQL(v:View){

         try {

             var connectionHelper: ConnectionHelper = ConnectionHelper()
             connection = connectionHelper.connection()

             val query = "SELECT * FROM testTable"
             val statement = connection.createStatement()
             val resultSet = statement.executeQuery(query)

             while (resultSet.next())
             {
                 findViewById<TextView>(R.id.textView).text = resultSet.getString(1)
                 findViewById<TextView>(R.id.textView2).text = resultSet.getString(2)
             }
         }catch (ex: Exception)
         {
             Log.d(TAG, "GetTextFromSQL: $ex")
         }

     }
}