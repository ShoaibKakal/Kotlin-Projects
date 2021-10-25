package com.shoaib.firebasechatapp.listeners

import com.shoaib.firebasechatapp.model.User

interface ConversationListener {
    fun onConversationClicker(user: User)
}