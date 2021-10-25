package com.shoaib.currentlocation

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.*
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task

class MainActivity : AppCompatActivity() {

    val TAG = "MainTag"

    val REQUEST_CODE_FINE_LOCATION = 99;
    private lateinit var fusedLocationProvider: FusedLocationProviderClient
    private var googleApiClient: GoogleApiClient? = null
    private val REQUESTLOCATION = 199

    private lateinit var locationRequest: LocationRequest
    private val REQUEST_CHECK_SETTING = 1001


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        askPermissions()
//        requestPermission()

//        findViewById<Button>(R.id.gps_btn).setOnClickListener {
//        checkLocationSetting()
//        }

    }


    private fun checkLocationSetting() {
        locationRequest = LocationRequest.create()
        locationRequest.apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 5000
            fastestInterval = 2000
        }

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)

        val result: Task<LocationSettingsResponse> =
            LocationServices.getSettingsClient(applicationContext)
                .checkLocationSettings(builder.build())

        result.addOnCompleteListener {
            try {
                val response: LocationSettingsResponse = it.getResult(ApiException::class.java)
                Toast.makeText(this@MainActivity, "GPS is On", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "checkSetting: GPS On")
            } catch (e: ApiException) {

                when (e.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        val resolvableApiException = e as ResolvableApiException
                        resolvableApiException.startResolutionForResult(
                            this@MainActivity,
                            REQUEST_CHECK_SETTING
                        )
                        Log.d(TAG, "checkSetting: RESOLUTION_REQUIRED")
                    }

                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        // USER DEVICE DOES NOT HAVE LOCATION OPTION
                    }
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("permtag", "onActivityResult: Activity called")

       supportFragmentManager.fragments.forEach {
           it.onActivityResult(requestCode, resultCode, data)
       }

    }

    private fun enableLoc() {
        googleApiClient = GoogleApiClient.Builder(this)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(object : GoogleApiClient.ConnectionCallbacks {
                override fun onConnected(bundle: Bundle?) {}
                override fun onConnectionSuspended(i: Int) {
                    googleApiClient?.connect()
                }
            })
            .addOnConnectionFailedListener {
            }.build()
        googleApiClient?.connect()
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 30 * 1000.toLong()
        locationRequest.fastestInterval = 5 * 1000.toLong()
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)
        val result: PendingResult<LocationSettingsResult> =
            LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
        result.setResultCallback { result ->
            val status: Status = result.status
            when (status.statusCode) {
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                    status.startResolutionForResult(
                        this@MainActivity,
                        REQUESTLOCATION
                    )
                } catch (e: IntentSender.SendIntentException) {
                }
            }
        }
    }


    private fun askPermissions() {


        if (ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )

        } else {
            //permission is already granted.
            Log.d(TAG, "askPermissions: Permission is already Granted")
            fetchLocation()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_FINE_LOCATION && grantResults.isNotEmpty()) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Permission is Granted
                Log.d(TAG, "onRequestPermissionsResult: Permission Granted")
                fetchLocation()
            } else {

                //show a alert box to tell that permission is necessary to use this application.
                if (!ActivityCompat.shouldShowRequestPermissionRationale(
                        this@MainActivity,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                ) {
                    //This block here means PERMANENTLY DENIED PERMISSION
                    Log.d(TAG, "onRequestPermissionsResult: Permission Denied Permanently")
                    val alertDialog = AlertDialog.Builder(this)
                    alertDialog.apply {
                        setMessage("You have permanently denied this permission, go to settings to enable this permission")
                        setPositiveButton("Go to settings") { dialog, which ->
                            gotoApplicationSettings()
                        }
                        setCancelable(false)
                        setNegativeButton("Cancel", null)
                            .show()
                    }
                } else {
                    Log.d(TAG, "onRequestPermissionsResult: Permission Denied")
                }
            }
        }
    }

    private fun gotoApplicationSettings() {
        val intent = Intent()
        intent.apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = Uri.fromParts("package", this@MainActivity.packageName, null)
            data = uri
            startActivity(intent)
        }
    }


    private fun fetchLocation() {
        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this)


        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProvider.lastLocation.addOnSuccessListener { location: Location? ->

                if (location != null) {
                    Log.d(TAG, "fetchLocation: ${location.latitude}, ${location.longitude}")
                } else {
                    Log.d(TAG, "fetchLocation: Location is null")
                }
            }
        }
    }

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //permission is NOT GRANTED
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                val builder = AlertDialog.Builder(this)

                builder.apply {
                    setMessage("we need permission for fine location")
                    setCancelable(false)
                    setPositiveButton("Ok") { dialog, which ->
                        ActivityCompat.requestPermissions(
                            this@MainActivity,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            REQUEST_CODE_FINE_LOCATION
                        )
                    }
                }
                    .show()
            } else {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_CODE_FINE_LOCATION
                )
            }


        } else {
            //Permission Granted
            fetchLocation()
            Log.d(TAG, "requestPermission: Permission Granted")
        }
    }
}