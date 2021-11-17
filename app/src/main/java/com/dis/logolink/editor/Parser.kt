package com.dis.logolink.editor
import android.content.Context
import android.content.res.loader.AssetsProvider
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.*
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path

class Parser() {

    val mapper = ObjectMapper(YAMLFactory())

    init {
        this.mapper.registerModule(
            KotlinModule.Builder()
                .build()
        )
        this.mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
    }

    fun parse(path : String): LevelDto? {
        val file = Files.newBufferedReader(FileSystems.getDefault().getPath(path))
        val result = file.use { mapper.readValue(it, LevelDto::class.java) }
        file.close()
        return result
    }
}