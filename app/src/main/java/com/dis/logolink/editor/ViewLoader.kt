package com.dis.logolink.editor

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Insets
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.WindowInsets
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.Guideline
import androidx.core.view.forEach
import com.dis.logolink.editor.models.Component

import com.dis.logolink.gui.R
import com.dis.logolink.editor.models.InputGate
import com.dis.logolink.editor.models.Layer
import com.dis.logolink.editor.models.Level
import com.dis.logolink.gui.LevelActivity
import kotlinx.android.synthetic.main.activity_level.*
import java.text.DecimalFormat

class ViewLoader(val activity: Activity,val context: Context) {
    lateinit var level: Level
    lateinit private var btnViewIds : List<Int>
    lateinit private var cmpViewIdsList : List<List<Int>>
    lateinit private var glViewIds : List<Int>
    lateinit var constraintSets: MutableList<ConstraintSet>
    var inputBtnViewList =  mutableListOf<ImageButton>()
    var layerViewList= mutableListOf<MutableList<ImageView>>()
    var guidelineList = mutableListOf<Guideline>()
    val constraintSet = ConstraintSet()


    fun mapLevelToView(){
        generateIdListsFromLevel()
        createInputView()
        createLayerViewList()
        createGuidelines()
        setInputConstraints()
        setComponentConstraints()
    }

    //Creates guidelines for each layer
    private fun createGuidelines() {
        var myfloat = 0.0F
        for(layer in level.layerList) {
            val layerIndex = level.layerList.indexOf(layer)
            val guideline = Guideline(context)
            //Set constraint parameters
            val constraintParams = ConstraintLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            constraintParams.orientation = ConstraintLayout.LayoutParams.VERTICAL
            guideline.layoutParams = constraintParams

            //Create guideline
            guideline.id = glViewIds[layerIndex]
            val index = level.layerList.indexOf(layer)
            val layerSize = level.layerList.size
            myfloat = (1 / (layerSize.toFloat() + 1))
            guideline.setGuidelineBegin(((index+1)*myfloat*getScreenWidth()).toInt())

            guidelineList.add(guideline)
            activity.LevelLayout.addView(guideline)
        }
    }
    //Left
    //constraintSet.connect(currentObject.id,
    // ConstraintSet.LEFT, leftObject.id, 0)

    //Creates gate element
    private fun createLayerViewList() {
        for (layer in level.layerList) {
            val compViewList = mutableListOf<ImageView>()
            val indexLayer = level.layerList.indexOf(layer)
            layer.componentList.forEach() {
                val indexComponent = layer.componentList.indexOf(it)
                compViewList.add(createComponentView(it,cmpViewIdsList[indexLayer][indexComponent]))
            }
            layerViewList.add(compViewList)
        }
    }

    //Creates input buttons
    private fun createInputView(){
        for (component in level.defaultInputList) {
            val input = ImageButton(context)
            input.layoutParams = LinearLayout.LayoutParams(
                    100,
                    100
            )
            //Image
            if (component.setResult())
                input.setImageResource(R.drawable.lamp_on)
            else
                input.setImageResource(R.drawable.lamp_off)
            input.setOnClickListener {
                if (component.setResult()) {
                    input.setImageResource(R.drawable.lamp_off)
                    !level.defaultInputList[level.defaultInputList.indexOf(component)]
                } else {
                    input.setImageResource(R.drawable.lamp_on)
                    !level.defaultInputList[level.defaultInputList.indexOf(component)]
                }
            }

            //get corresponding id
            input.id = btnViewIds[level.defaultInputList.indexOf(component)]
            inputBtnViewList.add(input)
            activity.LevelLayout.addView(input)
        }
    }

    //Generates ids for each and every component present on the screen
    private fun generateIdListsFromLevel(){
        //Button ids
        var btnIds = mutableListOf<Int>()
        level.defaultInputList.forEach(){
            btnIds.add(View.generateViewId())
        }
        btnViewIds = btnIds

        //Component ids
        var compIdsList = mutableListOf<MutableList<Int>>()
        for (input in level.layerList){
            compIdsList.add(generateCompIds(input))
        }
        cmpViewIdsList=compIdsList

        //Guideline ids
        var glIds = mutableListOf<Int>()
        for(layer in level.layerList)
            glIds.add(View.generateViewId())
        glViewIds=glIds
    }

    //Generate ids for each component
    private fun generateCompIds(layer: Layer): MutableList<Int> {
        var compIds = mutableListOf<Int>()
        for (component in layer.componentList) {
            compIds.add(View.generateViewId())
        }
        return compIds
    }


    //Create component image
    private fun createComponentView(component: Component,id: Int) : ImageView {
        //get image
        val componentView = ImageView(context)
        val className = component::class.java.simpleName
        when(className.substringBefore("Gate")){
            "And"->{componentView.setImageResource(R.drawable.image_android_development)}
            "Nand"->{componentView.setImageResource(R.drawable.image_android_development)}
            "Or" ->{componentView.setImageResource(R.drawable.image_android_development)}
            "Nor"->{componentView.setImageResource(R.drawable.image_android_development)}
            "Xor"->{componentView.setImageResource(R.drawable.image_android_development)}
            "Xnor"->{componentView.setImageResource(R.drawable.image_android_development)}
            "Not"->{componentView.setImageResource(R.drawable.image_android_development)}
            "Identity"->{componentView.setImageResource(R.drawable.image_android_development)}
            "Input"->{componentView.setImageResource(R.drawable.image_android_development)}
            else->{}
        }

        //determine size
        var heightMod = 0
        level.layerList.forEach(){
            if(it.componentList.contains(component))
                heightMod = it.componentList.size
        }
        // === Too big! -> AND Gate H:360, W:570 ===
        val height = getScreenHeight() / heightMod
        val width = getScreenWidth()/(level.layerList.size+1)
        // ===

        componentView.layoutParams = LinearLayout.LayoutParams(250,175)
        componentView.id = id
        activity.LevelLayout.addView(componentView)
        return componentView
    }

    //Set constraint for input lamps
    fun setInputConstraints(){
        val constId = R.id.LevelLayout

        constraintSet.clone(activity.LevelLayout)
        inputBtnViewList.forEach(){
            var index = inputBtnViewList.indexOf(it)
            constraintSet.connect(it.id,ConstraintSet.RIGHT,guidelineList[0].id,ConstraintSet.LEFT)
            constraintSet.connect(it.id,ConstraintSet.LEFT,constId,ConstraintSet.LEFT)
            //Top/Bottom constraints
            //Top Input
            if(index == 0){
                constraintSet.connect(it.id,ConstraintSet.TOP,constId,ConstraintSet.TOP)
                constraintSet.connect(it.id,ConstraintSet.BOTTOM,inputBtnViewList[index+1].id,ConstraintSet.TOP)
            }
            //Middle Input
            else if(index < inputBtnViewList.size-1) {
                constraintSet.connect(it.id,ConstraintSet.TOP,inputBtnViewList[index - 1].id,ConstraintSet.BOTTOM)
                constraintSet.connect(it.id,ConstraintSet.BOTTOM,inputBtnViewList[index + 1].id,ConstraintSet.TOP)
            }
            //Bottom Input
            else if(index == inputBtnViewList.size-1){
                constraintSet.connect(it.id,ConstraintSet.TOP,inputBtnViewList[index -1].id,ConstraintSet.BOTTOM)
                constraintSet.connect(it.id,ConstraintSet.BOTTOM,constId,ConstraintSet.BOTTOM)
            }
        }
    }

    //Get screen width
    @SuppressLint("ObsoleteSdkInt")
    private fun getScreenWidth(): Int {
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

    //Get screen height
    @SuppressLint("ObsoleteSdkInt")
    private fun getScreenHeight(): Int {
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

    //Set constraints for every component inside each layer
    private fun setComponentConstraints() {
        for (layer in layerViewList) {
            val layerIndex = layerViewList.indexOf(layer)
            for (view in layer) {
                val viewIndex = layer.indexOf(view)
                //End Gate
                if(layerIndex == layerViewList.lastIndex){
                    constraintSet.connect(view.id,ConstraintSet.LEFT,guidelineList[layerIndex].id,ConstraintSet.RIGHT)
                    constraintSet.connect(view.id,ConstraintSet.RIGHT,activity.LevelLayout.id,ConstraintSet.RIGHT)
                    constraintSet.connect(view.id,ConstraintSet.TOP,activity.LevelLayout.id,ConstraintSet.TOP)
                    constraintSet.connect(view.id,ConstraintSet.BOTTOM,activity.LevelLayout.id,ConstraintSet.BOTTOM)
                }
                //Top / First Gate
                else if(viewIndex == 0){
                    constraintSet.connect(view.id,ConstraintSet.LEFT,guidelineList[layerIndex].id,ConstraintSet.RIGHT)
                    constraintSet.connect(view.id,ConstraintSet.RIGHT,guidelineList[layerIndex+1].id,ConstraintSet.LEFT)
                    constraintSet.connect(view.id,ConstraintSet.TOP,activity.LevelLayout.id, ConstraintSet.TOP)
                    constraintSet.connect(view.id,ConstraintSet.BOTTOM,layer[viewIndex+1].id,ConstraintSet.TOP)
                }
                //Middle Gate
                else if(viewIndex != layer.lastIndex){
                    constraintSet.connect(view.id,ConstraintSet.LEFT,guidelineList[layerIndex].id,ConstraintSet.RIGHT)
                    constraintSet.connect(view.id,ConstraintSet.RIGHT,guidelineList[layerIndex+1].id,ConstraintSet.LEFT)
                    constraintSet.connect(view.id,ConstraintSet.TOP,layer[viewIndex-1].id,ConstraintSet.BOTTOM)
                    constraintSet.connect(view.id,ConstraintSet.BOTTOM,layer[viewIndex+1].id,ConstraintSet.TOP)
                }
                //Bottom / Last Gate
                else if(viewIndex == layer.lastIndex){
                    constraintSet.connect(view.id,ConstraintSet.LEFT,guidelineList[layerIndex].id,ConstraintSet.RIGHT)
                    constraintSet.connect(view.id,ConstraintSet.RIGHT,guidelineList[layerIndex+1].id,ConstraintSet.LEFT)
                    constraintSet.connect(view.id,ConstraintSet.TOP,layer[viewIndex-1].id,ConstraintSet.BOTTOM)
                    constraintSet.connect(view.id,ConstraintSet.BOTTOM,activity.LevelLayout.id,ConstraintSet.BOTTOM)
                }
            }
            activity.LevelLayout.setConstraintSet(constraintSet)
        }
    }
}