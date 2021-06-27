package com.example.linuxapp.controller.notif

import android.app.Service
import android.content.Intent

import android.os.IBinder
import android.util.Log


class YourService: Service() {
    var reciever: NotificationBroadcastReceiver = NotificationBroadcastReceiver()
    val TAG = "TAG"

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        reciever.setAlarm(applicationContext)
        Log.e("TAG", "onStart: YourService")

        return START_STICKY
    }

    override fun onStart(intent: Intent?, startId: Int) {
        reciever.setAlarm(applicationContext)
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.e("TAG", "onBind: YourService")
        return null
    }
//
//
//    fun SetAlarm(context: Context) {
//        Log.e(TAG, "SetAlarm: on BR")
//        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val i = Intent(context, NotificationBroadcastReceiver::class.java)
//        val pi = PendingIntent.getBroadcast(context, 0, i, 0)
//
//        val c: Calendar = Calendar.getInstance()
//
//        am.setExact(AlarmManager.RTC_WAKEUP, c.timeInMillis + (1000*20), pi)
//    }
//
//    fun CancelAlarm(context: Context) {
//        val intent = Intent(context, javaClass)
//        val sender = PendingIntent.getBroadcast(context, 0, intent, 0)
//        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        alarmManager.cancel(sender)
//    }

}