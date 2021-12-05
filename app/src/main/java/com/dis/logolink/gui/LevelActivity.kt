package com.dis.logolink.gui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

import com.dis.logolink.editor.ViewLoader
import com.dis.logolink.editor.models.InputGate
import com.dis.logolink.parser.LevelDto
import com.dis.logolink.parser.LevelMapper
import com.dis.logolink.parser.Parser
import kotlinx.android.synthetic.main.activity_level.*
import android.view.WindowInsets

import android.view.WindowMetrics

import android.os.Build

import android.app.Activity
import android.content.pm.ActivityInfo
import android.graphics.Insets
import android.graphics.drawable.GradientDrawable
import android.media.Image
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout

import androidx.annotation.NonNull
import androidx.appcompat.widget.LinearLayoutCompat
import kotlinx.android.synthetic.main.popup_layout_levelcomplete.*


class LevelActivity() : AppCompatActivity() {
lateinit var viewLoader: ViewLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val field = assets.list("")
        viewLoader = ViewLoader(this,this)
        //load Level with Mapper and Parser
        val regex = """level(\d{1}|\d{2}).yml""".toRegex()
        field!!.forEach ca@{
            if(it.matches(regex)) {
                if (it.equals(intent.extras?.get("levelname").toString()
                        .lowercase()
                        .replace(" ","")
                        .plus(".yml")
                        )){
                    viewLoader.level = LevelMapper().levelMapping(Parser().parse(assets.open(it))!!)
                    return@ca
                }
            }
        }
        viewLoader.mapLevelToView()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        viewLoader.drawLines()
    }
}












