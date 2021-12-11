package com.dis.logolink.gui

import android.os.Bundle
import android.view.View
import android.content.Intent
import android.os.Build
import android.view.WindowManager
import androidx.core.view.ViewCompat
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth
import android.view.animation.AnimationUtils
import com.google.firebase.auth.FirebaseAuth
import androidx.core.view.WindowInsetsCompat
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions


class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var database: DatabaseReference
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        when (Build.VERSION.SDK_INT) {
            in Int.MAX_VALUE..30 -> {
                ViewCompat . getWindowInsetsController (window.decorView)!!
                    .hide(WindowInsetsCompat.Type.systemBars())
            }
            in Int.MAX_VALUE..29 -> window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        //Onclick listeners
        btn_continue.setOnClickListener(this)
        btn_levels.setOnClickListener(this)
        btn_google.setOnClickListener(this)
        btn_sound.setOnClickListener(this)

        //Check Cloud & lateint
        auth = Firebase.auth
        database = FirebaseDatabase.getInstance().reference

        //Check Sound
        checkSound(buttonClick = false)

        //Check Google
        checkGoogle(buttonClick = false)



    }

    private fun getProgress() {
        if(auth.currentUser != null) {

            val levelPreference = getSharedPreferences("levelPref", MODE_PRIVATE)
            val highestLevelReachedSharedPref = levelPreference.getInt("highestLevelReached", 1)

            database.child("users").child(auth.uid!!)
                .child("highestLevelReached").get()
                .addOnCompleteListener{ task ->
                    val highestLevelReachedCloud = task.result.value.toString().toInt()
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
    }

    private fun checkSound(buttonClick: Boolean=true) {
        val settingsPreferences = getSharedPreferences("settingsPref", MODE_PRIVATE)
        var soundPreferences = settingsPreferences.getBoolean("sound", true)

        if(buttonClick) {
            settingsPreferences.edit().apply {
                putBoolean("sound", !soundPreferences)
                apply()
            }
            soundPreferences = !soundPreferences
        }

        if (soundPreferences) {
            val intent = Intent(this, SoundService::class.java)
            btn_sound.setImageResource(R.drawable.gate_false)
            stopService(intent)
        }
        else {
            val intent = Intent(this, SoundService::class.java)
            btn_sound.setImageResource(R.drawable.gate_true)
            startService(intent)
        }
    }

    private fun checkGoogle(buttonClick: Boolean=true) {
        when((auth.currentUser!=null) and (buttonClick)) {
            true -> {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()
                val googleSignInClient = GoogleSignIn.getClient(this, gso)

                googleSignInClient.signOut()
                auth.signOut()
            }
            false -> {
                if(buttonClick) {
                    startActivity(Intent(applicationContext, SignInActivity::class.java))
                }
            }
        }
        getProgress()
        if(auth.currentUser!=null) {
            btn_google.setImageResource(R.drawable.gate_true)
        }
        else {
            btn_google.setImageResource(R.drawable.gate_false)
        }
    }

    //On click listener override
    override fun onClick(view: View?) {
        val animation = AnimationUtils.loadAnimation(this, R.anim.wiggle)
        when (view?.id) {

            R.id.btn_continue -> {
                btn_continue.startAnimation(animation)
                btn_continue.postDelayed({}, 200)

                startActivity(
                    Intent(view.context, LevelActivity::class.java)
                        .putExtra("levelname",
                            getSharedPreferences("levelPref", MODE_PRIVATE)
                                .getInt("highestLevelReached", 1)
                        )
                )
            }

            R.id.btn_levels -> {
                btn_levels.startAnimation(animation)
                btn_levels.postDelayed({}, 200)

                startActivity(Intent(view.context, LevelsActivity::class.java))
            }

            R.id.btn_google -> {
                btn_google.startAnimation(animation)
                btn_google.postDelayed({}, 200)
                checkGoogle()
            }

            R.id.btn_sound -> {

                btn_sound.startAnimation(animation)
                btn_google.postDelayed({}, 200)
                checkSound()
            }

        }
    }
}