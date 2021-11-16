package com.dis.logolink.editor

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.dis.logolink.gui.R
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import kotlinx.android.synthetic.main.activity_level_editor.*
import com.beust.klaxon.*


//Klaxon
class LevelEditorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level_editor)
        var btn = Button(this)
        btn.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        btn.text = "Let the Toast Begin"
        btn.setOnClickListener { it ->
            it
            //var file = File("app/src/main/assets/testLevel.json")
            val json = assets.open("testLevel.json")
            var field = Klaxon().parse<FieldDto>(json.bufferedReader())
            createComponents(field as FieldDto)
            //generateComponentsList()
            LevelEditorLayout.removeView(btn)
        }
        LevelEditorLayout.addView(btn)

    }

    fun loadFromFile(path: String, context: Context): FieldDto {
        val mapper = ObjectMapper(YAMLFactory())
        mapper.registerModule(KotlinModule())
        return context.assets.open(path).use { mapper.readValue(it, FieldDto::class.java) }
    }

    private fun createComponents(fieldDto: FieldDto) {
        var itrLayers = fieldDto.layerDtos!!.iterator()
        lateinit var LayersCompList : MutableList<MutableList<Component>> //Field[Layer[Components[]]]
        var layerIndex = 0
        while(itrLayers!!.hasNext()){  //in LayersList
            LayersCompList.add(create)
            var buildObjItr = itrLayers.next().componentDtos!!.iterator()
            var componentCounter = 0 // index for OutputList -> Input Layer[>1]
            while(buildObjItr.hasNext()) { // in BuildingObjects List
                var nextBuildObj = buildObjItr!!.next()
                when (nextBuildObj.component) {
                    "or" -> {
                       /* if(inputList!![])
                        var orGate = OrGate(
                            Position(layerIndex,componentCounter),
                            mutableListOf(Input(false),Input(false))
                        compList[layerIndex].add()

                        */

                    }
                    "nor" -> {

                    }
                    "and" -> {

                    }
                    "nand" -> {

                    }
                    "xor" -> {

                    }
                    "xnor" -> {

                    }
                    "not" -> {

                    }
                    else -> {
                    }

                }
                componentCounter++
            }
            layerIndex++
        }
    }


}

