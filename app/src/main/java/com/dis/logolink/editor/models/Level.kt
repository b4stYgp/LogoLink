package com.dis.logolink.editor.models

class Level(val inputList: MutableList<Component>,
            val layerList: MutableList<Layer> = mutableListOf()) {

    var result = false

    fun setResult(): Boolean {
        result = (layerList.lastOrNull()?.componentList ?: inputList).all { it.setResult() }
        return result
    }

    override fun toString(): String {
        return inputList.zip(layerList).toString()
    }
}