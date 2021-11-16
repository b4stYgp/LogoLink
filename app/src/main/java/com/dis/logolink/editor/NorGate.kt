package com.dis.logolink.editor

class NorGate (position: Position,
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
        output.value = !result
        return !result
    }

    override operator fun not() : OrGate {
        return OrGate(this.position, this.inputList, "NandGate through !AndGate")
    }
}