package com.dis.logolink.parser
import com.dis.logolink.editor.models.*

class LayerMapper(){

    fun layerMapping(mappingList: MutableList<MutableList<Int>>,
                     componentNameList: MutableList<String>,
                     inputList: MutableList<Component>,
                     layerIndex: Int) : Layer {
        val componentList = mutableListOf<Component>()

        componentNameList.forEachIndexed() { index, componentName ->
            val componentInputList = mutableListOf<Component>()

            // index based mapping of input 'Components' to 'Components'
            // allows the use of 'componentList' as the 'inputList' of another 'Layer'
            mappingList[index].forEach() { mapping ->
                componentInputList.add(inputList[mapping])
            }
            when (componentName) {

                // TODO fix with dynamic class creation by name.

                "AND" -> componentList.add(AndGate(componentInputList))
                "ID" -> componentList.add(IdentityGate(componentInputList))
                "NAND" -> componentList.add(NandGate(componentInputList))
                "NOR" -> componentList.add(NorGate(componentInputList))
                "NOT" -> componentList.add(NotGate(componentInputList))
                "OR" -> componentList.add(OrGate(componentInputList))
                "XNOR" -> componentList.add(XnorGate(componentInputList))
                "XOR" -> componentList.add(XorGate(componentInputList))
            }

        }
        return Layer(layerIndex, componentList)
    }
}