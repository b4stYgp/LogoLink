package com.dis.logolink.gui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dis.logolink.editor.ViewLoader
import kotlinx.android.synthetic.main.activity_level.*




class LevelActivity() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level)
        val levelName = savedInstanceState.
        val listViews: List<View> = ViewLoader(levelName)


    }
}







