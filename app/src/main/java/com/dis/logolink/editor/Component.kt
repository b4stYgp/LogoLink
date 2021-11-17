package com.dis.logolink.editor

abstract class Component(
    var position: Position,
    var inputList: MutableList<Component>,
    val name: String
) {
    val result : Boolean
        get() {
            return setResult()
        }
    abstract fun setResult(): Boolean
    override fun toString():
            String = "($inputList)->$name->${this.setResult()}"
        .replace("[", "")
        .replace("]", "")
        .replace("()->", "")
}