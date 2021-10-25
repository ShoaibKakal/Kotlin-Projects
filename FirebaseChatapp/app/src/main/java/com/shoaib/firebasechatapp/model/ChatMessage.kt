package com.shoaib.firebasechatapp.model

import java.util.*

data class ChatMessage(
    var senderId: String,
    var receiverId: String,
    var message: String,
    var dateTime: String,
    var dateObject: Date,
    var conversionId: String?=null,
    var conversionName: String?=null,
    var conversionImage: String?=null,

){

    constructor() : this("","","","", Date(),null, null,null)
}