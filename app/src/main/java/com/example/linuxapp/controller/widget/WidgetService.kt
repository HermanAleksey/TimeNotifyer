package com.example.linuxapp.controller.widget

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log


class WidgetService: Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("TAG", "onStartCommand: Service started")

        return START_STICKY;
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.e("TAG", "onBind: Service started")
        TODO("Not yet implemented")
    }
}