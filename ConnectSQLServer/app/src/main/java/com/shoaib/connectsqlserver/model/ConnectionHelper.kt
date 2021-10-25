package com.shoaib.connectsqlserver.model

import android.annotation.SuppressLint
import android.os.StrictMode
import android.util.Log
import java.sql.Connection
import java.sql.DriverManager

class ConnectionHelper {
    var TAG  = "SQL-TAG"
    var connection: Connection? = null
    var name: String? = null
    var pass: String? = null
    var ip: String? = null
    var database: String? = null
    var port: String? = null



    @SuppressLint("NewApi")
    public fun connection(): Connection{
        ip = "172.1.1.0"
        database = "ProgrammingDB"
        port = "1433"
        val policy: StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()

        StrictMode.setThreadPolicy(policy);

        var connection: Connection? = null
        var connectionURL: String?


        try {

            Class.forName("net.sourceforge.jtds.jdbc.Driver")
            connectionURL = "jdbc:jtds:sqlserver://$ip:$port;databasename=$database;"
            connection = DriverManager.getConnection(connectionURL)
        }catch (ex: Exception)
        {
            Log.d(TAG, "connection: ${ex.message}")
        }

        return connection!!
    }
}