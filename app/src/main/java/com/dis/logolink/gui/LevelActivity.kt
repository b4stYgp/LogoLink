package com.dis.logolink.gui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Button
import com.dis.logolink.editor.ViewLoader
import com.dis.logolink.models.Level
import com.dis.logolink.parser.LevelDto
import com.dis.logolink.parser.LevelMapper
import com.dis.logolink.parser.Parser
import kotlinx.android.synthetic.main.activity_level.*
import kotlinx.android.synthetic.main.levels_item.*


class LevelActivity() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level)
        val field = assets.list("")
        val lvlMapper = LevelMapper()
        val viewLoader = ViewLoader()

        //load Level with Mapper and Parser
        field!!.forEach {
            if(it.contains(levelx_name.text.filter{it.isDigit()}))
           {viewLoader.level = lvlMapper.levelMapping(Parser().parse(assets.open(it)) as LevelDto)
            }
        }

        //Divide Screen into Layers
        val layerQty = viewLoader.level.layerList.size + 1 //+InputLayer
        
    }
}







