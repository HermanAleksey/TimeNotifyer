package com.example.linuxapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.linuxapp.R
import com.example.linuxapp.controller.notif.NotificationHelper
import com.example.linuxapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfig = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_statistics, R.id.navigation_notification
            )
        )
        setupActionBarWithNavController(navController, appBarConfig)
        binding.navViewMainActivity.setupWithNavController(navController)


        NotificationHelper().createNotificationChannel(this)
    }
}