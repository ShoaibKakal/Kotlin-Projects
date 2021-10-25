package com.shoaib.currentlocation

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task


/**
 * A simple [Fragment] subclass.
 * Use the [PermissionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PermissionFragment : Fragment() {


    private lateinit var locationRequest: LocationRequest
    private val REQUEST_CHECK_SETTING = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_permission, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        requestPermission()

            checkLocationSetting()


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
                Toast.makeText(requireContext(), "GPS is Onnnn", Toast.LENGTH_LONG).show()
                Log.d(TAG, "checkSetting: GPS Onnnnn")
            }catch(e: ApiException){

                when(e.statusCode){
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->{
                        val resolvableApiException = e as ResolvableApiException
                        startIntentSenderForResult(resolvableApiException.resolution.intentSender, REQUEST_CHECK_SETTING, null, 0, 0,0,null)
//                        resolvableApiException.sta rtResolutionForResult(requireActivity(), REQUEST_CHECK_SETTING)

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
        Log.d(TAG, "onActivityResult: called in frag")
        when(requestCode)

        {
            REQUEST_CHECK_SETTING ->{
                when(resultCode){
                    Activity.RESULT_OK->{
                        Toast.makeText(requireContext(), "GPS is Turned on result", Toast.LENGTH_LONG).show()
                        Log.d(TAG, "onActivityResult: GPS result")
                    }
                    Activity.RESULT_CANCELED ->{
                        Toast.makeText(requireContext(), "GPS is Required to use this app", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }



    private fun requestPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //permission is NOT GRANTED
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                val builder = AlertDialog.Builder(requireContext())

                builder.apply {
                    setMessage("we need permission for fine location")
                    setCancelable(false)
                    setPositiveButton("Ok") { dialog, which ->
                        ActivityCompat.requestPermissions(
                            requireActivity(),
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            REQUEST_CODE_FINE_LOCATION
                        )
                    }
                }
                    .show()
            } else {
                this.requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_CODE_FINE_LOCATION
                )
            }
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
                Log.d(TAG, "onRequestPermissionsResult: Permission Granted")
//                fetchLocation()
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
    companion object {

        const val REQUEST_CODE_FINE_LOCATION = 1
        const val TAG = "permTag"
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PermissionFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}