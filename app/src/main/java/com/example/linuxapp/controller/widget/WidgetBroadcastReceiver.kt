package com.example.linuxapp.controller.widget;

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.linuxapp.Constant
import com.example.linuxapp.Constant.BroadcastConstants


class WidgetBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.e("TAG", "onReceive:WidgetBroadcastReceiver:${intent.action}")

        val pref = context.getSharedPreferences(BroadcastConstants.PREFERENCES.value, Context.MODE_PRIVATE)
        val editor = pref.edit()

        val time = System.currentTimeMillis()
        editor.putLong(Constant.TimeConstants.TimeLastUpdate.value,time)

        
        if (intent.action == BroadcastConstants.ERROR.value) {
            Log.e("TAG", "onReceive: intent action: ${intent.action}")
            return
        }

        BroadcastConstants.values().forEach { const ->
            if (intent.action == const.value) {
                editor.putInt(
                    const.value,
                    pref.getInt(const.value, 0) + 1
                )
            }
        }



        editor.apply()
    }


}
