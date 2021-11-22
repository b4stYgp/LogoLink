package com.dis.logolink.models

class Layer(val layerIndex: Int,val componentList: MutableList<Component>) {

    override fun toString(): String {
        var str = ""
        componentList.forEach{
            str+= "${it}"
        }
        return str
    }
}