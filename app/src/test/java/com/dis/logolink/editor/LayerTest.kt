package com.dis.logolink.editor
import com.dis.logolink.models.IdentityGate
import com.dis.logolink.models.InputGate
import com.dis.logolink.parser.LevelDto
import com.dis.logolink.parser.LevelMapper
import com.dis.logolink.parser.Parser
import org.junit.Test


class LayerTest{

    @Test
    fun layerGenerator() {
        val iS = javaClass.classLoader?.getResourceAsStream("level0.yml")
        val level = LevelMapper().levelMapping(Parser().parse(iS!!) as LevelDto)
        println(level)
        val defList : MutableList<InputGate> = level.defaultInputList as MutableList<InputGate>
        defList[0] = !defList[0] as InputGate
        println(level)
        println(level)
        defList[1] = !defList[1] as InputGate
        println(level)
        }
}