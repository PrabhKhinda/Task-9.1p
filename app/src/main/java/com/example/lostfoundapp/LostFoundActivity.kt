package com.example.lostfoundapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class LostFoundActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lost_found)

        val lost_btn: ImageButton = findViewById(R.id.lost_btn)
        val found_btn: ImageButton = findViewById(R.id.found_btn)
        val showmap_btn: Button = findViewById(R.id.showmap_btn)

        val lostfrag: Fragment = LostFragment()
        val foundfrag: Fragment = FoundFragment()
        val mapFragment: Fragment = MapFragment()

        lost_btn.setOnClickListener {
            val fragtrans: FragmentTransaction = supportFragmentManager.beginTransaction()
            fragtrans.replace(R.id.fragmentContainer, lostfrag).commit()
        }

        found_btn.setOnClickListener {
            val fragtrans: FragmentTransaction = supportFragmentManager.beginTransaction()
            fragtrans.replace(R.id.fragmentContainer, foundfrag).commit()
        }

        showmap_btn.setOnClickListener {
            val fragtrans: FragmentTransaction = supportFragmentManager.beginTransaction()
            fragtrans.replace(R.id.fragmentContainer, mapFragment).commit()
        }
    }
}

