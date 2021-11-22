package com.dis.logolink.editor.models

open class IdentityGate(inputList: MutableList<Component>) : Component(inputList) {

    override fun setResult(): Boolean {
        assert(inputList.size == 1)
        return inputList[0].setResult()
    }

    override operator fun not() : NotGate {
        return NotGate(inputList)
    }
}