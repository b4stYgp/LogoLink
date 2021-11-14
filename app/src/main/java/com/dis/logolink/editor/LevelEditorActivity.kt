package com.dis.logolink.editor

import android.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.android.TextLayout
import com.dis.logolink.gui.R
import kotlinx.android.synthetic.main.activity_level_editor.*
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.nio.file.Path

class LevelEditorActivity : AppCompatActivity() {

    val inputCompontensFormula = "!(andGate + orGate)"

    //     / -> index -1 +1  outputs -> xorGgate


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level_editor)
        var btn = Button(this)
        btn.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        btn.text="Let the Toast Begin"
        btn.setOnClickListener {
            it
            //generateComponentsList()

            LevelEditorLayout.removeView(btn)
        }
        LevelEditorLayout.addView(btn)

    }

    fun startLevel(){

    }



    /*
    fun generateComponentsList(): List<List<kotlin.Any>> {
        val conjunctionComponents = inputCompontensFormula.split(" ")
        val cItr = conjunctionComponents.iterator()
        lateinit var componentList : MutableList<Component>
        lateinit var indexList: MutableList<String>
        while(cItr.hasNext()) {
            var conjunctionComponent = cItr.next()
            var boolConjunction = conjunctionComponent.contains(Regex.fromLiteral("(+|*|/)"))
            var boolGate = conjunctionComponent.contains(Regex.fromLiteral("(((and|or|xor|nand|nor|xnor|not)Gate)|input)"))
            if (boolGate) {
                Toast.makeText(LevelEditorLayout.context, conjunctionComponent, Toast.LENGTH_SHORT)
                    .show()
                when(conjunctionComponent){
                    "andGate" -> {componentList.add(AndGate(Position(0,0),mutableListOf(Input(false),Input(false)),"andGate"))}
                    "orGate" -> {componentList.add(OrGate(Position(0,0),mutableListOf(Input(false),Input(false)),"orGate"))}
                    "xorGate" -> {componentList.add(XorGate(Position(0,0),mutableListOf(Input(false),Input(false)),"xorGate"))}
                    "nandGate" -> {componentList.add(NandGate(Position(0,0),mutableListOf(Input(false),Input(false)),"nandGate"))}
                    "norGate" -> {componentList.add(NorGate(Position(0,0),mutableListOf(Input(false),Input(false)),"norGate"))}
                    "xnorGate" -> {componentList.add(XnorGate(Position(0,0),mutableListOf(Input(false),Input(false)),"xnorGate"))}
                    "notGate" -> {componentList.add(NotGate(Position(0,0),mutableListOf(Input(false)),"notGate"))}
                    else ->{}
                }

            } else if (boolConjunction) {
                Toast.makeText(LevelEditorLayout.context, conjunctionComponent, Toast.LENGTH_SHORT)
                    .show()
                var compListSize = componentList.size
                indexList.add(conjunctionComponent)
                when(conjunctionComponent){
                    "+" ->{
                        componentList.add(AndGate(Position(0,0),mutableListOf(componentList[compListSize].output),name="andGate+"))
                        }
                    "*" ->{
                        componentList.add(OrGate(Position(0,0),mutableListOf(componentList[compListSize].output),name="orGate*"))}
                    "/" ->{
                        componentList.add(XorGate(Position(0,0), mutableListOf(componentList[compListSize].output),name="xorGate/"))
                    }
                    else -> {}
                }
            } else
                Toast.makeText(LevelEditorLayout.context, conjunctionComponent, Toast.LENGTH_SHORT)
                    .show()

        }
        return listOf()
    }

     */


}

inline fun <reified T> ObjectMapper.readValue(src: Path): T {
    return readValue(src.toFile())
}
