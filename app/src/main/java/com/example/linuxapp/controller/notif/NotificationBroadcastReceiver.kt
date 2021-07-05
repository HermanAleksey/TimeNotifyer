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
import com.example.linuxapp.Constant.NotificationConstants


class NotificationBroadcastReceiver : BroadcastReceiver() {
    val TAG = "TAG"

    @SuppressLint("InvalidWakeLockTag")
    override fun onReceive(context: Context, intent: Intent?) {

        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "")
        wl.acquire(1 * 60 * 1000L /*1 minute*/)

        NotificationHelper().sendNotification(context)

        //get current time
        val millisCurrentTime = Calendar.getInstance().timeInMillis
        val millisInDay = 24 * 60 * 60 * 1000
        val millisCurrentTimeOfDay = millisCurrentTime % millisInDay
        //shared preferences
        val sp = context.getSharedPreferences("Prefs", Context.MODE_PRIVATE)
        val receiveAtNight = sp.getBoolean(NotificationConstants.RECEIVE_AT_NIGHT.value, false)

        //period value; default = 60 min
        val minNotificationPeriod =
            sp.getInt(NotificationConstants.NOTIFICATION_PERIOD.value, 60)
        val millisPeriod = minNotificationPeriod * 60 * 1000

        //night time start time; default = 8:00
        val minNightStart = sp.getInt(NotificationConstants.NIGHT_TIME_START.value, 8 * 60)
        val millisNightStart = minNightStart * 60 * 1000

        //night time end time; default = 24:00
        val minNightEnd = sp.getInt(NotificationConstants.NIGHT_TIME_END.value, 24 * 60)
        val millisNightEnd = minNightEnd * 60 * 1000

        Log.e(
            "TAG", """onReceive: NotificationBroadcastReceiver
            |setting new Alarm.
            |millisPeriod = $millisPeriod             minNotificationPeriod = $minNotificationPeriod
            |millisCurrentTime = $millisCurrentTime ;;;;       currentTime Min =${millisCurrentTime / 1000 / 60}
            |minNightStart = $minNightStart             millisNightStart = $millisNightStart
            |minNightEnd = $minNightEnd             millisNightEnd = $millisNightEnd
            |millisCurrentTimeOfDay = $millisCurrentTimeOfDay
        """.trimMargin()
        )

        //set Alarm
        val expectedTimeNextAlarm = (millisCurrentTimeOfDay + millisPeriod) % (24 * 60 * 60 * 1000)
        if (!receiveAtNight && isNightTime(expectedTimeNextAlarm, millisNightStart, millisNightEnd)) {
            //если пользователь не хочет получать уведомления в ночное время и
            //сейчас ночное время - тогда перенести будильник на время конца ночного режима
            if (millisCurrentTime > millisNightEnd) {
                //сегодня время "конца ночи" уже прошло - значит переходим на след. день
                setAlarm(
                    context,
                    millisCurrentTime - millisCurrentTimeOfDay + (24 * 60 * 60 * 1000) + millisNightEnd
                )
            } else {
                //если время "уонца ночи" ещё не прошло сегодня - просто установить на него
                setAlarm(context, millisCurrentTime-millisCurrentTimeOfDay+millisNightEnd)
            }
        } else {
            //иначе установить будильник через заданный промежуток
            setAlarm(context, millisCurrentTime + millisPeriod)
        }

        wl.release()
    }

    /*Метод проверят, входит ли millisTime в промежуток времени от
    * millisNightStart до millisNightEnd.
    * Значения должны быть реальными (от 0 до 24 часов)
    * */
    private fun isNightTime(
        millisTime: Long,
        millisNightStart: Int,
        millisNightEnd: Int
    ): Boolean {
        return if (millisNightStart > millisNightEnd) {
            //промежуток сна - "с", дня - "_". |сссс_______сссс|
            !(millisTime > millisNightEnd && millisTime < millisNightStart)
        } else {
            //if (millisNightStart<millisNightEnd)
            //  |____ccccccc_____|
            !(millisTime < millisNightStart || millisTime > millisNightEnd)
        }
    }

    fun setAlarm(context: Context, time: Long) {
        Log.e(TAG, "SetAlarm: on BR")
        val intent = Intent(context, NotificationBroadcastReceiver::class.java)
        val sender = PendingIntent.getBroadcast(context, 0, intent, 0)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, sender)
    }

    fun cancelAlarm(context: Context) {
        Log.e(TAG, "cancelAlarm: on BR")
        val intent = Intent(context, NotificationBroadcastReceiver::class.java)
        val sender = PendingIntent.getBroadcast(context, 0, intent, 0)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.cancel(sender)
    }

}