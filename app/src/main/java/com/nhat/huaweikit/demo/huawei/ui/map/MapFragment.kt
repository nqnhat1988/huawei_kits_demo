package com.nhat.huaweikit.demo.huawei.ui.map

import android.Manifest
import android.content.Context
import android.content.IntentSender.SendIntentException
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.huawei.hmf.tasks.OnFailureListener
import com.huawei.hmf.tasks.OnSuccessListener
import com.huawei.hms.common.ApiException
import com.huawei.hms.common.ResolvableApiException
import com.huawei.hms.location.*
import com.huawei.hms.maps.CameraUpdate
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.OnMapReadyCallback
import com.huawei.hms.maps.model.CameraUpdateParam
import com.huawei.hms.maps.model.LatLng
import com.nhat.huaweikit.demo.huawei.R
import kotlinx.android.synthetic.main.fragment_map.*


class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mLocationRequest: LocationRequest
    private lateinit var mLocationCallback: LocationCallback
    private lateinit var settingsClient: SettingsClient
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var mapViewModel: MapViewModel

    private val TAG = "MapViewDemoActivity"
    //Huawei map
    private var hMap: HuaweiMap? = null

    private val RUNTIME_PERMISSIONS = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.INTERNET
    )

    private val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mapViewModel =
            ViewModelProvider(this).get(MapViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_map, container, false)
        val textView: TextView = root.findViewById(R.id.text_my_location)
        mapViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!hasPermissions(requireContext(), RUNTIME_PERMISSIONS)) {
            ActivityCompat.requestPermissions(requireActivity(), RUNTIME_PERMISSIONS, 1)
        }

        //get mapview instance
        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY)
        }
        mapView.onCreate(mapViewBundle)
        //get map instance
        mapView.getMapAsync(this)

        //create a fusedLocationProviderClient
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        //create a settingsClient
        settingsClient = LocationServices.getSettingsClient(requireActivity())

        mLocationRequest = LocationRequest()
        // set the interval for location updates, in milliseconds.
        mLocationRequest.interval = 10000
        // set the priority of the request
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult?.let {
                    val locations: List<Location> = it.locations
                    if (locations.isNotEmpty()) {
                        for (location in locations) {
                            Log.i(
                                TAG,
                                "onLocationResult location[Longitude,Latitude,Accuracy]:" + location.longitude.toString() + "," + location.latitude.toString() + "," + location.accuracy
                            )
                            text_my_location.text =
                                "onLocationResult location[Longitude,Latitude,Accuracy]: ${location.longitude} , ${location.latitude}, ${location.accuracy}"
                            hMap?.moveCamera(CameraUpdate(CameraUpdateParam().apply {
                                newLatLngZoom = CameraUpdateParam.NewLatLngZoom(
                                    LatLng(
                                        location.latitude,
                                        location.longitude
                                    ), 12f
                                )
                            }))
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

        requestLocationUpdatesWithCallback()
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

    override fun onMapReady(map: HuaweiMap?) {
        Log.d(TAG, "onMapReady: ");
        hMap = map
        hMap?.isMyLocationEnabled = true
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        removeLocationUpdatesWithCallback()
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onPause() {
        mapView.onPause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    /**
     * function：Requests location updates with a callback on the specified Looper thread.
     * first：use SettingsClient object to call checkLocationSettings(LocationSettingsRequest locationSettingsRequest) method to check device settings.
     * second： use  FusedLocationProviderClient object to call requestLocationUpdates (LocationRequest request, LocationCallback callback, Looper looper) method.
     */
    private fun requestLocationUpdatesWithCallback() {
        try {
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
                            rae.startResolutionForResult(requireActivity(), 0)
                        } catch (sie: SendIntentException) {
                            Log.e(TAG, "PendingIntent unable to execute request.")
                        }
                    }
                }
        } catch (e: Exception) {
            Log.e(TAG, "requestLocationUpdatesWithCallback exception:" + e.message)
        }
    }

    /**
     * remove the request with callback
     */
    private fun removeLocationUpdatesWithCallback() {
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
}
