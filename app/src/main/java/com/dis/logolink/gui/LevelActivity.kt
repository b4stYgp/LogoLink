package com.dis.logolink.gui

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

import com.dis.logolink.editor.ViewLoader
import com.dis.logolink.models.IdentityGate
import com.dis.logolink.models.Level
import com.dis.logolink.parser.LevelDto
import com.dis.logolink.parser.LevelMapper
import com.dis.logolink.parser.Parser
import kotlinx.android.synthetic.main.activity_level.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.levels_item.*


class LevelActivity() : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level)
        val field = assets.list("")
        val lvlMapper = LevelMapper()
        val viewLoader = ViewLoader()

        //load Level with Mapper and Parser
        var regex = """level(\d{1}|\d{2}).yml""".toRegex()
        field!!.forEach {
            if(it.matches(regex)) {
                if (it.contains(intent.extras?.get("levelname").toString().filter { it.isDigit() })){
                    viewLoader.level = lvlMapper.levelMapping(Parser().parse(assets.open(it)) as LevelDto)
                }
            }
        }

        //Divide Screen into Layers
        val layerQty = viewLoader.level.layerList.size + 1 //+InputLayer
        //
        //Button generator
        val buttonList = mutableListOf<ImageButton>()
        val inputIterator = viewLoader.level.defaultInputList.iterator()
        while(inputIterator.hasNext()) {
            buttonList.add(viewLoader.createView(inputIterator.next() as IdentityGate,this))
        }

        val btnIterator = buttonList.iterator()
        while(btnIterator.hasNext()){
            LevelLayout.addView(btnIterator.next())
        }
    }
}







