package com.dis.logolink.editor

open class NotGate(position: Position,
                   inputList: MutableList<Component>,
                   name: String,
) : Component(position, inputList, name) {
    override fun setResult(): Boolean {
        return inputList[0].result.not()
    }
}