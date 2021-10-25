package com.shoaib.peepsaround.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.*
import com.shoaib.peepsaround.R
import com.shoaib.peepsaround.model.CurrentLocation
import com.shoaib.peepsaround.model.User
import java.util.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StarterFragment : Fragment() {

    // FusedLocationProviderClient - Main class for receiving location updates.
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    // LocationRequest - Requirements for the location updates, i.e., how often you
    // should receive updates, the priority, etc.
    private lateinit var locationRequest: LocationRequest

    // LocationCallback - Called when FusedLocationProviderClient has a new Location.
    private lateinit var locationCallback: LocationCallback

    // last location to create a Notification if the user navigates away from the app.


    private lateinit var et_username: TextInputEditText
    private lateinit var cb_Sports: CheckBox
    private lateinit var cb_Gaming: CheckBox
    private lateinit var cb_Coding: CheckBox
    private lateinit var cb_Food: CheckBox
    private lateinit var cb_Movies: CheckBox
    private lateinit var cb_Travel: CheckBox

    private lateinit var btn_Go: Button

    private lateinit var mDatabase: FirebaseDatabase
    private lateinit var mRef: DatabaseReference
    private lateinit var valueEventListener: ChildEventListener

    var locationPermission = false
    lateinit var currentLocation: CurrentLocation


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mDatabase = FirebaseDatabase.getInstance() //database reference
        mRef = mDatabase.getReference("users") //database root node reference


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_starter, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        et_username = view.findViewById(R.id.et_username)
        cb_Coding = view.findViewById(R.id.cb_Coding)
        cb_Food = view.findViewById(R.id.cb_Food)
        cb_Gaming = view.findViewById(R.id.cb_Gaming)
        cb_Movies = view.findViewById(R.id.cb_Movies)
        cb_Sports = view.findViewById(R.id.cb_Sports)
        cb_Travel = view.findViewById(R.id.cb_Travel)

        btn_Go = view.findViewById(R.id.btn_go)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())


        btn_Go.setOnClickListener {

            if (locationPermission) {
                goBtn()
                mRef.addChildEventListener(valueEventListener)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Allow Locations to procceed further",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        requestPermission()







        valueEventListener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                val user: HashMap<String, User> = snapshot.value as HashMap<String, User>
//                Log.d(TAG, "onChildAdded: ${user.get("username")}")
//                Log.d(TAG, "onChildAdded: ${user.get("interests")}")
//                Log.d(TAG, "onChildAdded: ${user.get("distance")}")

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "onActivityResult: called")
        when(requestCode)
        {
            REQUEST_CHECK_SETTING ->{
                when(resultCode){
                    Activity.RESULT_OK->{
                        Toast.makeText(requireContext(), "GPS is Turned on result", Toast.LENGTH_LONG).show()
                        fetchLocation()

                    }
                    Activity.RESULT_CANCELED ->{
                        //show a dialog: GPS is required to use this application [same as tinder]
                        Toast.makeText(requireContext(), "GPS is Required to use this app", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun goBtn() {

        val username = et_username.text.toString()
        val interests = ArrayList<String>()



        if (cb_Travel.isChecked) {
            interests.add("Travel")
        } else
            interests.remove("Travel")

        if (cb_Sports.isChecked)
            interests.add("Sports")
        else
            interests.remove("Sports")

        if (cb_Movies.isChecked)
            interests.add("Movies")
        else
            interests.remove("Movies")

        if (cb_Gaming.isChecked)
            interests.add("Gaming")
        else
            interests.remove("Gaming")

        if (cb_Food.isChecked)
            interests.add("Food")
        else
            interests.remove("Food")

        if (cb_Coding.isChecked)
            interests.add("Coding")
        else
            interests.remove("Coding")


//         val location: Location? = fetchLocation()


        val user = User(username, interests, currentLocation)
        mRef.child(username).setValue(user)

        Log.d(TAG, "goBtn: location is: ${currentLocation.longitude}")
        Toast.makeText(context, "Data Inserted...", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_starterFragment_to_homeFragment)
    }


    private fun fetchLocation() {

        Log.d(TAG, "fetchLocation: callled")
        //FusedLocationProviderClient




        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->

            if (location != null) {
                currentLocation = CurrentLocation(location.latitude, location.longitude)
                Log.d(TAG, "fetchLocation location: ${location.longitude}")
                Log.d(TAG, "fetchLocation currentLocation: ${currentLocation.longitude}")
            } else {
                Log.d(TAG, "fetchLocation: Unable to fetch location. Try again")
                Toast.makeText(context, "Unable to fetch location. Try again", Toast.LENGTH_SHORT)
                    .show()
                fetchLocation()

            }


        }
            .addOnFailureListener {
                Log.d(TAG, "fetchLocation: ${it.message}")
            }
        //LocationRequest
//        locationRequest = LocationRequest.create().apply {
//            interval = TimeUnit.SECONDS.toMillis(60)
//            fastestInterval = TimeUnit.SECONDS.toMillis(30)
//            maxWaitTime = TimeUnit.MINUTES.toMillis(2)
//            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//        }

        //LocationCallback
//        locationCallback = object : LocationCallback(){
//            override fun onLocationResult(locationResult: LocationResult) {
//                super.onLocationResult(locationResult)
//
//                //Todo: save it to database
//                currentLocation = locationResult.lastLocation
//
//                Log.d(TAG, "onLocationResult: ${currentLocation.toString()}")
//
////                val intent = Intent(ACTION_FOREGROUND_ONLY_LOCATION_BROADCAST)
////                intent.pu
//
//
//            }
//        }


//        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())

    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
            locationCallback,
            Looper.getMainLooper())
    }

    private fun requestPermission() {
        Log.d(TAG, "requestPermission: Called")
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //permission is NOT GRANTED
            Log.d(TAG, "requestPermission: Not Granted")
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                val builder = AlertDialog.Builder(requireContext())

                builder.apply {
                    setMessage("we need permission for fine location")
                    setCancelable(false)
                    setPositiveButton("Ok") { _, _ ->
                        ActivityCompat.requestPermissions(
                            requireActivity(),
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            REQUEST_CODE_FINE_LOCATION
                        )
                    }
                }
                    .show()
            } else {
                Log.d(TAG, "requestPermission: Ask Perm")
                this.requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_CODE_FINE_LOCATION
                )
            }
        } else {
            Log.d(TAG, "requestPermission: Permission is already Granted")
            locationPermission = true
            checkLocationSetting()

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Log.d(TAG, "onRequestPermissionResult Called ")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_FINE_LOCATION && grantResults.isNotEmpty()) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Permission is Granted
                locationPermission = true
                checkLocationSetting()
                Log.d(TAG, "onRequestPermissionsResult: Permission Granted")

            } else {

                //show a alert box to tell that permission is necessary to use this application.
                if (!ActivityCompat.shouldShowRequestPermissionRationale(
                        requireActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                ) {
                    //This block here means PERMANENTLY DENIED PERMISSION
                    Log.d(TAG, "onRequestPermissionsResult: Permission Denied Permanently")
                    val alertDialog = AlertDialog.Builder(requireContext())
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

    /*
     *  once user permanently denied permission then we will head user to goto settings and turn on permission.
    */
    private fun gotoApplicationSettings() {
        val intent = Intent()
        intent.apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = Uri.fromParts("package", requireActivity().packageName, null)
            data = uri
            startActivity(intent)
        }
    }

    private fun checkLocationSetting()
    {

        locationRequest = LocationRequest.create()
        locationRequest.apply {
            priority=LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 5000
            fastestInterval = 2000
        }

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)


        val result: Task<LocationSettingsResponse> = LocationServices.getSettingsClient(requireContext())
            .checkLocationSettings(builder.build())

        result.addOnCompleteListener {
            try{
                val response: LocationSettingsResponse = it.getResult(ApiException::class.java)
                Toast.makeText(requireContext(), "GPS is On", Toast.LENGTH_LONG).show()
                fetchLocation()
            }catch(e: ApiException){

                when(e.statusCode){
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->{
                        val resolvableApiException = e as ResolvableApiException
                        startIntentSenderForResult(resolvableApiException.resolution.intentSender, REQUEST_CHECK_SETTING, null, 0, 0,0,null)

//                        below line is not useful here, we can use it in activity.
//                        resolvableApiException.startResolutionForResult(requireActivity(), REQUEST_CHECK_SETTING)
                        Log.d(TAG, "checkSetting: RESOLUTION_REQUIRED")
                    }

                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        // USER DEVICE DOES NOT HAVE LOCATION OPTION
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mRef.removeEventListener(valueEventListener)
    }

    companion object {
        const val REQUEST_CODE_FINE_LOCATION = 1
        const val REQUEST_CHECK_SETTING = 2
        const val TAG = "TagFrag"
    }

}