package com.shoaib.peepsaround.model

import android.location.Location

class User(val username:String = "", val interests:ArrayList<String> = arrayListOf(), val currentLocation:CurrentLocation? = null ) {
}
