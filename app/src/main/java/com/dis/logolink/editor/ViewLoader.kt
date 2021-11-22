package com.dis.logolink.editor

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.dis.logolink.editor.models.Component

import com.dis.logolink.gui.R
import com.dis.logolink.editor.models.InputGate
import com.dis.logolink.editor.models.Layer
import com.dis.logolink.editor.models.Level
import com.dis.logolink.gui.LevelActivity

class ViewLoader() {
    lateinit var level: Level
    lateinit var btnViewIds : List<Int>
    lateinit var cmpViewIdsList : List<List<Int>>
    lateinit var constraintSet: MutableList<ConstraintSet>

    fun generateIdListsFromLevel(){
        var btnIds = mutableListOf<Int>()
        level.defaultInputList.forEach(){
            btnIds.add(View.generateViewId())
        }
        btnViewIds = btnIds
        var compIdsList = mutableListOf<MutableList<Int>>()
        for (input in level.layerList){
            compIdsList.add(generateCompIds(input))
        }

    }

    private fun generateCompIds(layer: Layer): MutableList<Int> {
        var compIds = mutableListOf<Int>()
        for (component in layer.componentList) {
            compIds.add(View.generateViewId())
        }
        return compIds
    }

    fun createLayerView(it: Layer, context: Context): MutableList<ImageView> {
        val compViewList = mutableListOf<ImageView>()
        var idCount = 0
        it.componentList.forEach(){
            compViewList.add(createComponentView(it,context,cmpViewIdsList[]))
            idCount++
        }
        return compViewList
    }

    private fun createComponentView(component: Component, context: Context, id: Int) : ImageView {
        val componentView = ImageView(context)
        componentView.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        componentView.id = id
        componentView.setImageResource(R.drawable.image_android_development)

        return componentView
    }

    fun createInputView(component: InputGate, context: Context) : ImageButton{
        val input = ImageButton(context)
        input.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        input.setOnClickListener {
            if(component.setResult())
                input.setImageResource(R.drawable.lamp_off)
            else
                input.setImageResource(R.drawable.lamp_on)
        }
        //var constraintLayout : ConstraintLayout = LevelLayout
        return input
    }

  /*  fun <T>createLayerObjects(objectType: T, constraintSet: ConstraintSet, context: Context){
        var oldObject = View(context)
        var currentObject = currentObject
        var count = 0


        while (count < objectCount){
            //Left
            constraintSet.connect(currentObject.id,
                ConstraintSet.LEFT, leftObject.id, 0)
            //Top
            if(count == 0 ) {
                //Constraint to parent
                constraintSet.connect(currentObject.id,
                    ConstraintSet.TOP, 0, 0)
            }else{
                constraintSet.connect(currentObject.id,
                    ConstraintSet.TOP, oldObject.id, 0)
            }
            //Bottom
            if(count == objectCount-1) {
                //Constraint to parent
                constraintSet.connect(currentObject.id,
                    ConstraintSet.BOTTOM, 0, 0)
            }

            oldObject = currentObject
            currentObject = View(context)
            count++
        }
        return currentObject
    }

   */



    /* fun creatView(component: Component): ImageView {

     }
     */
}