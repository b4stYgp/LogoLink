package com.dis.logolink.gui

import android.app.Activity
import android.graphics.*
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.Guideline
import kotlinx.android.synthetic.main.activity_level.*

class CanvasLoader(screenWidth: Int, screenHeight: Int) {
    val bitmap: Bitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.RGBA_F16)
    val canvas: Canvas = Canvas(bitmap)
    var paint: Paint? = null
    var positions = mutableListOf<Float>()

    fun <T>calculateLinePositions(elementList: MutableList<Pair<T, T>>, activity: Activity, layerViewList: MutableList<MutableList<ImageView>>,
                                  guidelineList: MutableList<Guideline>): Bitmap {
        elementList.forEachIndexed { elementIndex, pair ->
            val startView = activity.LevelLayout.findViewById<View>(pair.second as Int)
            val endView = activity.LevelLayout.findViewById<View>(pair.first as Int)


            if(startView is ImageButton){                
                setStartPosition(startView)
                setIdGuidelinePosition(guidelineList[0],endView)
            }
            else{
                val viewStartText = activity.LevelLayout.findViewById<TextView>(pair.second as Int *-1).text
                val viewEndText = activity.LevelLayout.findViewById<TextView>(pair.first as Int *-1).text
                if(viewStartText.equals("") || viewEndText.equals(""))
                {
                    //TODO get the corresponding Guideline for connection
                    if(viewStartText.equals("")) {
                        layerViewList.forEachIndexed { index, mutableList ->
                                if(mutableList.contains(startView)) {
                                    setIdGuidelinePosition(guidelineList[index],startView)
                                    setIdGuidelinePosition(guidelineList[index+1],endView)
                                }
                        }

                    }
                    if(viewEndText.equals("")) {
                        layerViewList.forEachIndexed { index, mutableList ->
                            if (mutableList.contains(startView)) {
                                setIdGuidelinePosition(guidelineList[index],startView)
                                setEndPosition(startView)
                                setStartPosition(startView)
                                setIdGuidelinePosition(guidelineList[index + 1],endView)
                            }
                        }
                    }
                }
                else{
                    layerViewList.forEachIndexed { index, mutableList ->
                        if(mutableList.contains(startView)){
                            setIdGuidelinePosition(guidelineList[index],startView)
                            setEndPosition(startView)
                            setStartPosition(startView)
                            setIdGuidelinePosition(guidelineList[index+1],endView)
                        }
                    }

                }
            }
        }
        val lastView = layerViewList.last().last()
        setIdGuidelinePosition(guidelineList.last(),lastView)
        setEndPosition(lastView)

        paint = Paint().apply{
                color = Color.BLACK
                strokeWidth = 10F
                strokeCap = Paint.Cap.SQUARE
            }
            CanvasPencil(canvas, paint!!).drawLines(positions)
            return bitmap

    }



    private fun setIdGuidelinePosition(guideline: Guideline, view: View?) {
        val posG = guideline.getLocationOnScreen()
        val posV = view!!.getLocationOnScreen()
        //Position with image offset
        positions.add(posG.x.toFloat())
        positions.add(posV.y.toFloat())
    }

    //Start position from LEFT TO RIGHT
    fun setStartPosition(element: View){
        //Position on Screen
        var pos = element.getLocationOnScreen()

        //Position with image offset
        //StartX
        positions.add(pos.x.toFloat() + element.width.toFloat() * 1/6)
        //StartY
        positions.add(pos.y.toFloat() + element.height.toFloat() * 1/3)
    }

    //End position from LEFT TO RIGHT
    fun setEndPosition(element: View) {
        //Position on Screen
        var pos = element.getLocationOnScreen()

        //Position with image offset
        //End X
        positions.add(pos.x.toFloat() - element.width.toFloat() * 1/2)
        //End Y
        positions.add(pos.y.toFloat())
    }

    //X, Y position of view
    fun View.getLocationOnScreen():Point
    {
        val location = IntArray(2)
        this.getLocationOnScreen(location)
        return Point(location[0],location[1])
    }

}


