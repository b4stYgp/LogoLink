package com.dis.logolink.parser
import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.*

class Parser {

    private val mapper = ObjectMapper(YAMLFactory())

    init {
        this.mapper.registerModule(
            KotlinModule.Builder()
                .build()
        )
        this.mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
    }

    fun parse(context: Context, level: String): LevelDto? {
        return mapper.readValue(context.assets.open(level), LevelDto::class.java)
    }
}