package com.dis.logolink.gui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.view.View
import com.dis.logolink.gui.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        //Onclick listeners
        binding.btnLevels.setOnClickListener(this)
        binding.btnSettings.setOnClickListener(this)
    }

    //On click listener override
    override fun onClick(view: View?){
        when(view?.id){
            R.id.btn_continue ->{}
            R.id.btn_levels ->{startActivity(Intent(view.context, LevelsActivity::class.java))}
            R.id.btn_settings ->{startActivity(Intent(view.context, SettingsActivity::class.java))}
        }
    }
}