package com.dis.logolink.editor.gates

import com.dis.logolink.editor.models.Component

class XorGate(inputList: MutableList<Component>) : Component(inputList) {

    override fun setResult(): Boolean {
        result = ((inputList.count { it.setResult() } % 2 == 0) != inverted)
        return result
    }
}