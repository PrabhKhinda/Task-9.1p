package com.example.lostfoundapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class AdDetailsFragment : Fragment() {
    private lateinit var advert: Advert

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_ad_details, container, false)

        advert = arguments?.getParcelable("selectedAdvert")!!



        // Display advert details in the corresponding views
        val textViewName = rootView.findViewById<TextView>(R.id.text_view_name)
        textViewName.text = advert.name

        val textViewType = rootView.findViewById<TextView>(R.id.text_view_type)
        textViewType.text = advert.type

        // ... Display values of other columns
        val textViewDescription = rootView.findViewById<TextView>(R.id.text_view_description)
        textViewDescription.text = advert.description

        val textViewDate = rootView.findViewById<TextView>(R.id.text_view_date)
        textViewDate.text = advert.date

        val textViewLocation = rootView.findViewById<TextView>(R.id.text_view_location)
        textViewLocation.text = advert.location

        val textViewPhone = rootView.findViewById<TextView>(R.id.text_view_phone)
        textViewPhone.text = advert.phone

        // Display latitude and longitude
        val textViewLatitude = rootView.findViewById<TextView>(R.id.text_view_latitude)
        textViewLatitude.text = advert.latitude.toString()

        val textViewLongitude = rootView.findViewById<TextView>(R.id.text_view_longitude)
        textViewLongitude.text = advert.longitude.toString()

        // Add click listener to remove button
        val removeButton = rootView.findViewById<Button>(R.id.button_remove)
        removeButton.setOnClickListener {
            removeAdvertFromDatabase()
            goBackToPreviousFragment()

        }

        return rootView
    }

    private fun removeAdvertFromDatabase() {
        val databaseHelper = DatabaseHelper(requireContext())
        databaseHelper.removeAdvert(advert.id.toInt())

    }

    private fun goBackToPreviousFragment() {
        parentFragmentManager.popBackStack()
    }
}

