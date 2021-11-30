package com.dis.logolink.editor.models

class OrGate(inputList: MutableList<Component>,) : Component(inputList) {

    override fun setResult(): Boolean {
        result = false
        inputList.forEach(){
            result = result.or(it.setResult())
        }
        return result
    }

    override operator fun not() : NorGate {
        return NorGate(inputList)}
}