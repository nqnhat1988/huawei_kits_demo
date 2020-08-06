package com.nhat.huaweikit.demo.huawei_services

import android.os.Bundle
import android.view.ViewGroup
import com.huawei.hms.maps.HuaweiMap.MAP_TYPE_NORMAL
import com.huawei.hms.maps.HuaweiMapOptions
import com.huawei.hms.maps.MapView
import com.nhat.icore_services.services.MapServices
import javax.inject.Inject

class HuaweiMapServices @Inject constructor() :
    MapServices {
    override val mapBundleId: String
        get() = "MapViewBundleKey"
    private lateinit var mapView: MapView
    override fun init(container: ViewGroup, savedInstanceState: Bundle?) {
        this.mapView = MapView(container.context, HuaweiMapOptions().apply {
            compassEnabled(true)
            zoomControlsEnabled(true)
            mapType(MAP_TYPE_NORMAL)
        }).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }

        container.addView(this.mapView)

        this.mapView.let {
            var mapViewBundle: Bundle? = null
            if (savedInstanceState != null) {
                mapViewBundle = savedInstanceState.getBundle(mapBundleId)
            }
            it.onCreate(mapViewBundle)
            //get map instance
            it.getMapAsync { huaweiMap ->
                //FIXME enhance here
                huaweiMap.isMyLocationEnabled = true
            }
        }
    }

    override fun onStart() {
        mapView.onStart()
    }

    override fun onStop() {
        mapView.onStop()
    }

    override fun onResume() {
        mapView.onResume()
    }

    override fun onPause() {
        mapView.onPause()
    }

    override fun onDestroy() {
        mapView.onDestroy()
    }
}