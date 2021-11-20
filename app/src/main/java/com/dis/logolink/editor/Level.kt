package com.dis.logolink.editor

/**
 * class Layer represents one column of Components of a Level
 * @param defaultInputList represents the clickable default inputs.
 * TODO add clickable on different Layers
 * @param mappingList all Layer mappings. Index references Layer.
 * @param componentNameList all Layer components. Index references Layer.
 */
class Level(defaultI: MutableList<Boolean>,
            layerDtoList: MutableList<LayerDto>) {


    override fun toString(): String {
        var str = "inputs:$defaultInputList\nlevel0:"
        layerList.forEach(){layer ->  str = "$str\n$layer"}
        return str
    }

    val layerList = mutableListOf<Layer>()
    val defaultInputList = mutableListOf<Component>()

    init {
            val pos = Position(0,0)

            defaultI.forEachIndexed(){index2, gate ->
                val inputGate = IdentityGate(pos, mutableListOf(), "INPUT$index2")
                if(gate) {
                    inputGate.state = 1
                }
                defaultInputList.add(inputGate)
            }
        layerDtoList.forEachIndexed(){ index, layer ->
                layer.components
                layer.mapping
                if (index!=0){
                    layerList.add(Layer(layer.mapping,
                        layerList[index-1].componentList,
                        layer.components,
                        index))
                }
                else {
                    layerList.add(Layer(layer.mapping, defaultInputList, layer.components, index))
                }
            }

    }
}