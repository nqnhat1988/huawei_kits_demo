package com.nhat.icore_services.services

import android.app.Activity
import com.nhat.icore_services.common.LocationData

interface LocationServices {
    fun init(activity: Activity)
    fun requestLocationUpdatesWithCallback(callback: (location: LocationData) -> Unit)
    fun removeLocationUpdatesWithCallback()
    fun requestGeoFenceCallback()
    fun removeWithID()
}