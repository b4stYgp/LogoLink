package com.dis.logolink.gui

import android.os.Bundle
import com.dis.logolink.parser.Parser
import com.dis.logolink.editor.ViewLoader
import com.dis.logolink.parser.LevelMapper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LevelActivity() : AppCompatActivity() {

    lateinit var viewLoader: ViewLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level)
        ViewCompat.getWindowInsetsController(window.decorView)!!
            .hide(WindowInsetsCompat.Type.systemBars())

        val level = Parser().parse(
            assets.open("levels/"+
                    assets.list("levels")!!.find {
                        it.substringBefore(".")
                            .equals(
                                "level${intent.extras!!.get("levelname")}",
                                true
                            )
                    }!!
            )
        )

        viewLoader = ViewLoader(this,this)
        viewLoader.level = LevelMapper().levelMapping(level!!)
        viewLoader.mapLevelToView()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        viewLoader.drawLines()
    }
}












