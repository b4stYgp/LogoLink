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

    fun <T>calculateLinePositions(elementList: MutableList<Pair<T, T>>, activity: Activity): Bitmap{
        for(element in elementList){
            setStartPosition(activity.findViewById(element.second as Int))
            //EndX EndY
            setEndPostition(activity.findViewById(element.first as Int))
        }
        paint = Paint().apply {
            color = Color.BLACK
            strokeWidth = 10F
            strokeCap = Paint.Cap.SQUARE
        }
        CanvasPencil(canvas, paint!!).drawLines(positions)
        return bitmap
    }

    //Start position from LEFT TO RIGHT
    fun setStartPosition(element: View){
        //Position on Screen
        var pos = element.getLocationOnScreen()

        //StartX
        positions.add(pos.x.toFloat() + element.width.toFloat()/2)
        //StartY
        positions.add(pos.y.toFloat())
    }

    //End position from LEFT TO RIGHT
    fun setEndPostition(element: View) {
        //Position on Screen
        var pos = element.getLocationOnScreen()
        //End X
        positions.add(pos.x.toFloat() - element.width.toFloat()/4)
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


