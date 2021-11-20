package com.dis.logolink.models

class Layer(val layerIndex: Int,val componentList: MutableList<Component>) {

    override fun toString(): String {
        var str = "\tlayer$layerIndex:"
        componentList.forEach(){component ->  str = "$str\n\t\t$component"}
        return str
    }
}