package com.dis.logolink.gui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager

class LevelsActivity : AppCompatActivity() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<LevelAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_levels)

        // window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //                 WindowManager.LayoutParams.FLAG_FULLSCREEN)
        layoutManager = LinearLayoutManager(this)

        //set recycle view object
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager

        //Set all available levels which are to be displayed in the level selection
        val highestLevelReachedAsAssetsIndex = getSharedPreferences("levelPref", MODE_PRIVATE)
            .getInt("highestLevelReached", 1) - 1


        val unlockedLevels = (assets.list("levels")
            ?.sortedBy {
                    item -> item.filter {
                it.isDigit()}.toInt()
            }?.slice(0..highestLevelReachedAsAssetsIndex)?: listOf<String>())
        adapter = LevelAdapter(unlockedLevels)
        recyclerView.adapter = adapter
    }
}