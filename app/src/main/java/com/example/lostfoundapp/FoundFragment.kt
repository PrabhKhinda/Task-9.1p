package com.example.lostfoundapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment

class FoundFragment : Fragment() {

    private lateinit var adsListView: ListView
    private lateinit var adsList: List<Advert>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_found, container, false)

        adsListView = rootView.findViewById(R.id.ads_list_view)
        adsList = retrieveLostAdsFromDatabase()

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, adsList.map { it.type })
        adsListView.adapter = adapter

        adsListView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selectedAdvert = adsList[position]
            showAdDetailsFragment(selectedAdvert)
        }

        return rootView
    }

    private fun showAdDetailsFragment(advert: Advert) {
        val fragment = AdDetailsFragment()
        val bundle = Bundle()
        bundle.putParcelable("selectedAdvert", advert)
        fragment.arguments = bundle

        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun retrieveLostAdsFromDatabase(): List<Advert> {
        val databaseHelper = DatabaseHelper(requireContext())
        return databaseHelper.getFoundAds()
    }
}
