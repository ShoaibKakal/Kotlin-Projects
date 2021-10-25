package com.shoaib.firebasechatapp.model

import java.io.Serializable

data class User(var name: String?, var image: String?, var email: String?, var token: String? = null, var id:String?=null) :
    Serializable{

        constructor():this("", "", "", null, null)
    }