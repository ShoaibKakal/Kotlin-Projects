package com.shoaib.firebasechatapp.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shoaib.firebasechatapp.R
import com.shoaib.firebasechatapp.databinding.LayoutItemRecentConversationBinding
import com.shoaib.firebasechatapp.listeners.ConversationListener
import com.shoaib.firebasechatapp.model.ChatMessage
import com.shoaib.firebasechatapp.model.User

class RecentConversationAdapter(private val chatMessages: List<ChatMessage>,private val conversationListener: ConversationListener) :
    RecyclerView.Adapter<RecentConversationAdapter.ConversionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_item_recent_conversation, parent, false)
        return ConversionViewHolder(view)
    }

    override fun onBindViewHolder(holder: ConversionViewHolder, position: Int) {
        holder.bindData(chatMessages[position], conversationListener)
    }

    override fun getItemCount(): Int {
        return chatMessages.size
    }



    class ConversionViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = LayoutItemRecentConversationBinding.bind(view)


        fun bindData(chatMessage: ChatMessage, conversationListener: ConversationListener)
        {
            binding.imageProfile.setImageBitmap(getConversionImage(chatMessage.conversionImage!!))
            binding.textName.text = chatMessage.conversionName
            binding.textRecentMessage.text = chatMessage.message

            binding.root.setOnClickListener {
                val user = User()
                user.id = chatMessage.conversionId
                user.name = chatMessage.conversionName
                user.image = chatMessage.conversionImage
                conversationListener.onConversationClicker(user)
            }
        }
        private fun getConversionImage(encodedImage: String):Bitmap{
            val bytes = Base64.decode(encodedImage, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        }
    }

}