package com.dis.logolink.gui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View

class CanvasLoader(screenWidth: Int, screenHeight: Int) {
    val bitmap: Bitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888)
    val canvas: Canvas = Canvas(bitmap)
    lateinit var positions: MutableList<Float>

    fun <T>calculateLinePositions(elementList: MutableList<T>, inputList: MutableList<T>, context: Context){
        for(element in elementList){
            setStartPosition(element as View)
            //EndX EndY
            //TODO:

        }
    }

    fun setStartPosition(element: View){
        //StartX
        positions.add(element.right.toFloat())
        //StartY
        positions.add(element.right.toFloat() - element.height.toFloat())
    }

    fun setEndPostition() {

    }
}