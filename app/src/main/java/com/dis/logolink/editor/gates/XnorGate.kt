package com.dis.logolink.editor.gates

class XnorGate(inputList: MutableList<Component>) : Component(inputList) {

    override fun setResult(): Boolean {
        result = true
        inputList.forEach(){
            result = result.and(it.setResult()==result)
        }
        return result
    }

    override fun not(): XorGate {
        return XorGate(inputList)
    }

}