package com.dis.logolink.models

class NorGate (inputList: MutableList<Component>) : Component(inputList) {

    override fun setResult(): Boolean {
        result = true
        inputList.forEach(){
            result = result.or(it.setResult())
        }
        return !result
    }

    override operator fun not() : OrGate {
        return OrGate(inputList)
    }
}