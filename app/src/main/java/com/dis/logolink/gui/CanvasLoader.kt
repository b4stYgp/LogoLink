package com.dis.logolink.gui

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class CanvasLoader(screenWidth: Int, screenHeight: Int) {
    val bitmap: Bitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888)
    val canvas: Canvas = Canvas(bitmap)
    var paint: Paint? = null
    lateinit var positions: MutableList<Float>

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

    //EXCEPTION CAN'T GET RIGHT VAL (NULL)
    fun setStartPosition(element: View){
        //StartX
        positions.add(element.right.toFloat())
        //StartY
        positions.add(element.right.toFloat() - element.height.toFloat())
    }

    fun setEndPostition(element: View) {
        //End X
        positions.add(element.left.toFloat())
        //End Y
        positions.add(element.left.toFloat() - element.height.toFloat())
    }
}