package com.dis.logolink.gui

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class SoundService : Service() {

    private lateinit var player: MediaPlayer

    override fun onBind(arg0: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        player = MediaPlayer.create(this, R.raw.orbit)
        player.setVolume(100f, 100f) // saved settings?
        player.isLooping = true

    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        player.start()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        player.stop()
    }
}