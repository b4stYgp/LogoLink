package com.dis.logolink.gui

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View

class CanvasPencil(val canvas: Canvas, val paint: Paint) {

    //Receives list of positions:
    //[0]: startPosX_Line1, [1]: startPosY_Line1,
    //[2]: endPosX_Line1, [3]: endPosY_Line1,
    //[4]: startPosX_Line2, ...
    fun drawLines(positions: MutableList<Float>){
        canvas.drawLines(positions.toFloatArray(), paint)
    }

    //Draw horizontal line
    fun horizontalLine(startPosX: Float, startPosY: Float, endPosX: Float, endPosY: Float){
        canvas.drawLine(startPosX, startPosY, endPosX, endPosY, paint)
    }

    fun verticalLine(){

    }
}