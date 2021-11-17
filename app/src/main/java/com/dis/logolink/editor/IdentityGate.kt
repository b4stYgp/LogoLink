package com.dis.logolink.editor

open class IdentityGate(position: Position,
                   inputList: MutableList<Component>,
                   name: String,
) : Component(position, inputList, name) {

    private var state = -1

    override fun setResult(): Boolean {

        when (state) {
            -1 -> return false
            1 -> return true
            0 -> if (inputList.size == 1) {
                return inputList[0].result
            }
        }
        throw Exception("Unknown 'IdentityGate' state")
    }

    fun invert(): IdentityGate {
        this.state = -this.state
        return this
    }
}