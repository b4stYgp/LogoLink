package com.dis.logolink.editor.gates

open class AndGate(inputList: MutableList<Component>) : Component(inputList) {

    override fun setResult(): Boolean {
        result = true
        inputList.forEach(){
            result = result.and(it.setResult())
        }
        return result
    }

    override operator fun not() : NandGate {
        return NandGate(inputList)
    }
}