package com.shoaib.firebasechatapp.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shoaib.firebasechatapp.databinding.LayoutItemReceiveMessageBinding
import com.shoaib.firebasechatapp.databinding.LayoutItemSentMessageBinding
import com.shoaib.firebasechatapp.model.ChatMessage

const val VIEW_TYPE_SENT = 1
const val VIEW_TYPE_RECEIVED = 2

class ChatAdapter(
    private val chatMessages: List<ChatMessage>,
    private var receiverProfileImage: Bitmap?,
    private val senderId: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun setReceiverProfileImage(bitmap:Bitmap?)
    {
        receiverProfileImage = bitmap
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == VIEW_TYPE_SENT)
        {
            val view = LayoutItemSentMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return SentMessageViewHolder(view.root)
        }
        else{
            val view = LayoutItemReceiveMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ReceiveMessageViewHolder(view.root )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_SENT){
            (holder as SentMessageViewHolder).bindData(chatMessages[position])
        }
        else{
            (holder as ReceiveMessageViewHolder).bindData(chatMessages[position], receiverProfileImage)
        }
    }

    override fun getItemCount(): Int {
        return chatMessages.size
    }

    override fun getItemViewType(position: Int): Int {
        if(chatMessages[position].senderId == senderId){
            return VIEW_TYPE_SENT
        }
        else{
            return VIEW_TYPE_RECEIVED
        }
    }

    class SentMessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = LayoutItemSentMessageBinding.bind(view)


        fun bindData(chatMessage: ChatMessage) {
            binding.textMessage.text = chatMessage.message
            binding.textDateTime.text = chatMessage.dateTime

        }
    }

    class ReceiveMessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = LayoutItemReceiveMessageBinding.bind(view)

        fun bindData(chatMessage: ChatMessage, receiverProfileImage: Bitmap?) {
            binding.textMessage.text = chatMessage.message
            binding.textDateTime.text = chatMessage.dateTime
            if (receiverProfileImage != null)
            {
                binding.imageProfile.setImageBitmap(receiverProfileImage)
            }

        }


    }

}