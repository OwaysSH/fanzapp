package com.fanz.oways.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.fanz.oways.databinding.ActivityFanzMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FanzMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFanzMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { false }

        binding = ActivityFanzMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}