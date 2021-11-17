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
            var field: Field = createField(fieldDto as FieldDto)

            //generateComponentsList()
            LevelEditorLayout.removeView(btn)
        }
        LevelEditorLayout.addView(btn)

    }

    //WIP
    fun loadFromFile(path: String, context: Context): FieldDto {
        val mapper = ObjectMapper(YAMLFactory())
        mapper.registerModule(KotlinModule())
        return context.assets.open(path).use { mapper.readValue(it, FieldDto::class.java) }
    }

    //WIP
    private fun createField(fieldDto: FieldDto): Field {
        var itrLayers = fieldDto.layerDtos!!.iterator()
        lateinit var field: Field//Field[Layer[Components[]]
        while (itrLayers!!.hasNext()) {  //in LayersList
            field.layers.add(createLayer(itrLayers.next()))
        }

        return field
    }
    //WIP
    private fun createLayer(next: LayerDto): Layer {
        var buildObjItr = next.componentDtos!!.iterator()
        lateinit var layer: Layer
        while (buildObjItr.hasNext()) { // in BuildingObjects List
           layer.components.add(createComponent(buildObjItr.next()))
        }
        return layer
    }
    //WIP
    private fun createComponent(next: ComponentDto): Component {
        var nextBuildObj = next
        lateinit var component: Component
        when (nextBuildObj.component) {
            "and" -> { component = AndGate(Position(0,0), mutableListOf(Input(false),Input(false)),"and")
            }
            "nand" -> { component = NandGate(Position(0,0), mutableListOf(Input(false),Input(false)),"nand")
            }
            "or" -> { component = OrGate(Position(0,0), mutableListOf(Input(false),Input(false)),"or")
                }
            "nor" -> { component = NorGate(Position(0,0), mutableListOf(Input(false),Input(false)),"nor")
                }
            "xor" -> { component = XorGate(Position(0,0),mutableListOf(Input(false), Input(false)), "xor")
            }
            "xnor" -> { component = XnorGate(Position(0,0), mutableListOf(Input(false),Input(false)),"xnor")
                }
            "not" -> { component = NotGate(Position(0,0), mutableListOf(Input(false)),"not")
                }
            else -> { return component
                }
        }
        return component
    }

    private fun createField(fieldList: MutableList<MutableList<Component>>){

    }
}







