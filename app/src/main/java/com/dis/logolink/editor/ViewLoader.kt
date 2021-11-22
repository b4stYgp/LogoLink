package com.dis.logolink.editor

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams

import com.dis.logolink.gui.R
import com.dis.logolink.models.Component
import com.dis.logolink.models.IdentityGate
import com.dis.logolink.models.InputGate
import com.dis.logolink.models.Level
import com.dis.logolink.parser.Parser
import kotlinx.android.synthetic.main.activity_level.*

class ViewLoader() {
    lateinit var level: Level

    fun createView(component: InputGate, context: Context) : ImageButton{
        val input = ImageButton(context)
        input.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        input.setImageResource(R.drawable.lamp_off)
        //var constraintLayout : ConstraintLayout = LevelLayout
        return input
    }

   /* fun creatView(component: Component): ImageView {

    }
    */
}