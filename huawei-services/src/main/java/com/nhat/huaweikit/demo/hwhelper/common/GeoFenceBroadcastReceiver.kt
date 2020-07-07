package com.nhat.huaweikit.demo.hwhelper.common

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.huawei.hms.location.GeofenceData
import com.nhat.huaweikit.demo.hwhelper.HuaweiLocationServices

class GeoFenceBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null) {
            val action = intent.action
            val sb = StringBuilder()
            val next = "\n"
            if (ACTION_PROCESS_LOCATION == action) {
                val geofenceData = GeofenceData.getDataFromIntent(intent)
                if (geofenceData != null) {
                    val errorCode = geofenceData.errorCode
                    val conversion = geofenceData.conversion
                    val list =
                        geofenceData.convertingGeofenceList
                    val mLocation =
                        geofenceData.convertingLocation
                    val status = geofenceData.isSuccess
                    sb.append("GeoFence is triggered$next")
                    sb.append("errorCode: $errorCode$next")
                    sb.append("conversion: ${getString(conversion)}$next")
                    for (i in list.indices) {
                        sb.append("geoFence id :" + list[i].uniqueId.toString() + next)
                    }
                    sb.append("location is :" + mLocation.longitude + " " + mLocation.latitude + next)
                    sb.append("is successful :$status")
                    Log.i(HuaweiLocationServices.TAG, sb.toString())
                    context?.let {
                        val ui = when (conversion) {
                            1 -> "You entered the location${next}location is : ${mLocation.longitude}  ${mLocation.latitude}"
                            2 -> "You left or is outside the location${next}location is : ${mLocation.longitude}  ${mLocation.latitude}"
                            4 -> "You are staying the location for 5 seconds${next}location is : ${mLocation.longitude}  ${mLocation.latitude}"
                            else -> "I don't know where you are, there must be something wrong"
                        }
                        Toast.makeText(context, ui, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun getString(status: Int): String {
        return when (status) {
            1 -> "ENTER_GEOFENCE_CONVERSION"
            2 -> "EXIT_GEOFENCE_CONVERSION"
            4 -> "DWELL_GEOFENCE_CONVERSION"
            else -> "UNDEFINED"
        }
    }

    companion object {
        const val ACTION_PROCESS_LOCATION =
            "com.nhat.huaweikit.demo.hwhelper.common.GeoFenceBroadcastReceiver.ACTION_PROCESS_LOCATION"
    }
}