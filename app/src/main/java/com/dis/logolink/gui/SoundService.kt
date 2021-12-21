package com.dis.logolink.gui


import android.os.Binder
import android.os.IBinder
import android.app.Service
import kotlin.random.Random
import android.content.Intent
import android.media.MediaPlayer
import android.content.SharedPreferences
import kotlinx.android.synthetic.main.activity_main.*

class SoundService() : Service() {

    lateinit var player: MediaPlayer
    private val binder = LocalBinder()

    private val menuTrackList = listOf(
        R.raw.freedom,
        R.raw.orbit,
        R.raw.electric,
        R.raw.clarity,
        R.raw.fuck
    )

    // val gameTrackList = listOf(R.raw.clarity, R.raw.crack, R.raw.fuck, R.raw.milk)
    //TODO change to different track list depending on Activity. Almost done.

    lateinit var settingsPreferences : SharedPreferences

    override fun onCreate() {
        super.onCreate()
        settingsPreferences = application.getSharedPreferences("settingsPref", MODE_PRIVATE)
        //check last set sound settings
        //TODO add to FireBase to sync over devices
        createMediaPlayer(menuTrackList)
        if (settingsPreferences.getBoolean("sound", true)) {
            player.start()
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    inner class LocalBinder : Binder() {
        fun getService(): SoundService = this@SoundService
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
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
        //TODO instead of random implement 'lastSongPlayed' to avoid playing the same song twice
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