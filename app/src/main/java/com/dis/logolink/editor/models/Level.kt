package com.dis.logolink.editor.models

import java.util.*

class Level(val defaultInputList: MutableList<Component>, val  layerList :MutableList<Layer>) {

    override fun toString(): String {
        var str = "\n"
        var maxSize = Collections.max(layerList.map { layer ->
            layer.componentList.size - 1
        })
        var index = maxSize

        str += "InputLayer:"
        defaultInputList.forEach() { component ->
            str += "${component}"
        }
        str += "\n\n"
        layerList.forEach() { layer ->
            str += "Layer: ${maxSize-index}"
            str += "${layer}"
            str += "\n\n"
            index--
        }


        assert(layerList.last().componentList.size == 1)
        val result = layerList.last().componentList[0].setResult()
        str += "\nresult: ${result}\n"
        return str
    }
}