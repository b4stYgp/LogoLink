package com.dis.logolink.editor.gates

import com.dis.logolink.editor.models.Component

open class AndGate(inputList: MutableList<Component>) : Component(inputList) {

    override fun setResult(): Boolean {
        result = (inputList.all{it.setResult()} != inverted)
        return result
    }
}