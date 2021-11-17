package com.dis.logolink.editor

/**
 * class Layer represents one column of Components of a Level
 * @param mappingList represents the mapping of inputs to 'Components'.
 * element of 'mappingList' is a 'MutableList' of 'inputList' indices.
 * element's index acts as a foreign key to a 'Component' with the same index in 'ComponentList'.
 * @param inputList represents all possible inputs to 'Components' of this Layer
 * @param componentNameList is a sequential list of names of 'Components' in this Layer
 */
class Layer(mappingList: MutableList<MutableList<Int>>,
            inputList: MutableList<Component>,
            componentNameList: MutableList<String>,
            layerIndex: Int)
{

    val componentList = mutableListOf<Component>()

    init {
        componentNameList.forEachIndexed() { index, componentName ->
            val componentInputList = mutableListOf<Component>()
            val defaultPosition = Position(index, layerIndex)
            val defaultName = "$componentName$index$layerIndex"

            // index based mapping of input 'Components' to 'Components'
            // allows the use of 'componentList' as the 'inputList' of another 'Layer'
            mappingList[index].forEach() { mapping ->
                componentInputList.add(inputList[mapping])
            }

            when (componentName) {

                // TODO fix with dynamic class creation by name.

                "AND" -> componentList.add(AndGate(defaultPosition,
                    componentInputList, defaultName))
                "ID" -> componentList.add(AndGate(defaultPosition,
                    componentInputList, defaultName))
                "NAND" -> componentList.add(AndGate(defaultPosition,
                    componentInputList, defaultName))
                "NOR" -> componentList.add(NorGate(defaultPosition,
                    componentInputList, defaultName))
                "NOT" -> componentList.add(NotGate(defaultPosition,
                    componentInputList, defaultName))
                "OR" -> componentList.add(OrGate(defaultPosition,
                    componentInputList, defaultName))
                "XNOR" -> componentList.add(XnorGate(defaultPosition,
                    componentInputList, defaultName))
                "XOR" -> componentList.add(XorGate(defaultPosition,
                    componentInputList, defaultName))
            }

        }
    }
}