package com.shoaib.firebasechatapp.listeners

import com.shoaib.firebasechatapp.model.User

interface UserListener {

    fun onUserClicked(user: User)
}