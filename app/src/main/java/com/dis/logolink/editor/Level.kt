package com.dis.logolink.editor

import com.dis.logolink.editor.gates.Component
import com.dis.logolink.editor.gates.InputGate
import com.dis.logolink.parser.LevelDto
import java.util.*

/**
 * class Level represents a collection of Layers
 * @param defaultInputList represents the clickable default inputs.
 * @param mappingList all Layer mappings. Index references Layer.
 * @param componentNameList all Layer components. Index references Layer.
 */
class Level(levelDto: LevelDto) {

    private val layerList = mutableListOf<Layer>()
    val defaultInputList = mutableListOf<Component>()

    init {
        val defaultI = levelDto.default
        val layerDtoList = levelDto.layers

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
                layerList.add(
                    Layer(
                        layer.mapping,
                        layer.components,
                        layerList[index-1].componentList,
                        index)
                )
            }
            else {
                layerList.add(
                    Layer(
                        layer.mapping,
                        layer.components,
                        defaultInputList,
                        index)
                )
            }
        }
    }

    val result : Boolean
        get() {
            assert(layerList.last().componentList.size == 1)
            return layerList.last().componentList[0].setResult()
            // or change return type to List<Boolean> and evaluate the List as a whole
            }

    override fun toString(): String {
            var str = "\n"
            var maxSize = Collections.max(layerList.map { layer ->
                layer.componentList.size
            })
            while (maxSize >= 0) {
                str += "$maxSize: ${defaultInputList.getOrElse(maxSize){""}}"
                layerList.forEach() { layer ->
                    str += layer.mappingList.getOrElse(maxSize){""}.toString()
                        .replace("[","    ")
                        .replace("]"," -> ") +
                            "${layer.componentList.getOrElse(maxSize){""}}"
                }
                maxSize--
                str += "\n\n"
            }
        str += "\nresult: ${this.result}\n"
        return str
    }
}