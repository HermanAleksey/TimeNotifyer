package com.example.linuxapp.controller.notif

import android.app.Service
import android.content.Intent

import android.os.IBinder
import android.util.Log
import java.util.*


class YourService : Service() {
    var reciever: NotificationBroadcastReceiver = NotificationBroadcastReceiver()
    val TAG = "TAG"

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val millisCurrentTime = Calendar.getInstance().timeInMillis
        //first notification will be received after 1 minute
        reciever.setAlarm(applicationContext, millisCurrentTime+1000)
        Log.e("TAG", "onStart: YourService")

        stopSelf()
        return START_STICKY
    }

//    override fun onStart(intent: Intent?, startId: Int) {
////        val millisCurrentTime = Calendar.getInstance().timeInMillis
////        //first notification will be received after 1 minute
////        reciever.setAlarm(applicationContext, millisCurrentTime + 1000)
//    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.e("TAG", "onBind: YourService")
        return null
    }

    override fun onDestroy() {
        Log.e(TAG, "\nonDestroy: \n")
        super.onDestroy()
    }
}