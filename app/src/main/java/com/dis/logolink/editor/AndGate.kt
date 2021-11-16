package com.dis.logolink.editor

open class AndGate(position: Position,
                   inputList: MutableList<Input>,
                   name: String,
) : Component(position, inputList, name) {

    override fun setResult(): Boolean {
        val list:MutableList<Input> = inputList
        var result = true
        val itr = list.iterator()
        while(itr.hasNext())
        {
            result = result.and(itr.next().value)
        }
        output.value = result
        return result
    }

    override operator fun not() : NandGate {
       return NandGate(this.position, this.inputList, "NandGate through !AndGate")
    }

}