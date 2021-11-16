package com.dis.logolink.level

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.compose.ui.unit.sp
import com.dis.logolink.editor.AndGate
import com.dis.logolink.editor.Input
import com.dis.logolink.editor.Position
import com.dis.logolink.gui.R
import kotlinx.android.synthetic.main.activity_level1.*

//Hard coded level for testing purposes
class Level1Activity : AppCompatActivity() {
    //Generates input list
    private fun eingangsListenGenerator(boolList: List<Boolean>): MutableList<Input> {
        val inputList = mutableListOf<Input>()
        val itr = boolList.iterator()
        while (itr.hasNext()) {
            inputList.add(Input(itr.next()))
        }
        return inputList
    }

    private fun ChangeLampImage(lampInput: Boolean, img: ImageButton){
        //Lamp is turned on, 1
        if (lampInput){
            img.setBackgroundResource(R.drawable.lamp_off)
        }
        //Lamp is turned off, 0
        else{
            img.setBackgroundResource(R.drawable.lamp_on)
        }
    }

    private fun RefreshGate(andGate: AndGate){
        inputList = eingangsListenGenerator(listOf(lamp1Input, lamp2Input,
        lamp1Input && lamp2Input))
        this.andGate = AndGate(position, inputList, "AndGate1")

        if (this.andGate.result){
            level1_output.setBackgroundResource(R.drawable.lamp_on)
        }
    }

    //Lamp values
    private var lamp1Input: Boolean = false
    private var lamp2Input: Boolean = false
    private var inputList: MutableList<Input> = eingangsListenGenerator(listOf(lamp1Input, lamp2Input,
        lamp1Input && lamp2Input))
    private var position = Position(2, 3)
    private var andGate: AndGate = AndGate(position, inputList, "AndGate1")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level1)

        level1_lamp1.setOnClickListener{
            ChangeLampImage(lamp1Input, level1_lamp1)
            lamp1Input = !lamp1Input
            RefreshGate(andGate)
        }
        level1_lamp2.setOnClickListener {
            ChangeLampImage(lamp2Input, level1_lamp2)
            lamp2Input = !lamp2Input
            RefreshGate(andGate)
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