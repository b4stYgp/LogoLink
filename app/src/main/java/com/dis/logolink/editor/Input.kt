package com.dis.logolink.editor

class Input(val value: Boolean) {

    operator fun plus(input: Input): AndGate {
        val newInputList = mutableListOf<Input>(this,input)
        return AndGate(Position(0,0),newInputList,"AndGate through addition with Input")
    }

    operator fun plus(component: Component): AndGate {
            val newInputList = mutableListOf(this,component.output)
            return AndGate(Position(0,0),newInputList,"AndGate through addition with Input")
    }

    operator fun times(input: Input): OrGate {
        val defaultPosition = Position(0,0)
        val operatorInput : MutableList<Input> = mutableListOf(this,input)
        val name:String = "OrGate through multiplication with Input"
        return OrGate(defaultPosition,operatorInput,name)
    }

    operator fun times(component: Component): OrGate {
        val defaultPosition = Position (0,0)
        val operatorInput = mutableListOf(this,component.output)
        val name = "OrGate through multiplication with Input"
        return OrGate(defaultPosition,operatorInput,name)
    }

    operator fun not(): Input {
        return Input(!this.value)
    }
}





