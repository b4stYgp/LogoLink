package com.dis.logolink.editor.models

abstract class Component(var inputList: MutableList<Component>) {
    var result = false
    abstract fun setResult(): Boolean
    abstract operator fun not() : Component
    override fun toString(): String = "[${this::class.java.simpleName}, ${if (this.setResult()) 1 else 0}]"
}