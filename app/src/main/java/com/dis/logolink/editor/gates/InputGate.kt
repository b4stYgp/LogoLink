package com.dis.logolink.editor.gates

import com.dis.logolink.editor.models.Component

open class InputGate(inputList: MutableList<Component> = mutableListOf()) : Component(inputList)  {

    override fun setResult(): Boolean {
        return !inverted
    }
}