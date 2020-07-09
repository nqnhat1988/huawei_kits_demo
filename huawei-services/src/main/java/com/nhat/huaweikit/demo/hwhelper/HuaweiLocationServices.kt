package com.nhat.huaweikit.demo.hwhelper

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentSender
import android.location.Location
import android.os.Looper
import android.util.Log
import com.huawei.hmf.tasks.OnFailureListener
import com.huawei.hmf.tasks.OnSuccessListener
import com.huawei.hms.common.ApiException
import com.huawei.hms.common.ResolvableApiException
import com.huawei.hms.location.*
import com.nhat.huaweikit.demo.hwhelper.common.GeoFenceBroadcastReceiver
import com.nhat.huaweikit.demo.nd_services.LocationData
import com.nhat.huaweikit.demo.nd_services.LocationServices
import java.util.concurrent.TimeUnit


class HuaweiLocationServices :
    LocationServices {
    companion object {
        const val TAG = "HuaweiLocationServices"
    }

    private lateinit var mLocationCallback: LocationCallback
    private lateinit var activity: Activity

    private var mLocationRequest: LocationRequest = LocationRequest()
    private lateinit var settingsClient: SettingsClient
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var geoFenceService: GeofenceService
    private lateinit var idList: ArrayList<String>
    private lateinit var geoFenceList: ArrayList<Geofence>
    private lateinit var pendingIntent: PendingIntent

    init {
        // set the interval for location updates, in milliseconds.
        mLocationRequest.interval = 10000
        // set the priority of the request
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    override fun init(activity: Activity) {
        this.activity = activity
        settingsClient =
            com.huawei.hms.location.LocationServices.getSettingsClient(activity)
        fusedLocationProviderClient =
            com.huawei.hms.location.LocationServices.getFusedLocationProviderClient(activity)

        geoFenceService = com.huawei.hms.location.LocationServices.getGeofenceService(activity)

        geoFenceList = ArrayList()

        idList = ArrayList()

        geoFenceList.add(
            Geofence.Builder()
                .setUniqueId("mGeofence")
                .setValidContinueTime(TimeUnit.MINUTES.toMillis(10))
                .setRoundArea(
                    10.782593,
                    106.701176,
                    100f //meters
                ) // Trigger callback when a user enters or leaves the geofence.
                .setDwellDelayTime(TimeUnit.SECONDS.toMillis(5).toInt())
                .setConversions(
                    Geofence.ENTER_GEOFENCE_CONVERSION
                            or Geofence.EXIT_GEOFENCE_CONVERSION
                            or Geofence.DWELL_GEOFENCE_CONVERSION
                )
                .build()
        )
        idList.add("mGeofence")

        pendingIntent = getPendingIntent(activity)
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
                            callback.invoke(LocationData(location.latitude, location.longitude))
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


    private fun getAddGeoFenceRequest(): GeofenceRequest? {
        val builder = GeofenceRequest.Builder()
        // Trigger callback immediately after a geofence is added if a user is already in the geofence.
        builder.setInitConversions(GeofenceRequest.ENTER_INIT_CONVERSION or GeofenceRequest.DWELL_INIT_CONVERSION or GeofenceRequest.EXIT_INIT_CONVERSION)
        builder.createGeofenceList(geoFenceList)
        return builder.build()
    }

    private fun getPendingIntent(activity: Activity): PendingIntent {
        // The GeoFenceBroadcastReceiver class is a customized static broadcast class. For details about the implementation, please refer to the sample code.
        val intent = Intent(activity, GeoFenceBroadcastReceiver::class.java)
        intent.action = GeoFenceBroadcastReceiver.ACTION_PROCESS_LOCATION
        return PendingIntent.getBroadcast(activity, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
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
                    // request location updates
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
        geoFenceService.createGeofenceList(getAddGeoFenceRequest(), pendingIntent)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.i(TAG, "add geofence success!");
                } else {
                    Log.w(TAG, "add geofence failed : " + task.exception.localizedMessage)
                }
            }
    }

    override fun removeWithID() {
        geoFenceService.deleteGeofenceList(idList)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.i(TAG, "delete geofence with ID success!")
                } else {
                    Log.w(TAG, "delete geofence with ID failed ")
                }
            }
    }
}