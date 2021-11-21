package com.dis.logolink.editor.gates

class NandGate(inputList: MutableList<Component>) : Component(inputList) {

    override fun setResult(): Boolean {
        result = true
        inputList.forEach(){
            result = result.and(it.setResult())
        }
        return !result
    }

    override operator fun not() : AndGate {
        return AndGate(inputList)
    }
}
