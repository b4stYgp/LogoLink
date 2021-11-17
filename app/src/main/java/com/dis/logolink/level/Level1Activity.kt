package com.dis.logolink.level

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.dis.logolink.editor.*
import com.dis.logolink.gui.R
import kotlinx.android.synthetic.main.activity_level1.*

//Hard coded level for testing purposes
class Level1Activity : AppCompatActivity() {

    val tmpPosition = Position(0,0)
    val defaultInput1 = IdentityGate(tmpPosition, mutableListOf<Component>(), "Identity0")
    val defaultInput2 =  IdentityGate(tmpPosition, mutableListOf<Component>(), "Identity1")
    val defaultInputList = mutableListOf<Component>(defaultInput1, defaultInput2)
    // level2
    //val mappingList = mutableListOf(mutableListOf(mutableListOf(0,1), mutableListOf(0)), mutableListOf(mutableListOf(0,1)))
    //val componentList = mutableListOf(mutableListOf("OR", "NOT"), mutableListOf("AND"))

    val mappingList = mutableListOf(mutableListOf(mutableListOf(0,1)))
    val componentList = mutableListOf(mutableListOf("AND"))
    val level1 = Level(defaultInputList, mappingList, componentList)

    private fun ChangeLampImage(lampInput: Boolean, img: ImageButton){
        //Lamp is turned on, 1
        if (lampInput){
            img.setBackgroundResource(R.drawable.lamp_on)
        }
        //Lamp is turned off, 0
        else{
            img.setBackgroundResource(R.drawable.lamp_on)
        }
    }

    fun checkSolution() {
        println(level1.layerList.last().componentList)
        if (level1.layerList.last().componentList[0].result) {
            level1_output.setImageResource(R.drawable.lamp_on)
        }
        else {level1_output.setImageResource(R.drawable.lamp_off)}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level1)

        level1_lamp1.setOnClickListener{
            ChangeLampImage(defaultInput1.result, level1_lamp1)
            defaultInput1.invert()
            checkSolution()
        }
        level1_lamp2.setOnClickListener {
            ChangeLampImage(defaultInput2.result, level1_lamp2)
            defaultInput2.invert()
            checkSolution()
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus){
            //Screen size
            val displayMetrics = this.resources.displayMetrics
            var screenWidth = displayMetrics.widthPixels
            var screenHeight = displayMetrics.heightPixels

            //Bitmap & Canvas
            val bitmap: Bitmap = Bitmap.createBitmap(
                screenWidth, screenHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas: Canvas = Canvas(bitmap)
            val paint: Paint = Paint().apply {
                color = getColor(R.color.black)
                strokeWidth = 10F
                strokeCap = Paint.Cap.SQUARE
            }

            val list = mutableListOf<Float>()

            //Lamp1 -> AND
            list.add(level1_lamp1.right.toFloat())
            list.add(level1_lamp1.bottom.toFloat())
            list.add(level1_and1.left.toFloat())
            list.add(level1_and1.top.toFloat() + (level1_and1.height * 0.5F))

            //Lamp2 -> AND
            list.add(level1_lamp2.right.toFloat())
            list.add(level1_lamp2.bottom.toFloat())
            list.add(level1_and1.left.toFloat())
            list.add(level1_and1.bottom.toFloat() - (level1_and1.height * 0.5F))

            //AND -> Output
            list.add(level1_and1.right.toFloat())
            list.add(level1_and1.bottom.toFloat()
            - (level1_and1.height * 0.5).toFloat())
            list.add(level1_output.left.toFloat())
            list.add(level1_output.bottom.toFloat()
            - (level1_output.height * 0.5F))

            //Draw lines
            canvas.drawLines(list.toFloatArray(), paint)

            //Rectangle
//            var shapeDrawable = ShapeDrawable(RectShape())
//            shapeDrawable.setBounds(level1_and1.left,
//                level1_and1.top,
//                level1_and1.right,
//                level1_and1.bottom
//            )
//            shapeDrawable.paint.setColor(ContextCompat.getColor(this, R.color.black))
//            shapeDrawable.draw(canvas)
            level1_canvas.background = BitmapDrawable(resources, bitmap)
        }
    }
}