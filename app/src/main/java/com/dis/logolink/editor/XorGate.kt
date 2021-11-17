package com.dis.logolink.editor

class XorGate(position: Position,
              inputList: MutableList<Component>,
              name: String,
) : Component(position, inputList, name) {

    override fun setResult(): Boolean {
        val list:MutableList<Component> = inputList
        var result = inputList[0].result
        val itr = list.iterator()
        while(itr.hasNext())
        {
            result = itr.next().result.xor(itr.next().result)
        }
        return result
    }
}