package com.dis.logolink.gui

import android.os.Build
import android.os.Bundle
import android.view.View
import android.os.IBinder
import android.content.Intent
import android.content.Context
import android.view.WindowManager
import androidx.core.view.ViewCompat
import android.content.ComponentName
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth
import android.content.ServiceConnection
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

    lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference

    private lateinit var soundService: SoundService
    private var soundServiceIsBound: Boolean = false

    private val connection = object : ServiceConnection {
        //create callback for Service for connect and disconnect
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as SoundService.LocalBinder
            soundService = binder.getService()
            soundServiceIsBound = true
        }
        override fun onServiceDisconnected(arg0: ComponentName) {
            soundServiceIsBound = false
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(this, SoundService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
            startService(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //full screen for diffrent devices
        when (Build.VERSION.SDK_INT) {
            in Int.MAX_VALUE..30 -> {       //consider deprecated FlagFullscreen
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

        //Get Firebase
        auth = Firebase.auth
        database = FirebaseDatabase.getInstance().reference

        //Check Google
        checkGoogle()
        checkSound()

    }

    private fun googleSignOut() {
        //TODO create Service class instead of LoginActivity
        updateProgress()
        //before sign-out check level progress
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()
        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        googleSignInClient.signOut()
        auth.signOut()
    }

    private fun checkSound() {
        if(soundServiceIsBound) {
            when(soundService.player.isPlaying) {
                true -> btn_sound.setImageResource(R.drawable.gate_true)
                false -> btn_sound.setImageResource(R.drawable.gate_false)
            }
        }
        else {
            btn_sound.setImageResource(R.drawable.gate_false)
        }
    }

    private fun checkGoogle() {
        when (auth.currentUser != null) {
            true -> {
                btn_google.setImageResource(R.drawable.gate_true)
                updateProgress()
            }
            false -> btn_google.setImageResource(R.drawable.gate_false)
        }
    }

    private fun updateProgress() {
        //compare and update level progress saved in FireBase database to shared Preferences
        val levelPreference = getSharedPreferences("levelPref", MODE_PRIVATE)
        val highestLevelSharedPref = levelPreference.getInt("highestLevelReached", 1)
        database.child("users").child(auth.uid!!)
            .child("highestLevelReached").get()
            .addOnCompleteListener {
                val highestLevelCloud = it.result.value.toString().toInt()
                if (highestLevelCloud > highestLevelSharedPref) {
                    levelPreference.edit().apply {
                        putInt("highestLevelReached", highestLevelCloud)
                        apply()
                    }
                }
                if (highestLevelCloud < highestLevelSharedPref) {
                    database.child("users").child(auth.uid!!)
                        .child("highestLevelReached")
                        .setValue(highestLevelSharedPref)
                }
            }
    }

    override fun onResume() {
        super.onResume()
        checkGoogle()
    }

    //On click listener override
    override fun onClick(view: View?) {
        val animation = AnimationUtils.loadAnimation(this, R.anim.wiggle)
        when (view?.id) {
            R.id.btn_continue -> {
                btn_continue.startAnimation(animation)
                btn_continue.postDelayed({}, 400)
                //load LevelActivity based on highest level reached
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
                btn_levels.postDelayed({}, 400)
                startActivity(Intent(view.context, LevelsActivity::class.java))
            }
            R.id.btn_google -> {
                btn_google.startAnimation(animation)
                btn_google.postDelayed({}, 400)
                when (auth.currentUser != null) {
                    true -> googleSignOut()
                    false -> startActivity(Intent(applicationContext, SignInActivity::class.java))
                }
            }
            R.id.btn_sound -> {
                btn_sound.startAnimation(animation)
                btn_google.postDelayed({}, 400)
                if(soundServiceIsBound) {
                    soundService.changeSoundPreferences()
                    //change to different track list depending on Activity
                    checkSound()
                }
            }

        }
    }
}