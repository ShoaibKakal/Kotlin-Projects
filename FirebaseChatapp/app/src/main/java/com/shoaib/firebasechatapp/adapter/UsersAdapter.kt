package com.shoaib.firebasechatapp.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shoaib.firebasechatapp.R
import com.shoaib.firebasechatapp.databinding.LayoutItemUserBinding
import com.shoaib.firebasechatapp.listeners.UserListener
import com.shoaib.firebasechatapp.model.User

class UsersAdapter(private val context: Context,private val userListener: UserListener, private val users: List<User>) :
    RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.setUserData(users[position], userListener)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    class UserViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        private val binding = LayoutItemUserBinding.bind(view)
        private fun getUserImage(encodedImage: String): Bitmap {
            val bytes = Base64.decode(encodedImage, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

        }

         fun setUserData(user: User, userListener: UserListener) {
            with(binding) {
                textName.text = user.name
                textEmail.text = user.email
                imageProfile.setImageBitmap(getUserImage(user.image!!))
                root.setOnClickListener {
                    userListener.onUserClicked(user)
                    Log.d("testTag", "setUserData: ${user.name} Clicked")
                }
            }
        }
    }


}