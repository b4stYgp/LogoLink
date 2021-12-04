package com.dis.logolink.gui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
//import com.dis.logolink.level.Level1Activity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    //val sp = this.getPreferences(Context.MODE_PRIVATE)

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
            R.id.btn_continue ->{
//                val maxLevel = resources.getInteger(R.integer.maxLevel)
//                if(maxLevel != null){
//                    var nextLevel = maxLevel
//                    nextLevel++
//                    startActivity(Intent(view.context, LevelActivity::class.java)
//                        .putExtra("levelname", nextLevel))
//                }
            }
            R.id.btn_levels ->{startActivity(Intent(view.context, LevelsActivity::class.java))}
            R.id.btn_settings ->{startActivity(Intent(view.context, SettingsActivity::class.java))}
        }
    }
}