package com.dis.logolink.gui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnContinue = findViewById<Button>(R.id.btn_continue)
        val btnLevels = findViewById<Button>(R.id.btn_levels)
        val btnProfiles = findViewById<Button>(R.id.btn_profiles)
        val btnSettings = findViewById<Button>(R.id.btn_settings)

        //Settings listener
        btnSettings.setOnClickListener{
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Clicked", Toast.LENGTH_LONG).show()
        }

        //Levels listener
        btnLevels.setOnClickListener{
            val intent = Intent(this, LevelsActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Clicked 'Levels'", Toast.LENGTH_LONG).show()
        }
    }
}