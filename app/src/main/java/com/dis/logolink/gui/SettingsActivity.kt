package com.dis.logolink.gui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.preference.Preference
import kotlinx.android.synthetic.main.settings_activity.*
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SeekBarPreference
import androidx.preference.SwitchPreferenceCompat

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        ViewCompat.getWindowInsetsController(window.decorView)!!
            .hide(WindowInsetsCompat.Type.systemBars())

        val intent = Intent(applicationContext, SoundService::class.java)
        applicationContext.stopService(intent)
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.settings, SettingsFragment())
                    .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //val actionBar = supportActionBar
        //actionBar!!.title = "Settings"
    }
    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            val music: SeekBarPreference? = findPreference("music")
            val darkMode: SwitchPreferenceCompat? = findPreference("dark_mode")
            val googleSignIn: SwitchPreferenceCompat? = findPreference("google_signIn")
            googleSignIn?.setDefaultValue(false)

            music!!.setOnPreferenceClickListener {
                if(music.isEnabled) {
                    music.title = "music enabled"
                }
                //disable dark mode
                else{
                    music.title = "music disabled"
                }
                true
            }

            darkMode!!.setOnPreferenceClickListener {
                if(darkMode.isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    darkMode.summaryOn
                }
                //disable dark mode
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    darkMode.summaryOff
                }
                true
            }

            googleSignIn!!.setOnPreferenceClickListener {
                if(googleSignIn.isChecked) {
                    googleSignIn.summaryOn
                }
                //disable dark mode
                else{
                    googleSignIn.summaryOff
                }
                true
            }

        }

    }
}