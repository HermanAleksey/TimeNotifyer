package com.example.linuxapp.ui

import android.content.*
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.linuxapp.Constant.BroadcastConstants
import com.example.linuxapp.Constant.TimeConstants
import com.example.linuxapp.R
import com.example.linuxapp.databinding.ActivityMainBinding
import com.example.linuxapp.controller.notif.NotificationBroadcastReceiver
import com.example.linuxapp.controller.notif.NotificationHelper
import com.example.linuxapp.controller.notif.YourService
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val CHANNEL_ID = "CHANNEL_ID"
    private val NOTIFICATION_ID = 1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfig = AppBarConfiguration(setOf(
            R.id.navigation_home,R.id.navigation_statistics,R.id.navigation_notification
        ))
        setupActionBarWithNavController(navController, appBarConfig)
        binding.navViewMainActivity.setupWithNavController(navController)


        NotificationHelper().createNotificationChannel(this)

//        viewJob()
//        configureNumberPickerPanel()
//        binding.btnSetAlarm.setOnClickListener {
//            startService(Intent(this,YourService::class.java))
//        }
//        binding.btnRemoveAlarm.setOnClickListener {
//            NotificationBroadcastReceiver().cancelAlarm(this)
//        }
    }

//    private fun configureNumberPickerPanel() {
//        //Number picker
//        val numbersArray = IntArray(10) { i -> (i + 1) * 30 }
//        binding.dialogNumberPicker.minValue = 1
//        binding.dialogNumberPicker.maxValue = numbersArray.size
//        binding.dialogNumberPicker.displayedValues =
//            numbersArray.map { it.toString() }.toTypedArray()
//        binding.dialogNumberPicker.wrapSelectorWheel = true
//        binding.dialogNumberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
//            val text = "Changed to ${newVal * 30}"
//            Toast.makeText(this@MainActivity, text, Toast.LENGTH_SHORT).show()
//        }
//        binding.btnMainSetNotifPeriod.setOnClickListener {
//            val currentNumber = binding.dialogNumberPicker.value * 30
//            val pref = getSharedPreferences(BroadcastConstants.Storage.value, Context.MODE_PRIVATE)
//            pref.edit().putInt(TimeConstants.TimeInterval.value, currentNumber).apply()
//            binding.tvMainSetNotifPeriod.text = "$currentNumber min"
//        }
//    }



    //    fun viewJob() {
//        val tv_var1_count = findViewById<TextView>(R.id.tv_var1_count)
//        val tv_var2_count = findViewById<TextView>(R.id.tv_var2_count)
//        val tv_var3_count = findViewById<TextView>(R.id.tv_var3_count)
//
//        val pref = getSharedPreferences(BroadcastConstants.Storage.value, Context.MODE_PRIVATE)
//        val firstVarVal = pref.getInt(BroadcastConstants.FirstButton.value, 0)
//        tv_var1_count.text = firstVarVal.toString()
//        val secondVarVal = pref.getInt(BroadcastConstants.SecondButton.value, 0)
//        tv_var2_count.text = secondVarVal.toString()
//        val thirdVarVal = pref.getInt(BroadcastConstants.ThirdButton.value, 0)
//        tv_var3_count.text = thirdVarVal.toString()
//
//        val btn_clear = findViewById<Button>(R.id.btn_clear_vars)
//        btn_clear.setOnClickListener {
//            val editor = pref.edit()
//            editor.putInt(BroadcastConstants.FirstButton.value, 0)
//            editor.putInt(BroadcastConstants.SecondButton.value, 0)
//            editor.putInt(BroadcastConstants.ThirdButton.value, 0)
//            editor.apply()
//        }
//    }
}