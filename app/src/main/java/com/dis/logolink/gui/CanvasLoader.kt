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
            //Guideline position END
            setGuidelinePosition(calculateGuidelineOffset(activity.findViewById(element.first as Int),
                activity.findViewById(element.second as Int)))
            //Guideline position START
            setGuidelinePosition(calculateGuidelineOffset(activity.findViewById(element.first as Int),
                activity.findViewById(element.second as Int)))
            //Gate position END
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

    //============TEST AREA=======================

    fun <T>calculateLinePositionsTEST(elementList: MutableList< Pair< T, Pair<T, T> > >, activity: Activity): Bitmap{
        for(element in elementList){
            //Gate - Guideline
            setStartPosition(activity.findViewById(element.second.first as Int))
            setEndPositionAsGuideline(activity.findViewById(element.first as Int),
                activity.findViewById(element.second.first as Int))
            //Vertical
            val guideLineOffset = setVerticalPosition(activity.findViewById(element.first as Int), activity.findViewById(element.second.first as Int), activity.findViewById(element.second.second as Int))
            //Guideline - Gate
            positions.add(guideLineOffset.first)
            positions.add(guideLineOffset.second)
            setEndPostition(activity.findViewById(element.second.second as Int))
        }
        paint = Paint().apply {
            color = Color.BLACK
            strokeWidth = 10F
            strokeCap = Paint.Cap.SQUARE
        }
        CanvasPencil(canvas, paint!!).drawLines(positions)
        return bitmap
    }

    //Calculate offset for guideline line
    fun calculateGuidelineOffset(elementA: View, elementB: View): Pair<Float, Float>{
        var posA = elementA.getLocationOnScreen()
        var posB = elementB.getLocationOnScreen()
        return Pair((posA.x.toFloat() + posB.x.toFloat()) / 2, (posA.y.toFloat() + posB.y.toFloat()) / 2)
    }

    //Guideline start position
    //Pair<X, Y>
    fun setGuidelinePosition(pair: Pair<Float, Float>){
        positions.add(pair.first)
        positions.add(pair.second)
    }

    fun setVerticalPosition(guideline: Guideline, elementLeft: View, elementRight: View) : Pair<Float, Float> {
        val posGuideline = guideline.getLocationOnScreen()
        val posElementLeft = elementLeft.getLocationOnScreen()
        val posElementRight = elementRight.getLocationOnScreen()

        positions.add(posGuideline.x.toFloat())
        positions.add(posElementLeft.y.toFloat() + elementLeft.width.toFloat() * 1/3)

        positions.add(posGuideline.x.toFloat())
        positions.add(posElementRight.y.toFloat() * 1/3)
        return Pair(posGuideline.x.toFloat(), posElementRight.y.toFloat())
    }

    fun setEndPositionAsGuideline(guideline: Guideline, startElement: View){
        //Position on Screen
        var pos = guideline.getLocationOnScreen()
        var posElement = startElement.getLocationOnScreen()

        positions.add(pos.x.toFloat())
        positions.add(posElement.y.toFloat())
    }

    //============TEST AREA=======================

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


