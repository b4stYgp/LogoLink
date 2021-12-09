package com.dis.logolink.parser

import com.dis.logolink.editor.models.*
import com.dis.logolink.editor.models.Component
import com.dis.logolink.editor.gates.InputGate

class LevelMapper() {

    fun levelMapping(levelDto: LevelDto) : Level {

        val defaultInputList = mutableListOf<Component>()

        levelDto.default.forEach(){
            when(it) {
                true -> defaultInputList.add(InputGate())
                false -> defaultInputList.add(!InputGate())
            }
        }

        val level = Level(defaultInputList)

        levelDto.layers.forEach(){layer ->
            level.layerList.add(
                    LayerMapper().layerMapping(
                            layer.mapping,
                            layer.components,
                            level.layerList.lastOrNull()?.componentList ?: level.inputList)
            )
        }
        return level
    }
}