package com.dis.logolink.editor

class OrGate(position: Position,
             inputList: MutableList<Input>,
             name: String
) : Component(position, inputList, name) {


    override fun setResult(): Boolean {
        val list: MutableList<Input> = inputList
        var result = false
        val itr = list.iterator()
        while (itr.hasNext()) {
            result = result.or(itr.next().value)
        }
        return result
    }

    override operator fun not() : NorGate {
        return NorGate(this.position, this.inputList, "NandGate through !AndGate")
    }
}