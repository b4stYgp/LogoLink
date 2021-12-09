package com.dis.logolink.gui

import android.os.Bundle
import android.view.View
import android.content.Intent
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.view.animation.AnimationUtils
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var database: DatabaseReference
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewCompat.getWindowInsetsController(window.decorView)!!
            .hide(WindowInsetsCompat.Type.systemBars())

        //Onclick listeners
        btn_continue.setOnClickListener(this)
        btn_levels.setOnClickListener(this)
        btn_google.setOnClickListener(this)
        btn_sound.setOnClickListener(this)

        //Start Services
        val settingsPreferences = getSharedPreferences("settingsPref", MODE_PRIVATE)
        if (settingsPreferences.getBoolean("sound", true)) {
            val intent = Intent(this, SoundService::class.java)
            startService(intent)
        }

        //Check Cloud
        auth = Firebase.auth
        database = FirebaseDatabase.getInstance().reference
        getProgress()
    }

    private fun getProgress() {
        if(auth.currentUser != null) {

            val levelPreference = getSharedPreferences("levelPref", MODE_PRIVATE)
            val highestLevelReachedSharedPref = levelPreference.getInt("highestLevelReached", 1)
            var highestLevelReachedCloud = 0

            database.child("users").child(auth.uid!!)
                .child("highestLevelReached").get()
                .addOnCompleteListener{ task ->
                    highestLevelReachedCloud = task.result.value?.toString()?.toInt()?:0
                }

            if (highestLevelReachedCloud > highestLevelReachedSharedPref) {
                levelPreference.edit().apply {
                    putInt("highestLevelReached", highestLevelReachedCloud)
                    apply()
                }
            }

            if (highestLevelReachedCloud < highestLevelReachedSharedPref) {
                database.child("users").child(auth.uid!!)
                    .child("highestLevelReached")
                    .setValue(highestLevelReachedSharedPref)
            }
            println("$highestLevelReachedCloud,$highestLevelReachedSharedPref")
        }
    }

    //On click listener override
    override fun onClick(view: View?): Unit {
        val animation = AnimationUtils.loadAnimation(this, R.anim.wiggle)
        when (view?.id) {

            R.id.btn_continue -> {
                btn_continue.startAnimation(animation)
                btn_continue.postDelayed({}, 350)

                startActivity(
                    Intent(view.context, LevelActivity::class.java)
                        .putExtra("level",
                            getSharedPreferences("levelPref", MODE_PRIVATE)
                                .getInt("highestLevelReached", 1)
                        )
                )
            }

            R.id.btn_levels -> {
                btn_levels.startAnimation(animation)
                btn_levels.postDelayed({}, 350)

                startActivity(Intent(view.context, LevelsActivity::class.java))
            }

            R.id.btn_google -> {
                btn_google.startAnimation(animation)
                btn_google.postDelayed({}, 350)

                when(Firebase.auth.currentUser!=null) {
                    true -> {

                        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(getString(R.string.default_web_client_id))
                            .requestEmail()
                            .build()
                        val googleSignInClient = GoogleSignIn.getClient(this, gso)

                        googleSignInClient.signOut()
                        Firebase.auth.signOut()
                    }
                    false -> startActivity(Intent(applicationContext, SignInActivity::class.java))
                }
            }

            R.id.btn_sound -> {

                btn_sound.startAnimation(animation)
                btn_google.postDelayed({}, 350)

                val settingsPreferences = getSharedPreferences("settingsPref", MODE_PRIVATE)
                val soundPreferences = settingsPreferences.getBoolean("sound", true)
                settingsPreferences.edit().apply {
                    putBoolean("sound", !soundPreferences)
                    apply()
                }
                if (soundPreferences) {
                    val intent = Intent(this, SoundService::class.java)
                    stopService(intent)
                }
                else {
                    val intent = Intent(this, SoundService::class.java)
                    startService(intent)
                }
            }

        }
    }
}