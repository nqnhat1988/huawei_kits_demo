package com.nhat.huaweikit.demo.huawei.ui.map

import android.Manifest.permission.*
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import com.nhat.huaweikit.demo.huawei.R
import com.nhat.huaweikit.demo.huawei.common.BaseFragment
import com.nhat.huaweikit.demo.nd_services.Constant
import com.nhat.huaweikit.demo.nd_services.LocationData
import com.nhat.huaweikit.demo.nd_services.LocationServices
import com.nhat.huaweikit.demo.nd_services.MapServices
import kotlinx.android.synthetic.main.fragment_map.*
import javax.inject.Inject


class MapFragment : BaseFragment<Void>()
{
    companion object {
        const val TAG = "MapFragment"
    }

    @Inject
    lateinit var locationServices: LocationServices

    @Inject
    lateinit var mapServices: MapServices

    private lateinit var locationCallback: (location: LocationData) -> Unit

    private val RUNTIME_PERMISSIONS = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        arrayOf(
            ACCESS_COARSE_LOCATION,
            ACCESS_FINE_LOCATION,
            INTERNET,
            ACCESS_BACKGROUND_LOCATION
        )
    } else {
        arrayOf(
            ACCESS_COARSE_LOCATION,
            ACCESS_FINE_LOCATION,
            INTERNET
        )
    }

    override val layoutId: Int
        get() = R.layout.fragment_map

    override fun setupScreenForLoadingState() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setupScreenForSuccess(t: Void?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setupScreenForError(message: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locationCallback = {
            text_my_location.text = it.toString()
        }

        locationServices.init(requireActivity())

        if (!hasPermissions(requireContext(), RUNTIME_PERMISSIONS)) {
            requestPermissions(RUNTIME_PERMISSIONS, Constant.REQUEST_MAP_LOCATION_PERMISSION)
        } else {
            locationServices.requestLocationUpdatesWithCallback(locationCallback)
        }

        mapServices.init(map_container, savedInstanceState)

        btn_start_geo_fence.setOnClickListener {
            locationServices.requestGeoFenceCallback()
        }
    }

    private fun hasPermissions(
        context: Context,
        permissions: Array<String>
    ): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constant.REQUEST_MAP_LOCATION_PERMISSION) {
            if (grantResults.all { i -> i == PackageManager.PERMISSION_GRANTED }) {
                locationServices.requestLocationUpdatesWithCallback(locationCallback)
            } else {
                //TODO fix UI problem
            }
        }
    }


    override fun onStart() {
        super.onStart()
        mapServices.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapServices.onStop()
    }

    override fun onDestroy() {
        locationServices.removeLocationUpdatesWithCallback()
        locationServices.removeWithID()
        mapServices.onDestroy()
        super.onDestroy()
    }

    override fun onPause() {
        mapServices.onPause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapServices.onResume()
    }
}