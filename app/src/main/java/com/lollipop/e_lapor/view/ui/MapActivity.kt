package com.lollipop.e_lapor.view.ui

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.lollipop.e_lapor.R
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style

class MapActivity : AppCompatActivity() {

    private var mapView: MapView? = null
    private var mapboxMap: MapboxMap ? = null
    private var permissionsManager: PermissionsManager ? = null
    private var hoveringMarker: ImageView ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Mapbox.getInstance(this, getString(R.string.mapbox_access_token))

        setContentView(R.layout.activity_map)

        mapView = findViewById(R.id.mapView)
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync { mapboxMap ->
            mapboxMap.setStyle(Style.MAPBOX_STREETS) { style ->
                // Map is set up and the style has loaded. Now you can add data or make other map adjustments
                hoveringMarker
            }

        }
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

//    private fun enableLocationPlugin(loadedMapStyle: Style) {
//// Check if permissions are enabled and if not request
//        if (PermissionsManager.areLocationPermissionsGranted(this)) {
//
//// Get an instance of the component. Adding in LocationComponentOptions is also an optional
//// parameter
//            val locationComponent: LocationComponent = mapboxMap?.getLocationComponent() ?:
//            locationComponent.activateLocationComponent(
//                LocationComponentActivationOptions.builder(
//                    this, loadedMapStyle
//                ).build()
//            )
//            locationComponent.isLocationComponentEnabled = true
//
//// Set the component's camera mode
//            locationComponent.cameraMode = CameraMode.TRACKING
//            locationComponent.renderMode = RenderMode.NORMAL
//        } else {
//            permissionsManager = PermissionsManager {
//
//            }
//            permissionsManager!!.requestLocationPermissions(this)
//        }
//    }

}