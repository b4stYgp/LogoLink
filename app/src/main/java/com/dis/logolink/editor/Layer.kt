package com.dis.logolink.editor

import com.dis.logolink.editor.gates.*

/**
 * class Layer represents one column of Components of a Level
 * @param mappingList represents the mapping of inputs to 'Components'.
 * @param inputList represents all possible inputs to 'Components' of this Layer
 * @param componentNameList is a sequential list of names of 'Components' in this Layer
 */
class Layer(var mappingList: MutableList<MutableList<Int>>,
            componentNameList: MutableList<String>,
            inputList: MutableList<Component>,
            var layerIndex: Int)
{

    val componentList = mutableListOf<Component>()

    override fun toString(): String {
         return this.mappingList.toString()
            .replace("[","    ")
            .replace("]"," -> ") +
            "$this.componentList"
    }

    init {
        componentNameList.forEachIndexed() { index, componentName ->
            val componentInputList = mutableListOf<Component>()
            val defaultName = "$componentName$index$layerIndex"

            // index based mapping of input 'Components' to 'Components'
            // allows the use of 'componentList' as the 'inputList' of another 'Layer'
            mappingList[index].forEach() { mapping ->
                componentInputList.add(inputList[mapping])
            }

            when (componentName) {
                "AND" -> componentList.add(
                    AndGate(componentInputList)
                )
                "ID" -> componentList.add(
                    IdentityGate(componentInputList)
                )
                "NAND" -> componentList.add(
                    NandGate(componentInputList)
                )
                "NOR" -> componentList.add(
                    NorGate(componentInputList)
                )
                "NOT" -> componentList.add(
                    NotGate(componentInputList)
                )
                "OR" -> componentList.add(
                    OrGate(componentInputList)
                )
                "XNOR" -> componentList.add(
                    XnorGate(componentInputList)
                )
                "XOR" -> componentList.add(
                    XorGate(componentInputList)
                )
            }

        }
    }
}