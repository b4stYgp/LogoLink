package com.dis.logolink.level

import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.ViewTreeObserver
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import com.dis.logolink.gui.DrawLine
import com.dis.logolink.gui.R
import kotlinx.android.synthetic.main.activity_level1.*
import java.lang.reflect.Modifier

class Level1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level1)
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
            canvas.drawLine(level1_lamp1.right.toFloat(),
                level1_lamp1.right.toFloat(),
                level1_and1.left.toFloat(),
                level1_and1.left.toFloat(),
                Paint(getColor(R.color.black))
            )
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