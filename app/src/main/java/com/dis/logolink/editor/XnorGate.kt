package com.dis.logolink.editor

class XnorGate(position: Position,
               inputList: MutableList<Input>,
               name: String,
) : Component(position, inputList, name) {

    override fun setResult(): Boolean {
        val list:MutableList<Input> = inputList
        var result = inputList[0].value
        val itr = list.iterator()
        while(itr.hasNext())
        {
            result = itr.next().value.xor(itr.next().value)
        }
        return !result
    }

    override fun not(): XorGate {
        return XorGate(this.position, this.inputList,"XorGate through !XnorGate")
    }
}