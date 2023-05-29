package com.example.lostfoundapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import java.io.IOException
import java.util.*

class NewAdvertActivity : AppCompatActivity() {

    private lateinit var advertNameEditText: EditText
    private lateinit var advertDescriptionEditText: EditText
    private lateinit var advertDateEditText: EditText
    private lateinit var advertLocationEditText: EditText
    private lateinit var advertPhoneEditText: EditText
    private lateinit var lostRadioButton: RadioButton
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var latitude: Double? = null
    private var longitude: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_advert)


        advertNameEditText = findViewById(R.id.advertname)
        advertDescriptionEditText = findViewById(R.id.advertdescription)
        advertDateEditText = findViewById(R.id.advertdate)
        advertLocationEditText = findViewById(R.id.advertlocation)
        advertPhoneEditText = findViewById(R.id.advertphone)
        lostRadioButton = findViewById(R.id.lost_rbtn)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Initialize the Places SDK
        Places.initialize(applicationContext, "AIzaSyCSzDX9P48HQw3b-6EqNxOBJ4noHDr30fo")

        // Create a new Places client instance
        val placesClient = Places.createClient(this)

        // Initialize the AutocompleteSupportFragment
        val autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS))

        // Set up the place selection listener
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // Handle the selected place
                val selectedLocation = place.address
                val selectedLatitude = place.latLng?.latitude
                val selectedLongitude = place.latLng?.longitude
                if (latitude == null && longitude == null) {
                    latitude = selectedLatitude
                    longitude = selectedLongitude
                }
                advertLocationEditText.setText(selectedLocation)

            }

            override fun onError(status: com.google.android.gms.common.api.Status) {
                // Handle any errors
                Toast.makeText(this@NewAdvertActivity, "Error: ${status.statusMessage}", Toast.LENGTH_SHORT).show()
            }
        })

        val saveButton = findViewById<Button>(R.id.advertsave_btn)
        saveButton.setOnClickListener {
            val advertType = if (lostRadioButton.isChecked) "Lost" else "Found"
            val advertName = advertNameEditText.text.toString()
            val advertDescription = advertDescriptionEditText.text.toString()
            val advertDate = advertDateEditText.text.toString()
            val advertLocation = advertLocationEditText.text.toString()
            val advertPhone = advertPhoneEditText.text.toString()





            fusedLocationClient.lastLocation
                .addOnSuccessListener(this) { location ->
                    location?.let {
                        if (latitude == null && longitude == null) {
                            latitude = location.latitude
                            longitude = location.longitude
                        }


                        // Store the advert in the database
                        val databaseHelper = DatabaseHelper(this)
                        latitude?.let { it1 ->
                            longitude?.let { it2 ->
                                databaseHelper.insertAdvert(
                                    advertType,
                                    advertName,
                                    advertDescription,
                                    advertDate,
                                    advertLocation,
                                    advertPhone,
                                    it1,
                                    it2
                                )
                            }
                        }
                        Toast.makeText(this, "Advert saved successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

        }


        val locationButton = findViewById<ImageButton>(R.id.location_btn)
        locationButton.setOnClickListener {
            if (checkLocationPermission()) {
                getCurrentLocation()
            } else {
                requestLocationPermission()
            }
        }
    }

    private fun checkLocationPermission(): Boolean {
        val permission = Manifest.permission.ACCESS_FINE_LOCATION
        val result = ContextCompat.checkSelfPermission(this, permission)
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        val permission = Manifest.permission.ACCESS_FINE_LOCATION
        ActivityCompat.requestPermissions(this, arrayOf(permission), LOCATION_PERMISSION_REQUEST_CODE)
    }

    private fun getCurrentLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                latitude = location.latitude
                longitude = location.longitude

                val geocoder = Geocoder(this, Locale.getDefault())
                try {
                    val addresses = geocoder.getFromLocation(latitude!!, longitude!!, 1)
                    if (addresses != null) {
                        if (addresses.isNotEmpty()) {
                            val address = addresses[0]
                            val locationName = address.getAddressLine(0)
                            advertLocationEditText.setText(locationName)
                            Toast.makeText(this, "Location found", Toast.LENGTH_SHORT).show()

                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            } ?: run {
                Toast.makeText(this, "Unable to get current location", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }
}
