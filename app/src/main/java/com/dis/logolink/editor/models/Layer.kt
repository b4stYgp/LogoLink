package com.dis.logolink.editor.models

class Layer(val componentList: MutableList<Component> = mutableListOf()) {

    override fun toString(): String {
        return componentList.toString()
    }
}