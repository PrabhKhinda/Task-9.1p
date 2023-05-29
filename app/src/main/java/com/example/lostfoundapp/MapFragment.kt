package com.example.lostfoundapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var googleMap: GoogleMap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_map, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
        return rootView
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        // Customize the map as needed
        // Enable zoom controls
        googleMap.uiSettings.isZoomControlsEnabled = true

        // Mark lost and found adverts on the map
        val databaseHelper = DatabaseHelper(requireContext())
        val lostAds = databaseHelper.getLostAds()
        val foundAds = databaseHelper.getFoundAds()
        var latLng: LatLng? = null

        for (ad in lostAds) {
            latLng = LatLng(ad.latitude, ad.longitude)
            googleMap.addMarker(MarkerOptions().position(latLng).title("Lost: ${ad.type}"))
        }

        for (ad in foundAds) {
            latLng = LatLng(ad.latitude, ad.longitude)
            googleMap.addMarker(MarkerOptions().position(latLng).title("Found: ${ad.type}"))
        }


            // Move the camera to a suitable position
        // You can customize the camera position and zoom level based on your requirements
        val defaultLatLng = LatLng(37.7749, -122.4194) // Example: San Francisco coordinates
        latLng?.let { CameraUpdateFactory.newLatLngZoom(it, 9f) }?.let { googleMap.moveCamera(it) }
    }
}
