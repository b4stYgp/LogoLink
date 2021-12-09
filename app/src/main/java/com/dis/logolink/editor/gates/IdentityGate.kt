package com.dis.logolink.editor.gates

import com.dis.logolink.editor.models.Component

open class IdentityGate(inputList: MutableList<Component>) : AndGate(inputList) {

    init {
        assert(inputList.size == 1)
    }
}