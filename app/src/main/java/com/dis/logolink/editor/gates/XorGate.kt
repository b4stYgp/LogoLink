package com.dis.logolink.editor.gates

class XorGate(inputList: MutableList<Component>) : Component(inputList) {

    override fun setResult(): Boolean {
        result = false
        inputList.forEach(){
            result = result.xor(it.setResult())
        }
        return result
    }

    override fun not(): XnorGate {
        return XnorGate(inputList)
    }
}