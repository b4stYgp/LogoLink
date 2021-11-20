package com.dis.logolink.models

class XorGate(position: Position,
              inputList: MutableList<Component>,
              name: String,
) : Component(position, inputList, name) {

    override fun setResult(): Boolean {
        val list:MutableList<Component> = inputList
        var result = inputList[0].setResult()
        val itr = list.iterator()
        while(itr.hasNext())
        {
            result = itr.next().setResult().xor(itr.next().setResult())
        }
        return result
    }
}