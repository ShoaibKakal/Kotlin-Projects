package com.shoaib.firebasechatapp


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.shoaib.firebasechatapp.utilities.Constants
import com.shoaib.firebasechatapp.utilities.PreferenceManager

class MainActivity : AppCompatActivity() {
    private lateinit var documentReference:DocumentReference
    private var bool = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val preferenceManager = PreferenceManager(applicationContext)
        val database = FirebaseFirestore.getInstance()

        if (preferenceManager.getString(Constants.KEY_USER_ID) != null)
        {
            bool = true
            documentReference = database.collection(Constants.KEY_COLLECTION_USERS)
                .document(preferenceManager.getString(Constants.KEY_USER_ID)!!)
        }
    }

    override fun onPause() {
        super.onPause()
        documentReference.update(Constants.KEY_AVAILABILITY, false)
    }
    override fun onResume() {
        super.onResume()
        if(bool)
            documentReference.update(Constants.KEY_AVAILABILITY, true)
    }
}