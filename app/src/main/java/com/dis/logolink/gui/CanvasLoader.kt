package com.dis.logolink.gui

import android.app.Activity
import android.content.Context
import android.graphics.*
import android.view.View
import kotlinx.android.synthetic.main.activity_level.*

class CanvasLoader(screenWidth: Int, screenHeight: Int) {
    val bitmap: Bitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888)
    val canvas: Canvas = Canvas(bitmap)
    var paint: Paint? = null
    var positions = mutableListOf<Float>()

    fun <T>calculateLinePositions(elementList: MutableMap<T, T>, activity: Activity): Bitmap{
        for(element in elementList){
            setStartPosition(activity.findViewById(element.key as Int))
            //EndX EndY
            setEndPostition(activity.findViewById(element.value as Int))
        }
        paint = Paint().apply {
            color = Color.BLACK
            strokeWidth = 10F
            strokeCap = Paint.Cap.SQUARE
        }
        CanvasPencil(canvas, paint!!).drawLines(positions)
        return bitmap
    }


    fun setStartPosition(element: View){
        //Position on Screen
        var pos = element.getLocationOnScreen()

        //StartX
        positions.add(pos.x.toFloat()) //former Right
        //StartY
        positions.add(pos.y.toFloat())
    }

    fun setEndPostition(element: View) {
        //Position on Screen
        var pos = element.getLocationOnScreen()
        //End X
        positions.add(pos.x.toFloat()) //former Left
        //End Y
        positions.add(pos.y.toFloat())
    }

    fun View.getLocationOnScreen():Point
    {
        val location = IntArray(2)
        this.getLocationOnScreen(location)
        return Point(location[0],location[1])
    }

}


