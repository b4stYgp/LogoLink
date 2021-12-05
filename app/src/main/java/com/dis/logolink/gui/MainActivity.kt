package com.dis.logolink.gui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import kotlin.math.max
import kotlin.reflect.typeOf

//import com.dis.logolink.level.Level1Activity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        //Onclick listeners
        btn_continue.setOnClickListener(this)
        btn_levels.setOnClickListener(this)
        btn_settings.setOnClickListener(this)

        setMaxLevel()
    }

    //On click listener override
    override fun onClick(view: View?): Unit {
        when(view?.id){
            R.id.btn_continue ->{
                val sp = getSharedPreferences("levelPref", MODE_PRIVATE)
                val maxLevel = sp.getInt("highestLevelReached", 0)
                if(maxLevel != 0) {
                    val nextLevel = maxLevel + 1
                    val nextLevelString = "Level " + nextLevel.toString()
                    startActivity(Intent(view.context, LevelActivity::class.java)
                        .putExtra("levelname", nextLevelString))
                } else{
                    startActivity(Intent(view.context, LevelActivity::class.java)
                        .putExtra("levelname", "Level 1"))
                }
            }
            R.id.btn_levels ->{startActivity(Intent(view.context, LevelsActivity::class.java))}
            R.id.btn_settings ->{startActivity(Intent(view.context, SettingsActivity::class.java))}
        }
    }

    //Checks and sets the highest level possible in order to prevent accessing non-existing levels
    private fun setMaxLevel(){
        //Set highest level accessible
        val field = assets.list("")
        val regex = """level(\d{1}|\d{2}).yml""".toRegex()
        val allLevels = mutableListOf<String>()
        var cnt = 0

        //Get last level number
        field!!.forEach {
            if(it.matches(regex)){
                val nmbr = it.substring(
                    it.indexOfFirst { it -> it.isDigit() },
                    it.indexOfLast { it -> it.isDigit() } + 1
                )
                allLevels.add(cnt, nmbr)
                cnt++
            }
        }
        var highestLevelFound = 0
        allLevels.forEach {
            if(highestLevelFound < it.toInt()){
                highestLevelFound = it.toInt()
            }
        }

        //Update "maxLevel"
        val sp = getSharedPreferences("levelPref", MODE_PRIVATE)
        val spe = sp.edit()
        spe.apply{
            putInt("maxLevel", highestLevelFound)
            apply()
        }
    }
}