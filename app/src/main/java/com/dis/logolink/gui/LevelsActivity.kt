package com.dis.logolink.gui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LevelsActivity : AppCompatActivity() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<LevelAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_levels)

        layoutManager = LinearLayoutManager(this)

        //set recycle view object
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager

        //Set all available levels which are to be displayed in the level selection
        val sp = getSharedPreferences("levelPref", MODE_PRIVATE)
        val maxLevel = sp.getInt("highestLevelReached", 0)
        var possibleLevels = arrayOfNulls<String>(maxLevel + 1)
        if(maxLevel != 0){
            for (i in 0..maxLevel){
                possibleLevels[i] = "Level " + (i + 1).toString()
            }
        }else{
            possibleLevels.set(0, "Level 1")
        }

        //set adapter
        adapter = LevelAdapter(possibleLevels)
        recyclerView.adapter = adapter
    }
}