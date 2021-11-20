package com.dis.logolink.editor
import com.dis.logolink.models.IdentityGate
import com.dis.logolink.parser.LevelMapper
import com.dis.logolink.parser.Parser
import org.junit.Test


class LayerTest{

    @Test
    fun layerGenerator() {
        val iS = javaClass.classLoader?.getResourceAsStream("level0.yml")
        val result = Parser().parse(iS!!)
        val levelMapper = LevelMapper()
        val level = levelMapper.levelMapping(result!!.default,result!!.layers)
        println(level)
        val defList : MutableList<IdentityGate> = level.defaultInputList as MutableList<IdentityGate>
        defList[0].invert()
        println(level)
        println(level)
        println(level)
        }
}