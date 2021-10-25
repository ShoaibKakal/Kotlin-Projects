package com.shoaib.pokemonandroid

import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import java.util.concurrent.TimeUnit

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val CODEACCESSLOCATION = 1
    private val REQUEST_CHECK_SETTINGS = 2
    private lateinit var mCurrentLocation: Location
    val TAG = "hello"
    private val ACTION_FOREGROUND_ONLY_LOCATION_BROADCAST = "1"


    //FusedLocationProviderClient - Main class for receiving location updates.
    private var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

    //LocationRequest - Requirements for the location updates, i.e., how often you receive updates, the priority, etc
    private lateinit var locationRequest: LocationRequest

    // LocationCallBack - called when FusedLocationProviderClient has a new Location
    private lateinit var locationCallback: LocationCallback


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        checkPermission()
        fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location ->
                    mCurrentLocation = location
                    makeToast("${location.latitude}  ${location.longitude}")
                    val latLng = LatLng(location.latitude, location.longitude)
                    mMap.addMarker(MarkerOptions()
                            .position(latLng)
                            .title("Shoaib")
                            .snippet(" Here is my location")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.mario)))
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f))
                }
                .addOnFailureListener()
                {
                    makeToast(it.toString())

                }

        locationCallback = object : LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                mCurrentLocation = locationResult.lastLocation



                for (location in locationResult.locations)
                {
                    mCurrentLocation = location
                }
            }
        }
    } //onCreate ends



    private fun createLocationRequest() {
        /*** Sets the desired interval for active location updates. This interval is inexact. You
        may not receive updates at all if no location sources are available, or you may
        receive them less frequently than requested. You may also receive updates more
        frequently than requested if other applications are requesting location at a more
        frequent interval.

         */

        val locationRequest = LocationRequest.create().apply {

            // IMPORTANT NOTE: Apps running on Android 8.0 and higher devices (regardless of
            // targetSdkVersion) may receive updates less frequently than this interval when the app
            // is no longer in the foreground
            interval = TimeUnit.SECONDS.toMillis(60)

            // Sets the fastest rate for active location updates. This interval is exact, and your
            // application will never receive updates more frequently than this value.
            fastestInterval = TimeUnit.SECONDS.toMillis(30)

            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)

        val client = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener { locationSettingResponse ->

            /*
              All location settings are satisfied. The client can initialize location requests here
             */
        }

        task.addOnFailureListener() {
            if (it is ResolvableApiException) {
                /*
                    location settings are not satisfied, but this can be fixed by showing the user a dialog
                 */
                it.startResolutionForResult(this, REQUEST_CHECK_SETTINGS)
                try {

                } catch (sendEx: IntentSender.SendIntentException) {
                    //ignore the error

                }
            }
        }


    }

    private fun startLocationUpdates() {
    }


    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), CODEACCESSLOCATION)
                return
            }
        }

        getUserLocation()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CODEACCESSLOCATION -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    getUserLocation()
                else
                    makeToast("We Cannot access to your location")
            }
        }
    }

    private fun getUserLocation() {
        makeToast("Location Access Successfull")
        Log.d(TAG, "getUserLocation: Location Access")


    }


    private fun makeToast(str: String) {
        return Toast.makeText(this, str, Toast.LENGTH_LONG).show()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera


    }


}