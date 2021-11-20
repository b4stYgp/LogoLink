package com.dis.logolink.parser

import com.dis.logolink.editor.*
import com.dis.logolink.models.*

/**
 * class Layer represents one column of Components of a Level
 * @param defaultInputList represents the clickable default inputs.
 * TODO add clickable on different Layers
 * @param mappingList all Layer mappings. Index references Layer.
 * @param componentNameList all Layer components. Index references Layer.
 */
class LevelMapper() {

    fun levelMapping(levelDto: LevelDto) : Level {
        val defaultI: MutableList<Boolean> = levelDto.default
        val layerDtoList:MutableList<LayerDto> = levelDto.layers
        val pos = Position(0,0)
        var layerList = mutableListOf<Layer>()
        val defaultInputList = mutableListOf<Component>()

            defaultI.forEachIndexed(){index2, gate ->
                val inputGate: IdentityGate = IdentityGate(pos, mutableListOf(), "INPUT$index2")
                if(gate) {
                    inputGate.state = 1
                }
                defaultInputList.add(inputGate)
            }
        val layerMapper = LayerMapper()
        layerDtoList.forEachIndexed(){ index, layer ->
                layer.components
                layer.mapping
                if (index!=0){
                    layerList.add(layerMapper.layerMapping(layer.mapping,
                        layerList[index-1].componentList,
                        layer.components,
                        index))
                }
                else {
                    layerList.add(layerMapper.layerMapping(layer.mapping, defaultInputList, layer.components, index))
                }
            }
        return Level(defaultInputList,layerList)
    }
}