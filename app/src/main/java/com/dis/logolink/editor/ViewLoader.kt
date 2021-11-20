package com.dis.logolink.editor

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout

import com.dis.logolink.gui.R
import com.dis.logolink.models.Component
import com.dis.logolink.models.IdentityGate
import com.dis.logolink.models.Level
import com.dis.logolink.parser.Parser

class ViewLoader() {
    lateinit var level: Level

    fun createView(component: IdentityGate, context: Context) : ImageButton{
        val input = ImageButton(context)
        input.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        input.setImageResource(R.drawable.lamp_off)
        return input
    }

   /* fun creatView(component: Component): ImageView {

    }
    */
}