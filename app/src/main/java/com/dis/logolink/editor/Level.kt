package com.dis.logolink.editor

/**
 * class Layer represents one column of Components of a Level
 * @param defaultInputList represents the clickable default inputs.
 * TODO add clickable on different Layers
 * @param mappingList all Layer mappings. Index references Layer.
 * @param componentNameList all Layer components. Index references Layer.
 */
class Level(defaultInputList: MutableList<Component>,
            nestedMappingList: MutableList<MutableList<MutableList<Int>>>,
            nestedComponentNameList: MutableList<MutableList<String>>) {

    val layerList = mutableListOf<Layer>()

    init {
        nestedMappingList.zip(nestedComponentNameList).forEachIndexed() { index, layerInfo ->
            val mappingList = layerInfo.first
            val componentList = layerInfo.second
            if (index==0) {
                layerList.add(Layer(mappingList,
                    defaultInputList,
                    componentList, index))
            }
            else {
                layerList.add(Layer(mappingList,
                    layerList[index-1].componentList,
                    componentList, index))
            }
        }
    }
}