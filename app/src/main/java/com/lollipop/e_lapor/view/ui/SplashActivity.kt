package com.lollipop.e_lapor.view.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.lollipop.e_lapor.databinding.ActivitySplashBinding

class SplashActivity: AppCompatActivity() {
    lateinit var _binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = _binding.root
        setContentView(view)

        _observeSplash()
    }

    private fun _observeSplash() {
        val r = Runnable {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
        Handler(Looper.getMainLooper()).postDelayed(r,2000)
    }

}