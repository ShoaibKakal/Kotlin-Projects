package com.shoaib.pokemonandroid

import android.app.*
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.common.util.SharedPreferencesUtils
import com.google.android.gms.location.*
import java.util.concurrent.TimeUnit

class ForegroundOnlyLocationService : Service() {

    private val TAG = "ForegroundOnlyLocationService"
    private var configurationChange = false
    private var serviceRunningInForeground = false
    private var localBinder = LocalBinder()

    private lateinit var notificationManager: NotificationManager


    // FusedLocationProvider - Main class for receiving location updates.
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    // LocationRequest - Requirements for the location updates, i.e., how often you should receive updates, the priority, etc.
    private lateinit var locationRequest: LocationRequest

    // LocationCallback - Called when FusedLocationProvider has a new Location
    private lateinit var locationCallback: LocationCallback


    private var currentLocation: Location? = null


    override fun onCreate() {
        Log.d(TAG, "onCreate: ")

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)


        locationRequest = LocationRequest.create().apply {

            interval = TimeUnit.SECONDS.toMillis(60) // 60 seconds

            fastestInterval = TimeUnit.SECONDS.toMillis(30) // 30 Seconds

            maxWaitTime = TimeUnit.MINUTES.toMillis(2) // 2 minutes

            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        // Initialize the LocationCallback

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)

                currentLocation = locationResult.lastLocation

                // Notify our Activity that a new location was added.

                val intent = Intent(ACTION_FOREGROUND_ONLY_LOCATION_BROADCAST)
                intent.putExtra(EXTRA_LOCATION, currentLocation)
                LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)


                // updates notification content if this service is running as a foreground service
                if (serviceRunningInForeground) {
                    notificationManager.notify(
                        NOTIFICATION_ID,
                        generateNotification(currentLocation)
                    )
                }
            }
        }
    } //onCreate

    private fun subscribeToLocationUpdates() {
        Log.d(TAG, "subscribeToLocationUpdates: ")

        SharedPreferenceUtil.saveLocationTrackingPref(this, true)
    }

    private fun generateNotification(location: Location?): Notification {

        Log.d(TAG, "generateNotification(): ")

        // Main steps for building a BIG_TEXT_STYLE notification:
        //      0. Get data
        //      1. Create Notification Channel for O+
        //      2. Build the BIG_TEXT_STYLE
        //      3. Set up Intent / Pending Intent for notification
        //      4. Build and issue the notification


        // 0. Get data
        val mainNotificationText = location.toString()
        val titleText = getString(R.string.app_name)


        // 1. create Notification Channel for 0+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                titleText,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            notificationManager.createNotificationChannel(notificationChannel)
        }


        // 2. Build the BIG_TEXT_STYLE
        val bigTextStyle = NotificationCompat.BigTextStyle()
            .bigText(mainNotificationText)
            .setBigContentTitle(titleText)


        // 3. set up Intent / Pending Intent for notification
        val launchActivityIntent = Intent(this, MapsActivity::class.java)

        val cancelIntent = Intent(this, ForegroundOnlyLocationService::class.java)
        cancelIntent.putExtra(EXTRA_CANCEL_LOCATION_TRACKING_FROM_NOTIFICATION, true)

        val servicePendingIntent = PendingIntent.getService(
            this, 0, cancelIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val activityPendingIntent = PendingIntent.getActivity(this, 0, launchActivityIntent, 0)


        //4.  Build and issue the notification

        // Notification Channel Id is ignored for Version< 0 (26).
        val notificationCompatBuilder =
            NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)


        return notificationCompatBuilder
            .setStyle(bigTextStyle)
            .setContentTitle(titleText)
            .setContentText(mainNotificationText)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setOngoing(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .addAction(
                R.drawable.ic_launcher_background, "Action Text", activityPendingIntent
            )
            .build()

    }


    override fun onBind(intent: Intent): IBinder {
        TODO("HELL PLEASE STOP IT ")

    }// onBind

    inner class LocalBinder : Binder() {
        internal val service: ForegroundOnlyLocationService
            get() = this@ForegroundOnlyLocationService
    }

    companion object {

        private const val PACKAGE_NAME = "com.shoaib.pokemonandroid"
        internal const val ACTION_FOREGROUND_ONLY_LOCATION_BROADCAST =
            "$PACKAGE_NAME.action.FOREGROUND_ONLY_LOCATION_BROADCAST"

        internal const val EXTRA_LOCATION = "$PACKAGE_NAME.extra.LOCATION"

        private const val EXTRA_CANCEL_LOCATION_TRACKING_FROM_NOTIFICATION =
            "$PACKAGE_NAME.extra.CANCEL_LOCATION_TRACKING_FROM_NOTIFICATION"

        private const val NOTIFICATION_ID = 123

        private const val NOTIFICATION_CHANNEL_ID = "location_Channel_ID_01"


    }


}