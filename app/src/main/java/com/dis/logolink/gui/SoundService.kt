package com.dis.logolink.gui

import android.app.Activity
import android.os.Binder
import android.os.IBinder
import android.app.Service
import kotlin.random.Random
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_main.*

class SoundService() : Service() {

    lateinit var player: MediaPlayer
    lateinit var activity: Activity
    private val binder = LocalBinder()

    val menuTrackList = listOf(R.raw.freedom,
        R.raw.orbit,
        R.raw.electric,
        R.raw.clarity,
        R.raw.fuck)
    //val gameTrackList = listOf(R.raw.clarity, R.raw.crack, R.raw.fuck, R.raw.milk)

    lateinit var settingsPreferences : SharedPreferences

    override fun onCreate() {
        super.onCreate()
        settingsPreferences = application.getSharedPreferences("settingsPref", MODE_PRIVATE)
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    inner class LocalBinder : Binder() {
        fun getService(): SoundService = this@SoundService
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        createMediaPlayer(menuTrackList)
        if (settingsPreferences.getBoolean("sound", true)) {
            player.start()
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        player.stop()
    }

    private fun createMediaPlayer(trackList: List<Int>) {
        val nextTrackNumber = Random.nextInt(0, trackList.size)
        player = MediaPlayer.create(this, trackList[nextTrackNumber])
        player.setOnCompletionListener {
            createMediaPlayer(trackList)
        }
    }

    fun changeSoundPreferences() {
        settingsPreferences.edit().apply {
            putBoolean("sound", !settingsPreferences.getBoolean("sound", true))
            apply()
        }
        when (settingsPreferences.getBoolean("sound", true)) {
            true -> player.start()
            false -> {
                player.stop()
                createMediaPlayer(menuTrackList)
            }
        }
    }
}