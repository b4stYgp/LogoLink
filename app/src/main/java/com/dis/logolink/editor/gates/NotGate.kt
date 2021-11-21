package com.dis.logolink.editor.gates

open class NotGate(inputList: MutableList<Component>) : Component(inputList) {

    override fun setResult(): Boolean {
        result = false
        inputList.forEach(){
            result = result.or(it.setResult())
        }
        result = !result
        return result
    }

    override operator fun not(): Component {
        return AndGate(inputList)
    }
}