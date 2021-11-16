package com.dis.logolink.editor

class XorGate(position: Position,
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
        output.value = result
        return result
    }

    override fun not(): XnorGate {
        return XnorGate(this.position, this.inputList,"XnorGate through !XorGate")
    }


}