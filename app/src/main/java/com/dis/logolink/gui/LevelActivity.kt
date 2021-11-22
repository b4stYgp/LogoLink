package com.dis.logolink.gui

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
import android.graphics.Insets

import androidx.annotation.NonNull





class LevelActivity() : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level)
        val field = assets.list("")
        val viewLoader = ViewLoader()
        //load Level with Mapper and Parser
        var regex = """level(\d{1}|\d{2}).yml""".toRegex()
        field!!.forEach {
            if(it.matches(regex)) {
                if (it.contains(intent.extras?.get("levelname").toString().filter { it.isDigit() })){
                    viewLoader.level = LevelMapper().levelMapping(Parser().parse(assets.open(it)) as LevelDto)
                }
            }
        }

        //Divide Screen into Layers
        val layerQty = viewLoader.level.layerList.size + 1 //+InputLayer

        var width = getScreenWidth(this)
        var height = getScreenHeight(this)


        //
        //InputGate ButtonView
        var inputBtnViewList = mutableListOf<ImageButton>()
        viewLoader.level.defaultInputList.forEach(){
            inputBtnViewList.add(viewLoader.createInputView(it as InputGate,this))
        }

        //Component View
        var inputCmpViewList = mutableListOf<MutableList<ImageView>>()
            viewLoader.level.layerList.forEach(){
            inputCmpViewList.add(viewLoader.createLayerView(it, this,)
            )
        }
        var btnCount = 0
        inputBtnViewList.forEach(){
            LevelLayout.addView(it)
            btnCount++
        }
        var layerCount = 0
        inputCmpViewList.forEach(){
            var compCount = 0
            it.forEach(){
                LevelLayout.addView(it)
                compCount++
            }
            layerCount++
        }
    }

    fun getScreenWidth(activity: Activity): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = activity.windowManager.currentWindowMetrics
            val insets: Insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            windowMetrics.bounds.width() - insets.left - insets.right
        } else {
            val displayMetrics = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.widthPixels
        }
    }

    fun getScreenHeight(activity: Activity): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = activity.windowManager.currentWindowMetrics
            val insets: Insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            windowMetrics.bounds.height() - insets.top - insets.bottom
        } else {
            val displayMetrics = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.heightPixels
        }
    }
}







