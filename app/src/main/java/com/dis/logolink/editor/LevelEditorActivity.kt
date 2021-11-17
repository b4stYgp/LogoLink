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
            var fieldDto = Klaxon().parse<FieldDto>(json.bufferedReader())
            var fieldList = createField(fieldDto as FieldDto)
            var field = connectField(fieldList)
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

    private fun createField(fieldDto: FieldDto): MutableList<MutableList<Component>> {
        var itrLayers = fieldDto.layerDtos!!.iterator()
        lateinit var LayersCompList: MutableList<MutableList<Component>> //Field[Layer[Components[]]
        while (itrLayers!!.hasNext()) {  //in LayersList
            LayersCompList.add(createLayer(itrLayers.next()))
        }
        return LayersCompList
    }

    private fun createLayer(next: LayerDto): MutableList<Component> {
        var buildObjItr = next.componentDtos!!.iterator()
        lateinit var ComponentList: MutableList<Component>
        while (buildObjItr.hasNext()) { // in BuildingObjects List
           ComponentList.add(createComponent(buildObjItr.next()))
        }
        return ComponentList
    }
    private fun createComponent(next: ComponentDto): Component {
        var nextBuildObj = next
        lateinit var component: Component
        when (nextBuildObj.component) {
            "and" -> {
            }
            "nand" -> {
            }
            "or" -> {
                }
            "nor" -> {
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
        return component
    }



    }
}
}






