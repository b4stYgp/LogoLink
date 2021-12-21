package com.dis.logolink.parser
import com.dis.logolink.editor.models.*

class LevelMapper() {

    fun levelMapping(levelDto: LevelDto) : Level {
        val defaultI: MutableList<Boolean> = levelDto.default
        val layerDtoList:MutableList<LayerDto> = levelDto.layers
        val layerList = mutableListOf<Layer>()
        val defaultInputList = mutableListOf<Component>()

        defaultI.forEach(){
            val inputGate = InputGate(mutableListOf())
            if(it) {
                defaultInputList.add(!inputGate)
            }
            else {
                defaultInputList.add(inputGate)
            }
        }

        layerDtoList.forEachIndexed(){ index, layer ->
                layer.components
                layer.mapping
                if (index!=0){
                    layerList.add(LayerMapper().layerMapping(layer.mapping,
                            layer.components,
                            layerList[index-1].componentList,
                            index))
                }
                else {
                    layerList.add(LayerMapper().layerMapping(layer.mapping, layer.components, defaultInputList, index))
                }
            }
        return Level(defaultInputList,layerList)
    }
}