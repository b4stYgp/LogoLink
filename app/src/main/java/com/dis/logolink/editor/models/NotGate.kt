package com.dis.logolink.editor.models

open class NotGate(inputList: MutableList<Component>) : Component(inputList) {

    override fun setResult(): Boolean {
        assert(inputList.size == 1)
        result = !inputList[0].result
        return result
    }

    override operator fun not(): Component {
        return NotGate(inputList)
    }
}