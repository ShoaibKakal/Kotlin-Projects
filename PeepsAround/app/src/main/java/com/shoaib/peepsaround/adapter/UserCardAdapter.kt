package com.shoaib.peepsaround.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shoaib.peepsaround.R
import com.shoaib.peepsaround.model.User

class UserCardAdapter(
    private val context: Context,
    private val userdataList: ArrayList<User>
) : RecyclerView.Adapter<UserCardAdapter.UserCardVH>() {


    class UserCardVH(private val view: View,private val context: Context) : RecyclerView.ViewHolder(view) {
        private val userName = view.findViewById<TextView>(R.id.tv_userName)
        private val distance = view.findViewById<TextView>(R.id.tv_distance)
        private val rv_interests = view.findViewById<RecyclerView>(R.id.recyclerview_interests)
        private val TAG = "HomeFrag"


        fun bindView(userdata: User) {
            userName.text = userdata.username

            /*current logged in user
               this [currentUserLat] and [currentUserLng] are considered as a origin to find the distance to other users. these are the lat/lng of
               current logged in user.
             */
            val currentUserLat = 30.1497079
            val currentUserLng = 74.3239235
            var result = calcDistance(
                currentUserLat,
                currentUserLng,
                userdata.currentLocation?.latitude!!,
                userdata.currentLocation.longitude!!
            )

            val mile = 0.621371
            result*=mile //convert KM to miles.

            result = if(result<1) 1.0 else result

            distance.text = context.getString(R.string.distance, result.toInt())


            //interests
            val testAdapter = InterestsAdapter(userdata.interests)
            rv_interests.adapter = testAdapter
        }

        private fun calcDistance(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Double {
            val p = 0.017453292519943295
            val a = 0.5 - Math.cos((lat2 - lat1) * p) / 2 +
                    Math.cos(lat1 * p) * Math.cos(lat2 * p) *
                    (1 - Math.cos((lng2 - lng1) * p)) / 2

            val x = 12742 * Math.asin(Math.sqrt(a))
            Log.d(TAG, "calcDistance: $x")
            return 12742 * Math.asin(Math.sqrt(a))
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserCardVH {
        val userCardLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_card_laryout, parent, false)
        return UserCardVH(userCardLayout, context)
    }

    override fun onBindViewHolder(holder: UserCardVH, position: Int) {
        holder.bindView(userdataList[position])
    }

    override fun getItemCount(): Int {
        return userdataList.size
    }
}