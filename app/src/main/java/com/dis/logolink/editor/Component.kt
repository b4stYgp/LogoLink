package com.dis.logolink.editor

abstract class Component(
    var position: Position,
    var inputList: MutableList<Component>,
    val name: String
) {
    open var state : Int = -1 //TODO remove yank
    val result : Boolean = false
    abstract fun setResult(): Boolean
    override fun toString(): String = "$name->${this.setResult()}"
}