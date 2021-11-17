package com.dis.logolink.editor
import android.content.res.loader.AssetsProvider
import org.junit.Test
import org.junit.Assert.*
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.junit.runner.manipulation.Ordering
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path


class LayerTest {

    @Test
    fun layerGenerator() {

        val path = "/Users/ornstein/StudioProjects/LogoLink/parser/app/src/main/assets/level0.yml"
        val result = Parser().parse(path)
        val level0 = Level(result!!.default, result.layers)
        println(level0)
        level0.defaultInputList[0].state = level0.defaultInputList[0].state * -1
        println(level0)
        }
}