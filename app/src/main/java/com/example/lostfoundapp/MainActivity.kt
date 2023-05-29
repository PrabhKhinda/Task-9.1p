package com.example.lostfoundapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val advert_btn:Button=findViewById(R.id.advert_btn)
        val lostfound_btn:Button=findViewById(R.id.lostfound_btn)

        advert_btn.setOnClickListener{
            val intent = Intent(this, NewAdvertActivity::class.java)

            // Start the new activity using the intent
            startActivity(intent)
        }

        lostfound_btn.setOnClickListener{
            val intent = Intent(this, LostFoundActivity::class.java)

            // Start the new activity using the intent
            startActivity(intent)
        }
    }
}