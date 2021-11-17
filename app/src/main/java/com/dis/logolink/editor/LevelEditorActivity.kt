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
        while (itrLayers!!.hasNext()) {  //in LayersList (Field)
            field.layers.add(createLayer(itrLayers.next()))
        }

        return field
    }
    //WIP
    private fun createLayer(next: LayerDto): Layer {
        var buildObjItr = next.componentDtos!!.iterator()
        lateinit var layer: Layer
        while (buildObjItr.hasNext()) { // in BuildingObjects List (Layer)
           layer.components.add(createComponent(buildObjItr.next()))
        }
        return layer
    }
    //WIP 
    private fun createComponent(next: ComponentDto): Component {  //in Component
        var nextBuildObj = next
        lateinit var component: Component
        var inputList: MutableList<Input> = createInputList(next.inputIndizes)
        when (nextBuildObj.component) {
            "and" -> { component = AndGate(Position(0,0), inputList,"and")
            }
            "nand" -> { component = NandGate(Position(0,0), inputList,"nand")
            }
            "or" -> { component = OrGate(Position(0,0), inputList,"or")
                }
            "nor" -> { component = NorGate(Position(0,0), inputList,"nor")
                }
            "xor" -> { component = XorGate(Position(0,0), inputList, "xor")
            }
            "xnor" -> { component = XnorGate(Position(0,0), inputList,"xnor")
                }
            "not" -> { component = NotGate(Position(0,0), inputList,"not")
                }
            else -> { return component
                }
        }
        return component
    }

    private fun createInputList(inputIndizes: String?): MutableList<Input> {
        var listCounter = inputIndizes?.split(",")?.size
        var counter = 0
        lateinit var inputList : MutableList<Input>
        while(counter < listCounter as Int){
            inputList.add(Input(false))
            counter++
        }
        return inputList
    }
}







