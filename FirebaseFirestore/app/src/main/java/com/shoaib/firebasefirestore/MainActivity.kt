package com.shoaib.firebasefirestore

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore


const val COLLECTION_STUDENTS = "students"
const val TAG = "testTag"

class MainActivity : AppCompatActivity() {


    private lateinit var database: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        database = FirebaseFirestore.getInstance()
        writeData()
        readData()
    }

    private fun writeData() {
        val user: MutableMap<String, Any> = HashMap()
        user["first"] = "Alan"
        user["middle"] = "Mathison"
        user["last"] = "Turing"
        user["born"] = 1912
        user["isAlive"] = true

        database.collection(COLLECTION_STUDENTS)
            .add(user)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot added with ID: ${it.id}")
            }
    }

    private fun readData()
    {
        database.collection(COLLECTION_STUDENTS)
            .get()
            .addOnCompleteListener { task->
                if(task.isSuccessful) {
                    task.result?.forEach { qS ->
                        Log.d(TAG, "readData: ${qS.data["first"]}")

                    }
                }
            }
    }
}