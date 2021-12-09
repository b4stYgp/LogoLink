package com.dis.logolink.editor.gates

import com.dis.logolink.editor.models.Component

class OrGate(inputList: MutableList<Component>,) : Component(inputList) {

    override fun setResult(): Boolean {
        result = (inputList.any{it.setResult()} != inverted)
        return result
    }
}