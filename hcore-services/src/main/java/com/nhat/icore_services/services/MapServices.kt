package com.nhat.icore_services.services

import android.os.Bundle
import android.view.ViewGroup

interface MapServices {
    val mapBundleId: String
    fun init(container: ViewGroup, savedInstanceState: Bundle?)
    fun onStart()
    fun onStop()
    fun onResume()
    fun onPause()
    fun onDestroy()
}