package com.dis.logolink.gui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Onclick listeners
        btn_levels.setOnClickListener(this)
        btn_settings.setOnClickListener(this)
    }

    //On click listener override
    override fun onClick(view: View?): Unit {
        when(view?.id){
            R.id.btn_continue ->{}
            R.id.btn_levels ->{startActivity(Intent(view.context, LevelsActivity::class.java))}
            R.id.btn_settings ->{startActivity(Intent(view.context, SettingsActivity::class.java))}
        }
    }
}