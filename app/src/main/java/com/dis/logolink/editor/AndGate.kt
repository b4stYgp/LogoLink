package com.dis.logolink.editor

open class AndGate(position: Position,
                   inputList: MutableList<Component>,
                   name: String,
) : Component(position, inputList, name) {

    override fun setResult(): Boolean {
        val list:MutableList<Component> = inputList
        var result = true
        val itr = list.iterator()
        while(itr.hasNext())
        {
            result = result.and(itr.next().setResult())
        }
        return result
    }
}