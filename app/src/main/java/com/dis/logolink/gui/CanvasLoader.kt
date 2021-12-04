package com.dis.logolink.gui

import android.app.Activity
import android.content.Context
import android.graphics.*
import android.view.View
import androidx.constraintlayout.widget.Guideline
import kotlinx.android.synthetic.main.activity_level.*

class CanvasLoader(screenWidth: Int, screenHeight: Int) {
    val bitmap: Bitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888)
    val canvas: Canvas = Canvas(bitmap)
    var paint: Paint? = null
    var positions = mutableListOf<Float>()

    fun <T>calculateLinePositions(elementList: MutableList<Pair<T, T>>, activity: Activity): Bitmap{
        for(element in elementList){
            //Gate position START
            setStartPosition(activity.findViewById(element.second as Int))
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

        //Position with image offset
        //StartX
        positions.add(pos.x.toFloat() + element.width.toFloat() * 1/6)
        //StartY
        positions.add(pos.y.toFloat() + element.height.toFloat() * 1/3)
    }

    //End position from LEFT TO RIGHT
    fun setEndPostition(element: View) {
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


