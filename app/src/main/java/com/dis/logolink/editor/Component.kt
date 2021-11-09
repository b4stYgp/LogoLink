package com.dis.logolink.editor

abstract class Component(
    var position: Position,
    var inputList: MutableList<Input>,
    val name: String
) {
    var result = setResult()
    abstract fun setResult(): Boolean
    var output = Input(result)
    abstract operator fun not() : Component

    operator fun plus(input: Input): AndGate {
        val newInputList = mutableListOf(this.output,input)
        return AndGate(Position(0,0),newInputList,"AndGate through addition with Input")
    }

    operator fun plus(component: Component) : AndGate {
        val newInputList = mutableListOf(this.output,component.output)
        return AndGate(Position(0,0),newInputList,"AndGate through addition with Component")
    }

    operator fun times(input: Input) : OrGate {
        val newInputList = mutableListOf(this.output,input)
        return OrGate(Position(0,0),newInputList,"AndGate through multiplication with Input")
    }

    operator fun times(component: Component) : OrGate {
        val newInputList = mutableListOf(this.output,component.output)
        return OrGate(Position(0,0),newInputList,"AndGate through multiplication with Component")
    }

    operator fun div(input: Input) : XorGate{
        val newInputList = mutableListOf(this.output,input)
        return XorGate(Position(0,0),newInputList,"AndGate through division with Input")
    }

    operator fun div(component: Component) : XorGate {
        val newInputList = mutableListOf(this.output,component.output)
        return XorGate(Position(0,0),newInputList,"XorGate through division with Component")
    }
}