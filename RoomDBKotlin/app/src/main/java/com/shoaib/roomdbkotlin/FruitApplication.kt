package com.shoaib.roomdbkotlin

import android.app.Application
import com.shoaib.roomdbkotlin.databases.FruitDatabase


/*
Use lazy delegate so the instance database is lazily created when you first need/access the reference (rather than when the app starts).
This will create the database (the physical database on the disk) on the first access.
 */
class FruitApplication : Application() {
    val fruitDatabase: FruitDatabase by lazy {
        FruitDatabase.getDatabase(this)
    }
}


