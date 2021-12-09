package com.dis.logolink.editor.models

abstract class Component(var inputList: MutableList<Component>) {

    var result = false
    var inverted = false

    abstract fun setResult(): Boolean

    operator fun not(): Component {
        inverted = !inverted
        return this
    }

    override fun toString(): String {
        return (if (inverted){"!"} else {""}) +
               "${this::class.java.simpleName}:" +
               "${this.result}"
    }
}