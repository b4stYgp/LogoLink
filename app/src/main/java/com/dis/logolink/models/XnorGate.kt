package com.dis.logolink.models

class XnorGate(inputList: MutableList<Component>) : Component(inputList) {

    override fun setResult(): Boolean {
        result = true
        inputList.forEach(){
            result = result.xor(it.setResult()==result)
        }
        return !result
    }

    override fun not(): XorGate {
        return XorGate(inputList)
    }

}