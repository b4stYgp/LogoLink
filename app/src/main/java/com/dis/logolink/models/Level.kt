package com.dis.logolink.models

class Level(val defaultInputList: MutableList<Component>, val  layerList :MutableList<Layer>) {

    override fun toString(): String {
        var str = "inputs:$defaultInputList\nlevel0:"
        layerList.forEach(){layer ->  str = "$str\n$layer"}
        return str
    }
}