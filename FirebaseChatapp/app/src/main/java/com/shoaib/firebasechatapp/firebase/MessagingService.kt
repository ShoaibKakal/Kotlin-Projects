package com.shoaib.firebasechatapp.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLink
import androidx.navigation.NavDeepLinkBuilder
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.shoaib.firebasechatapp.MainActivity
import com.shoaib.firebasechatapp.fragment.ChatFragment
import com.shoaib.firebasechatapp.model.User
import com.shoaib.firebasechatapp.utilities.Constants
import java.util.*
import com.shoaib.firebasechatapp.R


class MessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val user = User()
        user.id = remoteMessage.data[Constants.KEY_USER_ID]
        user.name = remoteMessage.data[Constants.KEY_NAME]
        user.token = remoteMessage.data[Constants.KEY_FCM_TOKEN]

        val notificationId = Random().nextInt()
        val channelId = "chat_message"

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(Constants.KEY_USER, user)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)



//        val pendingIntent = NavDeepLinkBuilder(this)
//            .setComponentName(MainActivity::class.java)
//            .setGraph(R.navigation.nav_graph)
//            .setDestination(R.id.chatFragment)
//            .setArguments(bundleOf(Constants.KEY_USER to user))
//            .createPendingIntent()


        val builder = NotificationCompat.Builder(this, channelId)
        builder.setSmallIcon(R.drawable.ic_notification)
        builder.setContentTitle(user.name)
        builder.setContentText(remoteMessage.data[Constants.KEY_MESSAGE])
        builder.setStyle(NotificationCompat.BigTextStyle().bigText(remoteMessage.data[Constants.KEY_MESSAGE]))
        builder.priority = NotificationCompat.PRIORITY_MAX
        builder.setContentIntent(pendingIntent)
        builder.setAutoCancel(true)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val channelName = "Chat Message"
            val channelDescription = "This notification channel is used for chat message notification"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance)
            channel.description = channelDescription
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        val notificationManagerCompat = NotificationManagerCompat.from(this)
        notificationManagerCompat.notify(notificationId, builder.build())
    }

}