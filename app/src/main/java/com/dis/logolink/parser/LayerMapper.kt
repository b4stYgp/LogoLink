package com.dis.logolink.parser

import com.dis.logolink.editor.models.Component
import com.dis.logolink.editor.models.Layer
import com.dis.logolink.editor.gates.*

class LayerMapper(){

    fun layerMapping(mappingList: MutableList<MutableList<Int>>,
                     componentNameList: MutableList<String>,
                     inputList: MutableList<Component>) : Layer {

        val componentList = mutableListOf<Component>()

        componentNameList.forEachIndexed() { index, componentName ->
            val componentInputList = mutableListOf<Component>()

            mappingList[index].forEach() { mapping ->
                componentInputList.add(inputList[mapping])
            }

            when (componentName) {
                "NOT"   -> componentList.add(!IdentityGate(componentInputList))
                "ID"    -> componentList.add(IdentityGate(componentInputList))
                "XOR"   -> componentList.add(XorGate(componentInputList))
                "AND"   -> componentList.add(AndGate(componentInputList))
                "OR"    -> componentList.add(OrGate(componentInputList))

                "!NOR"  -> componentList.add(!XorGate(componentInputList))
                "!AND"  -> componentList.add(!AndGate(componentInputList))
                "!OR"   -> componentList.add(!OrGate(componentInputList))
            }
        }
        return Layer(componentList)
    }
}