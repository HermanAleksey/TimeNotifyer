package com.example.linuxapp.controller.notif

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.util.Log
import java.util.*


class NotificationBroadcastReceiver : BroadcastReceiver() {
    val TAG = "TAG"

    @SuppressLint("InvalidWakeLockTag")
    override fun onReceive(context: Context, intent: Intent?) {
        Log.e("TAG", "onReceive: NotificationBroadcastReceiver")

        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "")
        wl.acquire(1*60*1000L /*1 minute*/)

        NotificationHelper().sendNotification(context)
        setAlarm(context)

        wl.release()
    }


    fun setAlarm(context: Context) {
        Log.e(TAG, "SetAlarm: on BR")
        val intent = Intent(context, NotificationBroadcastReceiver::class.java)
        val sender = PendingIntent.getBroadcast(context, 0, intent, 0)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val c: Calendar = Calendar.getInstance()

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.timeInMillis + (1000*20), sender)
    }

    fun cancelAlarm(context: Context) {
        val intent = Intent(context, NotificationBroadcastReceiver::class.java)
        val sender = PendingIntent.getBroadcast(context, 0, intent, 0)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.cancel(sender)
    }

}