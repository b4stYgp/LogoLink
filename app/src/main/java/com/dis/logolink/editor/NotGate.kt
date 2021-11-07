package com.dis.logolink.editor

open class NotGate(position: Position,
                   inputList: MutableList<Input>,
                   name: String,
) : Component(position, inputList, name) {
    override fun setResult(): Boolean {
        return inputList[0].value.not()
    }

    override operator fun not(): Component {
        return NotGate(this.position,mutableListOf(!this.inputList[0]),"NotGate through !NotGate")
    }
}