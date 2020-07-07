package com.nhat.huaweikit.demo.nd_services

import android.app.Activity

interface LocationServices {
    fun init(activity: Activity)
    fun requestLocationUpdatesWithCallback(callback: (location: LocationData) -> Unit)
    fun removeLocationUpdatesWithCallback()
    fun requestGeoFenceCallback()
    fun removeWithID()
}

data class LocationData(val latitude: Double, val longitude: Double)