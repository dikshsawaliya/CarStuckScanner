package com.diksh.qrcarscanner

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private val SPLASH_DISPLAY_LENGTH = 2000 // 2 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            // Start MainActivity and close this activity
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, SPLASH_DISPLAY_LENGTH.toLong())
    }
}
