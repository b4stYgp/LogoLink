package com.dis.logolink.parser
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.*
import java.io.InputStream

class Parser() {

    private val mapper = ObjectMapper(YAMLFactory())

    init {
        this.mapper.registerModule(
            KotlinModule.Builder()
                .build()
        )
        this.mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
    }

    fun parse(path : InputStream): LevelDto? {

        val file = path.bufferedReader()
        val result = file.use { mapper.readValue(it, LevelDto::class.java) }
        file.close()
        return result
    }
}