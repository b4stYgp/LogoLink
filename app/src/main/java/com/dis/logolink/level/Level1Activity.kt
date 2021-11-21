package com.dis.logolink.level

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageButton
import com.dis.logolink.editor.*
import com.dis.logolink.gui.R
import com.dis.logolink.parser.LevelDto
import com.dis.logolink.parser.Parser
import kotlinx.android.synthetic.main.activity_level1.*
import kotlin.random.Random

// UPDATE: Dynamically coded level for testing purposes.
// Only inputs are created dynamically.
class Level1Activity : AppCompatActivity() {

    var level1 = Level(LevelDto(mutableListOf(), mutableListOf()))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level1)
        playSound()

        val levelName="level2" // use level name or number with "level"+levelNo
        createInputs(levelName) // very scuffed but proof of concept. Creates inputs to click on.

    }

    private fun changeLampImage(lampInput: Boolean, img: ImageButton){
        when(lampInput) {
        true -> img.setImageResource(R.drawable.lamp_on)
        false -> img.setImageResource(R.drawable.lamp_off)
        }
    }

    private fun checkSolution() {
        when(level1.result) {
            true -> level1_output.setImageResource(R.drawable.lamp_on)
            false -> level1_output.setImageResource(R.drawable.lamp_off)
        }
    }

    private fun playSound() {

        val trackList = mutableListOf(R.raw.clarity, R.raw.crack, R.raw.daddy, R.raw.fuck, R.raw.orbit, R.raw.milk)
        var index = Random.nextInt(0, trackList.size-1)
        var mMediaPlayer = MediaPlayer.create(this, trackList[index])

        mMediaPlayer.setOnCompletionListener {
            it.selectTrack(index)
            when(index>0) {
                true -> index -= 1
                false -> index = trackList.size-1
            }

            mMediaPlayer = MediaPlayer.create(this, trackList[index])
            mMediaPlayer.start()
        }

        mMediaPlayer.start()
    }

    private fun createInputs(levelName: String) {

        val level1Dto = Parser().parse(applicationContext, "$levelName.yml")
        this.level1 = Level(level1Dto!!)
        level1.defaultInputList.forEachIndexed(){ index, input ->

            val lamp = ImageButton(this)
            val displayWidth = resources.displayMetrics.widthPixels

            lamp.setImageResource(R.drawable.lamp_off)
            lamp.setOnClickListener{
                changeLampImage((!input).setResult(), lamp)
                println(level1)
                checkSolution() // should be removed and replaced by changeLampImage
            }

            lamp.layoutParams = ViewGroup.LayoutParams(
                (displayWidth / 2),
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            lamp.rotation = 90/5F * index // THIS IS WHY WE CANNOT HAVE NICE THINGS!
            // in order to make all views 'clickable', rotate them to create a offset.
            // no idea how to display views side by side programmatically.
            root_layout.addView(lamp)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus){
            //Screen size
            val displayMetrics = this.resources.displayMetrics
            val screenWidth = displayMetrics.widthPixels
            val screenHeight = displayMetrics.heightPixels

            //Bitmap & Canvas
            val bitmap: Bitmap = Bitmap.createBitmap(
                screenWidth, screenHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas: Canvas = Canvas(bitmap)
            val paint: Paint = Paint().apply {
                color = getColor(R.color.black)
                strokeWidth = 10F
                strokeCap = Paint.Cap.SQUARE
            }

            level1_canvas.background = BitmapDrawable(resources, bitmap)
        }
    }
}