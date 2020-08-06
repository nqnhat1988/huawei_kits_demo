package com.nhat.huaweikit.demo.gms_services

import android.app.Activity
import android.content.IntentSender
import android.location.Location
import android.os.Looper
import android.util.Log
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient
import com.google.android.gms.location.LocationServices.getSettingsClient
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.nhat.icore_services.common.LocationData
import com.nhat.icore_services.services.LocationServices
import javax.inject.Inject

class GoogleLocationServices @Inject constructor() :
    LocationServices {
    companion object {
        const val TAG = "GoogleLocationServices"
    }

    private lateinit var mLocationCallback: LocationCallback
    private lateinit var activity: Activity

    private var mLocationRequest: LocationRequest = LocationRequest()
    private lateinit var settingsClient: SettingsClient
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    init {
        // set the interval for location updates, in milliseconds.
        mLocationRequest.interval = 10000
        // set the priority of the request
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    override fun init(activity: Activity) {
        this.activity = activity
        settingsClient = getSettingsClient(activity)
        fusedLocationProviderClient = getFusedLocationProviderClient(activity)
    }

    private fun buildLocationCallback(callback: (location: LocationData) -> Unit) =
        object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult?.let {
                    val locations: List<Location> = it.locations
                    if (locations.isNotEmpty()) {
                        for (location in locations) {
                            Log.i(
                                TAG,
                                "onLocationResult location[Longitude,Latitude,Accuracy]:" + location.longitude.toString() + "," + location.latitude.toString() + "," + location.accuracy
                            )
                            callback.invoke(
                                LocationData(
                                    location.latitude,
                                    location.longitude
                                )
                            )
                        }
                    }
                }
            }

            override fun onLocationAvailability(locationAvailability: LocationAvailability?) {
                locationAvailability?.let {
                    val flag = it.isLocationAvailable
                    Log.i(TAG, "onLocationAvailability isLocationAvailable:$flag")
                }
            }
        }


    override fun requestLocationUpdatesWithCallback(callback: (location: LocationData) -> Unit) {
        try {
            mLocationCallback = buildLocationCallback(callback)

            val builder = LocationSettingsRequest.Builder()
            builder.addLocationRequest(mLocationRequest)
            val locationSettingsRequest = builder.build()
            // check devices settings before request location updates.
            settingsClient.checkLocationSettings(locationSettingsRequest)
                .addOnSuccessListener {
                    Log.i(TAG, "check location settings success")
                    //FIXME request location updates
                    fusedLocationProviderClient
                        .requestLocationUpdates(
                            mLocationRequest,
                            mLocationCallback,
                            Looper.getMainLooper()
                        )
                }
                .addOnFailureListener { e ->
                    when ((e as ApiException).statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                            val rae = e as ResolvableApiException
                            rae.startResolutionForResult(activity, 0)
                        } catch (sie: IntentSender.SendIntentException) {
                            Log.e(TAG, "PendingIntent unable to execute request.")
                        }
                    }
                }
        } catch (e: Exception) {
            Log.e(TAG, "requestLocationUpdatesWithCallback exception:" + e.message)
        }
    }

    override fun removeLocationUpdatesWithCallback() {
        try {
            fusedLocationProviderClient.removeLocationUpdates(mLocationCallback)
                .addOnSuccessListener(OnSuccessListener<Void?> {
                    Log.i(
                        TAG,
                        "removeLocationUpdatesWithCallback onSuccess"
                    )
                })
                .addOnFailureListener(OnFailureListener { e ->
                    Log.e(
                        TAG,
                        "removeLocationUpdatesWithCallback onFailure:" + e.message
                    )
                })
        } catch (e: Exception) {
            Log.e(TAG, "removeLocationUpdatesWithCallback exception:" + e.message)
        }
    }

    override fun requestGeoFenceCallback() {
        //DO NOTHING
    }

    override fun removeWithID() {
        //DO NOTHING
    }
}